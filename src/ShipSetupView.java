
/**
 * Represnts view  for Ship Setup.
 */
public class ShipSetupView {
    // we choose a Jcombox because it was user friendly and enforced the selection of specific ships
    public JComboBox<possibleBoardStates> getShips() {
        return ships;
    }
    public void setShips(JComboBox<possibleBoardStates> ships) {
        this.ships = ships;
    }

    public JComboBox<Direction> getDirections() {
        return directions;
    }
    // we choose a Jcombox because it was user friendly and enforced the selection of specific ships
    public void setDirections(JComboBox<Direction> directions) {
        this.directions = directions;
    }

    public JComboBox<Direction> directions;
    public JComboBox<possibleBoardStates> ships;
    JFrame frame;
    JButton deploy;

        public ShipSetupView(BattleShipIntro intro){
            /**
             * initalize directions, ships, and deploy
             * set size of frame to width and length constants from BattleShipIntro
             * set frame layout to grid layout
             * put directions and ships on frame
             * put offensive board of player 1 on the right of the frame
             * register ShipSetupListener to deploy
             * swap intro's frame for the frame in this class
             */
        }
}
