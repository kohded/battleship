interface playerInterface {
    /**
     * Returns a reference to the ship's offensive board.
     *
     * @return a reference to defensive board.
     */
    char[][] getOffensiveBoard();

    /**
     * Returns a reference to the ship's defensive board.
     *
     * @return a reference to defensive board.
     */
    char[][] getShipDefensiveBoard();

    /**
     * Returns the number of hits this player successfuly made on the player.
     * @return number of hits
     */
    int getShotsTaken();

    /**
     * Returns whether a cell has been hit from the defensive board.
     *  @param x
     * the  x (horizontal) coordinate to check at
     * @param y
     * the y (vertical) coordinate to check at
     * @throws IllegalArgumentException if the arguments is out of bounds of the char array (10 * 10 board)
     * @return true if the coordinate has been hit / missed before.
     */
    boolean isHit(int x, int y);

    /**
     * Makes a shot during Play Mode.
     * @param loc The designator for the shot
     * @return The status of the shot. See the status constants
     * @throws IllegalStateException The game is not in Play Mode
     * @throws IllegalArgumentException if the arguments is out of bounds of the char array (10 * 10 board) (
     */
    public Status makeShot(Location loc);


    /**
     * Clear all ships from offensive and defensive grid, and reset all counters.
     */
    public void resetBoards();
}