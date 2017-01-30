package uk.ac.sussex.ianw.fp.futoshiki;
/**
 * If filledSquares, run the test
 * 
 * @author ianw
 * @version 1.0
 */
public class LessThanConstraint extends Constraint
{
    public LessThanConstraint(int r1, int c1, int r2, int c2, Futoshiki puzzle)
    {
        super(r1, c1, r2, c2, puzzle);
    }
    
    public boolean isOK()
    {
        FutoshikiSquare square1 = getFirstSquare();
        FutoshikiSquare square2 = getSecondSquare();
        if (square1.isFilled() && square2.isFilled()) {
            return ((FilledSquare)square1).getValue() < ((FilledSquare)square2).getValue();
        }
        else {
            return true;
        }
    }
    
    public String getSymbol()
    {
        if (row1 == row2) { // row constraint
            return "<";
        }
        else {              //column constraint
            return "^";
        }
    }
}
