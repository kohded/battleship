public interface PlayerInterface {
    /**
     * Returns a reference to the ship's offensive board.
     * @return a reference to defensive board.
     */
    public possibleBoardStates[][] getOffensiveBoard();

    /**
     * Returns a reference to the ship's defensive board.
     * @return a reference to defensive board.
     */
    public possibleBoardStates[][] getDefensiveBoard();

    /**
     * Makes a shot during Play Mode.
     * @param loc The designator for the shot
     * @return The status of the shot. See the status constants
     * @throws IllegalStateException    The game is not in Play Mode
     * @throws IllegalArgumentException if the arguments is out of bounds of the char array (10 * 10 board) (
     */
    public statusAndState makeShot(Location loc);
}