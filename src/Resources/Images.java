package Resources;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Created by AlexVR on 7/1/2018.
 */
public class Images {


    public static BufferedImage[] butstart;
    public static BufferedImage[] butSP;
    public static BufferedImage[] butMP;
    public static BufferedImage title;
    public static BufferedImage Pause;
    public static BufferedImage gameOver;
    public static BufferedImage[] Resume;
    public static BufferedImage[] BTitle;
    public static BufferedImage[] Options;
    public static BufferedImage[] Reset;
    public static ImageIcon icon;
    public static ImageIcon snakeIcon;
    public static BufferedImage Winner1;
    public static BufferedImage Winner2;

    
    public Images() {

        butstart = new BufferedImage[3];
        butSP = new BufferedImage[3];
        butMP = new BufferedImage[3];
        Resume = new BufferedImage[2];
        BTitle = new BufferedImage[2];
        Options = new BufferedImage[2];
        Reset = new BufferedImage[2];

        try {

            title = ImageIO.read(getClass().getResourceAsStream("/Sheets/Title.png"));
            Pause = ImageIO.read(getClass().getResourceAsStream("/Buttons/Pause.png"));
            gameOver = ImageIO.read(getClass().getResourceAsStream("/Sheets/gameOver.jpg"));
            Winner1 = ImageIO.read(getClass().getResourceAsStream("/Buttons/P1Wins.png"));
            Winner2 = ImageIO.read(getClass().getResourceAsStream("/Buttons/P2Wins.png"));
            Resume[0] = ImageIO.read(getClass().getResourceAsStream("/Buttons/Resume.png"));
            Resume[1] = ImageIO.read(getClass().getResourceAsStream("/Buttons/ResumeP.png"));
            BTitle[0] = ImageIO.read(getClass().getResourceAsStream("/Buttons/BTitle.png"));
            BTitle[1] = ImageIO.read(getClass().getResourceAsStream("/Buttons/BTitleP.png"));
            Options[0] = ImageIO.read(getClass().getResourceAsStream("/Buttons/Options.png"));
            Options[1] = ImageIO.read(getClass().getResourceAsStream("/Buttons/OptionsP.png"));
            butstart[0]= ImageIO.read(getClass().getResourceAsStream("/Buttons/NormBut.png"));//normbut
            butstart[1]= ImageIO.read(getClass().getResourceAsStream("/Buttons/HoverBut.png"));//hoverbut
            butstart[2]= ImageIO.read(getClass().getResourceAsStream("/Buttons/ClickedBut.png"));//clickbut
            Reset[0]= ImageIO.read(getClass().getResourceAsStream("/Buttons/Reset.png"));
            Reset[1]= ImageIO.read(getClass().getResourceAsStream("/Buttons/ResetP.png"));
            
            
            butSP[0]= ImageIO.read(getClass().getResourceAsStream("/Buttons/NormSPBut.png"));
            butSP[1]= ImageIO.read(getClass().getResourceAsStream("/Buttons/HoverSPBut.png"));
            butSP[2]= ImageIO.read(getClass().getResourceAsStream("/Buttons/ClickedSPBut.png"));
            
            butMP[0]= ImageIO.read(getClass().getResourceAsStream("/Buttons/NormMPBut.png"));
            butMP[1]= ImageIO.read(getClass().getResourceAsStream("/Buttons/HoverMPBut.png"));
            butMP[2]= ImageIO.read(getClass().getResourceAsStream("/Buttons/ClickedMPBut.png"));
            
            icon =  new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/Sheets/icon.png")));
            snakeIcon =  new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/Sheets/SnakeIcon.png")));


        }catch (IOException e) {
        e.printStackTrace();
    }


    }

    public static BufferedImage loadImage(String path) {
        try {
            return ImageIO.read(Images.class.getResourceAsStream(path));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return null;
    }

}
