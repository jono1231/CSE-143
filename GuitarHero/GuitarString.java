
/* 
 * Jonathan Wang
 * 13 October 2022
 * CSE 143 AJ
 * A GuitarString emulates a guitar string vibrating at a specific frequency
*/
import java.util.*;

public class GuitarString {

    public static final double ENERGY_DECAY_FACTOR = 0.996; // Energy decay factor
    private Queue<Double> ringBuffer; // Ring Buffer for storing Karplus-Strong Algorithm
    private int sampleSize; // Sample size for the Ring Buffer
    private Random randomGenerator;

    /*
     * Constructor:
     * Pre: frequency cannot be less than 0 or
     * resulting ring buffer size less than 2 (Trhows Illegal Argument Exception)
     * Post: Creates a new GuitarString of input frequency
     */
    public GuitarString(double frequency) {
        // Need to get sample size before doing preconditions
        sampleSize = (int) Math.round(StdAudio.SAMPLE_RATE / frequency);

        if (sampleSize < 2 || frequency <= 0) {
            throw new IllegalArgumentException(
                    "Bad String with frequency: " + frequency + " and sample size " + sampleSize);
        }

        ringBuffer = new LinkedList<>();
        randomGenerator = new Random();

        for (int i = 0; i < sampleSize; i++) {
            ringBuffer.add(0.0);
        }
    }

    /*
     * Constructor:
     * Pre: init must have at least 2 elements
     * (throws IllegalArgumentException if less than 2)
     * Post: Constructs a guitar string with ring buffer elements
     * equalling to the contents of the array
     */
    public GuitarString(double[] init) {
        sampleSize = init.length;

        if (sampleSize < 2) {
            throw new IllegalArgumentException("Cannot make buffer with size " + sampleSize);
        }

        ringBuffer = new LinkedList<>();

        for (double d : init) {
            ringBuffer.add(d);
        }
    }

    /*
     * Post: Simulates a pluck by replacing all elements in the ring buffer
     * with random values between
     * -0.5 exclusive and 0.5 inclusive.
     */
    public void pluck() {
        for (int i = 0; i < sampleSize; i++) {
            double random = randomGenerator.nextDouble() - 0.5;
            ringBuffer.remove();
            ringBuffer.add(random);
        }
    }

    /*
     * Post: simulates Karplus-Strong Update on the ringbuffer by 1 step.
     */
    public void tic() {
        double bufferOne = ringBuffer.remove();
        double bufferTwo = ringBuffer.peek();
        double newBufferVal = average(bufferOne, bufferTwo) * ENERGY_DECAY_FACTOR;
        ringBuffer.add(newBufferVal);
    }

    /*
     * Post: gets the sample at the front of the ring buffer
     */
    public double sample() {
        return ringBuffer.peek();
    }

    /*
     * Post: Takes two doubles and averages them.
     */
    private double average(double n1, double n2) {
        double avg = (n1 + n2) / 2.0;
        return avg;
    }
}
