import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by anjeo on 11/22/2015.
 */
public class ShipSetupListener implements ActionListener{
    BattleShipIntro setup;

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
         call model.place (setup.dirChoice.selected
        set currentPlayer to model.getCurrentPlayer
        */
    }
}
