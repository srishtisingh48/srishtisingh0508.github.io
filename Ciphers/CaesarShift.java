// This class represents the CaesarShift class and extends the Substitution class.
import java.util.*;
import java.io.*;

public class CaesarShift extends Substitution {
    private int shift;
    private String shifter;
    
    // Behavior:
    // - Constructs a CaesarShift Cipher with a shift value and a shifter.
    //   The shifter moves all the  characters within the range to the left shift times
    //   and moves the values at the front to the end each time. 
    // Parameter:
    // - The shift inputted by the user represents the value of shifts of characters to 
    //   the left that they want used when encrypting
    // Exception:
    // - Throws an IllegalArgumentException if the shift inputted by the user is less than
    //   or equal to zero. 
    public CaesarShift(int shift) {
        if(shift <= 0) {
            throw new IllegalArgumentException();
        }
        this.shift = shift;
        this.shifter = "";
        Queue<Character> shifterList = new LinkedList<>();
        for(int k = 0; k < TOTAL_CHARS; k++){
            shifterList.add((char)(MIN_CHAR + k));
        }
        int shifterListSize = shifterList.size();
        for(int j = 0; j < shift; j++){
            char temp = shifterList.remove();
            shifterList.add(temp);
        }
        for(int m = 0; m < shifterListSize; m++){
            shifter += shifterList.remove();
        }
        super.setShifter(shifter);
    }
}
