import java.util.ArrayList;

/**
 * Repersents a player for BattleShip
 */
public class Player implements PlayerInterface {
    ArrayList<Ship> ships;
    possibleBoardStates[][] offensiveBoard;
    possibleBoardStates[][] defensiveBoard;
    public static final int BOARD_LENGTH = 10;
    private int hits;

    public Player(){
        /**
         * Initialize offensive board and defensive board to possibleBoardStates[BOARD_LENGTH][BOARD_LENGTH]
         * Initialize ships to new arrayList<ships>
         */

    }
    /**
     * Returns a reference to the ship's offensive board.
     * @return a reference to defensive board.
     */
    @Override
    public possibleBoardStates[][] getOffensiveBoard() {
        return offensiveBoard;
    }

    /**
     * Returns a reference to the ship's defensive board.
     * @return a reference to defensive board.
     */
    @Override
    public possibleBoardStates[][] getDefensiveBoard() {
        return defensiveBoard;
    }

    /**
     * Returns the number of ships the player's board
     * @return number of hits
     */
    @Override
    public int getShipsSize() {
        return ships.size();
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
        /*
            set  value to defensiveBoard[loc.row][loc.col]
            if isMakeShotLegal returns false
                return state.DoOver
            if value equals enum that doesn't start with hit,
                change defensive board at loc to the "hit" version
                return Status.Hit
            if value equals null
                change defensive board at loc to miss
                return Status.miss
             if isShipSunk(loc) return true
                return value
         */
    }

    /**
     * Checks if a ship at a location has been sunk.
     * @param loc The designator for the shot
     * @return true if the ship has been hit, false if it has not
     */
    private boolean isShipSunk(Location loc){
        /*
            set value to defensiveBoard[loc.row][loc.col] "non hit version"
            set boolean sunk to true
            for int row = loc.row -1 to loc.row + 1
              for int col = loc.col -1 to loc.col + 1
                if(defensiveBoard[row][col] == value)
                   set sunk to false
                   break
           return sunk
           set value to defensiveBoard[loc.row][loc.col] " hit version"
           remove from array
         */
    }

    /**
     * It will check if a square doesn't or does contain an existing shot. If the square doesn't contain a shot the method will return true, otherwise it will return false.
     * @return
     */
    private boolean isMakeShotLegal(Location loc) {
        /*
           set value to defensiveBoard[loc.row][loc.col]
            if value equals enum that starts with hit or is miss
                return false
           else
                return true
         */
        return true;
    }

    private void placeShotResult(Status status, Location loc) {
        /**
         set  value to defensiveBoard[loc.row][loc.col]
         if isMakeShotLegal returns false
                return state.DoOver
         if status is a hit
                change offensive board at loc to the "hit" version

         if status is a miss
               change defensive board at loc to miss
        */
    }

    /**
     * Places a ship in on defensive board at the starting vertical and
     * horizontal co-ordinates and according to the ships type (length) and
     * direction. The ship will be represnted by contiguous squares in the direction passsed in.
     * @param ship the type of ship to be added
     * @throws IllegalArgumentException if the arguments is out of bounds of the defensive board (10 * 10 board) or if there already exists
     *                                  a ship of that type  or if the the ship can't be drawn (it can't be
     *                                  drawn if the ship intersect or overlap those of any other vessel in the defensive grid)
     */
    public void placeShip(Ship ship) {
        /**
         * if isPlaceShipLegal(ship) returns true, continue
         * otherwise throw exception
         *
         */
        // after 5 legal placements, switch to player two
        // after 10 placements ,switch to player one
        // any calls after this will return "cant be placed"
        /**
         * if ship is out of bounds of defensive board (ship.loc.col or row is <0 or >9
         *      return false
         *  if ship of that kind exists in the arrayList of ships
         *      return false
         *  initialize possibleBoardStates[][] copy to getDeeplyCopyOfDefensiveBoard
         *      for row + dictionary.get(direction).row to row + dictionary.get(direction).row*distance
         *          for col + dictionary.get(direction).col to col + dictionary.get(direction).row*distance
         *              if copy[row][col] is not null
         *                  throw exception
         *              if direction is upper left or upper right, or down left, or down right*
         *                  if copy[row][col - dictionary.get(direction).col] is not null & copy[row - dictionary.get(direction).row][col] is null
         *                      throw exception
         *              copy[row][col] = ship.type
         *      defensiveBoard = copy
         *      add ship to ships array
         */
    }

    private possibleBoardStates[][] getDeepCopyOfDefensiveBoard(){
        /**
         * initialize possibleBoardStates[][] copy
         * for each row in defensive board
         *  for each col in defensive board
         *      copy[row][col] = defensiveBoard[row][col]*
         */
    }
}