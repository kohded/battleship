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
    // return status, a hit, sunk aircraft carrier
    SUNK_AIRCRAFT,
    // return status, a hit, sunk battleship
    SUNK_BATTLESHIP,
    // return status, a hit, sunk cruiser
    SUNK_CRUISER,
    // return status, a hit, sunk destroyer
    SUNK_DESTROYER,
    // return status, location was already played or invalid
    DO_OVER,
    // return status, winner of the game
    WINNER
}