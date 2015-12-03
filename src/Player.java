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
        directionSlope.put(Direction.UP, new Location(-1,0));
        directionSlope.put(Direction.LEFT, new Location(0,-1));
        directionSlope.put(Direction.POSDIAGONAL, new Location(-1,1));
        directionSlope.put(Direction.NEGDIAGONAL, new Location(1,-1));

    }
    private void initialize(possibleBoardStates[][] board) {
        board = new possibleBoardStates[BOARD_LENGTH][BOARD_LENGTH];
        for(int i = 0; i < board.length; i++) {
            for(int j = 0; j < board.length; j++) {
                board[i][j] = possibleBoardStates.EMPTY;
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
    public int getHitsLeft() {
        int hitsLeft = 0;
        for( Ship s: ships){
            hitsLeft += s.hits - s.length;
        }
        return hitsLeft;
    }


    /**
     * Makes a shot during Play Mode.
     * @param loc The designator for the shot
     * @return The status of the shot. See the status constants
     * @throws IllegalStateException    The game is not in Play Mode
     * @throws IllegalArgumentException if the arguments is out of bounds of the char array (10 * 10 board) (
     */
    @Override
    public statusAndState makeShot(Location loc) {
        possibleBoardStates value = defensiveBoard[loc.row][loc.col];
        statusAndState result = new statusAndState();
        if(isNotHitVersion(value)) {
            defensiveBoard[loc.row][loc.col] = flipped(value);
            result.boardValue = defensiveBoard[loc.row][loc.col];
            Ship corresponding = find(value);
            corresponding.hits ++;
            if(isShipSunk(loc) == true) {
                result.status = sunk(value);
            }
            return result;
        }
        if(value == possibleBoardStates.EMPTY){
            result.status = Status.HIT;
            result.boardValue = possibleBoardStates.MISS;
            return result;
        }
        else {
            throw new IllegalArgumentException("Location has already been hit");
        }
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
        Ship corresponding = find(value);
        if(corresponding.hits == corresponding.length){
            return true;
        }
        return false;
    }

    private Ship find(possibleBoardStates dir){
        for(Ship x: ships){
            if(x.type == dir){
                return x;
            }
        }
        return null;
    }
    /**
     * It will check if a square doesn't or does contain an existing shot. If the square doesn't contain a shot the method will return true, otherwise it will return false.
     * @return
     */
    public boolean isMakeShotLegal(Location loc) {
       if(notWithinBoard(loc) || offensiveBoard[loc.col][loc.row] == flipped(offensiveBoard[loc.col][loc.row])){
           return false;
       }
        if(offensiveBoard[loc.col][loc.row] !=possibleBoardStates.EMPTY){
            return false;
        }
        return true;
    }

    public boolean notWithinBoard(Location loc){
        return loc.col >=BOARD_LENGTH || loc.col < 0 || loc.row >=BOARD_LENGTH || loc.col < 0;
    }


    public void placeShotResult(possibleBoardStates result, Location loc) {
        if(offensiveBoard[loc.row][loc.col] != possibleBoardStates.EMPTY){
            throw new IllegalArgumentException("Argument is not valid");
        }
        offensiveBoard[loc.row][loc.col] = result;
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
        if(notWithinBoard(ship.loc)){
            throw new IllegalArgumentException("location doesn't exist on board (out of bounds)");
        }
        if(shipDict.containsKey(ship.type)){
            throw new IllegalArgumentException("Ship already exists");
        }
        possibleBoardStates[][] copy = getDeepCopyOfDefensiveBoard();
        int size = ship.length;
        Location loc = new Location();
        loc.col = ship.loc.col;
        loc.row = ship.loc.row;
        while(size-- > 0) {
            if(copy[loc.row][loc.col] != possibleBoardStates.EMPTY){
                throw new IllegalArgumentException("Ship can't be placed because a ships can't overlap (another ship exists on the path)");
            }
            if(ship.direction == Direction.POSDIAGONAL && copy[loc.row+1][loc.col-1] != possibleBoardStates.EMPTY && copy[loc.row+1][loc.col-1] != possibleBoardStates.EMPTY){
                throw new IllegalArgumentException("Ship can't be placed as crossing");
            }
            if(ship.direction == Direction.NEGDIAGONAL && copy[loc.row+1][loc.col-1] != possibleBoardStates.EMPTY && copy[loc.row-1][loc.col+1] != possibleBoardStates.EMPTY){
                throw new IllegalArgumentException("Ship can't be placed as crossing");
            }
            copy[loc.row][loc.col] = ship.type;
            loc.row += directionSlope.get(ship.direction).row;
            loc.col += directionSlope.get(ship.direction).col;
        }
        defensiveBoard = copy;
        ships.add(ship);
    }



    private possibleBoardStates[][] getDeepCopyOfDefensiveBoard() {
        possibleBoardStates[][] copy = new possibleBoardStates[BOARD_LENGTH][BOARD_LENGTH];

        for(int i = 0; i < copy.length; i++) {
            for(int j = 0; j < copy[i].length; i++) {
                copy[i][j] = defensiveBoard[i][j];
            }
        }
        return copy;
    }
}