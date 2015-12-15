import java.util.HashMap;

/**
 * Represents the model for BattleShip Main. This model decides who is the current player initally
 * by counterPlaceShip and then by changing the player depending on hit / miss. We chose this design
 * to enforce business rules of how the game should alternate. This allowed also to not let the view access the players
 * directly since this class took care of which board to place ship on  or make a hit on.
 */
public class BattleShipMain {
    private Player player1 = new Player();
    private Player player2 = new Player();
    private int counterPlaceShip;
    private Player currentPlayer;
    private Player otherPlayer;
    // used a hashmap to create a strong correaltion between ships, and their length
    private HashMap<possibleBoardStates, Integer> shipLengthDic;

    /**
     * Constructs BattleShipMain with the players
     */
    public BattleShipMain() {
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
     * Set ship length based on ship
     * @param type possibleBoardStates of the ships
     * @return <tt>int</tt> of the ships length
     */
    public int getShipLengthDic(possibleBoardStates type) {
        if(shipLengthDic == null) {
            shipLengthDic = new HashMap<>();
            shipLengthDic.put(possibleBoardStates.AIRCRAFT, 5);
            shipLengthDic.put(possibleBoardStates.BATTLESHIP, 4);
            shipLengthDic.put(possibleBoardStates.CRUISER, 3);
            shipLengthDic.put(possibleBoardStates.DESTROYER, 2);
            shipLengthDic.put(possibleBoardStates.DESTROYER2, 2);
        }
        return shipLengthDic.get(type);
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
        if(winner() != null) {
            throw new IllegalArgumentException("Game is over");
        }
        if(!currentPlayer.isMakeShotLegal(loc)) {
            return Status.DO_OVER;
        }
        statusAndState state = otherPlayer.makeShot(loc);
        currentPlayer.placeShotResult(state.boardValue, loc);
        if(state.status == Status.MISS) {
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
        if(counterPlaceShip == 5) {
            switchPlayers();
        }
        if(counterPlaceShip == 10) {
            switchPlayers();
        }
    }

    /**
     * Returns whether setting up finished or not.
     * @return set up finished or not
     */
    public boolean setupFinished() {
        if(counterPlaceShip >= 10) {
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