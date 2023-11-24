import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Main {
    static HashMap<String, Integer> categories = new HashMap<>();
    static HashMap<String, Integer> topCompanies = new HashMap<>();
    static Map<String, Integer> developersWithApps = new HashMap<>();
    static Integer badLines = 0;
    public static void main(String[] args) throws IOException {
        ArrayList<App> apps = new ArrayList<App>();
        Long freeInstalls = 0L;
        Long paidInstalls = 0L;

        try {
            File f1 = new File("Google Play Store Apps.csv");

            Scanner s1 = new Scanner(f1);

            if (s1.hasNextLine()) s1.nextLine();

            while (s1.hasNextLine()) {
                try {
                    String lineoffile = s1.nextLine();
                    String[] lineparts = lineoffile.split(",(?=(?:[^\\\"]*\\\"[^\\\"]*\\\")*[^\\\"]*$)");

                    String appName = lineparts[0];
                    String appId = lineparts[1];
                    String category= lineparts[2];
                    String Installs = lineparts[5];
                    String free = lineparts[8];
                    Double price = Double.parseDouble(lineparts[9].trim());
                    String devId = lineparts[13];
                    String devMail = lineparts[15];

                    String numericValue = Installs.replaceAll("[^\\d]", "");
                    long intInstalls = Long.parseLong(numericValue);

                    App n = new App(appName, appId, category, Installs, free, price, devId, devMail);
                    if (Objects.equals(n.getFree(), "True")) freeInstalls += intInstalls;
                    else paidInstalls += intInstalls;
                    apps.add(n);
                }
                catch (NumberFormatException e) {
                    badLines++;
                    continue;
                }
            }
            s1.close();
        }

        catch(Exception e){
            System.out.println(e);
        }

        getCategories(apps);
        SaveToReport1(categories);
        getTopCompanies(apps);
        SaveToReport2(topCompanies);
        topThreeDevelopers(apps);
        SaveToReport3(developersWithApps);
        Map<String, Double> prices = new HashMap<>(AppPrices(apps));
        Map<String, Double> pricedMap = new HashMap<>(prices);
        List<String> boughtApps1 = new ArrayList<>(CalculateAppsToBuy(pricedMap, 1000));
        List<String> boughtApps2 = new ArrayList<>(CalculateAppsToBuy(pricedMap, 10000));
        SaveToReport4(boughtApps1);
        SaveToReport4b(boughtApps2);
        SaveToReport5(freeInstalls, paidInstalls);
        ErrorReport(badLines);
    }

    private static void getCategories(ArrayList<App> apps) {
        for (App a : apps) {
            //System.out.println(a.getCategory());
            if (categories.containsKey(a.getCategory())) {

                categories.put(a.getCategory(), categories.get(a.getCategory()) + 1);
            } else {
                categories.put(a.getCategory(), 1);
            }
        }
    }
    private static void SaveToReport1(Map<String, Integer> catgs) throws IOException {
        FileWriter fw = new FileWriter("Report 1.csv");
        fw.write("App categories:\n");
        for (Map.Entry<String, Integer> entry : catgs.entrySet()) {
            fw.write(entry.getKey() + "," + entry.getValue() + "\n");
        }
        fw.close();
    }
    private static String getCompanyName(App a){
        String full = a.getAppId();
        String fullId = full + ".";
        String[] idParts = fullId.split("\\.");
        if (idParts.length>=2) {
            String company = new StringBuilder().append(idParts[0]).append(".").append(idParts[1]).toString();
            return company;
        }
        return idParts[0];
    }
    private static void getTopCompanies(ArrayList<App> apps) {
        for (App a : apps) {
                String company = getCompanyName(a);
                if (topCompanies.containsKey(company)) {
                    topCompanies.put(company, topCompanies.get(company) + 1);
                } else {
                    topCompanies.put(company, 1);
                }
        }
    }
    private static void SaveToReport2(Map<String, Integer> comps) throws IOException {
        FileWriter fw = new FileWriter("Report 2.csv");
        fw.write("Top 100 companies with most apps:\n");

        List<Map.Entry<String,Integer>> sortedCompanies = new ArrayList<>(comps.entrySet());
        sortedCompanies.sort((entry1,entry2) -> entry2.getValue().compareTo(entry1.getValue()));

        for (int i=0; i<Math.min(sortedCompanies.size(),100); i++) {
            Map.Entry<String,Integer> entry = sortedCompanies.get(i);
            fw.write(entry.getKey() + ": " + entry.getValue() + " apps \n");
        }
        fw.close();
    }
    private static void topThreeDevelopers(ArrayList<App> apps) {
        for (App a : apps) {
            String[] devParts = a.getDevMail().split("@");
            if (devParts.length > 1) {
                String devName = devParts[1];
                String[] parts = devName.split("\\.");

                if (parts.length >= 2) {
                    String name = new StringBuilder().append(parts[1]).append(".").append(parts[0]).toString();
                    String id = a.getDevId();
                    if (!name.equals(getCompanyName(a))) {
                        if (developersWithApps.containsKey(id)) {
                            developersWithApps.put(id, developersWithApps.get(id) + 1);
                        } else {
                            developersWithApps.put(id, 1);
                        }
                    }
                }
            }
        }
    }
    private static void SaveToReport3(Map<String, Integer> comps) throws IOException {
        FileWriter fw = new FileWriter("Report 3.csv");
        fw.write("Independent developers with the most apps:\n");

        List<Map.Entry<String, Integer>> sortedDevelopers = new ArrayList<>(developersWithApps.entrySet());
        sortedDevelopers.sort((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()));

        for (int i = 0; i < Math.min(sortedDevelopers.size(), 3); i++) {
            Map.Entry<String, Integer> entry = sortedDevelopers.get(i);
            fw.write(entry.getKey() + ": " + entry.getValue() + " apps\n");
        }
        fw.close();
    }
    private static Map<String, Double> AppPrices(ArrayList<App> apps){
        Map<String, Double> appPrices = new HashMap<>();
        for (App a: apps){
            appPrices.put(a.getAppName(), a.getPrice());
        }
        return appPrices;
    }
    private static Map<String, Double> AppsByPrice(Map<String, Double> appPrices){
        Map<String, Double> sortedPrices = new HashMap<>(appPrices);
        sortedPrices.entrySet().stream().sorted((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()));
        return sortedPrices;
    }
    private static List<String> CalculateAppsToBuy(Map<String, Double> appPrices, double budget) {
        List<String> appsBought = new ArrayList<>();
        double remainBudget = budget;

        for (Map.Entry<String, Double> entry : appPrices.entrySet()) {
            if (remainBudget >= entry.getValue()) {
                remainBudget -= entry.getValue();
                appsBought.add(entry.getKey());
            } else {
                break;
            }
        }
        return appsBought;
    }
    private static void SaveToReport4(List<String> appsbought1) throws IOException {
        try(FileWriter fw = new FileWriter("Report 4.csv")) {
            fw.write("Apps that can be bought with $1000\n");
            for (String appName : appsbought1) {
                fw.write(appName + "\n");
            }
        }
    }
    private static void SaveToReport4b(List<String> appsbought2) throws IOException {
        try(FileWriter fw = new FileWriter("Report 4b.csv")) {
            fw.write("Apps that can be bought with $10000\n");
            for (String appName : appsbought2) {
                fw.write(appName + "\n");
            }
        }
    }
    private static void SaveToReport5(Long free, Long paid) throws IOException {
        try(FileWriter fw = new FileWriter("Report 5.csv")) {
            fw.write("Total number of installs for free and paid apps: \n");
            fw.write("Total number of installs for free apps: \n");
            fw.write(String.valueOf(free) + "\n");
            fw.write("Total number of installs for paid apps: \n");
            fw.write(String.valueOf(paid));
        }
    }
    private static void ErrorReport(Integer lines) throws IOException {
        try(FileWriter fw = new FileWriter("Report errors.csv")) {
            fw.write("Total number of bad lines: \n");
            fw.write(String.valueOf(badLines) + "\n");
        }
    }
}

