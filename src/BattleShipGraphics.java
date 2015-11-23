import com.sun.xml.internal.bind.annotation.OverrideAnnotationOf;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * Created by anjeo on 11/20/2015.
 */
public class BattleShipGraphics extends JPanel {
    public static final int SQUARE_LENGTH = 10;
    //width of board
    public static final int WIDTH= 650;
    //length of board
    public static final int LENGTH= 380;
    JFrame frame;
    playerView player1;
    playerView player2;


    public BattleShipGraphics (){
        frame = new JFrame();
        frame.setSize(WIDTH,LENGTH);
        player1 = new playerView(Players.PLAYER1);
        player2 = new playerView(Players.PLAYER2);
        //frame.add(introPanel)
        setSize(WIDTH,LENGTH);
        JTextField jt = new JTextField();
        jt.setBounds(500, 200, 100,20);
        frame.add(player1.playerName);
        frame.add(player2.playerName);
        //this.add(player1.playerName);
       // this.add(player2.playerName);
        //this.add(textField);
        this.setVisible(true);
        frame.add(this);
        frame.setVisible(true);
        this.repaint();
        //this.repaint();
                /*
                Add jpanel to jframe 9panel contaisn the name )
                // panel should have picture and two buttons inside it for figuring out the name
                // when another button is clicked, it should check what is niside the other buttons
                // and decide whether to remove the jpanel or stay (will be passed in BattleShipGraphics)
                // and register all the jubttons with one action lister (call a method)
                // this action listerne will pop player 1 jframe or the other one based on stuff
                // then using the nmaes, pop player 1 interface.
                // this should have a its own listener..
                // call the same listener 5 times
                // place 5 ships
                // rpeat for player two
                // remove everything from jpanel, then depending on the dediction in the game, alternate
                 */

    }

    @Override
    public void paintComponent(Graphics g){
        System.out.println(2);
        Image img;
        try {
            //draws the background
            img = ImageIO.read(new File("BattleShipPicture.png"));
            g.drawImage(img, 0, 0, null);
            //img = ImageIO.read(new File("spaceShip.png"));
            //draws ship approximately in the bottom middle


            //g.drawImage(img, ship.getX(), ship.getY() - 100, null);


            //img = ImageIO.read(new File("Chicken.png"));
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void main (String [] args){
        BattleShipGraphics x  =  new BattleShipGraphics();
    }
}
