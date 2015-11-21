import java.util.ArrayList;

public class Player implements PlayerInterface {
    ArrayList<Ship> ships;
    /**
     * Returns a reference to the ship's offensive board.
     * @return a reference to defensive board.
     */
    @Override
    public char[][] getOffensiveBoard() {
        return new char[0][];
    }

    /**
     * Returns a reference to the ship's defensive board.
     * @return a reference to defensive board.
     */
    @Override
    public char[][] getDefensiveBoard() {
        return new char[0][];
    }

    /**
     * Returns the number of hits this player successfuly made on the player.
     * @return number of hits
     */
    @Override
    public int getHits() {
        return 0;
    }

    /**
     * Makes a shot during Play Mode.
     * @param loc The designator for the shot
     * @return The status of the shot. See the status constants
     * @throws IllegalStateException    The game is not in Play Mode
     * @throws IllegalArgumentException if the arguments is out of bounds of the char array (10 * 10 board) (
     */
    @Override
    public Status makeShot(Location loc) {
        return null;
        //Check if a hit or miss

    }

    private void isMakeShotLegal() {
        // current player
    }

    private void placeShotResult(Status x) {
        // depending on status, update offensive board
    }
}