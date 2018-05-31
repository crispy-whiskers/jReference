
import org.jsoup.select.Elements;

import java.util.regex.*;


public class ScienceDirect {
    public static String[] AuthorAPA(org.jsoup.nodes.Document doc) {
        String lastnames[] = doc.getElementsByClass("text surname").text().split("\\s+");

        Elements element = doc.getElementsByClass("text given-name"); //parse first and last name classes

        String firstinit[] = element.eachText().toArray(new String[element.size()]);
        String init = "";
        Pattern p = Pattern.compile("\\b[a-zA-Z]");
        Matcher d;


        for(int a = 0; a<firstinit.length;a++){     //check for middle initials
            if(firstinit[a]!=null) {
                d = p.matcher(firstinit[a]);

                if (d.find()) {
                    init += firstinit[a].substring(d.start(), d.end()); //why is regex so wack
                }
            }
        }

        String names[] = new String[lastnames.length];
        String holder[] = init.split("");

        for (int b = 0; b < firstinit.length; b++) { //append first initial to name

                names[b] = lastnames[b] + ", " + holder[b] + ".";
        }
        return names;
    }
    public static String AuthorMLA(org.jsoup.nodes.Document doc) {
        String lastnames[] = doc.getElementsByClass("text surname").text().split("\\s+");

        Elements element = doc.getElementsByClass("text given-name"); //parse first and last name classes
        String firstname[] = element.eachText().toArray(new String[element.size()]);
        String init = "";
        Pattern p = Pattern.compile("\\b[a-zA-Z]");
        Matcher d;
        //duude, i know this code is horrendous and messy but shut up, it works

        String names = "";
        String holder[] = init.split("");

        if (lastnames.length == 1)
            names = lastnames[0] + ", " + firstname[0] + ".";    //just formatting, idk why im doing it this way
        else if(lastnames.length==2)
            names=lastnames[0] + ", " + firstname[0] + " and "+firstname[1]+" "+lastnames[1];
        else
            names=lastnames[0] + ", " + firstname[0] + ", et al.";

        return names;
    }



    public static String Date(org.jsoup.nodes.Document doc) {
        String date = doc.getElementsByClass("text-xs").text();

        Pattern datepattern = Pattern.compile("\\d\\d\\d\\d");
        Matcher m = datepattern.matcher(date); //just parse for teh year
        if (m.find()) //if found a match
            return date.substring(m.start(), m.end());
        else
            return "Date Not Found";

    }

    public static String[] Volume(org.jsoup.nodes.Document doc) {
        String vol = doc.getElementsByClass("text-xs").text(); //
        Pattern vi = Pattern.compile("(Volume\\s\\d+,\\s[^Issue])"); //find vol and issue number
        Pattern vi2 = Pattern.compile("(Volume\\s\\d+,\\sIssue\\s\\d+)");
        Matcher m = vi.matcher(vol); //first regex to find case issue and volume
        Matcher n = vi2.matcher(vol);
        String numberarray[] = new String[2]; //new array to store numbers

        if(m.find()){  //just volume

            vol = vol.substring(m.start(),m.end()-1);
            Pattern num = Pattern.compile("\\d{1,4}"); //find number
            Matcher d = num.matcher(vol);
            d.find();
            numberarray[0]=vol.substring(d.start(),d.end());
            numberarray[1]="none";

        }
        else if(n.find()){ //volume + issue

            vol = vol.substring(n.start(), n.end());
            Pattern num = Pattern.compile("\\d{1,4}"); //find number
            Matcher d = num.matcher(vol);
            for(int count=0;d.find();count++) {
            numberarray[count]=vol.substring(d.start(),d.end());
            }

        } else {
            String response[] = {"Journal Num Not found", "Journal Num Not found"};
            return response; //haha im bad at coding
        }
        return numberarray;

    }
    public static String Pages(org.jsoup.nodes.Document doc){
        String text = doc.getElementsByClass("text-xs").text();
        Pattern pg = Pattern.compile("\\d+-\\d+");  //parse for pages
        Matcher m = pg.matcher(text);
        if(m.find()){
            return m.group();
        } else {
            return "Pages not found";
        }
    }
    public static String Journal(org.jsoup.nodes.Document doc){
        String element = doc.getElementsByClass("publication-title-link").text();
        return element; //title is an href element, so just get text

    }
    public static String DOI(org.jsoup.nodes.Document doc){
        String element = doc.getElementsByClass("doi").text(); //stored in href element, so just .text()
        if(element.equals(null))
            return "DOI not found";
        return element;

    }
}

