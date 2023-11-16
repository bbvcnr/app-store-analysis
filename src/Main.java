import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Main {
    static HashMap<String, Integer> categories = new HashMap<>();
    public static void main(String[] args) {
        ArrayList<App> apps = new ArrayList<App>();

        try {
            File f1 = new File("1000apps.csv");

            Scanner s1 = new Scanner(f1);

            if (s1.hasNextLine()) s1.nextLine();

            while (s1.hasNextLine()) {
                try {
                    String lineoffile = s1.nextLine();
                    String[] lineparts = lineoffile.split(",");

                    String name = lineparts[0];
                    String id = lineparts[1];
                    String category = lineparts[2];
                    Double rating = Double.valueOf(lineparts[3]);
                    Integer ratingcount = Integer.valueOf((lineparts[4]));
                    String installs = lineparts[5];
                    Integer mininstalls = Integer.valueOf(lineparts[6]);
                    Integer maxinstalls = Integer.valueOf(lineparts[7]);
                    Boolean free = Boolean.valueOf(lineparts[8]);
                    Double price = Double.valueOf(lineparts[9]);
                    String currency = lineparts[10];
                    String size = lineparts[11];
                    String minandroid = lineparts[12];
                    String devid = lineparts[13];
                    String devwebsites = lineparts[14];
                    String devmail = lineparts[15];
                    String released = lineparts[16];
                    String lastupdated = lineparts[17];
                    String contentrating = lineparts[18];
                    String privacypolicy = lineparts[19];
                    Boolean adsupported = Boolean.valueOf(lineparts[20]);
                    Boolean inapppurchases = Boolean.valueOf(lineparts[21]);
                    Boolean editorschoice = Boolean.valueOf(lineparts[22]);
                    String scrapedtime = lineparts[23];

                    App n = new App(name, id, category, rating, ratingcount, installs, mininstalls, maxinstalls, free, price, currency, size, minandroid, devid, devwebsites, devmail, released, lastupdated, contentrating, privacypolicy, adsupported, inapppurchases, editorschoice, scrapedtime);
                    System.out.println(n);
                    apps.add(n);
                }
                catch (NumberFormatException e) {
                    throw new RuntimeException(e);
                }
            }
            s1.close();
        }

        catch(Exception e){
            System.out.println(e);
        }

        getCategories(apps);
    }

    private static void getCategories(ArrayList<App> apps) {
        System.out.println(apps);
        for (App a : apps) {
            //System.out.println(a.getCategory());
            if (categories.containsKey(a.getCategory())) {

                categories.put(a.getCategory(), categories.get(a.getCategory()) + 1);
            } else {
                categories.put(a.getCategory(), 1);
            }
            System.out.println(a.getCategory());
        }
        System.out.println(categories);

    }

}

