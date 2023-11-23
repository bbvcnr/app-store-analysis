public class App {
    private final String appName;
    private final String appId;
    private final String category;
    private final String Installs;
    private final String free;
    private final Double price;
    private final String devId;
    private final String devMail;

    public App(String appName, String appId, String category, String Installs, String free, Double price, String devId, String devMail){
        this.appName = appName;
        this.appId=appId;
        this.category=category;
        this.Installs=Installs;
        this.free = free;
        this.price = price;
        this.devId = devId;
        this.devMail = devMail;
    }

    public String getAppId() {return appId;}
    public String getCategory() {
        return category;
    }
    public String getFree() {return free;}
    public String getInstalls() {return Installs;}
    public String getDevMail() { return devMail;}

    public String getDevId() {
        return devId;
    }

    public Double getPrice() {
        return price;
    }

    public String getAppName() {
        return appName;
    }
}
