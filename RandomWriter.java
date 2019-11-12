// bvandyke17@georgefox.edu
// program 11
// 2018-12-2

// import utilities
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * This class creates a random generated text given the prefix, length, and
 * multiple texts from the command line. Prefix must be greater than 0, length
 * greater than 0 and 2 or more valid text files. Exits with 0 on success, 1
 * for invalid arguments, and 2 for insufficient characters.
 * @author Brett Van Dyke <bvandyke17@georgefox.edu>
 */
public class RandomWriter {

    // assign constants
    public static int SUCCESS_EXIT = 0;
    public static int INVALID_ARGS = 1;
    public static int INSUFFICIENT_CHARS = 2;
    public static int TEXT_FILE_START = 2;

    // initiate private hash map variable
    private static HashMap<String, ArrayList<String>> _hash;

    /**
     * This is the main method of the program. The argument string array
     * should be passed in by prefix, length, text file, text file, and any
     * more additional text files to fulfill your hearts desires.
     * @param args the string of arguments to pass in the correct format.
     */
    public static void main(String[] args) {

        // create and assign hash map variable
        _hash = new HashMap();

        // validate input
        validateCommand(args[0], args[1], args.length);

        // populate each text file
        for (int i = TEXT_FILE_START; i < args.length; i++)
        {
            // read files and convert string arguments to digits
            populate(readFile(args[i], getDigit(args[0])), getDigit(args[0]));
        }

        // generate final random text with given length
        generateFinalText(getDigit(args[1]));
    }

    /**
     * This method generates a final random text from a populated hash map from
     * 2 or more text files.
     * @param length number of characters to generate
     */
    private static void generateFinalText(int length)
    {
        // assign index count to the first char of the text
        int index = 0;

        // assign and create random number variable
        Random random = new Random();

        // assign random starting key from hash map
        String key = _hash.keySet().toArray()[random.nextInt(_hash.size())]
                .toString();

        // create variable for final text and assign initial empty string
        String generatedText = "";

        // initiate variable for character to add
        String addedChar;

        // loop until given length
        while (index < length && key != null)
        {
            // find from map
            if (_hash.containsKey(key))
            {
                // get value character to add from random index
                addedChar = _hash.get(key).get(random.nextInt(_hash.get(key)
                        .size()));

                // add character to final text
                generatedText += addedChar;

                // add new character to the last character of the key
                key += addedChar;

                // remove 0ith index of the key
                key = key.substring(1);
            }

            else
            {
                // exit with status code 2 if characters cannot be reached
                // before length is satisfied
                System.err.print("Insufficient characters!!");
                System.exit(INSUFFICIENT_CHARS);
            }

            index++;
        }

        // print new text
        System.out.print(generatedText);

        // exit with success code
        System.exit(SUCCESS_EXIT);
    }

    /**
     * This method populates a hash map with given texts.
     * @param text the text to populate
     * @param prefix the prefix to iterate by
     */
    private static void populate(String text, int prefix)
    {
        String key;

        // loop through entire text starting at first char
        for (int i = 0; i < text.length() - prefix; i++)
        {
            // assign the prefix string
            key = text.substring(i, i + prefix);

            // populate key if prefix is not contained
            if (!_hash.containsKey(key))
            {
                _hash.put(key, new ArrayList());
            }

            // add value to key
            _hash.get(key).add(text.substring(i + prefix, i + prefix + 1));
        }
    }

    /**
     * This method validates input given in the command line arguments.
     * @param prefixArg the number of characters to use as the prefix
     * @param lengthArg the number of characters to generate as output
     * @param length the number of arguments in the command line
     */
    private static void validateCommand(String prefixArg, String lengthArg,
            int length)
    {
        // exit with 0 if less than minimum required arguments, the prefix
        // length is 0 or less, or the generated length is less than 0
        if (length < 3 || getDigit(prefixArg) <= 0 || getDigit(lengthArg) < 0)
        {
            System.err.print("You are either missing a command, prefix is 0 or "
                    + "less, your generated characters are less than 0, or "
                    + "your files don't exist.");

            // exit with status code 1
            System.exit(INVALID_ARGS);
        }
    }

    /**
     * This method reads a text file and returns it as a single String variable.
     * @param file the text file to read
     * @return the text file as a single string variable
     */
    private static String readFile(String file, int validLength)
    {
        // initiate variables
        BufferedReader reader;
        String readingLine;

        // assign variable to return as empty string
        String generatedText = "";

        try
        {
            // assign reader to read file
            reader = new BufferedReader(new FileReader(file));

            // add lines to returned string
            while ((readingLine = reader.readLine()) != null)
            {
                generatedText += readingLine;
            }

            // close file
            reader.close();
        }

        // exit with status code 1 if text file not fount
        catch (FileNotFoundException fileNotFound)
        {
            System.err.print("File not found.");
            System.exit(INVALID_ARGS);
        }

        // exit with status code 1 if error in reading file
        catch (IOException error)
        {
            System.err.print("Error.");
            System.exit(INVALID_ARGS);
        }

        if (generatedText.length() < validLength)
        {
            System.err.print("Insufficient characters!!");
            System.exit(INSUFFICIENT_CHARS);
        }

        // return entire file as a single string variable
        return generatedText;
    }

    /**
     * This method converts a string to a digit and returns the value as an
     * integer, or returns the value of -1 if the string can not be converted
     * @param stringToConvert the string to turn into an int
     * @return the string as an int if possible, -1 if not possible
     */
    private static int getDigit(String stringToConvert)
    {
        int toInt;

        // try to parse string variable and assign to returned value
        try
        {
           toInt = Integer.parseInt(stringToConvert);
        }

        // assign returned variable to -1 if it cannot be parsed
        catch (NumberFormatException notValid)
        {
            toInt = INVALID_ARGS;
        }

        return toInt;
    }
}

