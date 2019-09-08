package Game.Entities.Dynamic;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.Random;

import Main.Handler;

/**
 * Created by AlexVR on 7/2/2018.
 */
public class Player2 {

	public int lenght;
	public boolean justAte;
	public static boolean dead;
	private Handler handler;

	public int xCoord;
	public int yCoord;
	public static double score;
	public int moveCounter;
	public int speed;
	public int steps;
	public String direction;//is your first name one?

	public Player2(Handler handler){
		this.handler = handler;
		xCoord = 50;
		yCoord = 2;
		speed = 20;
		moveCounter = 0;
		direction = "Left";
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
		if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_UP) ){
			if(!(direction == "Down")) {
				direction="Up";
			}
		}
		if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_DOWN)){
			if(!(direction == "Up")) {
				direction="Down";
			}
		}
		if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_LEFT)){
			if(!(direction == "Right")) {
				direction="Left";
			}
		}
		if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_RIGHT)) {
			if(!(direction == "Left")) {
				direction="Right";
			}
		}
		//Button added (N).
		if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_N)){
			handler.getWorld().body1.addFirst(new Tail2(xCoord, yCoord,handler));
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
		if (steps >= handler.getWorld().GridWidthHeightPixelCount) {
			handler.getWorld().getApple().setIsGood(false);
		}
	}

	public void checkCollisionAndMove(){
		steps++;			
		handler.getWorld().playerLocation1[xCoord][yCoord]=false;
		int x = xCoord;
		int y = yCoord;
		switch (direction){
		case "Left":
			if(xCoord==0){
				handler.getWorld().player2.xCoord = handler.getWorld().GridWidthHeightPixelCount - 1;
			}else {
				xCoord--;
			}
			break;
		case "Right":
			if(xCoord==handler.getWorld().GridWidthHeightPixelCount-1){
				handler.getWorld().player2.xCoord = 0;
			}else{
				xCoord++;
			}
			break;
		case "Up":
			if(yCoord==0){
				handler.getWorld().player2.yCoord = handler.getWorld().GridWidthHeightPixelCount - 1;
			}else{
				yCoord--;
			}
			break;
		case "Down":
			if(yCoord==handler.getWorld().GridWidthHeightPixelCount-1){
				handler.getWorld().player2.yCoord = 0;
			}else{
				yCoord++;
			}
			break;
		}

		handler.getWorld().playerLocation1[xCoord][yCoord]=true;

		if(handler.getWorld().appleLocation1[xCoord][yCoord]){
			Eat();
		}

		if(handler.getWorld().appleLocation[xCoord][yCoord]){
			Eat();
		}
		
		if(Main.GameSetUp.multiplayer == false) {
		for (int i = 0; i < handler.getWorld().body.size(); i++) {
			if(handler.getWorld().player.xCoord == handler.getWorld().body.get(i).x &&
					handler.getWorld().player.yCoord == handler.getWorld().body.get(i).y) {
				dead = true;
			}
		}
		}else {
			for (int i = 0; i < handler.getWorld().body.size(); i++) {
				if((handler.getWorld().player2.xCoord == handler.getWorld().body.get(i).x &&
						handler.getWorld().player2.yCoord == handler.getWorld().body.get(i).y) || (handler.getWorld().player.xCoord == handler.getWorld().body1.get(i).x &&
						handler.getWorld().player.yCoord == handler.getWorld().body1.get(i).y)) {
					Main.GameSetUp.winner2 = true;
				}
			}
		}

		if(!handler.getWorld().body1.isEmpty()) {
			handler.getWorld().playerLocation1[handler.getWorld().body1.getLast().x][handler.getWorld().body1.getLast().y] = false;
			handler.getWorld().body1.removeLast();
			handler.getWorld().body1.addFirst(new Tail2(x, y,handler));
		}
	}

	public static int snake = new Color(0,0,255).getRGB();
	public static int apple = new Color(255,0,0).getRGB();
	public static int rotten = new Color(128,0,0).getRGB();

	public void render(Graphics g,Boolean[][] playerLocation1){
		Random r = new Random();
		for (int i = 0; i < handler.getWorld().GridWidthHeightPixelCount ; i++) {
			for (int j = 0; j < handler.getWorld().GridWidthHeightPixelCount ; j++) {

				g.setColor(new Color(snake));
				if(playerLocation1[i][j]||handler.getWorld().appleLocation1[i][j]){
					g.fillRect((i*handler.getWorld().GridPixelsize),
							(j*handler.getWorld().GridPixelsize),
							handler.getWorld().GridPixelsize,
							handler.getWorld().GridPixelsize);
				}
				if(handler.getWorld().getApple().isGood() == true){
					if(handler.getWorld().appleLocation1[i][j]) {
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
						if(handler.getWorld().appleLocation1[i][j]) {
							g.setColor(new Color(rotten));
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
			score = score - Math.sqrt(2*score+1);
		}
		if(handler.getWorld().getApple().isGood() == true) {
			score =  Math.sqrt(2*score+1);
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
		}
		justAte = true;
		steps=0;

		Tail2 tail= null;
		handler.getWorld().appleLocation1[xCoord][yCoord]=false;
		handler.getWorld().appleOnBoard=false;
		switch (direction){
		case "Left":
			if( handler.getWorld().body1.isEmpty()){
				if(this.xCoord!=handler.getWorld().GridWidthHeightPixelCount-1){
					tail = new Tail2(this.xCoord+1,this.yCoord,handler);
				}else{
					if(this.yCoord!=0){
						tail = new Tail2(this.xCoord,this.yCoord-1,handler);
					}else{
						tail =new Tail2(this.xCoord,this.yCoord+1,handler);
					}
				}
			}else{
				if(handler.getWorld().body1.getLast().x!=handler.getWorld().GridWidthHeightPixelCount-1){
					tail=new Tail2(handler.getWorld().body1.getLast().x+1,this.yCoord,handler);
				}else{
					if(handler.getWorld().body1.getLast().y!=0){
						tail=new Tail2(handler.getWorld().body1.getLast().x,this.yCoord-1,handler);
					}else{
						tail=new Tail2(handler.getWorld().body1.getLast().x,this.yCoord+1,handler);

					}
				}
			}
			break;
		case "Right":
			if( handler.getWorld().body1.isEmpty()){
				if(this.xCoord!=0){
					tail=new Tail2(this.xCoord-1,this.yCoord,handler);
				}else{
					if(this.yCoord!=0){
						tail=new Tail2(this.xCoord,this.yCoord-1,handler);
					}else{
						tail=new Tail2(this.xCoord,this.yCoord+1,handler);
					}
				}
			}else{
				if(handler.getWorld().body1.getLast().x!=0){
					tail=(new Tail2(handler.getWorld().body1.getLast().x-1,this.yCoord,handler));
				}else{
					if(handler.getWorld().body1.getLast().y!=0){
						tail=(new Tail2(handler.getWorld().body1.getLast().x,this.yCoord-1,handler));
					}else{
						tail=(new Tail2(handler.getWorld().body1.getLast().x,this.yCoord+1,handler));
					}
				}
			}
			break;
		case "Up":
			if( handler.getWorld().body1.isEmpty()){
				if(this.yCoord!=handler.getWorld().GridWidthHeightPixelCount-1){
					tail=(new Tail2(this.xCoord,this.yCoord+1,handler));
				}else{
					if(this.xCoord!=0){
						tail=(new Tail2(this.xCoord-1,this.yCoord,handler));
					}else{
						tail=(new Tail2(this.xCoord+1,this.yCoord,handler));
					}
				}
			}else{
				if(handler.getWorld().body1.getLast().y!=handler.getWorld().GridWidthHeightPixelCount-1){
					tail=(new Tail2(handler.getWorld().body1.getLast().x,this.yCoord+1,handler));
				}else{
					if(handler.getWorld().body1.getLast().x!=0){
						tail=(new Tail2(handler.getWorld().body1.getLast().x-1,this.yCoord,handler));
					}else{
						tail=(new Tail2(handler.getWorld().body1.getLast().x+1,this.yCoord,handler));
					}
				}
			}
			break;
		case "Down":
			if( handler.getWorld().body1.isEmpty()){
				if(this.yCoord!=0){
					tail=(new Tail2(this.xCoord,this.yCoord-1,handler));

				}else{
					if(this.xCoord!=0){
						tail=(new Tail2(this.xCoord-1,this.yCoord,handler));
					}else{
						tail=(new Tail2(this.xCoord+1,this.yCoord,handler));
					} System.out.println("Tu biscochito");
				}
			}else{
				if(handler.getWorld().body1.getLast().y!=0){
					tail=(new Tail2(handler.getWorld().body1.getLast().x,this.yCoord-1,handler));
				}else{
					if(handler.getWorld().body1.getLast().x!=0){
						tail=(new Tail2(handler.getWorld().body1.getLast().x-1,this.yCoord,handler));
					}else{
						tail=(new Tail2(handler.getWorld().body1.getLast().x+1,this.yCoord,handler));
					}
				}

			}
			break;
		}
		if(handler.getWorld().getApple().isGood() == true){
			speed = speed - 4;
			handler.getWorld().body1.addLast(tail);
			handler.getWorld().playerLocation1[tail.x][tail.y] = true;
		}
		if(handler.getWorld().getApple().isGood() == false) {
			speed = speed + 4;
			if(!handler.getWorld().body1.isEmpty() ){
				handler.getWorld().playerLocation1[handler.getWorld().body1.getLast().x][handler.getWorld().body1.getLast().y] = false;
				handler.getWorld().body1.removeLast();
				kill();
			}
			else {
				kill();
			}
		}
	}

	public void kill(){
		lenght = 0;
		for (int i = 0; i < handler.getWorld().GridWidthHeightPixelCount; i++) {
			for (int j = 0; j < handler.getWorld().GridWidthHeightPixelCount; j++) {
				handler.getWorld().playerLocation1[i][j]=false;
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
