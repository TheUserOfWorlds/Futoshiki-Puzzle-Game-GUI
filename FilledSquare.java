package uk.ac.sussex.ianw.fp.futoshiki;
/**
 * enable value to be returned 
 * 
 * @author ianw
 * @version 1.0
 */
public class FilledSquare extends FutoshikiSquare
{
    private int value;
    
    public FilledSquare(int val)
    {
        value = val;
    }
    
    public int getValue()
    {
        return value;
    }
    
    @Override
    public String getSymbol()
    {
        return "" + value;
    }
    
    @Override
    public boolean isFilled()
    {
        return true;
    }
}
