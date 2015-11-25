import java.util.HashMap;

/**
 * Represents the model for BattleShip Main. This model decides who is the current player initally
 * by counterPlaceShip and then by changing the player depending on hit / miss. We chose this design
 * to enforce business rules of how the game should alternate. This allowed also to not let the view access the players
 * directly since this class took care of which board to place ship on  or make a hit on.
 */
public class BattleShipMain implements BattleShipMainInterface {
    Player player1 = new Player();
    Player player2 = new Player();
    int counterPlaceShip;
    Player currentPlayer;
    Player otherPlayer;
    // used a hashmap for the strong correlation
    HashMap<possibleBoardStates,Integer> shipLengthDic;

    Players getCurrentPlayer(){
        /**
         *  if currentPlayer1
         *      return player.PLAYER1
         *  if currentPlayer2
         *      return player.PLAYER2
         */
    }

    public void getShipLengthDic(ShipTypes type){
        /**
         * return shipLengthDict.get(type)
         */
    }

    /**
     * Makes a shot during Play Mode of one player, marking the defensive board of the player ( a shot was madagainst it)
     * the other player.
     * For example, one player (A) makes a "shot" a some location on the board, designated by a letter (A-J) and a number (1-10).
     * The corresponding location on other player's (B's) defensive grid is checked for a vessel in that square. T
     * @param loc The designator for the shot
     * @return The status of the shot. See the status constants
     * @throws IllegalStateException    The game is not in Play Mode
     * @throws IllegalArgumentException if the arguments is out of bounds of the char array (10 * 10 board)
     */
    public Status makeShot(Location loc) {
        /** if winner() doesn't return null
         *       throw IllegalStateException
         *  initialize status = otherplayer (not current).makerShot(loc)
         *  call currentPlayer.placeResult(status,loc)
         *  if status is miss
         *      swap current player to other player
         *  return status
         */
        Status status = otherPlayer.makeShot(loc);
        currentPlayer.placeShotResult(status,loc);
        if(status  == status.MISS){
            Player temp = otherPlayer;
            otherPlayer = currentPlayer;
            currentPlayer = temp;
        }
        return status;
    }

    /**
     *  Gets the value of the board state at Location
     *  @ param loc location where to get board state
     *  @ param player which player's board to print
     *  @ return board status at location
     */
    public possibleBoardStates getPlayerOffBoard(Location loc, Players player) {
        /**
         * if player1 equals player
         *      return  player1.getOffensiveBoard().[loc.row][loc.col]
         * else
         *      return  player2.getOffensiveBoard().[loc.row][loc.col]
         */

        if(player1.equals(player)){
            return  player1.getOffensiveBoard()[loc.row][loc.col];
        } else {
            return  player2.getOffensiveBoard()[loc.row][loc.col];
        }
    }

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
    @Override
    public void placeShip(possibleBoardStates ship, Direction d, Location loc) {
        /**
         *  if (ship is "hit" version)
         *      throw illegal argument exception
         * if (counterPlaceShips is less than 5)
         *    currentPlayer1 = true
         *    Ship package = new Ship
         *    package.direction = d
         *    package.loc = loc
         *    package.type = ship
         *    package.length = getShipLengthDic(ship)
         *    player1.placeShip(package)
         *    increment counterPlacementShip
         */
        //
        // after 10 placements ,switch to player one
        // any calls after this will return "cant be placed"
        //if move is legal send back the character in the 2d defensive board
    }

    public boolean setupFinished(){
        /* if (number of times place ship called is greater than 9)
            return true
           else
            return false
        */
    }
    public Players winner(){
        /**
         * if player1 ships are 0
         *      return player 2
         *  if player 2 ships are 0
         *      return player 1
         *   else
         *   return null
         */
    }

}