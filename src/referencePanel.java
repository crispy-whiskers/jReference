import net.miginfocom.layout.CC;
import net.miginfocom.swing.MigLayout;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class referencePanel extends JPanel {

    private boolean Apa;
    private boolean Mla;
    private JTextField url1;
    private JEditorPane cite;


    final String int5gr1ty_2 = "1190de84d01ce12f0c54a01a4752a33e659efad4247b13fd5b4381a63307412f5350491e7f078aa5b24fb3f86f9f722cf9ac2dd2a28a779e467d323d0b1aef605==";
    //dw bout it


    private referencePanel() {

        JButton about;
        JLabel instructions, title;   //local fields
        JButton get;

        setLayout(new MigLayout()); //miglayout is OG


        CC cc = new CC(); //component constraint, its a miglayout thing
        cc.alignX("center").spanX();

        title = new JLabel("<html><h1 style=\"text-align:center\">J-Reference</h1><p><br/>A timesaver for database journal citations.</p></html>", SwingConstants.CENTER);
        add(title, cc);        //adding panel components
        url1 = new JTextField(30);
        add(url1, cc);

        ButtonGroup group = new ButtonGroup();
        JRadioButton apa = new JRadioButton("APA 6th Edition");
        JRadioButton mla = new JRadioButton("MLA 8th Edition"); //radio buttons are pretty dank
        group.add(apa);
        group.add(mla);
        apa.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Apa = true;
                Mla = false;
                //i could just use one boolean, but this is a citation generator, not a game. ill use
                // as many bytes as i want.
            }
        });
        mla.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Apa = false;
                Mla = true;
            }
        });
        add(apa, cc);
        add(mla, cc);

        get = new JButton("Cite");
        get.addActionListener(new Listener1());
        get.setPreferredSize(new Dimension(100, 25)); //button
        add(get, cc);

        instructions = new JLabel("<html><p style=\"text-align:center\">Paste the url of the journal article above and press Cite when <br> you're ready!</p></html>", SwingConstants.CENTER);
        add(instructions, cc);

        JPanel subpanel2 = new JPanel(); //did you know jlabels support html formatting? well now you do
        cite = new JEditorPane("text/html", "Your citation will appear here! Everything is already in Times New Roman size 12 :)");
        cite.setPreferredSize(new Dimension(430, 120));
        cite.setFont(new Font("Times New Roman", Font.PLAIN, 12));
        add(cite, cc); //why are my var names so weird lol

        about = new JButton("About");
        about.addActionListener(new ActionListener() {
            int count = 0;

            @Override
            public void actionPerformed(ActionEvent e) { //about section and button
                count++;
                JOptionPane.showMessageDialog(null, "jAPA-Java Citation generator coded on IntelliJ \n" + "using JSoup HTML parser and MiGLayout Library.\n\nVersion 1.7\n\nConcept and Coding" + " by Allen Nguyen", "About me", JOptionPane.INFORMATION_MESSAGE);
                if (count > 40) {
                    about.setText("every click ur mum +1 gay"); //hehe easter egg for you filthy nerds
                    count = 40;
                }
            }
        });

        add(about, cc);


    }

    private class Listener1 implements ActionListener { //citation algorithm
        String names[];
        String name1;
        String date;
        String pages;
        String journal;
        String DOI;
        String title;
        String vols[];
        Document doc;

        public void actionPerformed(ActionEvent e) {
            org.jsoup.Connection.Response response = null;
            try {
                response = Jsoup.connect(url1.getText()).timeout(100000) //get error code
                        .ignoreHttpErrors(true).execute();
                doc = Jsoup.connect(url1.getText()).get(); //get html from database

            } catch (IOException f) {
                cite.setText("Connection error: could not access site. ");
            } catch (IllegalArgumentException g) {
                cite.setText("Whoops, that url isn't valid or supported! Sorry :( ");

            }


            if (url1.getText().contains("sciencedirect")) { //lol half the time i dont know what im typing


                if (Apa && !Mla) {
                    getAPAScienceDirect(doc);

                    String name = "";
                    if (names.length != 1) {
                        for (int a = 0; a < names.length; a++) {
                            if (a == names.length - 1) {
                                name += "& " + names[a];   //commas are very important so i left 12 lines of code for it
                            } else if (a == 6) {
                                name += " et al.";
                                break;
                            } else if (a < (names.length - 2)) {
                                name += names[a] + ", ";
                            }
                        }
                    } else {
                        name = names[0];
                    }

                    date = "(" + date + "). ";


                    if (!vols[1].equals("none"))
                        cite.setText("<html><p>" + name + " " + date + title + "<i>" + journal + ", " + vols[0] + "</i>(" + vols[1] + "). " + DOI + "</p></html>");
                    else
                        cite.setText("<html><p>" + name + " " + date + title + "<i>" + journal + ", " + vols[0] + "</i>. \n" + DOI + "</p></html>"); //volume to volume(issue)
                } else if (!Apa && Mla) {
                    getMLAScienceDirect(doc);

                    Date objDate = new Date();
                    String strDateFormat = "dd MMM yyyy";
                    SimpleDateFormat objSDF = new SimpleDateFormat(strDateFormat);

                    String accessed = objSDF.format(objDate);
                    if (!vols[1].equals("none"))
                        cite.setText("<html><p>" + name1 + " \"" + title + "\" <i>" + journal + "</i> " + vols[0] + ". (" + date + "): " + pages + ". <i>ScienceDirect.</i> Web. " + accessed + "</p></html>");
                    else
                        cite.setText("<html><p>" + name1 + " \"" + title + "\" <i>" + journal + "</i>. " + vols[0] + ". (" + date + "): " + pages + ". <i>ScienceDirect.</i> Web. " + accessed + ". </p></html>");

                }
            } else if (url1.getText().contains("proquest.com") || url1.getText().contains("jstor.org") || url1.getText().contains("gale.") || url1.getText().contains("accessscience.com")) {
                cite.setText("I sure as heck don't to code support for that database in. The website literally gives you the entire citation so go get it smh");
            }

        }

        private void getAPAScienceDirect(Document doc) {
            int hyphen = (doc.title().lastIndexOf(45));
            title = doc.title().substring(0, hyphen - 1); //get title, format it
            title += ". ";
            names = ScienceDirect.AuthorAPA(doc); //should return Lastname, F.
            date = ScienceDirect.Date(doc); //return year
            vols = ScienceDirect.Volume(doc); //return volume(issue)
            pages = ScienceDirect.Pages(doc); //return pg-pgs
            journal = ScienceDirect.Journal(doc); //return journaltitle
            DOI = ScienceDirect.DOI(doc); //return DOI, if there is one
        }

        private void getMLAScienceDirect(Document doc) {
            int hyphen = (doc.title().lastIndexOf(45));
            title = doc.title().substring(0, hyphen - 1); //get title, format it
            title += ".";
            name1 = ScienceDirect.AuthorMLA(doc);
            date = ScienceDirect.Date(doc); //return year
            vols = ScienceDirect.Volume(doc); //return volume(issue)
            pages = ScienceDirect.Pages(doc); //return pg-pgs
            journal = ScienceDirect.Journal(doc); //return journaltitle
            DOI = ScienceDirect.DOI(doc); //return DOI, if there is one
        }

    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Java Citation Generator");
        frame.setSize(470, 480);
        frame.setLocation(400, 100);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setContentPane(new referencePanel());
        frame.getContentPane().setBackground(new Color(255, 241, 208));


        frame.setVisible(true);

    }
}

