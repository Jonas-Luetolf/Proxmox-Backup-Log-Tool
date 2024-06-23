package backend.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class ParserUtils {
    /**
     * Converts a given string representation of data size to a factor relative to GiB (Gibibytes).
     * The method detects common data size suffixes (GiB, MiB, KiB, TiB, PiB, EiB, ZiB)
     * in the input string and returns the corresponding conversion factor.
     *
     * @param s a string containing a data size followed by its suffix
     * @return the conversion factor to GiB based on the detected suffix
     */
    public static double getFactorToGib(String s){
        if (s.contains("GiB")) return 1;
        else if (s.contains("MiB")) return Math.pow(2, -10);
        else if (s.contains("KiB")) return Math.pow(2, -20);
        else if (s.contains("TiB")) return Math.pow(2, 10);
        else if (s.contains("PiB")) return Math.pow(2, 20);
        else if (s.contains("EiB")) return Math.pow(2, 30);
        else if (s.contains("ZiB")) return Math.pow(2, 40);

        // leave value unchanged if no factor can be found
        else return 1;
    }

    /**
     * returns a float of the first floating-point number in a string
     * This method uses a regular expression to search for
     * the first floating-point number. If a valid floating-point number is found,
     * it is parsed and returned as a float value else it returns Float.Nan.
     *
     * @param s The input string from which to extract the floating-point number.
     * @return Number as Float or Float.NaN
     */
    public static float getFloatFromString(String s){
        Pattern floats = Pattern.compile("^[0-9]+(\\.[0-9]+)?");
        Matcher floatsMatcher = floats.matcher(s);

        if (floatsMatcher.find()){
            // No NumberFormatException can occur because regex pattern only matches valid strings
            return Float.parseFloat(floatsMatcher.group());
        }

        return  Float.NaN;
    }

    /**
     * Extracts all the integer numbers from a given string.
     * This method searches for sequences of digits in the input string
     * and converts them to integer values. The extracted integers are
     * then added to a list and returned.
     *
     * @param s the input string from which integers will be extracted
     * @return a list containing all the integer numbers found in the input string
     */
    public static List<Integer> getAllNums(String s){
        Pattern digits = Pattern.compile("[0-9]+");
        Matcher digitsMatcher = digits.matcher(s);
        List<Integer> numbers = new ArrayList<>();

        while (digitsMatcher.find()){
            numbers.add(Integer.parseInt(digitsMatcher.group()));
        }
        return numbers;
    }

    /**
     * Converts a time string in the format "(HH:)MM:SS" or "(HHh)MMminSSs" to a float value in hours.
     * This method extracts the hours, minutes, and seconds from the input time string
     * and calculates the total time in hours as a float value.
     *
     * @param s the input time string
     * @return a float value representing the time in minutes
     */
    public static float getTimeAsFloat(String s){
        List<Integer> timeParts = getAllNums(s);
        float time = 0;

        int n = 1;
        for (int i = timeParts.size() - 1; i >= 0; i--) {
            time += (float) (timeParts.get(i) / Math.pow(60,n));
            n--;
        }

        return time;
    }
}
