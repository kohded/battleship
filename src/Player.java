import java.util.ArrayList;
import java.util.HashMap;

/**
 * Represents a player for BattleShip
 */
public class Player implements PlayerInterface {
    ArrayList<Ship> ships;
    possibleBoardStates[][] offensiveBoard;
    possibleBoardStates[][] defensiveBoard;
    public static final int BOARD_LENGTH = 10;
    private HashMap<possibleBoardStates,Ship> shipDict;
    private HashMap<Direction, Location> directionSlope;
    private int hits;

    public Player() {
        initialize(offensiveBoard);
        ships = new ArrayList<Ship>();
        initialize(defensiveBoard);
    }

    private void directionSlopeIni(){
        directionSlope = new HashMap<Direction, Location> ();
        directionSlope.put(Direction.DOWN, new Location(-1,0));
        directionSlope.put(Direction.DOWNLEFT, new Location(-1,0));
        directionSlope.put(Direction.DOWN, new Location(-1,0));
        directionSlope.put(Direction.DOWN, new Location(-1,0));
        directionSlope.put(Direction.DOWN, new Location(-1,0));

    }
    private void initialize(possibleBoardStates[][] board) {
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
     * Returns the number of ships that are still on the player's board
     * @return number of hits
     */
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
        possibleBoardStates value = defensiveBoard[loc.row][loc.col];
        if(isNotHitVersion(value)) {
            defensiveBoard[loc.row][loc.col] = flipped(value);
            if(isShipSunk(loc) == true) {
                return sunk(value);
            }
            return Status.HIT;
        }
        else {
            return Status.MISS;
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
             if isShipSunk(loc) return true
                return value
         */
    }

    private boolean isNotHitVersion(possibleBoardStates state) {
        return state != possibleBoardStates.HIT_AIRCRAFT || state != possibleBoardStates.HIT_BATTLESHIP || state != possibleBoardStates.HIT_CRUISER || state != possibleBoardStates.HIT_DESTROYER;
    }

    public possibleBoardStates flipped(possibleBoardStates state) {
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
        if(state == possibleBoardStates.DESTROYER2) {
            return possibleBoardStates.HIT_DESTROYER2;
        }

        return state;
    }

    public Status sunk(possibleBoardStates state) {
        if(state == possibleBoardStates.AIRCRAFT) {
            return Status.SUNK_AIRCRAFT;
        }
        if(state == possibleBoardStates.BATTLESHIP) {
            return Status.SUNK_BATTLESHIP;
        }
        if(state == possibleBoardStates.CRUISER) {
            return Status.SUNK_CRUISER;
        }
        if(state == possibleBoardStates.DESTROYER) {
            return Status.SUNK_DESTROYER;
        }
        else  {
            return Status.SUNK_DESTROYER2;
        }
    }

    /**
     * Checks if a ship at a location has been sunk.
     * @param loc The designator for the shot
     * @return true if the ship has been hit, false if it has not
     */
    private boolean isShipSunk(Location loc) {
        possibleBoardStates value = defensiveBoard[loc.row][loc.col];
        for(int i = loc.row - 1; i < loc.row + 1; i++) {
            for(int j = loc.col - 1; j < loc.col + 1; i++) {
                if(defensiveBoard[i][j] == value) {
                    return false;
                    //break;
                }
                else {
                    value = defensiveBoard[i][j];
                    //remove from array
                    //return true;
                }
            }
        }
        return true;

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
    public boolean isMakeShotLegal(Location loc) {
        /*
           set value to defensiveBoard[loc.row][loc.col]
            if value equals enum that starts with hit or is miss
                return false
           else
                return true
         */
    }

    public boolean notWithinBoard(Location loc){
        return loc.col >=BOARD_LENGTH || loc.col < 0 || loc.row >=BOARD_LENGTH || loc.col < 0;
    }


    public Status placeShotResult(Status status, Location loc) {
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
        ship.loc.col -=1;
        ship.loc.row -=1;
        if(notWithinBoard(ship.loc)){
            throw new IllegalArgumentException("location doesn't exist on board (out of bounds)");
        }
        if(shipDict.containsKey(ship.type)){
            throw new IllegalArgumentException("Ship already exists");
        }
        possibleBoardStates[][] copy = getDeepCopyOfDefensiveBoard();
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



    private possibleBoardStates[][] getDeepCopyOfDefensiveBoard() {
        possibleBoardStates[][] copy = new possibleBoardStates[BOARD_LENGTH][BOARD_LENGTH];

        for(int i = 0; i < copy.length; i++) {
            for(int j = 0; j < copy[i].length; i++) {
                copy[i][j] = defensiveBoard[i][j];
            }
        }

        /**
         * initialize possibleBoardStates[][] copy
         * for each row in defensive board
         *  for each col in defensive board
         *      copy[row][col] = defensiveBoard[row][col]*
         */
        return copy;
    }
}