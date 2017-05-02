package url;

import java.util.HashSet;

/**
 * @author edotee
 */
public class ImmutableNyaaUrl implements NyaaUrl<ImmutableNyaaUrl> {

    private final Page page;
    private final Filter filter;
    private final NyaaCategory category;
    private final int userID, minAge, maxAge, minSize, maxSize;
    private final HashSet<Integer> exclude;
    private final String search;

    private final String normalSearch;
    private final String rssFeed;

    private ImmutableNyaaUrl(UrlBuilderImpl builder) {
        page = builder.page;
        filter = builder.filter;
        category = builder.category;
        userID = builder.userID;
        minAge = builder.minAge;
        maxAge = builder.maxAge;
        minSize = builder.minSize;
        maxSize = builder.maxSize;
        exclude = (HashSet<Integer>) builder.exclude.clone();
        search = builder.search;

        builder.page = Page.SEARCH;
        normalSearch = prepareURL();

        builder.page = Page.RSS;
        rssFeed = prepareURL();

        builder.reset();
    }

    /** Builder Singleton */

    private final static class UrlBuilderImpl implements NyaaUrlBuilder<ImmutableNyaaUrl> {

        private final static NyaaUrlBuilder<ImmutableNyaaUrl> INSTANCE = new UrlBuilderImpl();
        private static NyaaUrl<?> DEFAULTS = INSTANCE.page(Page.RSS)
                .cat(Category.ALL).filter(Filter.ALL)
                .user(0)
                .age(0, 0).size(0, 0)
                .search("")
                .build();

        private Page page;
        private Filter filter;
        private NyaaCategory category;
        private int userID, minAge, maxAge, minSize, maxSize;
        private HashSet<Integer> exclude;
        private String search;

        public UrlBuilderImpl() {
            exclude = new HashSet<>();
            reset();
        }

        @Override
        public NyaaUrlBuilder<ImmutableNyaaUrl> page(Page page) {
            if(page != null) this.page = page;
            return this;
        }

        @Override
        public NyaaUrlBuilder<ImmutableNyaaUrl> filter(Filter filter) {
            if(filter != null) this.filter = filter;
            return this;
        }

        @Override
        public NyaaUrlBuilder<ImmutableNyaaUrl> cat(NyaaCategory category) {
            if(category != null) this.category = category;
            return this;
        }

        @Override
        public NyaaUrlBuilder<ImmutableNyaaUrl> user(int userID) {
            if(userID > 0) this.userID = userID;
            return this;
        }

        @Override
        public NyaaUrlBuilder<ImmutableNyaaUrl> age(int minAge, int maxAge) {
            if(minAge > 0) this.minAge = minAge;
            if(maxAge > 0) this.maxAge = maxAge;
            return this;
        }

        @Override
        public NyaaUrlBuilder<ImmutableNyaaUrl> size(int minSize, int maxSize) {
            if(minSize > 0) this.minSize = minSize;
            if(maxSize > 0) this.maxSize = maxSize;
            return this;
        }

        @Override
        public NyaaUrlBuilder<ImmutableNyaaUrl> exclude(int... userID) {
            for(int id : userID)
                if(id > 0)
                    this.exclude.add(id);
            return this;
        }

        @Override
        public NyaaUrlBuilder<ImmutableNyaaUrl> search(String search) {
            if(search != null && search.length() > 0) this.search = search;
            return this;
        }

        @Override
        public NyaaUrlBuilder<ImmutableNyaaUrl> setDefaults(NyaaUrl<?> reference, boolean reset) {
            DEFAULTS = reference;
            return (reset)? reset() : this;
        }

        @Override
        public NyaaUrlBuilder<ImmutableNyaaUrl> reset() {
            page = DEFAULTS.getPage();
            filter = DEFAULTS.getFilter();
            category = DEFAULTS.getCategory();
            userID = DEFAULTS.getUserID();
            minAge = DEFAULTS.getMinAge();
            maxAge = DEFAULTS.getMaxAge();
            minSize = DEFAULTS.getMinSize();
            maxSize = DEFAULTS.getMaxSize();
            search = DEFAULTS.getSearch();      //Strings are by default immutable, so there's no need to .clone() it
            exclude.clear();
            return this;
        }

        @Override
        public ImmutableNyaaUrl build() {
            return new ImmutableNyaaUrl(this);
        }
    }

    public static NyaaUrlBuilder<ImmutableNyaaUrl> builder() {
        return UrlBuilderImpl.INSTANCE;
    }

    public static NyaaUrlBuilder<ImmutableNyaaUrl> builder(NyaaUrl<?> reference) {
        return UrlBuilderImpl.INSTANCE.setDefaults(reference);
    }

    /** Getter */

    public Page getPage() {
        return page;
    }

    public Filter getFilter() {
        return filter;
    }

    public NyaaCategory getCategory() {
        return category;
    }

    public int getUserID() {
        return userID;
    }

    public int getMinAge() {
        return minAge;
    }

    public int getMaxAge() {
        return maxAge;
    }

    public int getMinSize() {
        return minSize;
    }

    public int getMaxSize() {
        return maxSize;
    }

    public HashSet<Integer> getExclude() {
        return exclude;
    }

    public String getSearch() {
        return search;
    }

    public String getNormalSearch() {
        return normalSearch;
    }

    public String getRssFeed(){
        return rssFeed;
    }
}