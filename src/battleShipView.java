import java.util.HashMap;
import java.util.Scanner;

/**
 * Represents the view for BattleShip.
 */
public class battleShipView {
    static HashMap<String, possibleBoardStates> possibleBoardState;
    static HashMap<possibleBoardStates,String> getStringForState;
    static HashMap<String, Direction> stringDirection;

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
    }

    private static void initalizeStringDirection() {
        stringDirection = new HashMap<String, Direction>();
        stringDirection.put("u", Direction.UP);
        stringDirection.put("l", Direction.LEFT);
        stringDirection.put("pd", Direction.POSDIAGONAL);
        stringDirection.put("nd", Direction.NEGDIAGONAL);
    }

    public static void main(String[] args) {
        initPossibleBoardState();
         initGetStringForState();
        initalizeStringDirection();
        System.out.println("Get ready to play battleship! There are 5 possible ships you can place! 1 Aircraft Carrier (5 squares) , 1 Battleship (4 squares)," +
                "1 Cruiser (3 squares)+ 2 Destroyers (2 squares each)");
        System.out.println("Type in the name of ship (A for Aircraft, B for BattleShip, C for cruiser, D1 for destoryer1 and D2 for destroyer 2. Also, type in the" +
                "coordiante you want the ship to be placed to be starting from and direction. Possible directions are negative diagonal (reperesnted by nd), postive diagonal (pd), up (u), left (l). E.g" +
                "Type in C,u,A,4. This is translated to A, 4 on the board as starting value for where to place the ship. C stands for cruiser. PD means starting from " +
                "A 4 it will place the ship diagonally (upper right) ");
        BattleShipMain model = new BattleShipMain();
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
            if (possibleBoardState.containsKey(split[0])) {
                ship.type = possibleBoardState.get(split[0]);
            } else {
                System.out.println("Ship doesn't exist. Try again");
                continue;
            }
            if (stringDirection.containsKey(split[1])) {
                ship.direction = stringDirection.get(split[1]);
            } else {
                System.out.println("Direction doesn't exist. Try again");
                continue;
            }
            int row = split[2].charAt(0) - 'A';
            if (row > 9 || row < 0) {
                System.out.println("row is not valid");
                System.out.println(row);
                continue;
            }
            int col = Integer.parseInt(split[3]);
            if (col > 10 || col < 1) {
                System.out.println("col is not valid");
                continue;
            }
            Location loc = new Location();
            loc.col = col - 1;
            loc.row = row;
            try {
                model.placeShip(ship.type, ship.direction, loc);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        while (model.winner() == null) {
            System.out.println(model.getCurrentPlayer() + " turn to make shots on other player. Type in the locaiton on the board for e.g A,4");
            printBoard(model.getPlayerOffBoard(model.getCurrentPlayer()));
            String user = input.nextLine();
            String[] split = user.split(",");
            if (split.length < 2) {
                System.out.println("Try again. Input was wrong");
                continue;
            }
            int row = split[0].charAt(0) - 'A';
            if (row > 9 || row < 0) {
                System.out.println("row is not valid");
                System.out.println(row);
                continue;
            }
            int col = Integer.parseInt(split[1]);
            if (col > 10 || col < 1) {
                System.out.println("col is not valid");
                continue;
            }
            Location shot = new Location();
            shot.row = row;
            shot.col = col - 1;
            try {
                Status status = model.makeShot(shot);
                System.out.println(status);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }


    }

    /**
     * Prints the current state of the  battleship board.
     */
    public static void printBoard(possibleBoardStates [][] board) {
        for (int i = 0; i < board.length; i ++) {
            printOneRow(board[i]);
            // will not print if its the last row
            if (i != board.length -1 ) {
                System.out.println("---+---+---");
            }
        }
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
                System.out.print(" ");
            }
            if(getStringForState.containsKey(value)){
                System.out.print(getStringForState.get(value));
            }
            // will not print if last column
            if (i != boardRow.length -1 ) {
                System.out.print("|");
            }
        }
        System.out.println();
    }
}
