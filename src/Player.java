public class Player implements PlayerInterface {
    ArrayList<Ship> ships;
    /**
     * Returns a reference to the ship's offensive board.
     * @return a reference to defensive board.
     */
    @Override
    public char[][] getOffensiveBoard() {
        return new char[0][];
    }

    /**
     * Returns a reference to the ship's defensive board.
     * @return a reference to defensive board.
     */
    @Override
    public char[][] getShipDefensiveBoard() {
        return new char[0][];
    }

    /**
     * Returns the number of hits this player successfuly made on the player.
     * @return number of hits
     */
    @Override
    public int getShotsTaken() {
        return 0;
    }

    ///**
    // * Returns whether a cell has been hit from the defensive board.
    // * @param x the  x (horizontal) coordinate to check at
    // * @param y the y (vertical) coordinate to check at
    // * @return true if the coordinate has been hit / missed before.
    // * @throws IllegalArgumentException if the arguments is out of bounds of the char array (10 * 10 board)
    // */
    //@Override
    //public boolean isHit(int x, int y) {
    //    return false;
    //}

    /**
     * Makes a shot during Play Mode.
     * @param loc The designator for the shot
     * @return The status of the shot. See the status constants
     * @throws IllegalStateException    The game is not in Play Mode
     * @throws IllegalArgumentException if the arguments is out of bounds of the char array (10 * 10 board) (
     */
    @Override
    public Status makeShot(Location loc) {
        return null;
    }

    public void placeShotResult(Status x) {
        // depending on status, update offensive board
    }

    /**
     * Clear all ships from offensive and defensive grid, and reset all counters.
     */
    @Override
    public void resetBoards() {

    }

    private void isMakeShotLegal() {
        // current player
    }
}