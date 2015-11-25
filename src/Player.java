import java.util.ArrayList;

public class Player implements PlayerInterface {
    ArrayList<Ship> ships;
    possibleBoardStates[][] offensiveBoard;
    possibleBoardStates[][] defensiveBoard;
    public static final int BOARD_LENGTH = 10;
    int hits;

    public Player() {
        initalize(offensiveBoard);
        initalize(defensiveBoard);
    }

    private void initalize(possibleBoardStates[][] board) {
        board = new possibleBoardStates[BOARD_LENGTH][BOARD_LENGTH];
        for(int i = 0; i < board.length; i++) {
            for(int j = 0; j < board[i].length; i++) {
                offensiveBoard[i][j] = possibleBoardStates.EMPTY;
            }
        }
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
     * Returns the number of ships left on the player's board
     * @return number of hits
     */
    @Override
    public int getShipsLeft() {
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
        if(isMakeShotLegal(loc)) {
            return Status.DO_OVER;
        }
        possibleBoardStates value = defensiveBoard[loc.row][loc.col];
        if(startsWithHit(value)) {
            defensiveBoard[loc.row][loc.col] = value;
        }

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
         */
    }

    private boolean startsWithHit(possibleBoardStates state) {
        return state != possibleBoardStates.HIT_AIRCRAFT || state != possibleBoardStates.HIT_BATTLESHIP || state != possibleBoardStates.HIT_CRUISER || state != possibleBoardStates.HIT_DESTROYER;
    }

    private possibleBoardStates flipped(possibleBoardStates state) {
        if(state == possibleBoardStates.AIRCRAFT) {
            return possibleBoardStates.HIT_AIRCRAFT;
        }
        if(state == possibleBoardStates.BATTLESHIP) {
            return possibleBoardStates.HIT_BATTLESHIP;
        }
        if(state == possibleBoardStates.CRUISER) {
            return possibleBoardStates.HIT_CRUISER;
        }
        if(state == possibleBoardStates.DESTROYER) {
            return possibleBoardStates.HIT_DESTROYER;
        }
    }

    /**
     * Checks if a ship at a location has been sunk.
     * @param loc The designator for the shot
     * @return true if the ship has been hit, false if it has not
     */
    private boolean isShipSunk(Location loc) {
        /*
            set value to defensiveBoard[loc.row][loc.col] "non hit version"
            set boolean sunk to true
            for int row = loc.row -1 to loc.row + 1
              for int col = loc.col -1 to loc.col + 1
                if(defensiveBoard[row][col] == value)
                   set sunk to false
                   break
           return sunk;

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
     * @param d    the enumerated direction of the ship
     * @throws IllegalArgumentException if the arguments is out of bounds of the defensive char array (10 * 10 board) or if there already exists
     *                                  a ship of that type (for destoryer, it will allow upto two ships of the same kind) or if the the ship can't be drawn (it can't be
     *                                  drawn if the ship intersect or overlap those of any other vessel in the defensive grid)
     */
    public void placeShip(possibleBoardStates ship, Direction d, Location loc) {
        /**
         *
         */
        // after 5 legal placements, swtich to player two
        // aftr 10 placemnts ,swtich to player one
        // any calls after this will return "cant be placed"
        //if move is legal send back the character in the 2d defensive board

    }

    // validate if placement of ship is valid
    private boolean isPlaceShipLegal() {
        // current player
        return true;
    }
}