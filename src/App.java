public class App {
    private final String name;
    private final String id;
    private final String category;
    private final Double rating;
    private final Integer rcount;
    private final String installs;
    private final Integer mininstalls;
    private final Integer maxinstalls;
    private final Boolean free;
    private final Double price;
    private final String currency;
    private final String size;
    private final String minandroid;
    private final String devid;
    private final String devwebsite;
    private final String devmail;
    private final String released;
    private final String lastupdated;
    private final String contentrating;
    private final String privpolicy;
    private final Boolean adsupported;
    private final Boolean inapppurchases;
    private final Boolean edchoice;
    private final String scrapedtime;

    public App(String name, String id, String category, Double rating, Integer rcount, String installs, Integer mininstalls, Integer maxinstalls, Boolean free, Double price, String currency, String size, String minandroid, String devid, String devwebsite, String devmail, String released, String lastupdated, String contentrating, String privpolicy, Boolean adsupported, Boolean inapppurchases, Boolean edchoice, String scrapedtime) {
        this.name = name;
        this.id = id;
        this.category = category;
        this.rating = rating;
        this.rcount = rcount;
        this.installs = installs;
        this.mininstalls = mininstalls;
        this.maxinstalls = maxinstalls;
        this.free = free;
        this.price = price;
        this.currency = currency;
        this.size = size;
        this.minandroid = minandroid;
        this.devid = devid;
        this.devwebsite = devwebsite;
        this.devmail = devmail;
        this.released = released;
        this.lastupdated = lastupdated;
        this.contentrating = contentrating;
        this.privpolicy = privpolicy;
        this.adsupported = adsupported;
        this.inapppurchases = inapppurchases;
        this.edchoice = edchoice;
        this.scrapedtime = scrapedtime;
    }


    public String getCategory() {
        return category;
    }
}
