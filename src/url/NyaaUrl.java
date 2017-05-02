package url;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;

/**
 * @author edotee
 */
public interface NyaaUrl<T extends NyaaUrl<T>> {

    /*
    // Mandatory Static Builder Methods not possible
    static NyaaUrlBuilder<T> builder();

    static NyaaUrlBuilder<T> builder(T reference);
    */

    /** Other */

    public default URL newInstanceOfSearch() {
        return makeURLfromString(getNormalSearch());
    }

    public default URL newInstanceOfRssFeed() {
        return makeURLfromString(getRssFeed());
    }

    public static URL makeURLfromString(String url) {
        URL endResult = null;

        try {
            endResult = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return endResult;
    }

    public default String prepareURL() {
        StringBuilder result = new StringBuilder("https://")
                .append((getCategory().isNSFW())? "sukebei" : "www")
                .append(".nyaa.se/")
                .append(getPage().toString())
                .append(getFilter().toString())
                .append(getCategory().toString());
        if(getUserID() > 0) result.append("&user=").append(getUserID());
        if(getMinAge() > 0) result.append("&minage=").append(getMinAge());
        if(getMaxAge() > 0) result.append("&maxage=").append(getMaxAge());
        if(getMinSize() > 0) result.append("&minsize=").append(getMinSize());
        if(getMaxSize() > 0) result.append("&maxsize=").append(getMaxSize());
        if(getExclude().size() > 0) {
            Iterator<Integer> it = getExclude().iterator();
            result.append("&exclude=").append(it.next().intValue());
            while( it.hasNext() )
                result.append('-').append(it.next().intValue());
        }
        if(getSearch() != null && getSearch().length() > 0) result.append("&term=").append(getSearch());
        return result.toString();
    }

    /*
        private String prepareURL() {
            StringBuilder result = new StringBuilder("https://")
                    .append((category.isNSFW())? "sukebei" : "www")
                    .append(".nyaa.se/")
                    .append(page.toString())
                    .append(filter.toString())
                    .append(category.toString());
            if(userID > 0) result.append("&user=").append(userID);
            if(minSize > 0) result.append("&minage=").append(minAge);
            if(maxAge > 0) result.append("&maxage=").append(maxAge);
            if(minSize > 0) result.append("&minsize=").append(minSize);
            if(maxSize > 0) result.append("&maxsize=").append(maxSize);
            if(exclude.size() > 0) {
                Iterator<Integer> it = exclude.iterator();
                result.append("&exclude=").append(it.next().intValue());
                while( it.hasNext() )
                    result.append('-').append(it.next().intValue());
            }
            if(search != null && search.length() > 0) result.append("&term=").append(search);
            return result.toString();
        }
     */

    /** Data */

    public enum Page {
        SEARCH("Search", "search"),
        RSS("RSS", "rss"),
        UPLOAD("Upload", "upload"),
        LOGIN("Login", "login"),
        SIGNUP("Sign Up", "signup"),
        MANUAL("Manual", "manual");

        private String name, id;

        Page(String name, String id) {
            this.name = name;
            this.id = id;
        }

        public String getName() {
            return this.name;
        }

        public String getID() { return this.id; }

        @Override
        public String toString() {
            return "?page=" + getID();
        }
    }

    public enum Filter {
        ALL("All", 0),
        REMAKES("Remakes", 1),
        TRUSTED("Trusted", 2),
        Ap("A+", 3)
        ;

        private String name;
        private int id;

        Filter(String name, int id) {
            this.name = name;
            this.id = id;
        }

        public String getName() {
            return this.name;
        }

        public int getID() { return this.id; }

        @Override
        public String toString() {
            return "&filter=" + getID();
        }

    }

    public interface NyaaCategory {
        static String getID(int cat, int subcat) {
            return new StringBuffer().append(cat).append('_').append(subcat).toString();
        }

        default boolean isNSFW() {
            return false;
        }

        String getName();

        String getID();

        @Override
        String toString();
    }

    public enum Category implements NyaaCategory {
        ALL("All Categories", 0);

        private final static class LazyHelper {
            public final static ArrayList<NyaaCategory> INSTANCE = init();
            private static synchronized ArrayList<NyaaCategory> init() {
                //not sure if synchronized is needed
                ArrayList<NyaaCategory> result = new ArrayList<>();
                result.addAll( Arrays.asList( ANIME.values()) );
                result.addAll( Arrays.asList( LITERATURE.values()) );
                result.addAll( Arrays.asList( AUDIO.values()) );
                result.addAll( Arrays.asList( PICTURE.values()) );
                result.addAll( Arrays.asList( LIVEACTION.values()) );
                result.addAll( Arrays.asList( SOFTWARE.values()) );
                result.addAll( Arrays.asList( ART.values()) );
                result.addAll( Arrays.asList( REALLIFE.values()) );
                return result;
            }
        }

        public final static int CATNUM = 0;

        private String name;
        private int id;

        Category(String name, int id) {
            this.name = name;
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public String getID() {
            return NyaaCategory.getID(CATNUM, id);
        }

        public String toString() {
            return "&cats=" + this.getID();
        }

        public static ArrayList<NyaaCategory> recursiveValues() {
            return LazyHelper.INSTANCE;
        }

        public enum ANIME implements NyaaCategory {

            ALL("Anime", 0),
            RAW(ALL.getName() + " - Raw", 11),
            AMV(ALL.getName() + " - Anime Music Video", 32),
            EN(ALL.getName() + " - English-translated", 37),
            NOTEN(ALL.getName() + " – Non-English-Translated", 38);

            public static final int CATNUM = 1;

            private String name;
            private int id;

            ANIME(String name, int id) {
                this.name = name;
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public String getID() {
                return NyaaCategory.getID(CATNUM, id);
            }

            public String toString() {
                return "&cats=" + this.getID();
            }
        }

        public enum LITERATURE implements NyaaCategory {

            ALL("Literature", 0),
            EN(ALL.getName() + " - English-translated", 12),
            RAW(ALL.getName() + " - Raw", 13),
            NOTEN(ALL.getName() + " – Non-English-Translated", 39),;

            private static final int CATNUM = 2;

            private String name;
            private int id;

            LITERATURE(String name, int id) {
                this.name = name;
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public String getID() {
                return NyaaCategory.getID(CATNUM, id);
            }

            public String toString() {
                return "&cats=" + this.getID();
            }
        }

        public enum AUDIO implements NyaaCategory {

            ALL("Audio", 0),
            LOSSLESS(ALL.getName() + " - Lossless", 14),
            LOSSY(ALL.getName() + " - Lossy", 15),;

            private static final int CATNUM = 3;

            private String name;
            private int id;

            AUDIO(String name, int id) {
                this.name = name;
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public String getID() {
                return NyaaCategory.getID(CATNUM, id);
            }

            public String toString() {
                return "&cats=" + this.getID();
            }
        }

        public enum PICTURE implements NyaaCategory {

            ALL("Pictures", 0),
            PHOTO(ALL.getName() + " - Photo", 17),
            GRAPHICS(ALL.getName() + " -  Graphics", 18),;

            private static final int CATNUM = 4;


            private String name;
            private int id;

            PICTURE(String name, int id) {
                this.name = name;
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public String getID() {
                return NyaaCategory.getID(CATNUM, id);
            }

            public String toString() {
                return "&cats=" + this.getID();
            }
        }

        public enum LIVEACTION implements NyaaCategory {

            ALL("Live Action", 0),
            EN(ALL.getName() + " - English-translated", 19),
            NOTEN(ALL.getName() + " – Non-English-Translated", 20),
            IDOL(ALL.getName() + "", 21),
            RAW(ALL.getName() + " - Raw", 22),;

            private static final int CATNUM = 5;

            private String name;
            private int id;

            LIVEACTION(String name, int id) {
                this.name = name;
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public String getID() {
                return NyaaCategory.getID(CATNUM, id);
            }

            public String toString() {
                return "&cats=" + this.getID();
            }
        }

        public enum SOFTWARE implements NyaaCategory {

            ALL("Software", 0),
            APP(ALL.getName() + " - Application", 23),
            GAMES(ALL.getName() + " - Games", 24),;

            private static final int CATNUM = 6;

            private String name;
            private int id;

            SOFTWARE(String name, int id) {
                this.name = name;
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public String getID() {
                return NyaaCategory.getID(CATNUM, id);
            }

            public String toString() {
                return "&cats=" + this.getID();
            }
        }

        public enum ART implements NyaaCategory {

            ALL("Art", 0),
            ANIME(ALL.getName() + " - Anime", 25),
            MANGA(ALL.getName() + " - Manga", 26),
            GAMES(ALL.getName() + " - Games", 27),
            PICTURES(ALL.getName() + " – Pictures", 28),
            DOUJINSHI(ALL.getName() + " – Doujinshi", 33),;

            private static final int CATNUM = 7;

            private String name;
            private int id;

            ART(String name, int id) {
                this.name = name;
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public String getID() {
                return NyaaCategory.getID(CATNUM, id);
            }

            public String toString() {
                return "&cats=" + this.getID();
            }

            @Override
            public boolean isNSFW() {
                return true;
            }
        }

        public enum REALLIFE implements NyaaCategory {

            ALL("Real Life", 0),
            VIDEOS(ALL.getName() + " - Videos", 30),
            AMV(ALL.getName() + "Photobooks & Pictures", 31),;

            private static final int CATNUM = 8;

            private String name;
            private int id;

            REALLIFE(String name, int id) {
                this.name = name;
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public String getID() {
                return NyaaCategory.getID(CATNUM, id);
            }

            public String toString() {
                return "&cats=" + this.getID();
            }

            @Override
            public boolean isNSFW() {
                return true;
            }
        }

    }

    /** Getter */

    public abstract Page getPage();

    public abstract Filter getFilter();

    public abstract NyaaCategory getCategory();

    public abstract int getUserID();

    public abstract int getMinAge();

    public abstract int getMaxAge();

    public abstract int getMinSize();

    public abstract int getMaxSize();

    public abstract HashSet<Integer> getExclude();

    public abstract String getSearch();

    public abstract String getNormalSearch();

    public abstract String getRssFeed();
}
