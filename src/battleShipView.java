import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Represents the view for BattleShip
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
    }

    private static void mapShipToString(String rep, possibleBoardStates state){
        if(config.getAllowedShips().contains(state)){
            possibleBoardState.put(rep,state);
        }
    }
    /**
     * Correlates various ship states with characters
     */
    private static void  initGetStringForState(){
        getStringForState = new HashMap<possibleBoardStates,String>();
        getStringForState.put(possibleBoardStates.AIRCRAFT_CARRIER, "A");
        getStringForState.put(possibleBoardStates.BATTLESHIP, "B");
        getStringForState.put(possibleBoardStates.CRUISER, "C");
        getStringForState.put(possibleBoardStates.DESTROYER, "D1");
        getStringForState.put(possibleBoardStates.DESTROYER2, "D2");
        getStringForState.put(possibleBoardStates.HIT, "H");
        getStringForState.put(possibleBoardStates.MISS, "M");
    }

    /**
     * Set direction for ship placement
     */
    private static void initalizeStringDirection() {
        stringDirection = new HashMap<String, Direction>();
        stringDirection.put("U", Direction.UP);
        stringDirection.put("L", Direction.LEFT);
        if(config.isDiagonalAllowed()) {
            stringDirection.put("PD", Direction.POSDIAGONAL);
            stringDirection.put("ND", Direction.NEGDIAGONAL);
        }
    }

    /**
     * Initialize Battleship game
     */
    public static void setup(){
        config = new ConfigLoader("funorama.bshp.txt");
        model = new BattleShipModel(config);
        initPossibleBoardState();
        initGetStringForState();
        initalizeStringDirection();
        Location loc = new Location();
        System.out.println("Get ready to play battleship! There are 5 possible ships you can place! 1 Aircraft Carrier (5 squares) , 1 Battleship (4 squares), 1 Cruiser (3 squares), 2 Destroyers (2 squares each).");
        System.out.println("Type in the name of ship (A for Aircraft, B for BattleShip, C for cruiser, D1 for destroyer1, and D2 for destroyer 2). " +
                "Also, type in the ship direction and the starting coordinate of where you want the ship to be placed. Possible directions are negative diagonal (represented by ND), " +
                "postive diagonal (PD), up (U), left (L). E.g. Type in C,PD,D,1 - C stands for cruiser, PD means starting from D 1 it will place the ship diagonally (upper right)," +
                " D 1 is the starting coordinate for the ship placement on the board.");
        /**
         * Prompts players for ship placement
         */
        Scanner input = new Scanner(System.in);
        boolean player1Done = false;
        while (!model.setupFinished()) {
            Players currentPlayer = model.getCurrentPlayer();
            if(currentPlayer!= Players.PLAYER1 && !player1Done){
                System.out.println("Player 1 is done putting ships. This is what player's one board looks like");
                printBoard(model.getPlayerDefBoard(Players.PLAYER1));
                player1Done = true;
            }
            Ship ship;
            System.out.println(currentPlayer+ " turn to place ships");
            printBoard(model.getPlayerDefBoard(model.getCurrentPlayer()));
            String user = input.nextLine();
            String[] split = user.split(",");
            if (split.length < 4) {
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
     * Prompts players for moves after ships have been placed
     */
    public static void play(){
        Scanner input = new Scanner(System.in);
        Location shot  = new Location();
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
                System.out.println(status);
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
        String lines =" ";
       for (int i = 0; i< Player.BOARD_LENGTH; i++){
           lines += "----";
       }
        printNum();
        System.out.println();
        System.out.println(lines);
        for (int i = 0; i < board.length; i ++) {
            System.out.print((char) ('A' +i));
            printOneRow(board[i]);
            // will not print if its the last row
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
     * Places ship by direction and coordinates
     * @param shipAndLoc board location of ship
     * @return pack ship placement on the grid
     */
    private static Ship getShipAndLocation(String [] shipAndLoc){
        Ship pack = new Ship();
        if (possibleBoardState.containsKey(shipAndLoc[0])) {
            pack.type = possibleBoardState.get(shipAndLoc[0]);
        }
        else {
            throw new IllegalArgumentException("Ship doesn't exist. Try again");
        }
        if (stringDirection.containsKey(shipAndLoc[1])) {
            pack.direction = stringDirection.get(shipAndLoc[1]);
        }
        else {
            throw new IllegalArgumentException("Direction doesn't exist. Try again");
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
                System.out.print(" "+getStringForState.get(value)+" ");
            }
            // will not print if last column
            System.out.print("|");
        }
        System.out.println();
    }

    /**
     * Print out coordinates showing players' moves
     */
    private static void printNum(){

        for(int i=1; i<=Player.BOARD_LENGTH; i++){
            System.out.print("  " + i + " ");
        }
    }

    /**
     * Place ships by specified coordinates
     * @param type type of ship
     * @param dir direction of ship placement
     * @param row coordinate row
     * @param col coordinate column
     */
    private static void testMethod(possibleBoardStates type, Direction dir, int row, int col){
        Location x = new Location();
        x.col = col;
        x.row = row;
        model.placeShip(type,dir,x);
    }

    /**
     * Establish ship coordinates within the grid
     */
    private static void fill(){
        config = new ConfigLoader("funorama.bshp.txt");
        model = new BattleShipModel(config);
        testMethod(possibleBoardStates.AIRCRAFT_CARRIER,Direction.POSDIAGONAL,8,0);
        testMethod(possibleBoardStates.BATTLESHIP,Direction.NEGDIAGONAL,4,4);
        testMethod(possibleBoardStates.DESTROYER,Direction.UP,8,5);
        testMethod(possibleBoardStates.DESTROYER2,Direction.LEFT,7,7);
        testMethod(possibleBoardStates.CRUISER,Direction.LEFT,8,8);

    }

    public static Location testGameOver(int row, int col){
        Location x = new Location();
        x.row = row;
        x.col = col;
        return x;
    }
    private static void hitter(){
        model.makeShot(testGameOver(9,0));
        model.makeShot(testGameOver(8,1));
        model.makeShot(testGameOver(7,2));
        model.makeShot(testGameOver(6,3));
        model.makeShot(testGameOver(5,4));
        model.makeShot(testGameOver(4,4));
        model.makeShot(testGameOver(3,3));
        model.makeShot(testGameOver(2,2));
        model.makeShot(testGameOver(1,1));
        model.makeShot(testGameOver(9,5));
        model.makeShot(testGameOver(8,5));
        model.makeShot(testGameOver(7,7));
        model.makeShot(testGameOver(7,6));
        model.makeShot(testGameOver(9,6));
        model.makeShot(testGameOver(9,7));
        model.makeShot(testGameOver(9,8));
       // model.makeShot(testGameOver(9,8));
    }

    private static void hitterTwo(){
        model.makeShot(testGameOver(8,0));
        model.makeShot(testGameOver(7,1));
        model.makeShot(testGameOver(7,2));
        model.makeShot(testGameOver(6,3));
        model.makeShot(testGameOver(5,4));
        model.makeShot(testGameOver(4,4));
        model.makeShot(testGameOver(3,3));
        model.makeShot(testGameOver(2,2));
        model.makeShot(testGameOver(1,1));
        model.makeShot(testGameOver(8,5));
        model.makeShot(testGameOver(7,5));
        model.makeShot(testGameOver(7,7));
        model.makeShot(testGameOver(7,6));
        model.makeShot(testGameOver(8,6));
        model.makeShot(testGameOver(8,7));
        model.makeShot(testGameOver(8,8));
        // model.makeShot(testGameOver(9,8));
    }
    /**
     * Establish ship coordinates within the grid
     */
    private static void fill8(){
        config = new ConfigLoader("funorama.bshp.txt");
        model = new BattleShipModel(config);
        testMethod(possibleBoardStates.AIRCRAFT_CARRIER,Direction.POSDIAGONAL,8,0);
        testMethod(possibleBoardStates.BATTLESHIP,Direction.NEGDIAGONAL,4,4);
        testMethod(possibleBoardStates.DESTROYER,Direction.UP,8,5);
        testMethod(possibleBoardStates.DESTROYER2,Direction.LEFT,7,7);
        testMethod(possibleBoardStates.CRUISER,Direction.LEFT,8,8);

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
