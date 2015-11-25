import javax.swing.*;
import java.util.HashMap;

/**
 * Represents the View for each player.
 * @author Endrias Kahssay
 */
public class playerView {
    private double STARTING_X_RElATIVE = 0.77;
    private  double STARTING_Y_RELATIVE = 0.71;
    public static final double WIDTH_RELATIVE = 0.15;
    public static final double LENGTH_RELATIVE = 0.04;
    private JButton[][] playerOffesniveBoard;
    private JButton [][] playerDefensiveBoard;
    JTextField playerName;
    private JTextField promptForName;
    private String name;
    public static final int SQUARE_LENGTH = 10;
    HashMap<possibleBoardStates,String> converateDictionary;

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get Prompt for Name
     * @return prompt for Name
     */
    public JTextField getPromptForName() {
        return promptForName;
    }
    /**
     * Get Update Name
     * @return player Name
     */
    public String getUpdateName() {
        return name;
    }

    /**
     * Get Player Name
     * @return player Name
     */
    public JTextField getPlayerName() {
        return playerName;
    }
    public JButton[][] getPlayerOffesniveBoard() {
        return playerOffesniveBoard;
    }

    public playerView(Players player){
        playerOffesniveBoard = new JButton[SQUARE_LENGTH][SQUARE_LENGTH];
        playerDefensiveBoard = new JButton[SQUARE_LENGTH][SQUARE_LENGTH];
        playerName = new JTextField();
        int width = BattleShipIntro.WIDTH;
        int length = BattleShipIntro.LENGTH;
        if(player == Players.PLAYER2){
            STARTING_Y_RELATIVE = STARTING_Y_RELATIVE + LENGTH_RELATIVE;
        }
        // initalize prompt Name to be left of playerName
        playerName.setBounds((int)(width* STARTING_X_RElATIVE),(int) (length * STARTING_Y_RELATIVE), (int) (width * WIDTH_RELATIVE),(int) (length *LENGTH_RELATIVE));
    }

    public void updateOffensiveBoard(possibleBoardStates[][] updatedBoard){
        /*
              If the updatedBoard is null or board doesn't match dimension of offensive board
                 throw exception
              for each row in updatedBoard
                for each element in row in updatedBoard
                     put convertDictionary.get(updatedBoard[row][col]) on the OffensiveBoard [row][col]
         */
    }

    public void updateDefensiveBoard(possibleBoardStates[][] UpdatedBoard){
        /**
         * Switch defensive and offensive board
         * call updateOffensiveBoard(updatedBoard)
         * switch defensive and offensive board back
         */
    }
}
