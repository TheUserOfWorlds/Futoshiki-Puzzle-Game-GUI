package uk.ac.sussex.ianw.fp.futoshiki;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * This class is the updated "model answer" to the second part
 * of the Futoshiki assignment. It includes several extra methods used
 * for a visible futoshiki game .
 
 * @author Rudi Lutz, Vaios
 * @version 2.0
 */
public class Futoshiki
{
    //constant to specify size of puzzle
    public static final int GRIDSIZE = 5;
    
    private final FutoshikiSquare[][] squares;
    private final Constraint[][] rowConstraints;
    private final Constraint[][] columnConstraints;
    
    public Futoshiki()
    {
        squares = new FutoshikiSquare[GRIDSIZE][GRIDSIZE];
        rowConstraints = new Constraint[GRIDSIZE][GRIDSIZE - 1];
        columnConstraints = new Constraint[GRIDSIZE][GRIDSIZE - 1];
        
        //set up initial grid of empty squares
        for (int row = 0; row < GRIDSIZE; row++) {
            for (int column = 0; column < GRIDSIZE; column++) {
                squares[row][column] = new EmptySquare();
            }
        }
        
        //set up initial row constraints (no constraints)
        for (int row = 0; row < GRIDSIZE; row++) {
            for (int column = 0; column < GRIDSIZE - 1; column++) {
                rowConstraints[row][column] = new NoConstraint(row, column, row, column + 1, this);
            }
        }
        
        //set up initial column constraints (no constraints)
        for (int column = 0; column < GRIDSIZE; column++) {
            for (int row = 0; row < GRIDSIZE - 1; row++) {
                columnConstraints[column][row] = new NoConstraint(row, column, row + 1, column, this);
            }
        }
    }
      //set up a puzzle for the user to solve
    public void setConfiguration1() {
        emptyPuzzle();
        setSquare(4,0,4);
        setColumnConstraint(0,1,">");
        setColumnConstraint(4,3,"<");
        setColumnConstraint(4,2,"<");
        setRowConstraint(3,1,"<");
        setRowConstraint(4,2,"<");
        setRowConstraint(1,3,">");
        setRowConstraint(4,0,"<");
    }
     //another puzzle option for the user to solve
    public void setConfiguration2() {
        emptyPuzzle();
        setSquare(2,0,2);
        setSquare(4,3,3);
        setColumnConstraint(0,0,">");
        setColumnConstraint(1,1,"<");
        setColumnConstraint(2,1,"<");
        setRowConstraint(0,3,">");
        setRowConstraint(2,3,">");
        setRowConstraint(3,0,"<");
        setRowConstraint(3,1,">");
        setRowConstraint(4,0,"<");
        setRowConstraint(4,3,"<");
    }
    // replaces current puzzle with a new random easy one
    public void setRandom(){
        Random filler = new Random();
        int numberSquares = filler.nextInt(2) + 1;
        int numberRows = filler.nextInt(2) + 1;
        int numberColumns = filler.nextInt(3) + 1;
        do {
        emptyPuzzle();
        if(numberSquares==1) {
        int square1x = filler.nextInt(5);
        int square1y = filler.nextInt(5);
        setSquare(square1x,square1y,filler.nextInt(5)+1);
        }
        else {
        int square1x = filler.nextInt(5);
        int square1y = filler.nextInt(5);
        int square2x = filler.nextInt(5);
        int square2y = filler.nextInt(5);
        setSquare(square1x,square1y,filler.nextInt(5)+1);
        setSquare(square2x,square2y,filler.nextInt(5)+1);
        }
        if(numberRows == 1){
        int square1x = filler.nextInt(5);
        int square1y = filler.nextInt(4);
        setRowConstraint(square1x,square1y,"<");
        }
        else{
        int square1x = filler.nextInt(5);
        int square1y = filler.nextInt(4);
        setRowConstraint(square1x,square1y,"<");
        int square2x = filler.nextInt(5);
        int square2y = filler.nextInt(4);
        setRowConstraint(square2x,square2y,">");
        }
        if(numberColumns == 1) {
        int square1y = filler.nextInt(5);
        int square1x = filler.nextInt(4);  
        setColumnConstraint(square1y,square1x,"<");
        }
        else if(numberColumns == 2) {
        int square1y = filler.nextInt(5);
        int square1x = filler.nextInt(4);  
        setColumnConstraint(square1y,square1x,"<");
        int square2y = filler.nextInt(5);
        int square2x = filler.nextInt(4);  
        setColumnConstraint(square2y,square2x,">");
        }
        else{
        int square1y = filler.nextInt(5);
        int square1x = filler.nextInt(4);  
        setColumnConstraint(square1y,square1x,"<");
        int square2y = filler.nextInt(5);
        int square2x = filler.nextInt(4);  
        setColumnConstraint(square2y,square2x,">");
        int square3y = filler.nextInt(5);
        int square3x = filler.nextInt(4);  
        setColumnConstraint(square3y,square3x,"<");
        }
        }
        while(!isLegal());
    }
   //returns the position of the first blank square in the futoshiki Puzzle
    public int [] findBlank() {
        int[] blankSquare = new int[2];
                for(int row = 0;row<GRIDSIZE;row++) {
                    for(int column = 0;column<GRIDSIZE;column++) {
                        if(!squares[row][column].isFilled()){
                            blankSquare[0] = row;
                            blankSquare[1] = column;
                            return blankSquare;
                        }
                    }
                }
                return blankSquare;
    }
  
     
      //Solves the puzzle
    public boolean solvePuzzle(int u,int v) {
        if(!isLegal()) {
            return false;
        }
        if(isFull()) {
            return true;
        }
        
        int[] position = findBlank();
        int x = position[0];
        int y = position[1];
        for(int i = 1;i<=GRIDSIZE;i++){
            setSquare(x, y, i);
            if(solvePuzzle(x, y)) {
                return true;
            }
            squares[x][y] = new EmptySquare();
        }
        return false;
    }
    //returns true if the puzzle is filled up, false otherwise
    public boolean isFull(){
        for(int row=1;row<GRIDSIZE;row++){
            for(int column=1;column<GRIDSIZE;column++) {
                if(isBlank(row, column)){
                    return false;
                }
            }
        }
        return true;
    }
     //empties the puzzle
    public void emptyPuzzle() {
        emptySquares();
        emptyRowConstraints();
        emptyColumnConstraints();
    }
    //empties the squares that can contain numbers
    public void emptySquares() {
        for(int row=0;row<GRIDSIZE;row++) {
            for(int column = 0;column<GRIDSIZE;column++){
                emptySquare(row,column);
            }
        }
     
    }
    //empties all row constraints
    public void emptyRowConstraints() {
        for(int row=0;row<GRIDSIZE;row++){
            for(int column = 0;column<GRIDSIZE-1;column++){
                emptyRowConstraint(row,column);
            }
        }
    }
    //empties all column constraints
    public void emptyColumnConstraints() {
        for (int column = 0; column < GRIDSIZE; column++) {
            for (int row = 0; row < GRIDSIZE - 1; row++) {
                emptyColumnConstraint(column,row);
            }
        }
    }
    
    //inserts a square filled with a number value in the puzzle
    public void setSquare(int row, int column, int val)
    {
        squares[row][column] = new FilledSquare(val);      
    }
    //replaces a filled square with an empty one
    public void emptySquare(int row,int column) {
        squares[row][column] = new EmptySquare();
    }
    //inserts a row constraint in the puzzle
    public void setRowConstraint(int row, int col, String relation)
    {
        if (relation.equals("<")) {
            rowConstraints[row][col] = new LessThanConstraint(row, col, row, col + 1, this);
        }
        else if (relation.equals(">")) {
            rowConstraints[row][col] = new GreaterThanConstraint(row, col, row, col + 1, this);
        }
    }
    //inserts a column constraint in the puzzle
    public void setColumnConstraint(int col, int row, String relation)
    {
        if (relation.equals("<")) {
            columnConstraints[col][row] = new LessThanConstraint(row, col, row + 1, col, this);
        }
        else if (relation.equals(">")) {
            columnConstraints[col][row] = new GreaterThanConstraint(row, col, row + 1, col , this);
        }
    }
   
    public void emptyRowConstraint(int row,int col) {
        rowConstraints[row][col] = new NoConstraint(row,col,row,col+1,this);
    }
   
    public void emptyColumnConstraint(int col,int row) {
        columnConstraints[col][row] = new NoConstraint(row,col,row+1,col,this);
    }
    
    public FutoshikiSquare getSquare(int row, int col)
    {
        return squares[row][col];
    }
    //returns the symbol of a row constraint
    public String getRowConstraint(int row,int col){
        return rowConstraints[row][col].getSymbol();
    }
    //returns the symbol of a column constraint
    public String getColumnConstraint(int col,int row){
        return columnConstraints[col][row].getSymbol();
    }
    

       /********************************************************************
     * Returns a String in Ascii for pretty printing
     * @return The string representing the puzzle
     ********************************************************************/
    public String printPuzzle()
    {
        String s = "";
        for (int row = 0; row < GRIDSIZE - 1; row++) {
            s += drawRow(row);
            s += drawColumnConstraints(row);
        }
        s += drawRow(GRIDSIZE - 1);
        return s;
    }
 
    private String printTopBottom()
    {
        String s = "";
        for (int col = 0; col < GRIDSIZE; col++) {
            s += "---";
            if(col < (GRIDSIZE-1)) {
                s += " ";
            }
        }
        return s + "\n";
    }
    

    
    private String drawColumnConstraints(int row) 
    {
            String s = " ";
        for (int col = 0; col < GRIDSIZE; col++) {
             s += columnConstraints[col][row].getSymbol() + " ";
                     if(col < (GRIDSIZE-1)) {
                s += "  ";
            }
        }

        return s + "\n";   
    }
    
    private String drawRow(int row) 
    {
        String s = printTopBottom();
        for (int col = 0; col < GRIDSIZE; col++) {
            s += "|" + squares[row][col].getSymbol() + "|";   
            if (col < GRIDSIZE - 1) {
                s += rowConstraints[row][col].getSymbol();
            }
        }
        return s + "\n" + printTopBottom();
    }

    private boolean isBlank(int row, int col)
    {
        return !squares[row][col].isFilled();
    }
    
    /*********************************************************************** 
     * Lots of paired methods (row/column) or (boolean/String result) below
     ************************************************************************/
    
    private boolean checkRowDuplicates(int row)
    {
        boolean[] found = new boolean[GRIDSIZE + 1]; //1 longer as numbers go up to GRIDSIZE
        for (int col = 0; col < GRIDSIZE; col++) {
            if (!isBlank(row,col)) {
                int val = ((FilledSquare)squares[row][col]).getValue();
                if (!isIllegalValue(val)) {
                    if (found[val]) {
                        return false;
                    }
                    else {
                        found[val] = true;
                    }
                }
            }
        }
        return true;
    }
    
    private List<String> rowDuplicateProblems(int row)
    {
        ArrayList<String> result = new ArrayList();
        boolean[] found = new boolean[GRIDSIZE + 1]; //1 longer as numbers go up to GRIDSIZE
        for (int col = 0; col < GRIDSIZE; col++) {
            if (!isBlank(row,col)) {
                int val = ((FilledSquare)squares[row][col]).getValue();
                if (!isIllegalValue(val)) {
                    if (found[val]) {
                        result.add("Duplicate entry " + val + " on row " + row);
                    }
                    else {
                        found[val] = true;
                    }
                }
            }
        }
        return result;
    }
    
    private boolean checkColumnDuplicates(int col)
    {
        boolean[] found = new boolean[GRIDSIZE + 1]; //1 longer as numbers go up to GRIDSIZE
        for (int row = 0; row < GRIDSIZE; row++) {
            if (!isBlank(row,col)) {
                int val = ((FilledSquare)squares[row][col]).getValue();
                if (!isIllegalValue(val)) {
                    if (found[val]) {
                        return false;
                    }
                    else {
                        found[val] = true;
                    }
                }
            }
        }
        return true;
    }
    
    private List<String> columnDuplicateProblems(int col)
    {
        ArrayList<String> result = new ArrayList();
        boolean[] found = new boolean[GRIDSIZE + 1]; //1 longer as numbers go up to GRIDSIZE
        for (int row = 0; row < GRIDSIZE; row++) {
            if (!isBlank(row,col)) {
                int val = ((FilledSquare)squares[row][col]).getValue();
                if (!isIllegalValue(val)) {
                    if (found[val]) {
                        result.add("Duplicate entry " + val + " on column " + col);
                    }
                    else {
                        found[val] = true;
                    }
                }
            }
        }
        return result;
    }

    private boolean checkRowConstraints(int row)
    {
        for (int col = 0; col < GRIDSIZE - 1; col++) {
            Constraint constraint = rowConstraints[row][col];
            if (!constraint.isOK()) {
                return false;
            }
        }
        return true;
    }
    
    private List<String> rowConstraintProblems(int row)
    {
        ArrayList<String> result = new ArrayList();
        for (int col = 0; col < GRIDSIZE - 1; col++) {
            Constraint constraint = rowConstraints[row][col];
            if (!constraint.isOK()) {                   
                result.add("Row constraint violated at " + row + " "
                                            + col + " and " + row + " " + (col + 1));
            }
        }
        return result;
    }
    
    private boolean checkColumnConstraints(int col)
    {
        for (int row = 0; row < GRIDSIZE - 1; row++) {
            Constraint constraint = columnConstraints[col][row];
            if (!constraint.isOK()) {
                return false;
            }
        }
        return true;
    }
    
    private List<String> columnConstraintProblems(int col)
    {   
        ArrayList<String> result = new ArrayList();
        for (int row = 0; row < GRIDSIZE - 1; row++) {
            Constraint constraint = columnConstraints[col][row];
            if (!constraint.isOK()) {
                result.add("Column constraint violated at " + row + " "
                                            + col + " and " + (row + 1) + " " + col);
            }
        }
        return result;
    }
    
    private boolean checkRow(int row)
    {
        return checkRowDuplicates(row) && checkRowConstraints(row);
    }
    
    private List<String> rowProblems(int row)
    {
        List<String> s = rowDuplicateProblems(row);
        s.addAll(rowConstraintProblems(row));
        return s;
    }
    
    private boolean checkColumn(int col)
    {
        return checkColumnDuplicates(col) && checkColumnConstraints(col);
    }
    
    private List<String> columnProblems(int col)
    {
        List<String> s = columnDuplicateProblems(col);
        s.addAll(columnConstraintProblems(col));
        return s;
    }
    
    private boolean validValues()
    {
        for (int row = 0; row < GRIDSIZE; row++) {
            for (int col = 0; col < GRIDSIZE; col++) {
                if (!isBlank(row,col)) {
                    int value = ((FilledSquare)squares[row][col]).getValue();
                    if (isIllegalValue(value)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
    
    private boolean isIllegalValue(int val)
    {
        return (val < 1) || (val > GRIDSIZE);
    }
    
    private List<String> validValuesString()
    {
        ArrayList<String> result = new ArrayList();
        for (int row = 0; row < GRIDSIZE; row++) {
            for (int col = 0; col < GRIDSIZE; col++) {
                if (!isBlank(row,col)) {
                    int value = ((FilledSquare)squares[row][col]).getValue();
                    if (isIllegalValue(value)) {
                        result.add("Illegal value " + value + " at " + row + " " + col);
                    }
                }
            }
        }
        return result;
    }
    //checks if the puzzle configuration is correct( relations between squares and constraints) returns true or false
    public boolean isLegal()
    {
        boolean result = validValues();
        for (int row = 0; row < GRIDSIZE; row++) {
            result &= checkRow(row);
        }
        for (int col = 0; col < GRIDSIZE; col++) {
            result &= checkColumn(col);
        }
        return result;
    }
    
    public List<String> getProblems()
    {
        List<String> result = validValuesString();
        for (int row = 0; row < GRIDSIZE; row++) {
            result.addAll(rowProblems(row));
        }
        for (int col = 0; col < GRIDSIZE; col++) {
            result.addAll(columnProblems(col));
        }
        return result;
    }
}
