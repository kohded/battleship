import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * Represnts the view for the introduction to BattleShip
 */
public class BattleShipIntro extends JPanel {
    public static final int SQUARE_LENGTH = 10;
    //width of board
    public static final int WIDTH = 650;
    //length of board
    public static final int LENGTH = 380;

    public JFrame getFrame() {
        return frame;
    }

    public void setFrame(JFrame frame) {
        this.frame = frame;
    }

    JFrame frame;
    playerView player1;
    playerView player2;


    public BattleShipIntro() {
        /**
         * Initalize frame to new JFrame
         * set size to width and length
         * intalize player 1 to playerView (Players.PLAYER.1)
         * intalize player 2 to playerView (Players.PLAYER.2)
         * add player1.playerName and player2.playerName to frame
         * add player1.promptName and player2.promptName to frame
         * create a new JButton
         * add this button to bottom center corner
         * add JTextField to the left of the button that says "Click button After entering Name"
         * register the button with the a new NameEnteredListener (this)
         * set this "jpanel" visible
         * add this jpanel to frame
         * repaint to draw battleShip picture on screen
         *
         */
    }

    @Override
    public void paintComponent(Graphics g) {
        System.out.println(2);
        Image img;
        try {
            //draws the background
            img = ImageIO.read(new File("BattleShipPicture.png"));
            g.drawImage(img, 0, 0, null);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
    public static void main (String [] args){
        BattleShipIntro x  =  new BattleShipIntro();
    }
}
