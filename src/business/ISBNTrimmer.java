package business;

import config.Communication;

public class ISBNTrimmer {

    /**
     *
     * @param input user input in the form of 'search <isbn>'
     * @return the isbn without trailing whitespace
     */
    public static String retrieveISBN(String input) throws IllegalArgumentException {
        if(!input.startsWith(Communication.SEARCH)) {
            throw new IllegalArgumentException("Input must start with the 'search' keyword");
        }

        input = input.replace(Communication.SEARCH, "");
        String ISBN = input.trim();

        if(isValid(ISBN)) {
            return ISBN;
        } else {
            throw new IllegalArgumentException("Invalid ISBN");
        }
    }

    private static boolean isValid(String ISBN) {
        if(ISBN == null) {
            return false;
        }

        if(ISBN.isEmpty()) {
            return false;
        }

        if(ISBN.length() < 10) {
            return false;
        }

        return true;
    }
}
