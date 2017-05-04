import url.NyaaUrl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Aggregator for nyaa.se
 * @author edotee
 */

public class Nyaanko {

    public static void main(String[] args) {
        //System.out.println(NyaaUrl.Category.valueOf("ANIME"));
        test();
    }

    private static void test() {
        print(Arrays.asList( NyaaUrl.Category.values() ));

        print(Arrays.asList( NyaaUrl.Category.ANIME.subcatsAsArray() ));

        ArrayList<NyaaUrl.NyaaCategory> allCategories = NyaaUrl.Category.allValues();
        print(allCategories);
    }

    private static void print(List<NyaaUrl.NyaaCategory> cats) {
        for(NyaaUrl.NyaaCategory nc : cats)
            System.out.println( nc.getFullName() + "\nID:\t" + nc.getID() + "\n" );
    }
}