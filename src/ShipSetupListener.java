import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Listener for placing ships.
 */
public class ShipSetupListener implements ActionListener{
    BattleShipIntro setup;
    // make sure to add batttleShipModel
    public ShipSetupListener(ShipSetupView setup){
        // store setup
    }

    /**
     * Invoked when an action occurs.
     *
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        /* set model to setup.model
        Get the current player from the model
        get the defensive board of the view of the current player.
         use getSource() to figure out which button was clicked from the defensive board of the current player
         create a new location object by the name loc
         set loc's row to row of clicked button
         set loc's col to col of clicked button
         call model.place (setup.dirChoice.selected,setup.possibleBoardStates.selected, loc)
         set new to model.currentPlayer()
         if model.setupFinished() equals true
              remove defensive board of player 2
              put offensive board of player 1 on the left, and player 2 on the right of the jpanel
              register offensibe board of player 1 with ButtonListener
              return
          if new is different than "current" player from before
                remove defensive board of "current" player from jpanel
                add defensive board of "new " player from jpanel
        */
    }
}
