/**
 * @author Endrias, @author Arnold Koh, @author rschneider
 */

public interface BattleShipMainInterface {
    /**
     * Determines the winner of the game.
     * @return the winning player
     * @throws IllegalStateException if the game is not over, that is one of the players hasn't run out of ships
     */
    PlayerInterface winner();

    /**
     * Checks whether defensive grid of any of the two palyers has no more ships.
     * @return <tt>true</tt> if defensive grid of any of the two palyers has no more ships.
     */
    public boolean checkForWinner();

    /**
     * Returns a reference to the players array, with index 0 being player 1 and index 1 being player 2.
     * @return a reference to the players array.
     */
    PlayerInterface[] getPlayers();

    /**
     * Makes a shot during Play Mode of one player, marking the offensive board of the player shooting and the defensive board of
     * the other player.
     * For example, one player (A) makes a "shot" a some location on the board, designated by a letter (A-J) and a number (1-10).
     * The corresponding location on other player's (B's) defensive grid is checked for a vessel in that square. T
     * @param loc    The designator for the shot
     * @param player 1 for player 1 and 2  for player 2
     * @return The status of the shot. See the status constants
     * @throws IllegalStateException    The game is not in Play Mode
     * @throws IllegalArgumentException if the arguments is out of bounds of the char array (10 * 10 board) (
     */
    public Status makeShot(Location loc, int player);

    /**
     * Places a ship in on defensive board at the starting vertical and
     * horizontal co-ordinates and according to the ships type (length) and
     * direction. The ship will be represnted by contiguous squares in the direction passsed in.
     * @param ship the type of ship to be added
     * @param d    the enumerated direction of the ship
     * @throws IllegalArgumentException if the arguments is out of bounds of the defensive char array (10 * 10 board) or if there already exists
     *                                  a ship of that type (for destoryer, it will allow upto two ships of the same kind) or if the the ship can't be drawn (it can't be
     *                                  drawn if the ship intersect or overlap those of any other vessel in the defensive grid)
     */
    public void placeShip(Ship ship, Direction d, Location loc);
}