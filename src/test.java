import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class test {
    public static Document doc;

    public static void main(String[] args) {
        String url = "https://www.sciencedirect.com/science/article/pii/S0003497518302625";
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            System.out.println("oops");
        }
        String lastnames[] = doc.getElementsByClass("text surname").text().split("\\s+");

        Elements element = doc.getElementsByClass("text given-name"); //parse first and last name classes
        String firstname[] = element.eachText().toArray(new String[element.size()]);
        String init = "";
        Pattern p = Pattern.compile("\\b[a-zA-Z]");
        Matcher d;


        String names = new String();
        String holder[] = init.split("");

        if (lastnames.length == 1)
            names = lastnames[0] + ", " + firstname[0] + ".";
        else if(lastnames.length==2)
            names=lastnames[0] + ", " + firstname[0] + " and "+firstname[1]+" "+lastnames[1];
        else
            names=lastnames[0] + ", " + firstname[0] + ", et al.";

    }
}




