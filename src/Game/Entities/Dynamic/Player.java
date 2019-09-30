package Game.Entities.Dynamic;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.Random;
import Game.Entities.Dynamic.Player2;
import Main.Handler;

/**
 * Created by AlexVR on 7/2/2018.
 */
public class Player {

	public int lenght;
	public boolean justAte;
	public static boolean dead;
	private Handler handler;

	public static int xCoord;
	public static int yCoord;
	public static double score;
	public int moveCounter;
	public int speed;
	public int steps;
	public String direction;//is your first name one?

	public Player(Handler handler){
		this.handler = handler;
		xCoord = 2;
		yCoord = 2;
		speed = 5;
		moveCounter = 0;
		direction = "Right";
		justAte = false;
		dead = false;
		lenght = 0; 
		score = 0;
		steps = 0;
	}

	public void tick(){
		moveCounter++;
		if(moveCounter>=speed) {
			checkCollisionAndMove();
			moveCounter=0;
		}	

		// Added W,S,D,A in order to control the snake.
		if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_W)){
			if(!(direction == "Down")) {
				direction="Up";
			}
		}
		if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_S)){
			if(!(direction == "Up")) {
				direction="Down";
			}
		}
		if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_A)){
			if(!(direction == "Right")) {
				direction="Left"; 
			}
		}
		if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_D)) {
			if(!(direction == "Left")) {
				direction="Right";
			}
		}
		//Button added (N).
		if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_N)){
			handler.getWorld().body.addFirst(new Tail(xCoord, yCoord,handler));
		}
		//Button added (+) Speed boost.
		if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_EQUALS)){
			speed--;
		}
		//Button added (-) Speed reduced
		if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_MINUS)){
			speed++;
		}
		//Checking the state of the apple.
		if(Main.GameSetUp.multiplayer == false) {
			if (steps >= handler.getWorld().GridWidthHeightPixelCount * 2) {
				handler.getWorld().getApple().setIsGood(false);
			}
		}
	}

	public void checkCollisionAndMove(){
		steps++;			
		handler.getWorld().playerLocation[xCoord][yCoord]=false;
		int x = xCoord;
		int y = yCoord;
		switch (direction){
		case "Left":
			if(xCoord==0){
				handler.getWorld().player.xCoord = handler.getWorld().GridWidthHeightPixelCount - 1;
			}else {
				xCoord--;
			}
			break;
		case "Right":
			if(xCoord==handler.getWorld().GridWidthHeightPixelCount-1){
				handler.getWorld().player.xCoord = 0;
			}else{
				xCoord++;
			}
			break;
		case "Up":
			if(yCoord==0){
				handler.getWorld().player.yCoord = handler.getWorld().GridWidthHeightPixelCount - 1;
			}else{
				yCoord--;
			}
			break;
		case "Down":
			if(yCoord==handler.getWorld().GridWidthHeightPixelCount-1){
				handler.getWorld().player.yCoord = 0;
			}else{
				yCoord++;
			}
			break;
		}

		handler.getWorld().playerLocation[xCoord][yCoord]=true;

		if(handler.getWorld().appleLocation[xCoord][yCoord]){
			Eat();
		}
		//Eats the enemies food.
		if(Main.GameSetUp.multiplayer == true) {
			if(handler.getWorld().appleLocation1[xCoord][yCoord]){
				Eat();
			}
		}
		//Death of the single player and multiplayer.
		if(Main.GameSetUp.multiplayer == false) {
			for (int i = 0; i < handler.getWorld().body.size(); i++) {
				if(handler.getWorld().player.xCoord == handler.getWorld().body.get(i).x &&
						handler.getWorld().player.yCoord == handler.getWorld().body.get(i).y) {
					dead = true;
				}
			}
		}else if(Main.GameSetUp.multiplayer == true){
			for (int i = 0; i < handler.getWorld().body.size(); i++) {
				if(handler.getWorld().player.xCoord == handler.getWorld().body.get(i).x &&
						handler.getWorld().player.yCoord == handler.getWorld().body.get(i).y) {

					Main.GameSetUp.winner2 = true;
				}
				if(handler.getWorld().player2.Xcoord1 == handler.getWorld().body.get(i).x &&
						handler.getWorld().player2.ycoord1 == handler.getWorld().body.get(i).y) {

					Main.GameSetUp.winner1 = true;
				}
			}
		}

		if(!handler.getWorld().body.isEmpty()) {
			handler.getWorld().playerLocation[handler.getWorld().body.getLast().x][handler.getWorld().body.getLast().y] = false;
			handler.getWorld().body.removeLast();
			handler.getWorld().body.addFirst(new Tail(x, y,handler));
		}
	}

	public static int snake = new Color(0,255,0).getRGB();
	public static int apple = new Color(255,0,0).getRGB();
	public static int rotten = new Color(128,0,0).getRGB();

	public void render(Graphics g,Boolean[][] playerLocation){
		Random r = new Random();
		for (int i = 0; i < handler.getWorld().GridWidthHeightPixelCount ; i++) {
			for (int j = 0; j < handler.getWorld().GridWidthHeightPixelCount ; j++) {

				g.setColor(new Color(snake));
				if(playerLocation[i][j]||handler.getWorld().appleLocation[i][j]){
					g.fillRect((i*handler.getWorld().GridPixelsize),
							(j*handler.getWorld().GridPixelsize),
							handler.getWorld().GridPixelsize,
							handler.getWorld().GridPixelsize);
				}
				if(handler.getWorld().getApple().isGood() == true){
					if(handler.getWorld().appleLocation[i][j]) {
						g.setColor(new Color(apple));
						g.fillRect((i*handler.getWorld().GridPixelsize),
								(j*handler.getWorld().GridPixelsize),
								handler.getWorld().GridPixelsize,
								handler.getWorld().GridPixelsize);
					}
				}
				if(Main.GameSetUp.multiplayer == false) {
					if(handler.getWorld().getApple().isGood() == false) {
						g.setColor(new Color(rotten));
						if(handler.getWorld().appleLocation[i][j]) {
							g.fillRect((i*handler.getWorld().GridPixelsize),
									(j*handler.getWorld().GridPixelsize),
									handler.getWorld().GridPixelsize,
									handler.getWorld().GridPixelsize);
						}
					}
				}
			}
		}
	}

	public double score() {
		justAte = false;
		if(handler.getWorld().getApple().isGood() == false) {
			score -= Math.sqrt(2*score+1);
		}
		if(handler.getWorld().getApple().isGood() == true) {
			score +=  Math.sqrt(2*score+1);
		}
		if(score < 0) {
			score = 0;
		}
		return score;
	}

	public void Eat(){
		lenght++;
		if(Main.GameSetUp.multiplayer == false) {
			score();
		}else {
			handler.getWorld().getApple().setIsGood(true);
		}
		justAte = true;
		steps=0;

		Tail tail= null;
		handler.getWorld().appleLocation[xCoord][yCoord]=false;
		handler.getWorld().appleOnBoard=false;
		switch (direction){
		case "Left":
			if( handler.getWorld().body.isEmpty()){
				if(this.xCoord!=handler.getWorld().GridWidthHeightPixelCount-1){
					tail = new Tail(this.xCoord+1,this.yCoord,handler);
				}else{
					if(this.yCoord!=0){
						tail = new Tail(this.xCoord,this.yCoord-1,handler);
					}else{
						tail =new Tail(this.xCoord,this.yCoord+1,handler);
					}
				}
			}else{
				if(handler.getWorld().body.getLast().x!=handler.getWorld().GridWidthHeightPixelCount-1){
					tail=new Tail(handler.getWorld().body.getLast().x+1,this.yCoord,handler);
				}else{
					if(handler.getWorld().body.getLast().y!=0){
						tail=new Tail(handler.getWorld().body.getLast().x,this.yCoord-1,handler);
					}else{
						tail=new Tail(handler.getWorld().body.getLast().x,this.yCoord+1,handler);

					}
				}
			}
			break;
		case "Right":
			if( handler.getWorld().body.isEmpty()){
				if(this.xCoord!=0){
					tail=new Tail(this.xCoord-1,this.yCoord,handler);
				}else{
					if(this.yCoord!=0){
						tail=new Tail(this.xCoord,this.yCoord-1,handler);
					}else{
						tail=new Tail(this.xCoord,this.yCoord+1,handler);
					}
				}
			}else{
				if(handler.getWorld().body.getLast().x!=0){
					tail=(new Tail(handler.getWorld().body.getLast().x-1,this.yCoord,handler));
				}else{
					if(handler.getWorld().body.getLast().y!=0){
						tail=(new Tail(handler.getWorld().body.getLast().x,this.yCoord-1,handler));
					}else{
						tail=(new Tail(handler.getWorld().body.getLast().x,this.yCoord+1,handler));
					}
				}
			}
			break;
		case "Up":
			if( handler.getWorld().body.isEmpty()){
				if(this.yCoord!=handler.getWorld().GridWidthHeightPixelCount-1){
					tail=(new Tail(this.xCoord,this.yCoord+1,handler));
				}else{
					if(this.xCoord!=0){
						tail=(new Tail(this.xCoord-1,this.yCoord,handler));
					}else{
						tail=(new Tail(this.xCoord+1,this.yCoord,handler));
					}
				}
			}else{
				if(handler.getWorld().body.getLast().y!=handler.getWorld().GridWidthHeightPixelCount-1){
					tail=(new Tail(handler.getWorld().body.getLast().x,this.yCoord+1,handler));
				}else{
					if(handler.getWorld().body.getLast().x!=0){
						tail=(new Tail(handler.getWorld().body.getLast().x-1,this.yCoord,handler));
					}else{
						tail=(new Tail(handler.getWorld().body.getLast().x+1,this.yCoord,handler));
					}
				}
			}
			break;
		case "Down":
			if( handler.getWorld().body.isEmpty()){
				if(this.yCoord!=0){
					tail=(new Tail(this.xCoord,this.yCoord-1,handler));

				}else{
					if(this.xCoord!=0){
						tail=(new Tail(this.xCoord-1,this.yCoord,handler));
					}else{
						tail=(new Tail(this.xCoord+1,this.yCoord,handler));
					} System.out.println("Tu biscochito");
				}
			}else{
				if(handler.getWorld().body.getLast().y!=0){
					tail=(new Tail(handler.getWorld().body.getLast().x,this.yCoord-1,handler));
				}else{
					if(handler.getWorld().body.getLast().x!=0){
						tail=(new Tail(handler.getWorld().body.getLast().x-1,this.yCoord,handler));
					}else{
						tail=(new Tail(handler.getWorld().body.getLast().x+1,this.yCoord,handler));
					}
				}

			}
			break;
		}
		if(handler.getWorld().getApple().isGood() == true){
			speed = speed - 1;
			handler.getWorld().body.addLast(tail);
			handler.getWorld().playerLocation[tail.x][tail.y] = true;
		}
		if(Main.GameSetUp.multiplayer == false) {
			if(handler.getWorld().getApple().isGood() == false) {
				speed = speed + 1;
				if(!handler.getWorld().body.isEmpty()){
					handler.getWorld().playerLocation[handler.getWorld().body.getLast().x][handler.getWorld().body.getLast().y] = false;
					handler.getWorld().body.removeLast();
					kill();
				}
				else {
					kill();
				}
			}
		}
	}

	public void kill(){
		lenght = 0;
		for (int i = 0; i < handler.getWorld().GridWidthHeightPixelCount; i++) {
			for (int j = 0; j < handler.getWorld().GridWidthHeightPixelCount; j++) {
				handler.getWorld().playerLocation[i][j]=false;
			}
		}
	}

	public boolean isJustAte() {
		return justAte;
	}

	public void setJustAte(boolean justAte) {
		this.justAte = justAte;
	}
}
