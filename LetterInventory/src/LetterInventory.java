/*
*Jonathan Wang
*30 September 2022
*Section: AJ
*This program counts the number of occurences of each letter given a String
*/

public class LetterInventory {

    private int[] charCount; // The number of each character in LetterInventory
    private int size; // Size of the letterInventory
    public static final int LETTERS_IN_ALPHABET = 26; // The number of letters in the alphabet

    /*
     * Post: Takes a string data and constructs a new LetterInventory
     *       counting the number of occurences of each character.
     *       Ignores case and makes sure the character
     *       is a letter in the english alphabet
     */
    public LetterInventory(String data) {
        charCount = new int[LETTERS_IN_ALPHABET];
        size = 0;

        for (int i = 0; i < data.length(); i++) {
            char iChar = data.charAt(i);
            int val = getLetterValue(iChar);
            if (isValidLetter(iChar)) {
                charCount[val]++;
                size++;
            }
        }
    }

    /*
     * Pre: letter must be a valid character in the english alphabet
     *      (Throws IllegalArgumentException if not).
     * Post: Returns the number of occurences of letter in the current 
     *       instance of the object
     */
    public int get(char letter) throws IllegalArgumentException {
        int letterValue = getLetterValue(letter);

        if (!isValidLetter(letter)) {
            throw new IllegalArgumentException("Invalid character passed to method: " + letter);
        }

        return charCount[letterValue];
    }

    /*
     * Pre: letter must be a character in the english alphabet
     *      AND value must be nonnegative.
     *      (Throws IllegalArgumentException if either are false)
     * Post: Sets the number of occurences of letter to value
     */
    public void set(char letter, int value) throws IllegalArgumentException {
        if (!isValidLetter(letter) || value < 0) {
            throw new IllegalArgumentException("Invalid Arguments! Char: " + letter + " Value: " + value);
        }

        int letterValue = getLetterValue(letter);
        int sizeIncrease = value - charCount[letterValue];
        charCount[letterValue] = value;
        size += sizeIncrease;
    }

    /*
     * Post: Returns the number of letters currently stored in this inventory
     */
    public int size() {
        return size;
    }

    /*
     * Post: Returns true if this inventory is empty (size = 0)
     */
    public boolean isEmpty() {
        return (size() == 0);
    }

    /*
     * Pre: Second LetterInventory is not null (Throws NullPointerException)
     * Post: Returns a new LetterInventory containing the total number of
     *       letter occurences within both LetterInventories.
     */
    public LetterInventory add(LetterInventory other) {
        LetterInventory ret = new LetterInventory("");
        for (int i = 0; i < charCount.length; i++) {
            char curLetter = intToLetter(i);
            int curCount = this.get(curLetter);
            curCount += other.get(curLetter);
            ret.set(curLetter, curCount);
        }
        return ret;
    }

    /*
     * Pre: All letter occurences in LetterInventory other must be lower than
     *      letter occurences in the current LetterInventory (returns null)
     * Post: Subtracts letter occurences in this by letter occurences
     *       in other LetterInventory
     */
    public LetterInventory subtract(LetterInventory other) {
        String diffString = "";
        for (int i = 0; i < charCount.length; i++) {
            char curChar = intToLetter(i);
            int difference = this.get(curChar) - other.get(curChar);

            if (difference < 0) {
                return null;
            }

            for (int j = 0; j < difference; j++) {
                diffString += curChar;
            }
        }

        return new LetterInventory(diffString);
    }

    /*
     * Post: Returns a String containing all the letters stored in the Inventory
     */
    public String toString() {
        String ret = "[";
        for (int i = 0; i < charCount.length; i++) {
            for (int j = 0; j < charCount[i]; j++) {
                ret += (char) ('a' + i);
            }
        }
        ret += "]";
        return ret;
    }

    /*
     * Post: Takes any char letter and returns true
     *       if the letter is in the english alphabet
     */
    private boolean isValidLetter(char letter) {
        int letterValue = getLetterValue(letter);
        return (0 <= letterValue && 25 >= letterValue);
    }

    /*
     * Post: Takes any char letter and returns an int corresponding to it's letter
     *       value. If 0 <= getLetterValue(letter) <= 25
     *       then letter is within the english alphabet
     */
    private int getLetterValue(char letter) {
        return Character.toLowerCase(letter) - 'a';
    }

    /*
     * Post: Takes an int value corresponding to a letter in the alphabet
     *       and converts it into a character in the english alphabet
     */
    private char intToLetter(int value) {
        return (char) (value + 'a');
    }

    /*
     * Main method for debugging code
     */
    public static void main(String[] args) {
        Test1.main(null);
        Test2.main(null);
        Test3.main(null);
    }
}
