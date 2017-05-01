import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author edotee
 */
public interface NyaaUrlBuilder {
    NyaaUrlBuilder page(NyaaUrl.Page page);
    NyaaUrlBuilder filter(NyaaUrl.Filter filter);
    NyaaUrlBuilder cat(NyaaUrl.Category cat);
    NyaaUrlBuilder user(int userID);
    NyaaUrlBuilder age(int min, int max);
    NyaaUrlBuilder size(int min, int max);
    NyaaUrlBuilder exclude(int... userID);
    NyaaUrlBuilder reset();
    URL buildURL();
    NyaaUrl build();

    static URL makeURLfromString(String url) {
        URL endResult = null;

        try {
            endResult = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return endResult;
    }
}
