import java.util.*;
import java.io.*;

// This class represents a tree of Huffman nodes with its leaves consisting of a character.

public class HuffmanCode {

    private HuffmanNode overallRoot;

    // Constructs a HuffmanCode tree using a list of frequencies inputted by the user where  
    // the index represents the Ascii Value and the value at that index reprents the frequency
    // of the character associated with that Ascii Value.
    public HuffmanCode(int[] frequencies) {
        Queue<HuffmanNode> pq = new PriorityQueue<>();
        for (int i = 0; i < frequencies.length; i++) {
            if (frequencies[i] > 0) {
                pq.add(new HuffmanNode(frequencies[i], i));
            }
        }
        overallRoot = createTree(pq);
    }
    
    // This is a helper method for the HuffmanCode constructor that takes in a list of
    // frequencies. It creates a tree using a priority queue and continues to return a 
    // HuffmanNode until the full tree is created.  
    private HuffmanNode createTree(Queue<HuffmanNode> pq) {
        if (pq.size() == 1) {
            return pq.remove();
        }

        HuffmanNode left = pq.remove();
        HuffmanNode right = pq.remove();
        HuffmanNode combinedNode = new HuffmanNode(
            left.frequency + right.frequency, left, right, -1);

        pq.add(combinedNode);
        return createTree(pq);
    }

    // Constructs a HuffmanCode tree using the input file inputted by the user. 
    // The input file provided by the user represents a pre-order traversal.
    public HuffmanCode(Scanner input) {
        while (input.hasNextLine()) {
            int asciiValue = Integer.parseInt(input.nextLine());
            String code = input.nextLine();
            overallRoot = readTree(input, code, asciiValue, overallRoot);
        }
    }

    // This is a helper method for the HuffmanCode constructor that takes in an input file. 
    // It creates the tree using the input file, the code representing the path for
    // the tree we still need to go through, the asciiValue, and the root representing where
    // we are in the tree. The method continues to return a HuffmanNode until the full tree
    // is created. The input file provided by the user represents a pre-order traversal.
    private HuffmanNode readTree(Scanner input, String code, int asciiValue, HuffmanNode root) {
        if (code.length() == 0) {
            return new HuffmanNode(-1, asciiValue);
        }
        if (root == null) {
            root = new HuffmanNode(-1, -1);
        }
        if (root != null) {
            if (code.charAt(0) == '1') {
                root.right = readTree(input, code.substring(1), asciiValue, root.right);
            } else if (code.charAt(0) == '0') {
                root.left = readTree(input, code.substring(1), asciiValue, root.left);
            } 
        } 
        return root;

    }

    // This method stores the current HuffmanCode tree into an output file inputted by the user.
    // The Ascii value of the character will be in one line and the encoding of that character
    // will be in the next line. The output file represents a pre-order traversal.
    public void save(PrintStream output) {
        save(output, overallRoot, "");
    }

    // This is a helper method for the save method which takes in the ouput file inputted by the
    // user, the root representing where in the tree we are, and binary which represents the
    // encoding so far to get to the character. The output file represents a pre-order traversal.
    private void save(PrintStream output, HuffmanNode root, String binary) {
        if (root != null) {
            if (root.right == null && root.left == null) {
                output.println(root.asciiValue);
                output.println(binary);
            } else {
                save(output, root.left, binary + "0");
                save(output, root.right, binary + "1");
            }
        }
    }

    // This method translates the individual bits from the input inputted by the user and 
    // translates it into the corresponding characters which is then printed in the output file.
    public void translate(BitInputStream input, PrintStream output) {
        HuffmanNode root = overallRoot;
        while (input.hasNextBit()) {
            int nextBit = input.nextBit();
            if (root != null) {
                if (nextBit == 1) {
                    root = root.right;
                } else if (nextBit == 0) {
                    root = root.left;
                }
                if (root.right == null && root.left == null) {
                    output.write((char)root.asciiValue);
                    root = overallRoot;
                }
            } 
        }     
    }

    // This class reprensents a single node in the tree which either represents the frequency 
    // or the frequency and an asciiValue.
    private static class HuffmanNode implements Comparable<HuffmanNode> {
        public HuffmanNode left;
        public HuffmanNode right;
        public final int frequency;
        public final int asciiValue;

        // Constructs a HuffmanNode using the frequency which represents the total frequency 
        // of the given characters and asciiValue which is associated with a character.
        public HuffmanNode(int frequency, int asciiValue) {
            this(frequency, null, null, asciiValue);
        }

        // Constructs a HuffmanNode using the frequency which represents the total frequency 
        // of the given characters, asciiValue which is associated with a character, and 
        // the right and left node (the children) of the node.
        public HuffmanNode(int frequency, HuffmanNode left, HuffmanNode right, int asciiValue) {
            this.frequency = frequency;
            this.left = left;
            this.right = right;
            this.asciiValue = asciiValue;
        }

        // This method allows for the HuffmanNodes with the lower frequencies to be 
        // prioritized first. It returns a positive number if this frequency is greater 
        // than other frequency and visa versa. If the frequency of this HuffmanNode and other
        // Huffman node are the same, 0 is returned.
        public int compareTo(HuffmanNode other) {
            return this.frequency - other.frequency;
        }
    }
    
}
