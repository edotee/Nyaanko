import url.ImmutableNyaaUrl;

/**
 * Aggregator for nyaa.se
 * @author edotee
 */

public class Nyaanko {

    public static void main(String[] args) {
        for(ImmutableNyaaUrl.NyaaCategory nc : ImmutableNyaaUrl.Category.recursiveValues())
            System.out.println( nc.getName() + "\nID:\t" + nc.getID() + "\n" );
    }
}