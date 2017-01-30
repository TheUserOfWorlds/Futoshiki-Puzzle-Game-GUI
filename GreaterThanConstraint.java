package uk.ac.sussex.ianw.fp.futoshiki;
/**
 * If there are filled squares, enable testing
 * 
 * @author ianw
 * @version 1.0
 */
public class GreaterThanConstraint extends Constraint
{
    public GreaterThanConstraint(int r1, int c1, int r2, int c2, Futoshiki puzzle)
    {
        super(r1, c1, r2, c2, puzzle);
    }
    
    @Override
    public boolean isOK()
    {
        FutoshikiSquare square1 = getFirstSquare();
        FutoshikiSquare square2 = getSecondSquare();
        if (square1.isFilled() && square2.isFilled()) {
            return ((FilledSquare)square1).getValue() > ((FilledSquare)square2).getValue();
        }
        else {
            return true;
        }
    }
    
    @Override
    public String getSymbol()
    {
        if (row1 == row2) { // row constraint
            return ">";
        }
        else {
            return "V";
        }
    }
}
