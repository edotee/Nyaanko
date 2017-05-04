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

    public static URL makeURLfromString(String url) {
        URL endResult = null;

        try {
            endResult = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return endResult;
    }

    /** Other */

    public default URL newInstanceOfSearch() {
        return makeURLfromString(getNormalSearch());
    }

    public default URL newInstanceOfRssFeed() {
        return makeURLfromString(getRssFeed());
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

    /** Getter */

    public abstract Page getPage();

    public abstract Filter getFilter();

    public abstract NyaaCategory getCategory();

    public abstract int getUserID();

    /*
    // Mandatory Static Builder Methods not possible
    static NyaaUrlBuilder<T> builder();

    static NyaaUrlBuilder<T> builder(T reference);
    */

    public abstract int getMinAge();

    public abstract int getMaxAge();

    public abstract int getMinSize();

    public abstract int getMaxSize();

    public abstract HashSet<Integer> getExclude();

    public abstract String getSearch();

    public abstract String getNormalSearch();

    public abstract String getRssFeed();
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

        default String getUrlToken() {
            return "&cats=" + getID();
        }

        String getFullName();

        boolean isNSFW();

        String getID();

        @Override
        String toString();

        String getName();

        int getCatNum();

        MainCategory getMainCategory();
    }

    public interface MainCategory extends NyaaCategory {

        default String getFullName() {
            return getName();
        }

        default String getID() {
            return getMainCategory().getCatNum() + "_" + 0;
        }

        SubCategory[] subcatsAsArray();

    }

    public interface SubCategory extends NyaaCategory {

        default boolean isNSFW() {
            return getMainCategory().isNSFW();
        }

        default String getFullName() {
            return getMainCategory().getName() + " - " + getName();
        }

        default String getID() {
            return getMainCategory().getCatNum() + "_" + getCatNum();
        }

    }

    public enum Category implements MainCategory {

        ANIME("Anime", 1, AnimeSubCategory.values()),
        LITERATURE("Literature", 2, LiteratureSubCategory.values()),
        AUDIO("Audio", 3, AudioSubCategory.values()),
        PICTURE("Picture", 4, PictureSubCategory.values()),
        LIVEACTION("Live Action", 5, LiveActionSubCategory.values()),
        SOFTWARE("Software", 6, SoftwareSubCategory.values()),
        ART("Art", 7, ArtSubCategory.values(), true),
        REALLIFE("Real Life", 8, RealLifeSubCategory.values(), true)
        ;

        public enum AnimeSubCategory implements SubCategory {

            RAW("ANIME", "Raw", 11),
            AMV("ANIME", "Anime Music Video", 32),
            EN("ANIME", "English-translated", 37),
            NOTEN("ANIME", "Non-English-Translated", 38);

            private String maincat;
            private String name;
            private int catnum;

            AnimeSubCategory(String maincat, String name, int catnum) {
                this.maincat = maincat;
                this.name = name;
                this.catnum = catnum;
            }

            public String getName() {
                return name;
            }

            public int getCatNum() {
                return catnum;
            }

            public String toString() {
                return getUrlToken();
            }

            public MainCategory getMainCategory() {
                return Category.valueOf(maincat);
            }

        }

        public enum LiteratureSubCategory implements SubCategory {

            EN("LITERATURE", "English-translated", 12),
            RAW("LITERATURE", "Raw", 13),
            NOTEN("LITERATURE", "Non-English-Translated", 39)
            ;

            private String maincat;
            private String name;
            private int catnum;

            LiteratureSubCategory(String maincat, String name, int catnum) {
                this.maincat = maincat;
                this.name = name;
                this.catnum = catnum;
            }

            public String getName() {
                return name;
            }

            public int getCatNum() {
                return catnum;
            }

            public String toString() {
                return getUrlToken();
            }

            public Category getMainCategory() {
                return Category.valueOf(maincat);
            }
        }

        public enum AudioSubCategory implements SubCategory {

            LOSSLESS("AUDIO", "Lossless", 14),
            LOSSY("AUDIO", "Lossy", 15)
            ;

            private String maincat;
            private String name;
            private int catnum;

            AudioSubCategory(String maincat, String name, int catnum) {
                this.maincat = maincat;
                this.name = name;
                this.catnum = catnum;
            }

            public String getName() {
                return name;
            }

            public int getCatNum() {
                return catnum;
            }

            public String toString() {
                return getUrlToken();
            }

            public Category getMainCategory() {
                return Category.valueOf(maincat);
            }
        }

        public enum PictureSubCategory implements SubCategory {

            PHOTO("PICTURE", "Photo", 17),
            GRAPHICS("PICTURE", "Graphics", 18)
            ;

            private String maincat;
            private String name;
            private int catnum;

            PictureSubCategory(String maincat, String name, int catnum) {
                this.maincat = maincat;
                this.name = name;
                this.catnum = catnum;
            }

            public String getName() {
                return name;
            }

            public int getCatNum() {
                return catnum;
            }

            public String toString() {
                return getUrlToken();
            }

            public Category getMainCategory() {
                return Category.valueOf(maincat);
            }
        }

        public enum LiveActionSubCategory implements SubCategory {

            EN("LIVEACTION", "English-translated", 19),
            NOTEN("LIVEACTION", "Non-English-Translated", 20),
            IDOL("LIVEACTION", "Idol & Promotional", 21),
            RAW("LIVEACTION", "Raw", 22)
            ;

            private String maincat;
            private String name;
            private int catnum;

            LiveActionSubCategory(String maincat, String name, int catnum) {
                this.maincat = maincat;
                this.name = name;
                this.catnum = catnum;
            }

            public String getName() {
                return name;
            }

            public int getCatNum() {
                return catnum;
            }

            public String toString() {
                return getUrlToken();
            }

            public Category getMainCategory() {
                return Category.valueOf(maincat);
            }
        }

        public enum SoftwareSubCategory implements SubCategory {

            APP("SOFTWARE", "Application", 23),
            GAMES("SOFTWARE", "Games", 24)
            ;

            private String maincat;
            private String name;
            private int catnum;

            SoftwareSubCategory(String maincat, String name, int catnum) {
                this.maincat = maincat;
                this.name = name;
                this.catnum = catnum;
            }

            public String getName() {
                return name;
            }

            public int getCatNum() {
                return catnum;
            }

            public String toString() {
                return getUrlToken();
            }

            public Category getMainCategory() {
                return Category.valueOf(maincat);
            }
        }

        public enum ArtSubCategory implements SubCategory {

            ANIME("ART", "Anime", 25),
            MANGA("ART", "Manga", 26),
            GAMES("ART", "Games", 27),
            PICTURES("ART", "Pictures", 28),
            DOUJINSHI("ART", "Doujinshi", 33)
            ;

            private String maincat;
            private String name;
            private int catnum;

            ArtSubCategory(String maincat, String name, int catnum) {
                this.maincat = maincat;
                this.name = name;
                this.catnum = catnum;
            }

            public String getName() {
                return name;
            }

            public int getCatNum() {
                return catnum;
            }

            public String toString() {
                return getUrlToken();
            }

            public Category getMainCategory() {
                return Category.valueOf(maincat);
            }
        }

        public enum RealLifeSubCategory implements SubCategory {

            VIDEOS("REALLIFE", "Videos", 30),
            AMV("REALLIFE", "Photobooks & Pictures", 31)
            ;

            private String maincat;
            private String name;
            private int catnum;

            RealLifeSubCategory(String maincat, String name, int catnum) {
                this.maincat = maincat;
                this.name = name;
                this.catnum = catnum;
            }

            public String getName() {
                return name;
            }

            public int getCatNum() {
                return catnum;
            }

            public String toString() {
                return getUrlToken();
            }

            public Category getMainCategory() {
                return Category.valueOf(maincat);
            }
        }


        /** Class & Instance things */
        private String name;
        private int catnum;
        private boolean nsfw;
        private SubCategory[] subcats;

        Category(String name, int catnum, SubCategory[] subcats) {
            this(name, catnum, subcats, false);
        }

        Category(String name, int catnum, SubCategory[] subcats, boolean nsfw) {
            this.name = name;
            this.catnum = catnum;
            this.nsfw = nsfw;
            this.subcats = subcats;
        }

        public SubCategory[] subcatsAsArray() {
            return subcats;
        }

        public MainCategory getMainCategory() {
            return this;
        }

        public String getName() {
            return name;
        }

        public int getCatNum() {
            return catnum;
        }

        @Override
        public String toString() {
            return getUrlToken();
        }

        @Override
        public boolean isNSFW() {
            return nsfw;
        }

        public static ArrayList<NyaaCategory> allValues() {
            return LazyHelper.INSTANCE;
        }

        //TODO
        private final static class LazyHelper {
            public final static ArrayList<NyaaCategory> INSTANCE = init();
            private static synchronized ArrayList<NyaaCategory> init() {
                //not sure if synchronized is needed
                ArrayList<NyaaCategory> result = new ArrayList<>();
                for(MainCategory mc : Category.values()) {
                    result.add( mc );
                    result.addAll(Arrays.asList( mc.subcatsAsArray() ));
                }
                return result;
            }
        }
    }
}
