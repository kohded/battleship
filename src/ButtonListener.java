import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by anjeo on 11/22/2015.
 */
public class ButtonListener implements ActionListener {
    private BattleShipMain model;
    BattleShipIntro setup;
    public ButtonListener (BattleShipMain model, BattleShipIntro setup){
        // store model
        // store setup
    }

    /**
     * Invoked when an action occurs.
     *
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        /*
        get the current player from the model
        get the Offensive board of the model of the current player
        use e.getSource() to figure out which button was clicked from the offensive board of the current player
        disable the button
        use the index of the button clicked to call currentplayer.model.makeShot() and store the return of the call as "result"
        get the offensive and defensive board from the model of the two players
        initalize winner = model.getWinner
        if (winner is not null)
            pop up a menu saying who the winner is
            disable all buttons of both players
            return
        if(result equals miss)
            remove the old "current player" view's jpanel from model.frame
            put the current player's jpanel to model.frame
        call updateOffensiveBoard and updateDefensiveBoard from each player's view and pass in the corresponding "boards" retrieved earlier
       if(result equals SUNK ???)
           pop up a menu saying what ship got sunk
       result equals do over is not possible because any played buttons before will be disabled
         */
    }
}
