package Game.GameStates;

import Game.Entities.Dynamic.Player;
import Main.Handler;
import Worlds.WorldBase;
import Worlds.WorldOne;
import Game.Entities.Dynamic.Player2;
import java.awt.*;


/**
 * Created by AlexVR on 7/1/2018.
 */
public class GameState extends State {

	private WorldBase world;

	public GameState(Handler handler){
		super(handler);
		world = new WorldOne(handler);
		handler.setWorld(world);
		handler.getWorld().player= new Player(handler);
		handler.getWorld().player2 = new Player2(handler);
		for (int i = 0; i < handler.getWorld().GridWidthHeightPixelCount; i++) {
			for (int j = 0; j < handler.getWorld().GridWidthHeightPixelCount; j++) {

				handler.getWorld().playerLocation[i][j]=false;
				handler.getWorld().appleLocation[i][j]=false;
				handler.getWorld().playerLocation1[i][j]=false;
				handler.getWorld().appleLocation1[i][j]=false;

			}
		}
		handler.getWorld().playerLocation[handler.getWorld().player.xCoord][handler.getWorld().player.yCoord] =true;
		handler.getWorld().playerLocation1[handler.getWorld().player2.Xcoord1][handler.getWorld().player2.ycoord1] =true;
	}

	@Override
	public void tick() {
 
		handler.getWorld().tick();

	}

	@Override
	public void render(Graphics g) {

		handler.getWorld().render(g);
		if(Main.GameSetUp.multiplayer == false) {
			g.setColor(Color.green);
			Font font = new Font ("SansSerif", Font.PLAIN, 24);
			g.setFont(font);
			g.drawString("Score: " + Player.score,handler.getWidth()/10-70 ,handler.getHeight()/10-50);
		}
	}
}
