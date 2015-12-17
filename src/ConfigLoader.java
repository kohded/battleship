import java.io.FileInputStream;
import java.util.HashSet;
import java.util.Properties;

/**
 * BattleShip Config Reader used to decide variations between ships, board size, diagonal placement, turn taking.
 * @Author Endrias Kahssay
 */
public class ConfigLoader {
    private static FileInputStream file;

    /**
     * Returns whether diagonal placement is allowed
     * @return is diagonal placement allowed
     */
    public boolean isDiagonalAllowed() {
        return diagonalAllowed;
    }


    private static HashSet<possibleBoardStates> allowedShips;
    /**
     * Gets whether the game should switch every shot.
     * @return whether the game should switch every shot
     */
    public boolean isSwitchEveryShot() {
        return switchEveryShot;
    }

    /**
     * Returns ships in the current game mode.
     * @return
     */
    public static HashSet<possibleBoardStates> getAllowedShips() {
        return allowedShips;
    }

    private boolean switchEveryShot;
    private boolean diagonalAllowed;
    private int length;

    /**
     * Loads a config file and stores its properties as allowed ships, whether diagonal placemnt is allowed, and how the game should alternate.
     * @param name - name of config
     */
    public ConfigLoader(String name){
        allowedShips = new HashSet<possibleBoardStates>();
        try {
            file = new FileInputStream(name);
            Properties prop = new Properties();
            prop.load(file);
            length = Integer.parseInt(prop.getProperty("length"));
            String [] ships = prop.getProperty("ships").split(",");
            addShips(ships);
            switchEveryShot = Boolean.parseBoolean(prop.getProperty("switchOnHit"));
            diagonalAllowed = Boolean.parseBoolean(prop.getProperty("diagonalsAllowed"));
        } catch (Exception e) {
            throw new IllegalArgumentException("Config file is invalid", e);
        }
    }

    /*
    Adds ships passed in to allowedShips hashset.
     */
    private void addShips(String[] configShips) {
        String shipName ="";
        for (int i=0; i<configShips.length; i++){
            try{
                shipName = configShips[i].trim();
                possibleBoardStates value = possibleBoardStates.valueOf(shipName);
                allowedShips.add(value);
            }
            catch(Exception e){
                System.out.println("Ignored ship " + shipName );
            }
        }
    }

    /**
     * Returns board length.
     * @return length
     */
    public int getLength() {
        return length;
    }

}

