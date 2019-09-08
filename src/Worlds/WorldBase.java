package Worlds;

import java.awt.Graphics;
import java.util.LinkedList;

import Game.Entities.Dynamic.Player;
import Game.Entities.Dynamic.Tail;
import Game.Entities.Dynamic.Tail2;
import Game.Entities.Static.Apple;
import Main.Handler;
import Game.Entities.Dynamic.Player2;


/**
 * Created by AlexVR on 7/2/2018.
 */
public abstract class WorldBase {

    //How many pixels are from left to right
    //How many pixels are from top to bottom
    //Must be equal
    public int GridWidthHeightPixelCount;

    //automatically calculated, depends on previous input.
    //The size of each box, the size of each box will be GridPixelsize x GridPixelsize.
    public int GridPixelsize;

    public Player player;
    public Player2 player2;

    protected Handler handler;

    public Boolean appleOnBoard;
    protected Apple apple;
    public Boolean[][] appleLocation;
    public Boolean[][] playerLocation;
    public Boolean[][] appleLocation1;
    public Boolean[][] playerLocation1;
    public LinkedList<Tail> body = new LinkedList<>();
    public LinkedList<Tail2> body1 = new LinkedList<>();

    public WorldBase(Handler handler){
        this.handler = handler;

        appleOnBoard = false;
    }
    
    public void tick(){

    }

    public void render(Graphics g){

        for (int i = 0; i <= 800; i = i + GridPixelsize) {

            /*g.setColor(Color.white);
            g.drawLine(0, i, handler.getWidth() , i);
            g.drawLine(i,0,i,handler.getHeight());
			*/
        }

    }

	public Apple getApple() {
		return apple;
	}

	public void setApple(Apple apple) {
		this.apple = apple;
	}

}
