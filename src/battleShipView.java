import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Implementation for text based view for BattleShip. Displays instructions on how to play the game, and prints the defensive board of
 * each player, and lets them place ships. Then players make shots on each others defensive board until one of them sinks all the ships.
 * @author Endrias Kahssay
 * @author Arnold Koh
 * @author Russell Schneider
 */
public class battleShipView {
    private static HashMap<String, possibleBoardStates> possibleBoardState;
    private static HashMap<possibleBoardStates,String> getStringForState;
    private static HashMap<String, Direction> stringDirection;
    private static BattleShipModel model;
    private static ConfigLoader config;
    /**
     * Correlates characters with the ships that they represent
     */
    private static void initPossibleBoardState() {
        possibleBoardState = new HashMap<String, possibleBoardStates>();
        mapShipToString("A", possibleBoardStates.AIRCRAFT_CARRIER);
        mapShipToString("B", possibleBoardStates.BATTLESHIP);
        mapShipToString("C", possibleBoardStates.CRUISER);
        mapShipToString("D1", possibleBoardStates.DESTROYER);
        mapShipToString("D2", possibleBoardStates.DESTROYER2);
        mapShipToString("S", possibleBoardStates.SUBMARINE);
        mapShipToString("MS1", possibleBoardStates.MINI_SUBMARINE);
        mapShipToString("MS2", possibleBoardStates.MINI_SUBMARINE2);
    }

    /**
     * Maps a ship to a character if its exists in that game mode.
     * @param rep - string repersentation of ship
     * @param ship - ship
     */
    private static void mapShipToString(String rep, possibleBoardStates ship){
        if(config.getAllowedShips().contains(ship)){
            possibleBoardState.put(rep,ship);
        }
    }
    /**
     * Correlates the various states the board can be with characters that represent them on the board.
     */
    private static void  initGetStringForState(){
        getStringForState = new HashMap<possibleBoardStates,String>();
        for( String key : possibleBoardState.keySet()){
            getStringForState.put(possibleBoardState.get(key),key);
        }
        getStringForState.put(possibleBoardStates.HIT, "H");
        getStringForState.put(possibleBoardStates.MISS, "M");
    }

    /**
     * Correlates Direction with respective character.
     */
    private static void initalizeStringDirection() {
        stringDirection = new HashMap<String, Direction>();
        stringDirection.put("U", Direction.UP);
        stringDirection.put("L", Direction.LEFT);
        stringDirection.put("R", Direction.RIGHT);
        stringDirection.put("D", Direction.DOWN);
        if(config.isDiagonalAllowed()) {
            stringDirection.put("UR", Direction.UPPER_RIGHT_DIAGONAL);
            stringDirection.put("UL", Direction.UPPER_LEFT_DIAGONAL);
            stringDirection.put("LR", Direction.LOWER_RIGHT_DIAGONAL);
            stringDirection.put("LL", Direction.LOWER_LEFT_DIAGONAL);
        }
    }

    /**
     * Facilates Setup mode where each player places ships. Player 1 always comes first and after player 1 is done placing all their ships,
     * player two follows.
     */
    public static void setup(){
        if(config == null){
            config = new ConfigLoader("battleship.bshp.txt");
            model = new BattleShipModel(config);
        }
        initPossibleBoardState();
        initGetStringForState();
        initalizeStringDirection();
        Location loc;
        introduceGame();
        /**
         * Prompts players for ship placement
         */
        Scanner input = new Scanner(System.in);
        boolean player1Done = false;
        while (!model.setupFinished()) {
            Players currentPlayer = model.getCurrentPlayer();
            // print player one's defensive board one last time after placement of ships
            if(currentPlayer!= Players.PLAYER1 && !player1Done){
                System.out.println("Player 1 is done putting ships. This is what player's one board looks like:");
                printBoard(model.getPlayerDefBoard(Players.PLAYER1));
                player1Done = true;
            }
            Ship ship;
            System.out.println(currentPlayer+ " turn to place ships");
            printBoard(model.getPlayerDefBoard(model.getCurrentPlayer()));
            String user = input.nextLine();
            String[] split = user.split(",");
            // expected input should be in the format shipAlias,Direction,Row,Col coordinate
            if (split.length != 4) {
                System.out.println("Try again. Input was wrong");
                continue;
            }
            try{
                ship = getShipAndLocation(split);
            }
            catch(Exception e){
                System.out.println(e.getMessage());
                continue;
            }
            try{
                // the location are at index 2 and 3
                loc = getColAndRow(Arrays.copyOfRange(split,2,4));
            }
            catch(Exception e){
                System.out.println(e.getMessage());
                continue;
            }
            try {
                model.placeShip(ship.type, ship.direction, loc);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        System.out.println("Setup is finished. This is what players two's defensive board looks like");
        printBoard(model.getPlayerDefBoard(Players.PLAYER2));
    }

    /**
     * Introduces the game and gives a basic instruction of how to play the game.
     */
    private static void introduceGame() {
        System.out.print("Get ready to play battleship!");
        System.out.println(" Below are the ships you can place ");
        System.out.println(" text that corresponds to ship -> ship");
        for( String key : possibleBoardState.keySet()){
            System.out.println( key + " --> " + possibleBoardState.get(key) +" ---> " +model.getShipLengthDic(possibleBoardState.get(key)));
        }
        System.out.println("Below are the directions you can place the ship in.");
        System.out.println("text that corresponds to direction -> direction");
        for( String key : stringDirection.keySet()){
            System.out.println( key + " --> " + stringDirection.get(key));
        }
        System.out.println("To place a ship, type in the the alias for the ship, followed by alias for direction, and then its starting coordinates from the board. The row should be the same" +
                "\n as the character that is at the far left of that row, and the number should match the number far top of the row. Each should be sepearted by a comma.");
        System.out.println("E.g. Type in C,UR,D,1  to place cruiser,starting from D,1 diagonally (upper right)");
    }

    /**
     *Facilates play mode where each player make shots on each others board until one of sinks the other
     * person's ships. When they switch turns is determined by the game type. In Funorama and Option 2, players switch when one player
     * misses. In option 1, each player alternates.
     */
    public static void play(){
        Scanner input = new Scanner(System.in);
        Location shot;
        System.out.println("Welcome to play mode.");
        if(config.isSwitchEveryShot()){
            System.out.println("Each player will alternate at making shots on other player.");
        }
        else{
            System.out.println("One player will make shots until they miss and then players will switch.");
        }
        System.out.println(" Game is over when one " +
        "person sinks all the other person's ships. Type in where you want to make the shot. For e.g A,4 would make a shot at A,4. A hit will be marked as H on the board " +
                "and a miss will be recorded as M.");
        while (model.winner() == null) {
            System.out.println(model.getCurrentPlayer() + " turn to make shots on other player. Type in the location on the board for e.g A,4");
            printBoard(model.getPlayerOffBoard(model.getCurrentPlayer()));
            String user = input.nextLine();
            String[] split = user.split(",");
            if (split.length < 2) {
                System.out.println("Try again. Input was wrong");
                continue;
            }
            try{
                shot = getColAndRow(split);
            }
            catch(Exception e){
                System.out.println(e.getMessage());
                continue;
            }
            try {
                Status status = model.makeShot(shot);
                if(status != Status.MISS && status != Status.HIT && status != status.DO_OVER )
                {
                    System.out.println("HIT. "+ status);
                }
                else{
                    System.out.println(status);
                }
            }
            catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        System.out.println(model.winner() + " Congratulations! You are the winner!");
    }
    /**
     * Prints the current state of the  Battleship board
     */
    private static void printBoard(possibleBoardStates [][] board) {
        String lines ="--";
       for (int i = 0; i< Player.BOARD_LENGTH; i++){
           lines += "----";
       }
        printNum();
        System.out.println();
        System.out.println(lines);
        for (int i = 0; i < board.length; i ++) {
            System.out.print((char) ('A' +i) + " ");
            printOneRow(board[i]);
            if (i != board.length -1 ) {
                System.out.println(lines);
            }
        }
    }

    /**
     * Accesses board square input by user
     * @param rowAndCol coordinates of player's move
     * @return loc location of move on grid
     */
    private static Location getColAndRow(String [] rowAndCol){
        Location loc = new Location();
        loc.row = rowAndCol[0].charAt(0) - 'A';
        if (loc.row >  Player.BOARD_LENGTH -1 || loc.row < 0) {
            throw new IllegalArgumentException("row is not valid");
        }
        loc.col = Integer.parseInt(rowAndCol[1]) - 1;
        if (loc.col >  Player.BOARD_LENGTH -1 || loc.col < 0) {
            throw new IllegalArgumentException("col is not valid");
        }
        return loc;
    }

    /**
     * Converts string board location to location object and returns it.
     * @param shipAndLoc board location of ship in string
     * @return pack location object
     */
    private static Ship getShipAndLocation(String [] shipAndLoc){
        Ship pack = new Ship();
        if (possibleBoardState.containsKey(shipAndLoc[0])) {
            pack.type = possibleBoardState.get(shipAndLoc[0]);
        }
        else {
            throw new IllegalArgumentException("Ship doesn't exist. Try again.");
        }
        if (stringDirection.containsKey(shipAndLoc[1])) {
            pack.direction = stringDirection.get(shipAndLoc[1]);
        }
        else {
            throw new IllegalArgumentException("Direction doesn't exist. Try again.");
        }
        return pack;
    }

    /**
     * Prints one row of the boardRow.
     *
     * @param boardRow what row to print
     */
    private static void printOneRow(possibleBoardStates [] boardRow ) {

        for (int i = 0; i < boardRow.length; i++) {
            possibleBoardStates value = boardRow[i];
            if(value == possibleBoardStates.EMPTY){
                System.out.print("   ");
            }
            if(getStringForState.containsKey(value)){
                String state = getStringForState.get(value);
                // add two spaces to make it "Fit" in the center
                if(state.length() == 1){
                    state += " ";
                    state = " " + state;
                }
                // avoid collapse of board
                if(state.length() == 2 ){
                    state += " ";
                }
                System.out.print(state);
            }
            System.out.print("|");
        }
        System.out.println();
    }

    /**
     * Print out  1 to board size on the top.
     */
    private static void printNum(){

        for(int i=1; i<=Player.BOARD_LENGTH; i++){
            System.out.print("  " + i + " ");
        }
    }


    /**
     * Main method
     * @param args
     */
    public static void main(String[] args) {
        setup();
        play();
    }



}
