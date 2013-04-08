import java.io.*;

public class test1 {
    private boolean _debug;
    private String _filename;
    private String _links;
    private String _linksFilename;

    public test1(String filename, String linksFilename) {
        _debug = false;
        _filename = filename;
        _linksFilename = linksFilename;
    }

    public test1(String filename, String linksFilename, boolean debug) {
        _debug = debug;
        _filename = filename;
        _linksFilename = linksFilename;
}


    public String getLinks() {
        return _links;
    }

    private void setLinks(String link){
        if (link == null) {link = "";}
        _links = link;
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

    public static void main(String[] args) {

        if (args.length == 1) {
            test1 MarvelDownloader = new test1(args[0],args[0] + ".links.txt",true);
            try {
                if (MarvelDownloader.GetLinks()) {
                    MarvelDownloader.SaveLinks(MarvelDownloader.getLinks());
                }

            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        } else {
            System.out.println("Please supply filename as parameter");
        }
    }

    boolean GetLinks() throws IOException {
        try {
            BufferedInputStream file = new BufferedInputStream(new FileInputStream(getFilename()),1048576);
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
                        debug("");
                        debug("konec odkazu");
                        count = 0;
                    } else
                        count = 0;

                    if (zacatekBytes.length == count && !zacatek) {
                        count = 0;
                        zacatek = true;
                        debug("");
                        debug("nelezen zacatek");
                    }
                    if (konecBytes.length == count && zacatek) {
                        count = 0;
                        zacatek = false;
                        debug("");
                        debug("nelezen konec");
                    }

                    if (hrefBytes.length == count && zacatek) {
                        count = 0;
                        href = true;
                        zacatek = false;
                        debug("");
                        debug("nalezen odkaz");
                    }

                    if (href && znak != '\"') {
                        odkaz = odkaz + znak;
                    } else if (href && znak == '\"') {
                        odkaz = odkaz + "\n";
                        numberOfLinks++;
                    }
            }

            file.close();
            performance.Stop();
            debug("File processed in: " + performance.getTimeInMs() + " ms");
            setLinks(odkaz);
            System.out.println(numberOfLinks + " links found");
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            return false;
        }
        return true;
    }

    boolean SaveLinks(String links) throws FileNotFoundException {
        try {

            OutputStream file = new FileOutputStream(getLinksFilename());
            file.write(links.getBytes());
            file.close();
            System.out.println("Links written to: \"" + getFilename() + "\"");
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            return false;
        }
        return true;
    }

    void debug(String message) {
        if (getDebug()) System.out.println(message);
    }
}
