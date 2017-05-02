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
}
