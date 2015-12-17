import java.io.FileInputStream;
import java.util.HashSet;
import java.util.Properties;

/**
 * Created by anjeo on 12/16/2015.
 */
public class ConfigLoader {
    private static FileInputStream file;

    public boolean isDiagonalAllowed() {
        return diagonalAllowed;
    }

    private static HashSet<possibleBoardStates> allowedShips;

    public boolean isSwitchEveryShot() {
        return switchEveryShot;
    }

    public static HashSet<possibleBoardStates> getAllowedShips() {
        return allowedShips;
    }

    private boolean switchEveryShot;
    private boolean diagonalAllowed;
    private int length;

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
            System.out.println(length);
            System.out.println(switchEveryShot);
            System.out.println(allowedShips);
            System.out.println(diagonalAllowed);
        } catch (Exception e) {
            throw new IllegalArgumentException("Config file is invalid", e);
        }
    }

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
    public int getLength() {
        return length;
    }

}

