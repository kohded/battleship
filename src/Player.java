import java.util.ArrayList;
import java.util.HashMap;

/**
 * Represents an implementation  for player for BattleShip which keeps track of its boards and ships.
 * @author Endrias Kahssay
 * @author Arnold Koh
 * @author Russell Schneider
 */
public class Player implements PlayerInterface {
    ArrayList<Ship> ships;
    possibleBoardStates[][] offensiveBoard;
    possibleBoardStates[][] defensiveBoard;
    public static int BOARD_LENGTH;
    private HashMap<Direction, Location> directionSlope;
    private int hits;
    public Players player;

    /**
     * Setup for player board
     */
    public Player(int length) {
        BOARD_LENGTH = length;
        offensiveBoard = new possibleBoardStates[BOARD_LENGTH][BOARD_LENGTH];
        initialize(offensiveBoard);
        ships = new ArrayList<Ship>();
        defensiveBoard = new possibleBoardStates[BOARD_LENGTH][BOARD_LENGTH];
        initialize(defensiveBoard);
        directionSlopeIni();
    }

    /**
     * Different directions for ship placement
     */
    private void directionSlopeIni(){
        directionSlope = new HashMap<Direction, Location> ();
        directionSlope.put(Direction.UP, new Location(-1,0));
        directionSlope.put(Direction.LEFT, new Location(0,-1));
        directionSlope.put(Direction.UPPER_RIGHT_DIAGONAL, new Location(-1,1));
        directionSlope.put(Direction.UPPER_LEFT_DIAGONAL, new Location(-1,-1));
        directionSlope.put(Direction.RIGHT, new Location(0,1));
        directionSlope.put(Direction.DOWN, new Location(1,0));
        directionSlope.put(Direction.LOWER_LEFT_DIAGONAL, new Location(1,-1));
        directionSlope.put(Direction.LOWER_RIGHT_DIAGONAL, new Location(1,1));
    }

    /**
     * Initialize the playing board
     * @param board
     */
    private void initialize(possibleBoardStates[][] board) {
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
            hitsLeft +=  s.length - s.hits;
        }
        if(ships.size() == 0)
        {
            hitsLeft = 1;
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
        if(value == possibleBoardStates.EMPTY){
            result.status = Status.MISS;
            result.boardValue = possibleBoardStates.MISS;
            return result;
        }
        if(isNotHitVersion(value)) {
            defensiveBoard[loc.row][loc.col] = flipped(value);
            result.boardValue = possibleBoardStates.HIT;
            Ship corresponding = find(value);
            corresponding.hits ++;
            if(isShipSunk(corresponding) == true) {
                result.status = sunk(value);
            }
            else{
                result.status = Status.HIT;
            }
            return result;
        }

        else {
            throw new IllegalArgumentException("Location has already been hit");
        }
    }

    /**
     * Alternate version for when player takes a turn
     * @param state
     * @return
     */
    private boolean isNotHitVersion(possibleBoardStates state) {
        return !(flipped(state) == state);
    }

    /**
     * Record hits on respective ship types
     * @param state state of ship
     * @return state state of ship
     */
    public possibleBoardStates flipped(possibleBoardStates state) {
        if(state == possibleBoardStates.AIRCRAFT_CARRIER) {
            return possibleBoardStates.HIT_AIRCRAFT_CARRIER;
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
        if(state == possibleBoardStates.SUBMARINE) {
            return possibleBoardStates.HIT_SUBMARINE;
        }
        if(state == possibleBoardStates.MINI_SUBMARINE) {
            return possibleBoardStates.HIT_MINI_SUBMARINE;
        }
        if(state == possibleBoardStates.MINI_SUBMARINE2) {
            return possibleBoardStates.HIT_MINI_SUBMARINE2;
        }

        return state;
    }

    /**
     * Reflects whether a ship was sunk
     * @param state state of ship
     * @return status whether ship was sunk
     */
    public Status sunk(possibleBoardStates state) {
        if(state == possibleBoardStates.AIRCRAFT_CARRIER) {
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
        if(state == possibleBoardStates.SUBMARINE) {
            return Status.SUNK_SUBMARINE;
        }
        if(state == possibleBoardStates.MINI_SUBMARINE) {
            return Status.SUNK_MINI_SUBMARINE;
        }
        if(state == possibleBoardStates.MINI_SUBMARINE2) {
            return Status.SUNK_MINI_SUBMARINE2;
        }
        else {
            return Status.SUNK_DESTROYER2;
        }
    }

    /**
     * Checks if a ship  has been sunk.
     * @param sunk The ship to be checked
     * @return true if the ship has been sunk, false if it has not
     */
    private boolean isShipSunk(Ship sunk) {
        if(sunk.hits == sunk.length){
            return true;
        }
        return false;
    }

    /**
     * Finds board states
     * @param dir direction
     * @return ship or null
     */
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
       return !(notWithinBoard(loc) ||offensiveBoard[loc.col][loc.row] !=possibleBoardStates.EMPTY);
    }

    /**
     * Checks if coordinates are in bounds of grid
     * @param loc location
     * @return location
     */
    public boolean notWithinBoard(Location loc){
        return loc.col >=BOARD_LENGTH || loc.col < 0 || loc.row >=BOARD_LENGTH || loc.row < 0;
    }

    /**
     * Place the results of as shot on the offensive board.
     * @param result - result
     * @param loc - location
     */
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
     * a ship of that type  or if the the ship can't be drawn (it can't be
     * drawn if the ship intersect or overlap those of any other vessel in the defensive grid)
     */
    public void placeShip(Ship ship) {
        if(!isNotHitVersion(ship.type)){
            throw new IllegalArgumentException("ship can't be already be hit");
        }
        if(notWithinBoard(ship.loc)){
            throw new IllegalArgumentException("location doesn't exist on board (out of bounds)");
        }
        if(find(ship.type) != null){
            throw new IllegalArgumentException("Ship already exists");
        }
        possibleBoardStates[][] copy = getDeepCopyOfDefensiveBoard();
        int size = ship.length;
        Location loc = new Location();
        loc.col = ship.loc.col;
        loc.row = ship.loc.row;
        while(size-- > 0) {
            if(notWithinBoard(loc)){
                throw new IllegalArgumentException("Ship out of board.");
            }
            if(copy[loc.row][loc.col] != possibleBoardStates.EMPTY){
                throw new IllegalArgumentException("Ship can't be placed because ships can't overlap (another ship exists on the path)");
            }
            if(!CheckUpperDiagonal(loc, ship.direction) || !checkLowerDiagonal(loc, ship.direction)){
                throw new IllegalArgumentException("Ship can't cross over each other ");
            }
            copy[loc.row][loc.col] = ship.type;
            loc.row += directionSlope.get(ship.direction).row;
            loc.col += directionSlope.get(ship.direction).col;
        }
        defensiveBoard = copy;
        ships.add(ship);
    }

    /**
     * Checks that the ship will not cross diagonally (upper).
     * @param loc location
     * @param dir direction
     * @return state of placement
     */
    private boolean CheckUpperDiagonal(Location loc,  Direction dir){
        if(dir != Direction.UPPER_LEFT_DIAGONAL && dir!= Direction.UPPER_RIGHT_DIAGONAL){
            return true;
        }
        Location checkFirstPoint = new Location();
        Location checkSecond = new Location();
        checkFirstPoint.col = loc.col;
        checkFirstPoint.row = loc.row - 1;
        if(notWithinBoard(checkFirstPoint)){
            return true;
        }
        checkSecond.row = loc.row;
        if(dir == Direction.UPPER_LEFT_DIAGONAL){
            checkSecond.col = loc.col + 1;
        }
        else{
            checkSecond.col = loc.col - 1;
        }
        if(notWithinBoard(checkSecond)){
            return true;
        }
        return defensiveBoard[checkFirstPoint.row][checkFirstPoint.col] == possibleBoardStates.EMPTY ||
                defensiveBoard[checkSecond.row][checkSecond.col] == possibleBoardStates.EMPTY;
    }
    /**
     * Checks that the ship will not cross diagonally (lower).
     * @param loc location
     * @param dir direction
     * @return state of placement
     */
    private boolean checkLowerDiagonal(Location loc,  Direction dir){
        if(dir != Direction.LOWER_LEFT_DIAGONAL && dir!= Direction.LOWER_RIGHT_DIAGONAL){
            return true;
        }
        Location checkFirstPoint = new Location();
        Location checkSecond = new Location();
        checkFirstPoint.col = loc.col;
        checkFirstPoint.row = loc.row + 1;
        if(notWithinBoard(checkFirstPoint)){
            return true;
        }
        checkSecond.row = loc.row;
        if(dir == Direction.LOWER_RIGHT_DIAGONAL){
            checkSecond.col = loc.col + 1;
        }
        else{
            checkSecond.col = loc.col - 1;
        }
        if(notWithinBoard(checkSecond)){
            return true;
        }
        return defensiveBoard[checkFirstPoint.row][checkFirstPoint.col] == possibleBoardStates.EMPTY ||
                defensiveBoard[checkSecond.row][checkSecond.col] == possibleBoardStates.EMPTY;
    }
    /**
     * Gets updated board state.
     * @return copy of board state
     */
    private possibleBoardStates[][] getDeepCopyOfDefensiveBoard() {
        possibleBoardStates[][] copy = new possibleBoardStates[BOARD_LENGTH][BOARD_LENGTH];
        for(int i = 0; i < copy.length; i++) {
            for(int j = 0; j < copy[i].length; j++) {
                copy[i][j] = defensiveBoard[i][j];
            }
        }
        return copy;
    }
}