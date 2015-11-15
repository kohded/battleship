/**
 * @author Endrias, @author Arnold Koh, @author rschneider
 */
public interface BattleshipMain {

    /**
     * Determines the winner of the game.
     *
     * @throws IllegalStateException if the game is not over, that is one of the players hasn't run out of ships.
     *
     * @return the winning player
     */
    playerInterface winner();

    /**
     * Checks whether defensive grid of any of the two palyers has no more ships.
     * @return <tt>true</tt> if defensive grid of any of the two palyers has no more ships.
     */
    public boolean checkForWinner();

    /**
     * Returns a reference to the players array, with index 0 being player 1 and index 1 being player 2.
     *
     * @return a reference to the players array.
     */
    playerInterface[] getPlayers();
    /**
     * Makes a shot during Play Mode of one player, marking the offensive board of the player shooting and the defensive board of
     * the other player.
     * For example, one player (A) makes a "shot" a some location on the board, designated by a letter (A-J) and a number (1-10).
     * The corresponding location on other player's (B's) defensive grid is checked for a vessel in that square. T
     * @param loc The designator for the shot
     * @param x 1 for player 1 and 2  for player 2
     * @return The status of the shot. See the status constants
     * @throws IllegalStateException The game is not in Play Mode
     * @throws IllegalArgumentException if the arguments is out of bounds of the char array (10 * 10 board) (
     */
    public playerInterface.Status makeShot(playerInterface.Location loc, int x);

    interface playerInterface {
        /**
         * Places a ship in on defensive board at the starting vertical and
         * horizontal co-ordinates and according to the ships type (length) and
         * direction. The ship will be represnted by contiguous squares in the direction passsed in.
         *
         * @param ship
         *            the type of ship to be added
         * @param d
         *            the enumerated direction of the ship
         * @param startX
         *            the starting x (horizontal) coordinate
         * @param startY
         *            the starting y (vertical) coordinate
         *
         * @throws IllegalArgumentException if the arguments is out of bounds of the defensive char array (10 * 10 board) or if there already exists
         * a ship of that type (for destoryer, it will allow upto two ships of the same kind) or if the the ship can't be drawn (it can't be
         * drawn if the ship intersect or overlap those of any other vessel in the defensive grid)
         *
         */
        void placeShip(ships ship, Direction d, int startX, int startY);


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

        /**
         * A helper class for location
         */
        class Location {
            /**
             * The row
             */
            char row;
            /**
             * The column
             */
            int col;
        }

        enum Direction {

            /**
             * Corresponds to the vertical negative axis.
             */
            VERTICAL,

            /**
             * Corresponds to the horizontal positive axis
             */
            HORIZONTAL,
            DIAGONAL;
        }

        /**
         * Helper enumerated type for return status The SUNK_XXX
         * values indicate HIT. The current player's turn continues
         * until the return status is MISS.
         */
        enum Status {
            // return status, a miss
            MISS,
            // return status, a hit, doesn't sink a ship
            HIT,
            // return status, a hit, sunk destroyer
            SUNK_DESTROYER,
            // return status, a hit, sunk cruiser
            SUNK_CRUISER,
            // return status, a hit, sunk battleship
            SUNK_BATTLESHIP,
            // return status, a hit, sunk aircraft carrier
            SUNK_AIRCRAFT_CARRIER,
            // return status, location was already played or invalid
            DO_OVER
        }
    }

    enum ships {
        /**
         * Battleship.
         */
        BATTLESHIP,
        /**
         * Carrier.
         */
        CARRIER,
        /**
         * Cruiser.
         */
        CRUISER,
        /**
         * Destroyer Ship.
         */
        DESTROYER,
        /**
         * Submarine Ship.
         */
        SUBMARINE,
    }
}
