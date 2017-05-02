package url;

/**
 * @author edotee
 */
public interface NyaaUrlBuilder<T extends NyaaUrl<T>> {

    NyaaUrlBuilder<T> page(T.Page page);
    NyaaUrlBuilder<T> filter(T.Filter filter);
    NyaaUrlBuilder<T> cat(T.NyaaCategory cat);
    NyaaUrlBuilder<T> user(int userID);
    NyaaUrlBuilder<T> age(int min, int max);
    NyaaUrlBuilder<T> size(int min, int max);
    NyaaUrlBuilder<T> exclude(int... userID);
    NyaaUrlBuilder<T> search(String search);
    default NyaaUrlBuilder setDefaults(NyaaUrl<?>  reference){
        return setDefaults(reference, true);
    }
    NyaaUrlBuilder setDefaults(NyaaUrl<?> reference, boolean reset);
    NyaaUrlBuilder<T> reset();

    T build();

    /*
    NyaaUrlBuilder defaultPage(T.Page page);
    NyaaUrlBuilder defaultFilter(T.Filter filter);
    NyaaUrlBuilder defaultCat(T.NyaaCategory cat);
    NyaaUrlBuilder defaultUser(int userID);
    NyaaUrlBuilder defaultAge(int min, int max);
    NyaaUrlBuilder defaultSize(int min, int max);
    NyaaUrlBuilder defaultExclude(int... userID);
    NyaaUrlBuilder defaultSearch(String search);
    */

    /*
    NyaaUrlBuilder page(ImmutableNyaaUrl.Page page);
    NyaaUrlBuilder filter(ImmutableNyaaUrl.Filter filter);
    NyaaUrlBuilder cat(ImmutableNyaaUrl.NyaaCategory cat);
    NyaaUrlBuilder user(int userID);
    NyaaUrlBuilder age(int min, int max);
    NyaaUrlBuilder size(int min, int max);
    NyaaUrlBuilder exclude(int... userID);
    NyaaUrlBuilder search(String search);
    */
}
