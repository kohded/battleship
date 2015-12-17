import java.util.HashMap;
/**
 * Represents the model for BattleShip Main. This model decides who is the current player initally
 * by counterPlaceShip (during setup mode) and then by changing the player depending on hit / miss. We chose this design
 * to enforce business rules of how the game should alternate. This allowed also to not let the view access the players
 * directly since this class took care of which board to place ship on or make a hit on.
 * @author Endrias Kahssay
 * @author Arnold Koh
 * @author Russell Schneider
 *
 */
public class BattleShipModel {
    ConfigLoader config;
    private Player player1;
    private Player player2;
    private int counterPlaceShip;
    private Player currentPlayer;
    private Player otherPlayer;
    // used a hashmap to create a strong correaltion between ships, and their length
    private HashMap<possibleBoardStates, Integer> shipLengthDic;

    /**
     * Constructs BattleShipModel with the players
     */
    public BattleShipModel(ConfigLoader config) {
        this.config = config;
        player1 = new Player(this.config.getLength());
        player2 = new Player(this.config.getLength());
        currentPlayer = player1;
        otherPlayer = player2;
        player1.player = Players.PLAYER1;
        player2.player = Players.PLAYER2;
    }

    /**
     * Gets the current player
     * @return <tt>Players</tt> player1 or player2
     */
    Players getCurrentPlayer() {
        if(player1 == currentPlayer) {
            return player1.player;
        }
        else {
            return player2.player;
        }
    }

    /**
     * Correlates ship length to ship
     * @param type possibleBoardStates of the ships
     * @return <tt>int</tt> of the ships length
     */
    public int getShipLengthDic(possibleBoardStates type) {
        if(shipLengthDic == null) {
            shipLengthDic = new HashMap<>();
            shipLengthDic.put(possibleBoardStates.AIRCRAFT_CARRIER, 5);
            shipLengthDic.put(possibleBoardStates.BATTLESHIP, 4);
            shipLengthDic.put(possibleBoardStates.CRUISER, 3);
            shipLengthDic.put(possibleBoardStates.DESTROYER, 2);
            shipLengthDic.put(possibleBoardStates.DESTROYER2, 2);
            shipLengthDic.put(possibleBoardStates.SUBMARINE,3);
            shipLengthDic.put(possibleBoardStates.MINI_SUBMARINE,1);
            shipLengthDic.put(possibleBoardStates.MINI_SUBMARINE2,1);
        }
        return shipLengthDic.get(type);
    }

    /**
     * Makes a shot during Play Mode of one player, marking the defensive board of the player ( a shot was made against it)
     * the other player.
     * For example, one player (A) makes a "shot" a some location on the board, designated by a letter (A-J) and a number (1-10).
     * The corresponding location on other player's (B's) defensive grid is checked for a vessel in that square.
     * The outcome of the play is a "hit" or "miss" reported back to the player A.
     * The return status of the "shot" is recorded on A's offensive grid. All "hits" are recorded on B's defensive grid.
     * If the "hit" marks the final unmarked square for a vessel, the "hit" return status includes an indication of the vessel type which has been sunk.
     * How the game alternates depends on a boolean in BattleShip config. If the boolean alternateOnHit is true, then the game alternates but if its false the players
     * only switch when one player missses.
     * @param loc The designator for the shot
     * @return The status of the shot. See the status constants
     * @throws IllegalStateException    The game is not in Play Mode
     * @throws IllegalArgumentException if the arguments is out of bounds (depending on config)
     */
    public Status makeShot(Location loc) {
        if (!setupFinished()){
            throw new IllegalStateException("Game is still in setup mode");
        }
        if(winner() != null) {
            throw new IllegalStateException("Game is over");
        }
        if(!currentPlayer.isMakeShotLegal(loc)) {
            return Status.DO_OVER;
        }
        statusAndState state = otherPlayer.makeShot(loc);
        currentPlayer.placeShotResult(state.boardValue, loc);
        if(config.isSwitchEveryShot()){
            switchPlayers();
        }
        else if(state.status == Status.MISS) {
            switchPlayers();
        }
        return state.status;
    }

    /**
     * Gets the value of the offesnive board of the player
     * @ param player which player's board to print
     * @ return board status at location
     */
    public possibleBoardStates[][] getPlayerOffBoard(Players player) {
        if(player1.player.equals(player)) {
            return player1.getOffensiveBoard();
        }
        else {
            return player2.getOffensiveBoard();
        }
    }

    /**
     * Gets the value of the deffensive board of the player
     * @ param player which player's board to print
     * @ return board status at location
     */
    public possibleBoardStates[][] getPlayerDefBoard(Players player) {
        if(player1.player.equals(player)) {
            return player1.getDefensiveBoard();
        }
        else {
            return player2.getDefensiveBoard();
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
    public void placeShip(possibleBoardStates ship, Direction d, Location loc) {
        Ship pack = new Ship();
        pack.direction = d;
        pack.type = ship;
        pack.length = getShipLengthDic(pack.type);
        pack.loc = loc;
        currentPlayer.placeShip(pack);
        counterPlaceShip++;
        if(counterPlaceShip == config.getAllowedShips().size()) {
            switchPlayers();
        }
        if(counterPlaceShip == config.getAllowedShips().size() * 2) {
            switchPlayers();
        }
    }

    /**
     * Returns whether setting up finished or not.
     * @return set up finished or not
     */
    public boolean setupFinished() {
        if(counterPlaceShip >= config.getAllowedShips().size()*2) {
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Switch players between player1 and player2
     */
    private void switchPlayers() {
        Player temp = otherPlayer;
        otherPlayer = currentPlayer;
        currentPlayer = temp;
    }

    /**
     * Gets the winner of the game.
     * @return winner
     */
    public Players winner() {
        if(player1.getHitsLeft() == 0) {
            return Players.PLAYER2;
        }
        if(player2.getHitsLeft() == 0) {
            return Players.PLAYER1;
        }
        return null;
    }
}