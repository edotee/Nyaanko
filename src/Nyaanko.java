import java.net.MalformedURLException;
import java.net.URL;

/**
 * Aggregator for nyaa.se
 * @author edotee
 */

public class Nyaanko {

    /**
     * Create & return a URL object of the input string if possible.
     * @return returns null if the string isn't a valid url
     */
    public static URL makeURLfromString(String url_string) {
        URL result = null;

        if(checkUrlString(url_string))
            try {
                result = new URL(url_string);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

        return result;
    }

    private static boolean checkUrlString(String url_string) {
        boolean result = false;
        return result;
    }

    public static void main(String[] args) {

    }
}