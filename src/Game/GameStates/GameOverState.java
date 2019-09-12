package Game.GameStates;

import Main.Handler;
import Resources.Images;
import UI.UIImageButton;
import UI.UIManager;

import java.awt.*;

import Game.Entities.Dynamic.Player;

/**
 * Created by AlexVR on 7/1/2018.
 */
public class GameOverState extends State {

    private UIManager uiManager;

    public GameOverState(Handler handler) {
        super(handler);
        uiManager = new UIManager(handler);
        handler.getMouseManager().setUimanager(uiManager);

        uiManager.addObjects(new UIImageButton(handler.getWidth()/3 - 200, handler.getHeight()/2 + 150, 128, 64, Images.Reset, () -> { //restart
        	handler.getMouseManager().setUimanager(null);
            handler.getGame().reStart();
            State.setState(handler.getGame().gameState);
            
        }));
    
        uiManager.addObjects(new UIImageButton(handler.getWidth()/3 + 60, handler.getHeight()/2 + 150, 128, 64, Images.Options, () -> {
            handler.getMouseManager().setUimanager(null);
            State.setState(handler.getGame().menuState);
        }));

        uiManager.addObjects(new UIImageButton(handler.getWidth()/3 + 350, handler.getHeight()/2 + 150, 128, 64, Images.BTitle, () -> {
            handler.getMouseManager().setUimanager(null);
            State.setState(handler.getGame().menuState);
        }));

    }

    @Override
    public void tick() {
        handler.getMouseManager().setUimanager(uiManager);
        uiManager.tick();
    }

    @Override
    public void render(Graphics g) {
    	g.setColor(Color.black);
    	g.fillRect(0,0,handler.getWidth(),handler.getHeight());
        g.drawImage(Images.gameOver,0,0,800,700,null);
        
        g.setColor(Color.green);
		Font font = new Font ("SansSerif", Font.PLAIN, 24);
		g.setFont(font);
		g.drawString("Score: " + Player.score,handler.getWidth()/3 + 65 ,handler.getHeight()/2 + 100);
        uiManager.Render(g);

    }
}
