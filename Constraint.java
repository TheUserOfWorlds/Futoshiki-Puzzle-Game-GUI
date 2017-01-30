package uk.ac.sussex.ianw.fp.futoshiki;

/**
 * Abstract class Constraint -
 * Hold the two squares, and ensure there is a test, and a way of getting a
 * symbol
 * 
 * @author ianw
 * @version 1.1
 */
public abstract class Constraint
{
    //coordinates of squares involved in this constraint
    protected int row1;
    protected int column1;
    protected int row2;
    protected int column2;
    
    private final Futoshiki myPuzzle;
    
    public Constraint(int r1, int c1, int r2, int c2, Futoshiki puzzle)
    {
        row1 = r1;
        column1 = c1;
        row2 = r2;
        column2 = c2;
        myPuzzle = puzzle;
    }
    
    public FutoshikiSquare getFirstSquare()
    {
        return myPuzzle.getSquare(row1, column1);
    }
    
    public FutoshikiSquare getSecondSquare()
    {
        return myPuzzle.getSquare(row2, column2);
    }
    
    public abstract boolean isOK();
    public abstract String getSymbol();
}
