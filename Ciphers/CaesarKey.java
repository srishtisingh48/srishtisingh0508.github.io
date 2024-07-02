// This class represents the CaesarKey class and extends the Substitution class.
public class CaesarKey extends Substitution {
    private String key;
    private String shifter;

    // Behavior:
    // - Constructs a CaesarKey Cipher with a key and a shifter. The
    //   shifter places the key at the front followed by the other different characters 
    //   in the encodable range. The shifter does not contain duplicate characters from the key.
    // Parameter:
    // - The key inputted by the user represents the characters that will be placed before 
    //   the other different characters in the encodable range
    // Exception:
    // - Throws an IllegalArgumentException if the key inputted by the user is empty, contains 
    //   an invalid characteror, or has duplicate characters
    public CaesarKey(String key){
        if(key.length() == 0) {
            throw new IllegalArgumentException();
        }
        for(int i = 0; i < key.length(); i++) {
            if((int)(key.charAt(i)) < Cipher.MIN_CHAR || (int)(key.charAt(i)) > Cipher.MAX_CHAR) {
                throw new IllegalArgumentException();
            }
            for(int j = i + 1; j < key.length(); j++){
                if(key.charAt(i) == key.charAt(j)){
                    throw new IllegalArgumentException();
                }
            } 
        }
        this.key = key;
        this.shifter = "";
        shifter += key;
        for(int j = 0; j < TOTAL_CHARS; j++){
            String temp = Character.toString((MIN_CHAR + j));
            if(key.contains(temp)){
                // do nothing
            } else {
                shifter += temp;
            }
        }
        super.setShifter(shifter);
    }
}

