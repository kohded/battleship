/**
 * A helper class for location
 */
class Location {
    public Location(){

    }

    /**
     * Location constructor
     * @param row coordinate row
     * @param col coordinate column
     */
    public Location(int row, int col){
        this.row = row;
        this.col = col;
    }
    /**
     * The row
     */
    int row;
    /**
     * The column
     */
    int col;
}