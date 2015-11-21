/**
 * @author Endrias, @author Arnold Koh, @author rschneider
 */

public interface BattleShipMainInterface {

    // return players.player1;

    // currgnt player
    Players currentPlayer(){
        if(player1 turn){
            return Players.player1;
        }
        if(player2Turn){
            return Players.player2;
        }
    }

    //4 methods that return all 4 boards
    public char getPlayer1DefBoard(Location loc){
        //call getOffensiveBoard & getShipDefensiveBoard
        player1.getDefBoard()[x][y];
    }
        public getPlayer2defBoard()
            public getPlayer

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
    public void placeShip(ShipTypes ship, Direction d, Location loc);
}