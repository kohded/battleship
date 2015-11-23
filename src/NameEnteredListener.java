import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by anjeo on 11/22/2015.
 */
public class NameEnteredListener implements ActionListener {
    BattleShipIntro setup;
    public NameEnteredListener(BattleShipIntro setup){
        // store this.setup
    }
    /**
     * Invoked when an action occurs.
     *
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        //capture name from both players (instance members BattleShipIntro setup) playerName field
        // if one or both of the names are empty, change the PromptText jtextField in the player(s) with the empty name(s)
        // if they are not empty, then set the names accordingly to each player using player.setName
        // remove the current jpanel from setup
        // create battleShipMain object
        // request both boards of players
    }
}
