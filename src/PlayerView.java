import javax.swing.*;

/**
 * Created by anjeo on 11/20/2015.
 */
public class playerView {
    private JButton[][] playerOffesniveBoard;
    private JButton [][] playerDefensiveBoard;
    JPanel panel;
    public static final int SQUARE_LENGTH = 10;

    public playerView(){
        playerOffesniveBoard = new JButton[SQUARE_LENGTH][SQUARE_LENGTH];
        playerDefensiveBoard = new JButton[SQUARE_LENGTH][SQUARE_LENGTH];
        panel = new JPanel();
    }
}
