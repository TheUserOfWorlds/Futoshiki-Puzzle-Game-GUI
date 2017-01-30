package uk.ac.sussex.ianw.fp.futoshiki;
/**
 * Unfilled, returning a space
 * 
 * @author ianw
 * @version 1.0
 */
public class EmptySquare extends FutoshikiSquare
{
    public EmptySquare()
    {
        //do nothing
    }
    
    @Override
    public String getSymbol()
    {
        return " ";
    }
    
    @Override
    public boolean isFilled()
    {
        return false;
    }
}
