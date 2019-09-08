package Game.Entities.Dynamic;
import Main.Handler;

/**
 * Created by AlexVR on 7/2/2018.
 */
public class Tail2 {
    public int x,y;
    public Tail2(int x, int y,Handler handler){
        this.x=x;
        this.y=y;
        handler.getWorld().playerLocation1[x][y]=true;
        
    }

}


