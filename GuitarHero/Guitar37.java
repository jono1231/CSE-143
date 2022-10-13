/* 
 * Jonathan Wang
 * 13 October 2022
 * CSE 143 AJ
 * A Guitar37 emulates a guitar with 37 unique notes.
*/
public class Guitar37 implements Guitar {
   public static final String KEYBOARD = "q2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/' "; // keyboard layout
   private GuitarString[] strings; // Storage of guitar strings
   private int ticCount; // Number of tics that have occurred

   /*
    * Constructor:
    * Creates a Guitar37 with strings corresponding to notes on a piano.
    */
   public Guitar37() {
      strings = new GuitarString[37];
      for (int i = 0; i < strings.length; i++) {
         double twoPow = Math.pow(2.0, (i - 24) / 12.0);
         double pitch = 440 * twoPow;
         GuitarString newString = new GuitarString(pitch);
         strings[i] = newString;
      }
   }

   /*
    * Pre: Pitch must be within the range of the Guitar37 (ignored if not)
    * Post: Plays note of the pitch, with 0 representing Concert A
    */
   public void playNote(int pitch) {
      int index = pitch + 24;
      if (index < strings.length && index >= 0) {
         GuitarString string = strings[index];
         string.pluck();
      }
   }

   /*
    * Post: Returns true if key is a valid key on the guitar
    */
   public boolean hasString(char key) {
      return (KEYBOARD.indexOf(key) != -1);
   }

   /*
    * Pre: key must be a valid key on guitar (throws IllegalArgumentException)
    * Post: Pluckes the String corresponding to the key.
    */
   public void pluck(char key) {
      if (!hasString(key)) {
         throw new IllegalArgumentException("Invalid key: " + key);
      }
      GuitarString keyString = strings[KEYBOARD.indexOf(key)];
      keyString.pluck();
   }

   /*
    * Post: Returns the sum of all samples
    */
   public double sample() {
      double ticSample = 0.0;
      for (GuitarString string : strings) {
         ticSample += string.sample();
      }
      return ticSample;
   }

   /*
    * Post: Tics all guitar strings forwarts by one
    */
   public void tic() {
      for (GuitarString string : strings) {
         string.tic();
      }
      ticCount++;
   }

   /*
    * Post: Returns the amount of tics that have passed
    */
   public int time() {
      return ticCount;
   }

}
