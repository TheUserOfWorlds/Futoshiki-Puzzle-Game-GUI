package uk.ac.sussex.ianw.fp.futoshiki;
/**
 * The empty constraint class
 * 
 * @author ianw
 * @version 1.0
 */
public class NoConstraint extends Constraint
{
    public NoConstraint(int r1, int c1, int r2, int c2, Futoshiki puzzle)
    {
        super(r1, c1, r2, c2, puzzle);
    }
    
    public boolean isOK()
    {
        return true;
    }
    
    public String getSymbol()
    {
        return " ";
    }
}
