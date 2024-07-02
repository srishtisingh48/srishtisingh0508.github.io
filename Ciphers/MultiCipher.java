// This class represents the MultiCipher class and extends the Cipher class.
import java.util.*;
import java.io.*;

public class MultiCipher extends Cipher {
    private List<Cipher> ciphers;

    // Behavior:
    //  - Constructs a MultiCipher Cipher with a list of ciphers
    // Parameter:
    // - The list of ciphers inputted by the user
    // Exception:
    // - Throws an IllegalArgumentException if the list of ciphers is null
    public MultiCipher(List<Cipher> ciphers) {
        if(ciphers == null) {
            throw new IllegalArgumentException();
        }
        this.ciphers = new ArrayList<>(ciphers);
    }
    
    // Behavior:
    // - This method applies the MultiCipher Cipher to the input.
    //   The input is encrypted by being encrypted through each of the ciphers in the ordered 
    //   list. 
    // Parameter:
    // - The input represents the string the user wants to encrypt
    // Return:
    // - The input encrypted is returned
    public String encrypt(String input) {
        String encrypted = ciphers.get(0).encrypt(input);
        for(int i = 1; i < ciphers.size(); i++){
            encrypted = ciphers.get(i).encrypt(encrypted);
        }
        return encrypted;
    }


    // Behavior:
    //  - This method applies the inverse of the MultiCipher Cipher to the input. The input 
    //    is decrypted by being  decrypted through each of the ciphers in the ordered list 
    //    backwards
    // Parameter:
    // - The input represents the string the user wants to decrypt
    // Return:
    // - The input decrypted is returned
    public String decrypt(String input) {
        String decrypted = ciphers.get(ciphers.size() - 1).decrypt(input);
        for(int i = ciphers.size() - 2; i >= 0; i--){
            decrypted = ciphers.get(i).decrypt(decrypted);
        }
        return decrypted;
    }

}
