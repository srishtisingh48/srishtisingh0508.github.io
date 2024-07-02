// This class works to create a game called Absurdle. The game will consider the
// list of all possible secret words. When the user makes a guess, Absurdle
// will prune the list as little as possible so the game can last as long as possible.
// This game is a variant of the game Wordle.   

import java.util.*;
import java.io.*;

public class Absurdle  {
    public static final String GREEN = "🟩";
    public static final String YELLOW = "🟨";
    public static final String GRAY = "⬜";

    // [[ ALL OF MAIN PROVIDED ]]
    public static void main(String[] args) throws FileNotFoundException {
        Scanner console = new Scanner(System.in);
        System.out.println("Welcome to the game of Absurdle.");

        System.out.print("What dictionary would you like to use? ");
        String dictName = console.next();

        System.out.print("What length word would you like to guess? ");
        int wordLength = console.nextInt();

        List<String> contents = loadFile(new Scanner(new File(dictName)));
        Set<String> words = pruneDictionary(contents, wordLength);

        List<String> guessedPatterns = new ArrayList<>();
        while (!isFinished(guessedPatterns)) {
            System.out.print("> ");
            String guess = console.next();
            String pattern = record(guess, words, wordLength);
            guessedPatterns.add(pattern);
            System.out.println(": " + pattern);
            System.out.println();
        }
        System.out.println("Absurdle " + guessedPatterns.size() + "/∞");
        System.out.println();
        printPatterns(guessedPatterns);
        
    }

    // [[ PROVIDED ]]
    // Prints out the given list of patterns.
    // - List<String> patterns: list of patterns from the game
    public static void printPatterns(List<String> patterns) {
        for (String pattern : patterns) {
            System.out.println(pattern);
        }
    }

    // [[ PROVIDED ]]
    // Returns true if the game is finished, meaning the user guessed the word. Returns
    // false otherwise.
    // - List<String> patterns: list of patterns from the game
    public static boolean isFinished(List<String> patterns) {
        if (patterns.isEmpty()) {
            return false;
        }
        String lastPattern = patterns.get(patterns.size() - 1);
        return !lastPattern.contains("⬜") && !lastPattern.contains("🟨");
    }

    // [[ PROVIDED ]]
    // Loads the contents of a given file Scanner into a List<String> and returns it.
    // - Scanner dictScan: contains file contents
    public static List<String> loadFile(Scanner dictScan) {
        List<String> contents = new ArrayList<>();
        while (dictScan.hasNext()) {
            contents.add(dictScan.next());
        }
        return contents;
    }

    // Behavior:
    // - This method gets all the words from the dictionary and removes any duplicates and only 
    //   keeps the words that are the length specified by the user.
    // Parameters: 
    // - contents: list of words from a specified dictionary file
    // - wordLength: the length of the word the user would like to guess
    // Returns: 
    // - set: the words from the dictionary, with no duplicates, that are the length chosen by the user
    // Exceptions: 
    // - if the user inputs a word length that is less than one, an IllegalArgumentException is thrown
    public static Set<String> pruneDictionary(List<String> contents, int wordLength) {
        if (wordLength < 1) {
            throw new IllegalArgumentException();
        }
        Set<String> prunedContents = new TreeSet<>();
        for (int i = 0; i < contents.size(); i++) {
            if (contents.get(i).length() == wordLength) {
                prunedContents.add(contents.get(i));
            }
        }
        return prunedContents;
    }

    // Behavior:
    // - This method finds the next set of words that will be considered and gets the pattern 
    //   (associated with the largest number of words) for the guess. The set of words will be 
    //   updated to store the largest set of words which will be considered next
    // Parameters: 
    // - guess: the word the user would like to guess 
    // - words: the words from the dictionary, with no duplicates, that are the length chosen by the user
    //   which will then be updated to store the largest set of words associated with the pattern returned
    // - wordLength: the length of the word the user would like to guess
    // Returns: 
    // - string: the pattern of the guess (which has the largest number of words associated with it)
    // Exceptions: 
    // - if the set of words inputted by the user is empty or if the length of the guess is incorrect, 
    //   an IllegalArgumentException will be thrown
    public static String record(String guess, Set<String> words, int wordLength) {
        if (words.isEmpty() || guess.length() != wordLength) {
            throw new IllegalArgumentException();
        }
        Map<String, Set<String>> patternToWords = new TreeMap<>();

        // Matches each pattern associated with a set of words in the map
        for (String word : words) {
            String pattern = patternFor(word, guess);
            if (patternToWords.containsKey(pattern)) {
                patternToWords.get(pattern).add(word);
            } else if (!patternToWords.containsKey(pattern)) {
                Set<String> setOfWords = new TreeSet<String>();
                setOfWords.add(word);
                patternToWords.put(pattern, setOfWords);
            }
        }
        // Gets the pattern with the largest set of words 
        String finalPattern = findLongestSetPattern(patternToWords);

        // Replaces the set of words inputted by the user with the largest set of words 
        words.clear();
        for (String finalWord: patternToWords.get(finalPattern)) {
            words.add(finalWord);
        }
        return finalPattern;
    }

    // Behavior:
    // - This method finds the pattern associated with the largest number of words
    // Parameters: 
    // - wordsToPattern: Each possible pattern is associated with a set of possible words
    // Returns: 
    // - string: the pattern of the guess (which has the largest number of words associated with it)
    //   If there are multiple patterns with the same largest number of words, the pattern that appears
    //   first in alphabethical order will be chosen.
    public static String findLongestSetPattern(Map<String, Set<String>> patternToWords){
        String finalPattern = "";
        int numOfWords = 0;
        for (String pattern : patternToWords.keySet()) {
            if (patternToWords.get(pattern).size() > numOfWords) {
                numOfWords = patternToWords.get(pattern).size();
                finalPattern = pattern;
            }
        }
        return finalPattern;
    }

    // Behavior:
    // - This method finds the pattern consiting of green, yellow, and gray squares for a given word
    //   and guess. (The color of the squares works with the same rules as Wordle).
    // Parameters: 
    // - word: the word the user is trying to guess
    // - guess: the word the user would like to guess
    // Returns: 
    // - string: the pattern consisting of green, yellow, and gray squares. The green squares represent the 
    //   letters of the guess in the correct spot. The yellow squares represent the letters of the guess
    //   which are in the word but that are in an incorrect spot. The gray squares represent the letters of 
    //   the guess which are not in the word.
    public static String patternFor(String word, String guess) {
        String[] guessArray = new String[guess.length()];
        Map<Character, Integer> charToCount = new TreeMap<>();

        for (int i = 0; i < guess.length(); i++) {
            Character wordChar = word.charAt(i); 
            if (charToCount.containsKey(wordChar)) {
                charToCount.put(wordChar, charToCount.get(wordChar) + 1);
            } else if (!charToCount.containsKey(wordChar)) {
                charToCount.put(wordChar, 1);
            }
        }

        // Add the green squares - correct letters in the correct spot
        for (int i = 0; i < guess.length(); i++) {
            Character wordChar = word.charAt(i); 
            Character guessChar = guess.charAt(i); 
            if (guessChar == wordChar && charToCount.get(guessChar) > 0) {
                guessArray[i] = GREEN;
                charToCount.put(wordChar, charToCount.get(wordChar) - 1);
            } 
        }

        // Add the yellow squares - correct letters in the incorrect spot
        for (int i = 0; i < guess.length(); i++) { 
            Character guessChar = guess.charAt(i);
            if (charToCount.containsKey(guessChar) && charToCount.get(guessChar) > 0 && guessArray[i] != GREEN) {
                guessArray[i] = YELLOW;
                charToCount.put(guessChar, charToCount.get(guessChar) - 1);
            } 
        }
        
        // Add the gray squares = incorrect letters in the incorrect spot
        String pattern = "";
        for (int i = 0; i < guessArray.length; i++) {
            if (guessArray[i] != GREEN && guessArray[i] != YELLOW) {
                guessArray[i] = GRAY;
            } 
            pattern += guessArray[i];
        }   
        return pattern;  

    }
}
