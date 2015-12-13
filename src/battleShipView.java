import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Represents the view for BattleShip.
 */
public class battleShipView {
    private static HashMap<String, possibleBoardStates> possibleBoardState;
    private static HashMap<possibleBoardStates,String> getStringForState;
    private static HashMap<String, Direction> stringDirection;
    private static BattleShipMain model = new BattleShipMain();

    /**
     * Correlates characters with what state they reperesnt
     */
    private static void initPossibleBoardState() {
        possibleBoardState = new HashMap<String, possibleBoardStates>();
        possibleBoardState.put("A", possibleBoardStates.AIRCRAFT);
        possibleBoardState.put("B", possibleBoardStates.BATTLESHIP);
        possibleBoardState.put("C", possibleBoardStates.CRUISER);
        possibleBoardState.put("D1", possibleBoardStates.DESTROYER);
        possibleBoardState.put("D2", possibleBoardStates.DESTROYER2);
    }
    /**
     * Correlates states with characters they reperesnt
     */
    private static void initGetStringForState() {
        getStringForState = new HashMap<possibleBoardStates,String>();
        getStringForState.put(possibleBoardStates.AIRCRAFT, "A");
        getStringForState.put(possibleBoardStates.BATTLESHIP, "B");
        getStringForState.put(possibleBoardStates.CRUISER, "C");
        getStringForState.put(possibleBoardStates.DESTROYER, "D1");
        getStringForState.put(possibleBoardStates.DESTROYER2, "D2");
        getStringForState.put(possibleBoardStates.HIT_AIRCRAFT, "AH");
        getStringForState.put(possibleBoardStates.HIT_BATTLESHIP, "BH");
        getStringForState.put(possibleBoardStates.HIT_CRUISER, "CH");
        getStringForState.put(possibleBoardStates.HIT_DESTROYER, "D1H");
        getStringForState.put(possibleBoardStates.HIT_DESTROYER2, "D2H");
        getStringForState.put(possibleBoardStates.HIT, "H");
    }

    private static void initalizeStringDirection() {
        stringDirection = new HashMap<String, Direction>();
        stringDirection.put("u", Direction.UP);
        stringDirection.put("l", Direction.LEFT);
        stringDirection.put("pd", Direction.POSDIAGONAL);
        stringDirection.put("nd", Direction.NEGDIAGONAL);
    }

    public static void setup(){
        initPossibleBoardState();
        initGetStringForState();
        initalizeStringDirection();
        Location loc = new Location();
        System.out.println("Get ready to play battleship! There are 5 possible ships you can place! 1 Aircraft Carrier (5 squares) , 1 Battleship (4 squares)," +
                "1 Cruiser (3 squares)+ 2 Destroyers (2 squares each)");
        System.out.println("Type in the name of ship (A for Aircraft, B for BattleShip, C for cruiser, D1 for destoryer1 and D2 for destroyer 2. Also, type in the \n" +
                "coordiante you want the ship to be placed to be starting from and direction. Possible directions are negative diagonal (reperesnted by nd), postive diagonal (pd), up (u), left (l). E.g" +
                "Type in C,A,4,u  = This is translated to A, 4 on the board as starting value for where to place the ship. C stands for cruiser. PD means starting from " +
                "A 4 it will place the ship diagonally (upper right) ");

        Scanner input = new Scanner(System.in);
        while (!model.setupFinished()) {
            System.out.println(model.getCurrentPlayer() + " turn to place ships");
            Ship ship = new Ship();
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
    }

    public static void play(){
        Scanner input = new Scanner(System.in);
        Location shot  = new Location();
        while (model.winner() == null) {
            System.out.println(model.getCurrentPlayer() + " turn to make shots on other player. Type in the locaiton on the board for e.g A,4");
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
    }
    /**
     * Prints the current state of the  battleship board.
     */
    private static void printBoard(possibleBoardStates [][] board) {
        String lines = " ----------------------------------------";
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
    private static Location getColAndRow(String [] rowAndCol){
        Location loc = new Location();
        loc.row = rowAndCol[0].charAt(0) - 'A';
        if (loc.row > 9 || loc.row < 0) {
            throw new IllegalArgumentException("row is not valid");
        }
        loc.col = Integer.parseInt(rowAndCol[1]) - 1;
        if (loc.col > 9 || loc.col < 0) {
            throw new IllegalArgumentException("col is not valid");
        }
        return loc;
    }

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

    private static void printNum(){

        for(int i=1; i<=Player.BOARD_LENGTH; i++){
            System.out.print("  " + i + " ");
        }
    }

    private static void testMethod(possibleBoardStates type, Direction dir, int row, int col){
        Location x = new Location();
        x.col = col;
        x.row = row;
        model.placeShip(type,dir,x);
    }

    private static void fill(){
        testMethod(possibleBoardStates.AIRCRAFT,Direction.POSDIAGONAL,9,0);
        testMethod(possibleBoardStates.BATTLESHIP,Direction.NEGDIAGONAL,4,4);
        testMethod(possibleBoardStates.DESTROYER,Direction.UP,9,5);
        testMethod(possibleBoardStates.DESTROYER2,Direction.LEFT,7,7);
        testMethod(possibleBoardStates.CRUISER,Direction.LEFT,9,8);

    }
    public static void main(String[] args) {
        fill();
        fill();
        setup();
        play();
    }



}
