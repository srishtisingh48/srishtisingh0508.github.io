// This class represents the Substitution class and extends the Cipher class.
import java.util.*;
import java.io.*;

public class Substitution extends Cipher {
    private String shifter;

    // Behavior: 
    // - Constructs a Substitution Cipher with an empty shifter (which represent an output that 
    //   contains characters corresponding to the input characters).
    public Substitution() {
        shifter = "";
    }

    // Behavior:
    // - Constructs a Substitution Cipher with a shifter
    // Parameter:
    // - The shifter inputted by the user represents an output which contains characters that 
    //   correspond to the input characters
    // Exception: 
    //  - Throws an IllegalArgumentException if the shifter has a duplicate character, does not 
    //    have the same length as the Cipher's encodable range, or if the characters itself are 
    //    not in the encodable range.
    public Substitution(String shifter) {
        if(shifter.length() != Cipher.TOTAL_CHARS) {
            throw new IllegalArgumentException();
        }
        for(int i = 0; i < shifter.length(); i++) {
            if((int)(shifter.charAt(i)) < Cipher.MIN_CHAR || 
                (int)(shifter.charAt(i)) > Cipher.MAX_CHAR){
                throw new IllegalArgumentException();
            }
            for(int j = i + 1; j < shifter.length(); j++){
                if(shifter.charAt(i) == shifter.charAt(j)){
                    throw new IllegalArgumentException();
                }
            } 
        }
        this.shifter = shifter;
    }

    // Behavior:
    // - This method updates the shifter for the Substitution Cipher to the shifter inputted by
    //   the user. 
    // Parameter:
    // - The shifter inputted by the user is an output which contains characters that correspond 
    //   to the input characters 
    // Exception:
    // - Throws an IllegalArgumentException if the shifter has a duplicate character, does not have 
    //   the same length as the Cipher's encodable range, or if the characters itself are not in the
    //   encodable range.
    public void setShifter(String shifter) {
        if(shifter.length() != Cipher.TOTAL_CHARS) {
            throw new IllegalArgumentException();
        }
        for(int i = 0; i < shifter.length(); i++) {
            if((int)(shifter.charAt(i)) < Cipher.MIN_CHAR ||
                (int)(shifter.charAt(i)) > Cipher.MAX_CHAR){
                throw new IllegalArgumentException();
            }
            for(int j = i + 1; j < shifter.length(); j++){
                if(shifter.charAt(i) == shifter.charAt(j)){
                    throw new IllegalArgumentException();
                }
            } 
        }

        this.shifter = shifter;
    }

    // Behavior:
    // - This method applies the Substitution Cipher to the input. Each input character is replaced
    //    with the character in shifter corresponding to the spot in the ordered encodable range. 
    // Parameter:
    // - The input represents a string the user wants to encrypt
    // Return:
    // - The input encrypted is returned
    // Exception:
    //  - Throws an IllegalStateException if the input is null or empty.
    public String encrypt(String input) {
        if(this.shifter.length() == 0 || this.shifter == null){
            throw new IllegalStateException();
        }
        String encrypted = "";
        for(int i = 0; i < input.length(); i++){
            encrypted += shifter.charAt((int)input.charAt(i) - MIN_CHAR);
        }

        return encrypted;
    }

    // Behavior:
    // - This method applies the inverse of the Substitution Cipher to the input. Each input  
    //   character is replaced with the character in the ordered encodable range corresponding 
    //   to the spot in shifter.
    // Parameter:
    //  - The input represents a string the user wants to decrypt
    // Return:
    // - The input decrypted is returned
    // Exception:
    //  - Throws an IllegalStateException if the input is null or empty.
    public String decrypt(String input) {
        if(this.shifter.length() == 0 || this.shifter == null){
            throw new IllegalStateException();
        }
        String decrypted = "";
        for(int i = 0; i < input.length(); i++) {
            int value = shifter.indexOf(input.charAt(i));
            decrypted += (char) (MIN_CHAR + value);
        }
        return decrypted;
    }
}
