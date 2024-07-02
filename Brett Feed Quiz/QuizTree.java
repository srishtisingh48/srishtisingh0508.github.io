import java.util.*;
import java.io.*;

// This class represents a tree of quiz choices (which have a score) and with leaves 
// as the possible results (which also have a score). 

public class QuizTree {
    private QuizTreeNode overallRoot;

    // PROVIDED
    // Returns the given percent rounded to two decimal places.
    private double roundTwoPlaces(double percent) {
        return (double) Math.round(percent * 100) / 100;
    }

    // Constructs a quiz using the input file provided by the user. The file should
    // represent a pre-order traversal.
    public QuizTree(Scanner inputFile) {
        overallRoot = createTree(inputFile);
    }

    // A helper method for the QuizTree constructor which creates a tree containing the 
    // different choices, the possible results, and all their scores.
    // This method continues to return the QuizTreeNodes until the full tree is created
    // and takes in the input file provided by the user which is in a pre-order traversal. 
    private QuizTreeNode createTree(Scanner inputFile) {
        if (!inputFile.hasNextLine()) {
            return null;
        }

        String treeNode = inputFile.nextLine();
        int indexOfSlash = treeNode.indexOf("/");
        int indexOfDash = treeNode.indexOf("-");
        int score = Integer.parseInt(treeNode.substring(indexOfDash + 1));
        
        if (indexOfSlash != -1) {
            QuizTreeNode root = new QuizTreeNode(treeNode.substring(0, indexOfDash), score);
            root.left = createTree(inputFile);
            root.right = createTree(inputFile);
            return root;
        } else {
            QuizTreeNode leaf = new QuizTreeNode(treeNode.substring(treeNode.indexOf(":")
                                             + 1, indexOfDash), score);
            return leaf;
        }

    }

    // This method allows the user to take the quiz. The user is prompted with questions 
    // which they  must answer until they get a result. Their score (which is the sum of 
    // all the  scores of all the choices that the user picked and the score of the result)  
    // is also printed with the result. This method takes in the console in order to get the 
    // answers provided by the user.
    public void takeQuiz(Scanner console) {
        takeQuiz(console, overallRoot, 0);
    }
    
    // This is a helper method for takeQuiz which follows the path correlating to the 
    // user's answer (which are case insensitive) to the question until the user gets 
    // a result. The score is also incremented as the user continues to answer questions.
    // The method takes in the console in order to get the answers provided by the user.
    // The root passed into the method allows us to keep track of the current QuizTreeNode
    // we are looking at in the tree.
    private void takeQuiz(Scanner console, QuizTreeNode root, int score) {
        String[] allChoices = root.choices.split("/");
        if (allChoices.length == 1) {
            System.out.println("Your result is: " + allChoices[0]);
            System.out.println("Your score is: " + (score + root.score));
        } else {
            System.out.print("Do you prefer " + allChoices[0] + " or " + allChoices[1] + "? ");
            String answer = console.nextLine().toLowerCase();

            if (answer.equals(allChoices[0])) {
                takeQuiz(console, root.left, score + root.score);
            } else if (answer.equals(allChoices[1])) {
                takeQuiz(console, root.right, score + root.score);
            } else {
                System.out.println("Invalid response; try again.");
                takeQuiz(console, root, score);
            }
        } 
    }

    // This method prints the current quiz to the output file inputted by the user. The 
    // output file will represent a pre-order traversal of the current quiz tree.
    public void export(PrintStream outputFile) {
        export(outputFile, overallRoot);
    }

    // This is a helper method for the export method which takes in the output file inputted 
    // by the user and the root allows us to keep track of the current QuizTreeNode we are 
    // looking at in the tree. The method continues until the entire current quiz is printed 
    // into the output file. The output file will represent a pre-order traversal of the 
    // current quiz tree.
    private void export(PrintStream outputFile, QuizTreeNode root) {
        if (root.choices.indexOf("/") != -1) {
            outputFile.println(root.choices + "-" + root.score);
            export(outputFile, root.left);
            export(outputFile, root.right);
        } else {
            outputFile.println("END:" + root.choices + "-" + root.score);
        }
    }

    // This method replaces the result toReplace (inputted by the user), if in the tree, with
    // a new choices (inputted by the user and is case insensitive). Depending on what the 
    // user chooses as its choice, the tree will either lead to a leftResult or rightResult 
    // (also inputted by the user).
    public void addQuestion(String toReplace, String choices, 
                        String leftResult, String rightResult) {
        
        overallRoot = addQuestion(toReplace, choices, leftResult, rightResult, overallRoot);

    }

    // This is a helper method for addQuestions which takes in the result the user would like 
    // to replace, the choices the user would like to replace toReplace with, the leftResult and  
    // rightResult which represent the possible results for choices, and the root which allows 
    // us to keep track of the current QuizTreeNode we  are looking at in the tree. This 
    // method continues to return each of the QuizTreeNodes in the tree until the question and 
    // its possible results are in the tree.
    private QuizTreeNode addQuestion(String toReplace, String choices, 
                        String leftResult, String rightResult, QuizTreeNode root) {
        if (root != null) {
            int indexOfSlash = root.choices.indexOf("/");
            if (root.choices.equalsIgnoreCase(toReplace) && indexOfSlash == -1) {
                String allChoices = choices.substring(0, choices.indexOf("-"));
                int score = Integer.parseInt(choices.substring(choices.indexOf("-") + 1));
                root = new QuizTreeNode(allChoices, score);
                int leftDash = leftResult.indexOf("-");
                int rightDash = rightResult.indexOf("-");
                int scoreLeft = Integer.parseInt(leftResult.substring(leftDash + 1));
                int scoreRight = Integer.parseInt(rightResult.substring(rightDash + 1));
                root.left = new QuizTreeNode(
                    leftResult.substring(0, leftDash), scoreLeft);
                root.right = new QuizTreeNode(
                    rightResult.substring(0, rightDash), scoreRight);
            } else {
                root.right = addQuestion(
                    toReplace, choices, leftResult, rightResult, root.right);
                root.left = addQuestion(
                    toReplace, choices, leftResult, rightResult, root.left);
            }
        }
        return root;
        
    }

    // This method prints the possible results with its percentages (the total score of the  
    // entire path to get to the result divided by the full score of the tree).
    public void creativeExtension() {
        creativeExtension(overallRoot, 0);
    }

    // This is a helper method for creativeExtension which takes in a root which allows us 
    // to keep track of the current QuizTreeNode we are looking at in the tree and the  
    // leafScore which keeps track of the total score of the entire path to get to one of 
    // the possible results. This method  continues until the total score of the entire path
    // to get to each of the possible results are found.
    private void creativeExtension(QuizTreeNode root, int leafScore) {
        if (root != null) {
            if (root.left == null && root.right == null) {
                leafScore += root.score;
                double percentage = (double)(leafScore) / (double)(sumOfScores()) * 100.00;
                System.out.println(root.choices + ": " + roundTwoPlaces(percentage) + "%");
            } else {
                creativeExtension(root.right, leafScore + root.score);
                creativeExtension(root.left, leafScore + root.score);
            }
        }
    }

    // This is a helper method for creativeExtension that finds the full score of the tree.
    private int sumOfScores() {
        return sumOfScores(overallRoot);
    }

    // This is a helper method of the sumOfScores which takes in a root which allows us to 
    // keep track of the current QuizTreeNode we are looking at in the tree and helps 
    // us account for its score in the full score of the tree. The method continues to 
    // return the scores being adeded up from the choices and results until it goes through
    // the whole tree.
    private int sumOfScores(QuizTreeNode root) {
        if (root == null) {
            return 0;
        } else {
            return root.score + sumOfScores(root.right) + sumOfScores(root.left);
        }
    }

    // This class represents a single node in the tree which can either be a choice in 
    // the quiz or a possible result.
    private static class QuizTreeNode {
        public final String choices;
        public QuizTreeNode left;
        public QuizTreeNode right;
        public final int score;

        // Constructs a QuizTreeNode using the two choices specified by the user and that 
        // individual node's score. This node also has no children.
        public QuizTreeNode(String choices, int score) {
            this(choices, null, null, score);
        }

        // Constructs a QuizTreeNode using the two choices specified by the user, that
        // individual node'sscore, and the right and left node (the children) of this node. 
        public QuizTreeNode(String choices, QuizTreeNode left, QuizTreeNode right, int score) {
            this.choices = choices;
            this.left = left;
            this.right = right;
            this.score = score;
        }
    }
}
