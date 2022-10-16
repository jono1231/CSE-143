
/*
 * Testcode for AssassinManager
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class AssassinTest {
    private static List<String> list;
    private static AssassinManager manager;
    private static int numErrors;

    public static void main(String[] args) throws FileNotFoundException {
        manager = createNewManager();
        printGame();
        String badString = "Kaymin Cider";

        // TESTING NORMAL INPUTS (These are those that would happen normally)
        System.out.println("Testing benevolent inputs: ");
        System.out.println("Winner (Should be null): " + manager.winner()
                + ", Game Over (Should be false): " + manager.gameOver());
        System.out.println("Kill ring contains (should all be true): "
                + manager.killRingContains(list.get(0)) + " "
                + manager.killRingContains(list.get(0).toLowerCase()) + " "
                + manager.killRingContains(list.get(0).toUpperCase()));

        System.out.println("Testing removal of all things in order, should all be killed by the last guy: ");
        for (String name : list) {
            try {
                manager.kill(name);
            } catch (IllegalStateException e) {
                numErrors++;
                System.out.println("Illegal State Exceptions (should only be 1): " + numErrors);
            }
        }
        System.out.println(
                "printing game, winner should be Alonzo Church, everyone should be killed by him");
        printGame();
        System.out.println("Winner: " + manager.winner() + ", Game Over: " + manager.gameOver());
        System.out.println("Graveyard contains (should all be true): "
                + manager.graveyardContains(list.get(0)) + " "
                + manager.graveyardContains(list.get(0).toLowerCase()) + " "
                + manager.graveyardContains(list.get(0).toUpperCase()));

        // TESTING BAD INPUTS (Those that wanna destroy the code do this)
        manager = createNewManager();
        System.out.println("Testing malevolent inputs: ");
        System.out.println("Testing empty list: ");
        try {
            manager = new AssassinManager(new ArrayList<String>(0));
            throw new NullPointerException("THIS SHOULDNT BE HAPPENING: Manager created with 0 size");
        } catch (IllegalArgumentException e) {
            System.out.println("Empty string passed");
        }

        System.out.println("Kill ring contains (should all be false): "
                + manager.killRingContains(badString) + " "
                + manager.killRingContains(badString.toLowerCase()) + " "
                + manager.killRingContains(badString.toUpperCase()));
        try {
            manager.kill(badString);
            throw new NullPointerException("THIS SHOULDNT BE HAPPENING: Manager killed with invalid string");
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid kill String threw error, it works");
        }
        System.out.println("Graveyard contains (should all be false): "
                + manager.graveyardContains(badString) + " "
                + manager.graveyardContains(badString.toLowerCase()) + " "
                + manager.graveyardContains(badString.toUpperCase()));

        System.out.println("Testing killing from the back");
        for (int i = list.size() - 1; i >= 0; i--) {
            try {
                manager.kill(list.get(i));
            } catch (IllegalStateException e) {
                numErrors++;
                System.out.println("Illegal State Exceptions (should be 2 now): " + numErrors);
            }
        }
        System.out.println(
                "printing game, winner should be Don Knuth, and person at index i should be killed by the i-1 guy");
        printGame();

        // TESTING MISC INPUTS (Shuffled input stuffs)
        System.out.println("Testing shuffling, you can do the calculations yourself if its correct lol: ");
        List<String> shuffle = new ArrayList<>(list);
        Collections.shuffle(shuffle);
        System.out.println(shuffle);
        manager = new AssassinManager(list);
        for (String name : shuffle) {
            try {
                manager.kill(name);
            } catch (IllegalStateException e) {
                numErrors++;
                System.out.println("Illegal State Exceptions (should be 3): " + numErrors);
            }
        }
        System.out.println("Shuffled guy: ");
        printGame();

    }

    // Prints the current game going on.
    public static void printGame() {
        System.out.println("Printing kill ring:");
        manager.printKillRing();
        System.out.println("Printing graveyard:");
        manager.printGraveyard();
        System.out.println();
    }

    public static AssassinManager createNewManager() throws FileNotFoundException {
        Scanner input = new Scanner(new File("names.txt"));
        Set<String> names = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
        List<String> names2 = new ArrayList<>();
        while (input.hasNextLine()) {
            String name = input.nextLine().trim();
            if (name.length() > 0 && !names.contains(name)) {
                names.add(name);
                names2.add(name);
            }
        }
        list = names2;
        return new AssassinManager(Collections.unmodifiableList(names2));
    }
}
