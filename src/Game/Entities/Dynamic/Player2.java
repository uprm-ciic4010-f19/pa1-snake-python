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

	public int Xcoord1;
	public int ycoord1;
	public static double score;
	public int moveCounter;
	public int speed;
	public int steps;
	public String direction;//is your first name one?

	public Player2(Handler handler){
		this.handler = handler;
		Xcoord1 = 50;
		ycoord1 = 2;
		speed = 4;
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
		//Button added (M).
		if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_M)){
			handler.getWorld().body1.addFirst(new Tail2(Xcoord1, ycoord1,handler));
		}
		//Button added (0) Speed boost.
		if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_0)){
			speed--;
		}
		//Button added (9) Speed reduced
		if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_9)){
			speed++;
		}
	}

	public void checkCollisionAndMove(){
		steps++;			
		handler.getWorld().playerLocation1[Xcoord1][ycoord1]=false;
		int x = Xcoord1;
		int y = ycoord1;
		switch (direction){
		case "Left":
			if(Xcoord1==0){
				handler.getWorld().player2.Xcoord1 = handler.getWorld().GridWidthHeightPixelCount - 1;
			}else {
				Xcoord1--;
			}
			break;
		case "Right":
			if(Xcoord1==handler.getWorld().GridWidthHeightPixelCount-1){
				handler.getWorld().player2.Xcoord1 = 0;
			}else{
				Xcoord1++;
			}
			break;
		case "Up":
			if(ycoord1==0){
				handler.getWorld().player2.ycoord1 = handler.getWorld().GridWidthHeightPixelCount - 1;
			}else{
				ycoord1--;
			}
			break;
		case "Down":
			if(ycoord1==handler.getWorld().GridWidthHeightPixelCount-1){
				handler.getWorld().player2.ycoord1 = 0;
			}else{
				ycoord1++;
			}
			break;
		}

		handler.getWorld().playerLocation1[Xcoord1][ycoord1]=true;

		if(handler.getWorld().appleLocation[Xcoord1][ycoord1]){
			Eat();
		}

		if(Main.GameSetUp.multiplayer == false) {
			for (int i = 0; i < handler.getWorld().body.size(); i++) {
				if(handler.getWorld().player2.Xcoord1 == handler.getWorld().body1.get(i).x &&
						handler.getWorld().player2.ycoord1 == handler.getWorld().body1.get(i).y) {
					dead = true;
				}
			}
		}else if (Main.GameSetUp.multiplayer == true){
			for (int i = 0; i < handler.getWorld().body1.size(); i++) {
				if(handler.getWorld().player2.Xcoord1 == handler.getWorld().body1.get(i).x &&
						handler.getWorld().player2.ycoord1 == handler.getWorld().body1.get(i).y) {
					
					Main.GameSetUp.winner1 = true;
				}
				if(handler.getWorld().player.xCoord == handler.getWorld().body1.get(i).x &&
						handler.getWorld().player.yCoord == handler.getWorld().body1.get(i).y){
					
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
				if(playerLocation1[i][j]||handler.getWorld().appleLocation[i][j]){
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
		//Check if game is played on multiplayer or singleplayer.
		if(Main.GameSetUp.multiplayer == false) {
			score();
		}else {
			handler.getWorld().getApple().setIsGood(true);
		}
		justAte = true;
		steps=0;

		Tail2 tail= null;
		handler.getWorld().appleLocation[Xcoord1][ycoord1]=false;
		handler.getWorld().appleOnBoard=false;
		switch (direction){
		case "Left":
			if( handler.getWorld().body1.isEmpty()){
				if(this.Xcoord1!=handler.getWorld().GridWidthHeightPixelCount-1){
					tail = new Tail2(this.Xcoord1+1,this.ycoord1,handler);
				}else{
					if(this.ycoord1!=0){
						tail = new Tail2(this.Xcoord1,this.ycoord1-1,handler);
					}else{
						tail =new Tail2(this.Xcoord1,this.ycoord1+1,handler);
					}
				}
			}else{
				if(handler.getWorld().body1.getLast().x!=handler.getWorld().GridWidthHeightPixelCount-1){
					tail=new Tail2(handler.getWorld().body1.getLast().x+1,this.ycoord1,handler);
				}else{
					if(handler.getWorld().body1.getLast().y!=0){
						tail=new Tail2(handler.getWorld().body1.getLast().x,this.ycoord1-1,handler);
					}else{
						tail=new Tail2(handler.getWorld().body1.getLast().x,this.ycoord1+1,handler);

					}
				}
			}
			break;
		case "Right":
			if( handler.getWorld().body1.isEmpty()){
				if(this.Xcoord1!=0){
					tail=new Tail2(this.Xcoord1-1,this.ycoord1,handler);
				}else{
					if(this.ycoord1!=0){
						tail=new Tail2(this.Xcoord1,this.ycoord1-1,handler);
					}else{
						tail=new Tail2(this.Xcoord1,this.ycoord1+1,handler);
					}
				}
			}else{
				if(handler.getWorld().body1.getLast().x!=0){
					tail=(new Tail2(handler.getWorld().body1.getLast().x-1,this.ycoord1,handler));
				}else{
					if(handler.getWorld().body1.getLast().y!=0){
						tail=(new Tail2(handler.getWorld().body1.getLast().x,this.ycoord1-1,handler));
					}else{
						tail=(new Tail2(handler.getWorld().body1.getLast().x,this.ycoord1+1,handler));
					}
				}
			}
			break;
		case "Up":
			if( handler.getWorld().body1.isEmpty()){
				if(this.ycoord1!=handler.getWorld().GridWidthHeightPixelCount-1){
					tail=(new Tail2(this.Xcoord1,this.ycoord1+1,handler));
				}else{
					if(this.Xcoord1!=0){
						tail=(new Tail2(this.Xcoord1-1,this.ycoord1,handler));
					}else{
						tail=(new Tail2(this.Xcoord1+1,this.ycoord1,handler));
					}
				}
			}else{
				if(handler.getWorld().body1.getLast().y!=handler.getWorld().GridWidthHeightPixelCount-1){
					tail=(new Tail2(handler.getWorld().body1.getLast().x,this.ycoord1+1,handler));
				}else{
					if(handler.getWorld().body1.getLast().x!=0){
						tail=(new Tail2(handler.getWorld().body1.getLast().x-1,this.ycoord1,handler));
					}else{
						tail=(new Tail2(handler.getWorld().body1.getLast().x+1,this.ycoord1,handler));
					}
				}
			}
			break;
		case "Down":
			if( handler.getWorld().body1.isEmpty()){
				if(this.ycoord1!=0){
					tail=(new Tail2(this.Xcoord1,this.ycoord1-1,handler));

				}else{
					if(this.Xcoord1!=0){
						tail=(new Tail2(this.Xcoord1-1,this.ycoord1,handler));
					}else{
						tail=(new Tail2(this.Xcoord1+1,this.ycoord1,handler));
					} System.out.println("Tu biscochito");
				}
			}else{
				if(handler.getWorld().body1.getLast().y!=0){
					tail=(new Tail2(handler.getWorld().body1.getLast().x,this.ycoord1-1,handler));
				}else{
					if(handler.getWorld().body1.getLast().x!=0){
						tail=(new Tail2(handler.getWorld().body1.getLast().x-1,this.ycoord1,handler));
					}else{
						tail=(new Tail2(handler.getWorld().body1.getLast().x+1,this.ycoord1,handler));
					}
				}

			}
			break;
		}
		if(handler.getWorld().getApple().isGood() == true){
			speed = speed - 1;
			handler.getWorld().body1.addLast(tail);
			handler.getWorld().playerLocation1[tail.x][tail.y] = true;
		}
		if(Main.GameSetUp.multiplayer == false) {
			if(handler.getWorld().getApple().isGood() == false) {
				speed = speed + 1;
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
