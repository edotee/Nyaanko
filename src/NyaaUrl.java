import java.net.URL;
import java.util.*;

/**
 * @author edotee
 */
public class NyaaUrl {

    public final static NyaaUrlBuilder builder = new NyaaUrlBuilderImpl();

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

        public static final int CATNUM = 0;
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

            ALL("", 0),
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

    private final static class NyaaUrlBuilderImpl implements NyaaUrlBuilder {

        private Page page;
        private Filter filter;
        private Category category;
        private int userID, minAge, maxAge, minSize, maxSize;
        private HashSet<Integer> exclude;

        public NyaaUrlBuilderImpl() {
            this.exclude = new HashSet<>();
            this.reset();
        }

        @Override
        public NyaaUrlBuilder page(Page page) {
            this.page = page;
            return this;
        }

        @Override
        public NyaaUrlBuilder filter(Filter filter) {
            this.filter = filter;
            return this;
        }

        @Override
        public NyaaUrlBuilder cat(Category category) {
            this.category = category;
            return this;
        }

        @Override
        public NyaaUrlBuilder user(int userID) {
            this.userID = userID;
            return this;
        }

        @Override
        public NyaaUrlBuilder age(int min, int max) {
            this.minAge = min;
            this.maxAge = max;
            return this;
        }

        @Override
        public NyaaUrlBuilder size(int minSize, int maxSize) {
            this.minSize = minSize;
            this.maxSize = maxSize;
            return this;
        }

        @Override
        public NyaaUrlBuilder exclude(int... userID) {
            for(int id : userID)
                if(id > 0)
                    this.exclude.add(id);
            return this;
        }

        @Override
        public NyaaUrlBuilder reset() {
            page = Page.RSS;
            filter = Filter.TRUSTED;
            category = Category.ALL;
            userID = 0;
            minAge = 0;
            maxAge = 0;
            minSize = 0;
            maxSize = 0;
            exclude.clear();

            return this;
        }

        @Override
        public URL buildURL() {
            return NyaaUrlBuilder.makeURLfromString(prepareURL());
        }

        @Override
        public NyaaUrl build() {
            //TODO
            return new NyaaUrl();
        }

        private String prepareURL() {
            StringBuilder result = new StringBuilder("https://")
                    .append((category.isNSFW())? "sukebei" : "www")
                    .append(".nyaa.se/")
                    .append(page.toString())
                    .append(filter.toString())
                    .append(category.toString());
            if(userID > 0) result.append("&user=" + userID);
            if(minSize > 0) result.append("&minage=" + minAge);
            if(maxAge > 0) result.append("&maxage=" + maxAge);
            if(minSize > 0) result.append("&minsize=" + minSize);
            if(maxSize > 0) result.append("&maxsize=" + maxSize);
            if(exclude.size() > 0) {
                Iterator<Integer> it = exclude.iterator();
                result.append("&exclude=").append(it.next().intValue());
                while( it.hasNext() )
                    result.append('-').append(it.next().intValue());
            }
            reset();
            return result.toString();
        }

        /*
        @Override
        public NyaaUrl build() {
            NyaaUrl result = new NyaaUrl();
            result.page = result;
            result.filter = filter;
            result.category = category;
            result.userID = userID;
            result.minAge = minAge;
            result.maxAge = maxAge;
            result.minSize = minSize;
            result.maxSize = maxSize;
            result.exclude.clear();
            return null;
        }
        */
    }
}
