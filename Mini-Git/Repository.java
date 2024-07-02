import java.util.*;
import java.text.SimpleDateFormat;

// A class that represents a Repository. A Repository is characterized by
// a name and a head (the current commit).

public class Repository {
    private String name;
    private Commit head;

    // Behavior: 
    // - Constructs an empty Repository with a name.
    // Parameter:
    // - The name inputted by the user represents the name of the Repository
    // Exception:
    // - Throws an IllegalArgumentException if the name inputted by the user is null or empty
    public Repository(String name) {
        if (name == null || name.equals("")) {
            throw new IllegalArgumentException();
        }
        this.name = name;
    }

    // Returns the id of the current head of this repository and null if the head does not exist
    public String getRepoHead() {
        if(head == null) {
            return null;
        }
        return head.id;
    }

    // Returns the number of commits in the Repository
    public int getRepoSize() {
        int size = 0;
        Commit current = head;
        while (current != null) {
            size++;
            current = current.past;
        }
        return size;
    }

    // Returns a String representation of the Repository name and the current head in the
    // Repository. If there are no commits in the repository, the Repository name and 
    // specification that there are no commits is returned.
    public String toString() {
        if (head == null) {
            return name + " - No commits";
        }
        return name + " - Current head: " + head.toString();
    }

    // Behavior:
    // - Determines whether there exists a commit in this Repository with the id inputted by the 
    //   user.
    // Parameter:
    // - The targetId represents the id of a commit the user wishes to look for in this Repository
    // Return:
    // - Returns true if the id inputted by the user is in the repository and false if that id is  
    //   not in the Repository.
    public boolean contains(String targetId) {
        Commit current = head;
        while (current != null) {
            if (current.id.equals(targetId)) {
                return true;
            }
            current = current.past;
        }
        return false;
    }

    // Behavior and Return: 
    // - Returns a String representation of the user-inputted number most recent commits in the
    //  repository, showing the most recent commit first. If the user asks for more commits than
    //  exists in the Repository, then al commits are returned.
    // Parameter:
    // - the integer inputted by the user represents the number of most recent commits the user 
    //   would like to see
    // Exception:
    // - Throws an IllegalArgumentException if the integer inputted by the user is not positive.
    public String getHistory(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }
        String listCommits = "";
        Commit current = head;
        while (n != 0 && current != null) {
            listCommits += current.toString() + "\n";
            current = current.past;
            n--;
        }
        return listCommits;
    }

    // Behavior:
    // - Creates a new commit which becomes the head of the repository (hisory of commits in the 
    //   repository is maintained)
    // Parameter: 
    // - The message represents the message the user would like to have associated with this commit
    // Return:
    // - The id of the new commit is returned
    public String commit(String message) {
        Commit current = new Commit(message, head);
        head = current;
        return current.id;
    }

    // The commit assoiciated with the id inputted by the user is removed. The history of commits 
    // in the repository is maintained. Returns true if the commit was removed correctly and false 
    // if the commit associated with the id inputted by the user was not in the repository.
    public boolean drop(String targetId) {
        if (head != null) {
            if (head.id == targetId) {
                head = head.past;
                return true;
            } else {
                Commit current = head;
                while (current.past != null && current.past.id != targetId) {
                    current = current.past;
                }
                if (current.past != null) {
                    current.past = current.past.past;
                    return true;
                }
            }
        }
        return false;
    }

    // Behavior:
    // - Combines this and other repository together by adding all the commits in the other
    //   repository to this repository. The history of this repository should be ordered by the 
    //   commits' timestamps from most recent to least recent after the commits in the other 
    //   repository are added to this repository. The other repository becomes an empty respoitory.
    // Parameter:
    // - other represents the other repository the user would like to synchronize with this
    //   repository
    public void synchronize(Repository other) {
       if (this.head == null) {
            this.head = other.head;
            other.head = null;
        } else if (other.head != null) {
            // front case
            if (this.head.timeStamp < other.head.timeStamp) {
                Commit temp = other.head.past;
                other.head.past = this.head;
                this.head = other.head;
                other.head = temp;
            }
            Commit thisCommit = this.head;
            while (other.head != null && thisCommit.past != null) {
                if (thisCommit.past.timeStamp < other.head.timeStamp) {
                    Commit temp = other.head.past;
                    other.head.past = thisCommit.past;
                    thisCommit.past = other.head;
                    other.head = temp;
                }
                thisCommit = thisCommit.past;
            }

            if (thisCommit.past == null) {
                thisCommit.past = other.head;
                other.head = null;
            }
        }
    }
    /**
     * DO NOT MODIFY
     * A class that represents a single commit in the repository.
     * Commits are characterized by an identifier, a commit message,
     * and the time that the commit was made. A commit also stores
     * a reference to the immediately previous commit if it exists.
     *
     * Staff Note: You may notice that the comments in this 
     * class openly mention the fields of the class. This is fine 
     * because the fields of the Commit class are public. In general, 
     * be careful about revealing implementation details!
     */
    public class Commit {

        private static int currentCommitID;

        /**
         * The time, in milliseconds, at which this commit was created.
         */
        public final long timeStamp;

        /**
         * A unique identifier for this commit.
         */
        public final String id;

        /**
         * A message describing the changes made in this commit.
         */
        public final String message;

        /**
         * A reference to the previous commit, if it exists. Otherwise, null.
         */
        public Commit past;

        /**
         * Constructs a commit object. The unique identifier and timestamp
         * are automatically generated.
         * @param message A message describing the changes made in this commit.
         * @param past A reference to the commit made immediately before this
         *             commit.
         */
        public Commit(String message, Commit past) {
            this.id = "" + currentCommitID++;
            this.message = message;
            this.timeStamp = System.currentTimeMillis();
            this.past = past;
        }

        /**
         * Constructs a commit object with no previous commit. The unique
         * identifier and timestamp are automatically generated.
         * @param message A message describing the changes made in this commit.
         */
        public Commit(String message) {
            this(message, null);
        }

        /**
         * Returns a string representation of this commit. The string
         * representation consists of this commit's unique identifier,
         * timestamp, and message, in the following form:
         *      "[identifier] at [timestamp]: [message]"
         * @return The string representation of this collection.
         */
        @Override
        public String toString() {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
            Date date = new Date(timeStamp);

            return id + " at " + formatter.format(date) + ": " + message;
        }

        /**
        * Resets the IDs of the commit nodes such that they reset to 0.
        * Primarily for testing purposes.
        */
        public static void resetIds() {
            Commit.currentCommitID = 0;
        }
    }
}
