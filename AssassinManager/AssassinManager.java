
/*
 * Jonathan Wang
 * CSE 143 AJ
 * 20 October 2022
 * An AssassinManager creates and 
 * manages a game of assassin
 */
import java.util.*;

public class AssassinManager {
    private AssassinNode graveyard; // Stores the current number people in the graveyard and their killers
    private AssassinNode game; // Stores the names of people in the game and who they're stalking (kill ring)
    private String winner; // Stores the winner of the game. Stores null if no winner

    /*
     * Constructor:
     * Pre: names is nonempty (throws IllegalArgumentExcpetion if false)
     * Post: Constructs a new AssassinManager with the contents of the list
     * ignores any duplicates and caseing is ignored.
     * assigns targets in the order that they are in the list.
     */
    public AssassinManager(List<String> names) {
        if (names == null || names.isEmpty()) {
            throw new IllegalArgumentException("Invalid: names is empty!");
        }

        AssassinNode sentinal = new AssassinNode(null);
        AssassinNode cur = sentinal;
        for (String name : names) {
            cur.next = new AssassinNode(name);
            cur = cur.next;
        }
        game = sentinal.next;
    }

    /*
     * Post: Prints the list of who stalking who.
     * Since game is guarenteed to be non-null, don't need to check if it's null
     */
    public void printKillRing() {
        AssassinNode cur = game;
        AssassinNode next = cur.next;
        while (next != null) {
            System.out.println(cur.name + " is stalking " + next.name);
            cur = cur.next;
            next = next.next;
        }
        System.out.println(cur.name + " is stalking " + game.name);
    }

    /*
     * Post: Prints the list of who killed who in reverse kill order
     * Provides no output if graveyard is empty
     */
    public void printGraveyard() {
        AssassinNode cur = graveyard;
        while (cur != null) {
            System.out.println(cur.name + " was killed by " + cur.killer);
            cur = cur.next;
        }
    }

    /*
     * Pre: Name is in the kill ring (throws IllegalArgumentException otherwise)
     * Game is still going (throws IllegalStateException otherwise)
     * Post: kills the person with the input name,
     * adding the killed person to the graveyard
     */
    public void kill(String name) {
        if (gameOver()) {
            throw new IllegalStateException("Game is over!");
        }

        AssassinNode removal = remove(name);
        if (removal == null) {
            throw new IllegalArgumentException(name + " isn't in the list!");
        }

        removal.next = graveyard;
        graveyard = removal;

        checkWinner();
    }

    /*
     * Post: Returns true if the name is in the kill ring
     * ignores case while searching.
     */
    public boolean killRingContains(String name) {
        return (get(name, game) != null);
    }

    /*
     * Post: Returns true if the name is in the graveyard
     * ignores case while searching.
     */
    public boolean graveyardContains(String name) {
        return (get(name, graveyard) != null);
    }

    /*
     * Post: Returns true if the game is over
     */
    public boolean gameOver() {
        return (winner != null);
    }

    /*
     * Post: returns the winner of the game. Returns null if no winner.
     */
    public String winner() {
        return winner;
    }

    /*
     * Post: Checks if there is a winner.
     * A winner exists if there's only 1 name in the game
     */
    private void checkWinner() {
        if (game.next == null) {
            winner = game.name;
        }
    }

    /*
     * Post: Finds the name in the specific list, ignoring case.
     * If the name doesn't exist in the list, it returns null
     */
    private AssassinNode get(String name, AssassinNode list) {
        AssassinNode cur = list;
        while (cur != null) {
            if (cur.name.equalsIgnoreCase(name)) {
                return cur;
            }
            cur = cur.next;
        }
        return null;
    }

    /*
     * Post: finds and removes the name in the game list, ignoring case.
     * If the name doesn't exist in the list, it returns null.
     * Adds the name of the killer to the list.
     */
    private AssassinNode remove(String name) {
        AssassinNode sentinal = new AssassinNode(null, game);
        AssassinNode cur = sentinal;
        AssassinNode ret = null;

        while (cur != null && cur.next != null) {
            if (cur.next.name.equalsIgnoreCase(name)) {
                ret = cur.next;
                cur.next = cur.next.next;
                ret.killer = cur.name;
            }
            cur = cur.next;
        }

        if (ret != null && ret.killer == null) {
            ret.killer = cur.name;
        }

        game = sentinal.next;
        return ret;
    }
}
