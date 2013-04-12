import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Pavel
 * Date: 8.4.13
 * Time: 17:24
 * To change this template use File | Settings | File Templates.
 */
public class Linker {

    private String _filename;
    private List<String> _links;
    private String _linksFilename;
    private boolean _debug;
    private static boolean DEBUG = true;


    public Linker(String filename, String linksFilename) {
        _filename = filename;
        _linksFilename = linksFilename;
        _links = new ArrayList<String>();
    }

    public Linker(String filename, String linksFilename, boolean debug) {
        _filename = filename;
        _linksFilename = linksFilename;
        _links = new ArrayList<String>();
    }


    public List<String> getLinks() {
        return _links;
    }

    private void setLinks(List<String> link){
        if (link == null) {link = new ArrayList<String>();}
        _links = link;
    }

    private void addLink(String link){
        if (link != null && !link.equals("")){
        _links.add(link);
        }
    }

    public String getLinksFilename() {
        return _linksFilename;
    }

    private void setLinksFilename(String linksFilename){
        if (linksFilename == null) {linksFilename = "";}
        _linksFilename = linksFilename;
    }

    public String getFilename() {
        return _filename;
    }

    public boolean getDebug(){
        return _debug;
    }

    boolean GetLinks() throws IOException {
        try {
            BufferedInputStream file = new BufferedInputStream(new FileInputStream(getFilename()),1048576);
            GenericUtils genericUtils = new GenericUtils(DEBUG);
            byte data;
            byte[] zacatekBytes = "<!--[if IE 9]>".getBytes();
            byte[] konecBytes = "<![endif]-->".getBytes();
            byte[] hrefBytes = "data=\"".getBytes();
            boolean zacatek = false;
            boolean href = false;
            int count = 0;
            String odkaz = "";
            char znak;
            int numberOfLinks = 0;
            TimeUtils performance = new TimeUtils();
            performance.Start();
            while ((data = (byte) file.read()) != -1) {
                znak = (char) data;
                if (count < zacatekBytes.length && znak == zacatekBytes[count])
                    count++;
                else if (count < konecBytes.length && znak == konecBytes[count])
                    count++;
                else if (count < hrefBytes.length && znak == hrefBytes[count] && zacatek)
                    count++;
                else if (href && znak == '\"') {
                    href = false;
                    genericUtils.debug("");
                    genericUtils.debug("konec odkazu");
                    count = 0;
                } else
                    count = 0;

                if (zacatekBytes.length == count && !zacatek) {
                    count = 0;
                    zacatek = true;
                    genericUtils.debug("");
                    genericUtils.debug("nelezen zacatek");
                }
                if (konecBytes.length == count && zacatek) {
                    count = 0;
                    zacatek = false;
                    genericUtils.debug("");
                    genericUtils.debug("nelezen konec");
                }

                if (hrefBytes.length == count && zacatek) {
                    count = 0;
                    href = true;
                    zacatek = false;
                    genericUtils.debug("");
                    genericUtils.debug("nalezen odkaz");
                }

                if (href && znak != '\"') {
                    odkaz = odkaz + znak;
                } else if (href && znak == '\"' && odkaz != null && !odkaz.equals("")) {
                    addLink(odkaz);
                    odkaz = "";
                    numberOfLinks++;
                }
            }

            file.close();
            performance.Stop();
            GenericUtils genUtils = new GenericUtils(DEBUG);
            genUtils.debug("File processed in: " + performance.getTimeInMs() + " ms");
            System.out.println(numberOfLinks + " links found");
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            return false;
        }
        return true;
    }
}
