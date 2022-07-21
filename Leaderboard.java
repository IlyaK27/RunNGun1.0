/**
 * Final Game Leaderboard Class
 * @Author Ilya Kononov
 * @Date = June 20 2022
 * This class reads text files and stores the information inside them
 * The info inside the text files are previous player names and scores
 * The file is in the structure of:
 * PlayerName1
 * 100
 * PlayerName2
 * 70
 * PlayerName3
 * 32
 */

import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader; 
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;
import java.io.IOException;

public class Leaderboard {
    private Entry[] entries;
    private String fileName;

    /**
     * Construct a Leaderboard object with the leaderboard file.
     * @param fileName The file name of the leaderboard file.
     */
    public Leaderboard(String fileName) {
        this.fileName = fileName;
        this.readEntriesFromFile();
    }

    /**
     * This method gets the number of lines in the leaderboard file.
     * @return The number of lines in the leaderboard file.
     * @throws IOException
     */
    private int getNumLines() throws IOException {
        FileReader file = new FileReader(this.fileName);
        BufferedReader input = new BufferedReader(file);

        int numLines = 0;

        while (input.ready()) {
            numLines++;
            input.readLine();
        }

        input.close();

        return numLines;
    }

    public int getNumEntries() {
        return this.entries.length;
    }
    
    public Entry[] getEntries(){
        return this.entries;
    }

    public void sort() {
        Arrays.sort(this.entries, Entry.COMPARE_REV_BY_SCORE);
    }

    /**
     * This method adds a player's score to the leaderboard.
     * @param name The player's name.
     * @param score The player's score.
     */
    public void addEntry(String name, int score) {
        // Make room for the new entry.
        this.entries = Arrays.copyOf(this.entries, this.entries.length + 1);

        this.entries[this.entries.length - 1] = new Entry(name, score);
        this.sort();
    }

    /**
     * This method updates an existing player's score. If the player is not
     * in the leaderboard, it does nothing.
     * @param name The player's name.
     * @param newScore The player's new score.
     */
    public void updateScore(String name, int newScore) {
        boolean found = false;

        for (int i = 0; i < this.entries.length && !found; i++) {
            if (this.entries[i].getName().equals(name)) {
                this.entries[i].setScore(newScore);
                found = true;
            }
        }

        // Resort the leaderboard if the score was modified.
        if (found) {
            this.sort();
        }
    }

    /**
     * This method adds 1 to an existing player's score. If the player is not
     * in the leaderboard, it does nothing.
     * @param name The player's name.
     */
    /*public void incScore(String name) {
        boolean found = false;

        for (int i = 0; i < this.entries.length && !found; i++) {
            if (this.entries[i].getName().equals(name)) {
                int oldScore = this.entries[i].getScore();
                this.entries[i].setScore(oldScore + 1);
                found = true;
            }
        }

        // Resort the leaderboard if the score was modified.
        if (found) {
            this.sort();
        }
    }*/

    /**
     * This method checks if a player is in the leaderboard.
     * @param name The player's name.
     * @return True if the player is in the leaderboard, false otherwise.
     */
    public boolean hasEntry(String name) {
        boolean found = false;

        for (int i = 0; i < this.entries.length && !found; i++) {
            if (this.entries[i].getName().equals(name)) {
                found = true;
            }
        }

        return found;
    }

    /**
     * This method loads the leaderboard into memory from file.
     */
    private void readEntriesFromFile() {
        int numEntries = 0;

        // Get the number of entries in the leaderboard file.
        try {
            numEntries = this.getNumLines() / 2;
        } catch (IOException ex) {
            System.out.println("Could not get the number of entries in the leaderboard file.");
        }

        this.entries = new Entry[numEntries];

        // Read the leaderboard file.
        try {
            FileReader file = new FileReader(this.fileName);
            BufferedReader input = new BufferedReader(file);

            for (int i = 0; i < numEntries; i++) {
                String name = input.readLine();
                int score = Integer.parseInt(input.readLine());
                this.entries[i] = new Entry(name, score);
            }
            input.close();
        } catch (IOException ex) {
            System.out.println("Could not read the leaderboard file.");
        }
    }

    /**
     * This method saves the leaderboard to file.
     */
    public void saveToFile() {
        File file = new File(this.fileName);
        // Write the leaderboard to file.
        try {
            PrintWriter output = new PrintWriter(file);
            output.print(this);
            output.close();
        } catch(IOException ex) {
            // Handle exceptions.
            System.out.println("File not found: [" + this.fileName + "]");
        }
    }

    /**
     * This method returns the string representation of the leaderboard. It is
     * in the same format as the leaderboard file.
     * @return The string representation of the leaderboard.
     */
    @Override
    public String toString() {
        String info = "";
        for (int i = 0; i < this.entries.length; i++) {
            info += this.entries[i];

            if (i < this.entries.length - 1) {
                info += "\n";
            }
        }
        return info;
    }

    /**
     * The Entry class represents an entry in the leaderboard. It stores the player's
     * name and score.
     * @see Leaderboard
     */
    public static class Entry {
        private String name;
        private int score;

        /**
         * Constructs an Entry object with the player's name and score.
         * @param playerName The player's name.
         * @param score The player's score.
         */
        public Entry(String playerName, int score) {
            this.name = playerName;
            this.score = score;
        }

        public String getName() {
            return this.name;
        }

        public int getScore() {
            return this.score;
        }

        public void setScore(int newScore) {
            this.score = newScore;
        }

        public static final Comparator<Entry> COMPARE_REV_BY_SCORE = new Comparator<Entry>() {
            @Override
            public int compare(Entry e1, Entry e2) {
                return e2.getScore() - e1.getScore();
            }
        };
        /**
         * This method returns the string representation of the entry. It is in the 
         * same format as an entry in the leaderboard file.
         */
        @Override
        public String toString() {
            return this.name + "\n" + score;
        }
    }
}