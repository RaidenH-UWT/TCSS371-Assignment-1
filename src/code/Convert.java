/*
 * Convert.java
 * 
 * TCSS 371 assignment 1
 */

package code;

/**
 * A class to provide static methods for converting numbers between bases.
 * 
 * @author Alan Fowler
 * @author Raiden H
 * @author Emma Szebenyi
 * @version Winter 2025
 */
public final class Convert {
    
    /**
     * A private constructor to inhibit instantiation of this class.
     */
    private Convert() {
        // Objects should not be instantiated from this class.
        // This class is just a home for static methods (functions).
        // This design is similar to the Math class in the Java language.
    }
    
    /**
     * Accepts an array of characters representing the bits in a two's complement number
     * and returns the decimal equivalent.
     *
     * precondition:
     * This method requires that the maximum length of the parameter array is 16.
     *
     * postcondition:
     * The value returned is the decimal equivalent of the two's complement parameter.
     * The parameter array is unchanged.
     * 
     * @param theBits an array representing the bits in a two's complement number
     * @throws IllegalArgumentException if the length of the parameter array > 16.
     * @return the decimal equivalent of the two's complement parameter
     */
    public static int convert2sCompToDecimal(final char[] theBits) {
        // Throw the specified exception if parameters don't meet requirements
        if (theBits.length > 16) {
            throw new IllegalArgumentException("Input larger than 16 bits");
        }
        // Using Method 1 of Slide 24, combined with the special case for negatives

        int sum = 0;

        // If the number is negative, use the special case
        if (theBits[0] == '1') {
            sum -= Math.pow(2,  theBits.length - 1);
        }

        // Iterate over every bit, and add it's corresponding power of two if it's '1'
        for (int i = theBits.length - 1; i > 0; i--) {
            if (theBits[i] == '1') {
                sum += Math.pow(2, theBits.length - i - 1);
            }
        }

        return sum;
    }
    

    
    /**
     * Accepts a decimal parameter and returns an array of characters
     * representing the bits in the 16 bit two's complement equivalent.
     * 
     * precondition:
     * This method requires that the two's complement equivalent
     * won't require more than 16 bits
     *
     * postcondition:
     * The returned array represents the 16 bit two's complement equivalent
     * of the decimal parameter.
     * 
     * @param theDecimal the decimal number to convert to two's complement
     * @throws IllegalArgumentException if the parameter cannot be represented in 16 bits.
     * @return a 16 bit two's complement equivalent of the decimal parameter
     */
    public static char[] convertDecimalTo2sComp(final int theDecimal) {
        // Throw the specified exception if parameters don't meet requirements
        if (theDecimal > 32767 || theDecimal < -32768) {
            throw new IllegalArgumentException("Input larger than 16 bits");
        }

        //Using Method 2 from slide 29
        //Use absolute value so negative input doesn't impact solving
        int decimal2 = Math.abs(theDecimal);

        //An arbitrary array of max length
        char[] outputBits = new char[16];

        for (int j = outputBits.length - 1; j >= 0; j--) {
            //Calculate and add remainders to array from right to left
            if (decimal2 != 0) {
                outputBits[j] = (char)((decimal2 % 2) + '0');
                decimal2 = (decimal2 - (decimal2 % 2)) / 2;
                //Fill any empty indexes with zeros
            } else {
                outputBits[j] = '0';
            }
        }

        //Takes two's complement if the passed parameter was negative
        if (theDecimal < 0) {
            outputBits = twosComplement(outputBits);
        }

        return outputBits;
    }
    
    
    /*
     * NOTE: If you wish, you may also include private helper methods in this class.
     */
    private static char[] twosComplement(final char[] theBits) {
        // This method works for an array of arbitrary length (i believe)
        char[] outputBits = new char[theBits.length];

        // Flip all the bits
        for (int i = 0; i < theBits.length; i++) {
            switch (theBits[i]) {
                case '0':
                    outputBits[i] = '1';
                    break;
                case '1':
                    outputBits[i] = '0';
                    break;
                default:
                    System.out.println("weird bits");
                    break;
            }
        }

        // Add 1
        for (int j = outputBits.length - 1; j >= 0; j--) {
            if (outputBits[j] == '1') {
                outputBits[j] = '0';
            } else {
                outputBits[j] = '1';
                break;
            }
        }
        return outputBits;
    }
}