import javax.swing.*;

/**
 * Created by anjeo on 11/20/2015.
 */
public class playerView {
    private double STARTING_X_RElATIVE = 0.77;
    private  double STARTING_Y_RELATIVE = 0.71;
    public static final double WIDTH_RELATIVE = 0.15;
    public static final double LENGTH_RELATIVE = 0.04;

    public JButton[][] getPlayerOffesniveBoard() {
        return playerOffesniveBoard;
    }

    private JButton[][] playerOffesniveBoard;
    private JButton [][] playerDefensiveBoard;
    JTextField playerName;
    private JTextField promptForName;
    private String name;
    JPanel panel;
    public static final int SQUARE_LENGTH = 10;

    public void setName(String name) {
        this.name = name;
    }
    public JTextField getPromptForName() {
        return promptForName;
    }

    public String getUpdateName() {
        return name;
    }

    public JTextField getPlayerName() {
        return playerName;
    }

    public playerView(Players player){
        playerOffesniveBoard = new JButton[SQUARE_LENGTH][SQUARE_LENGTH];
        playerDefensiveBoard = new JButton[SQUARE_LENGTH][SQUARE_LENGTH];
        playerName = new JTextField();
        int width = BattleShipGraphics.WIDTH;
        int length = BattleShipGraphics.LENGTH;
        if(player == Players.PLAYER2){
            STARTING_Y_RELATIVE = STARTING_Y_RELATIVE + LENGTH_RELATIVE;
        }
        promptForName = new JTextField(16);
        //promptForName.(int)(width* STARTING_X_RElATIVE),(int) (length * STARTING_Y_RELATIVE), (int) (width * WIDTH_RELATIVE),(int) (length *LENGTH_RELATIVE)
        playerName.setBounds((int)(width* STARTING_X_RElATIVE),(int) (length * STARTING_Y_RELATIVE), (int) (width * WIDTH_RELATIVE),(int) (length *LENGTH_RELATIVE));
        panel = new JPanel();
    }

    public void updateOffensiveBoard(char[][] updatedBoard){
        /*
              If the updatedBoard is null or board doesn't match dimension of offensive board
                 throw exception
              for each row in updatedBoard
                for each element in row in updatedBoard
                     put updatedBoard[row][col] on the OffensiveBoard [row][col]
         */
    }

    public void updateDefensiveBoard(char[][] UpdatedBoard){
        /**
         * Switch defensive and offensive board
         * call updateOffensiveBoard(updatedBoard)
         * switch defensive and offensive board back
         */
    }



}
