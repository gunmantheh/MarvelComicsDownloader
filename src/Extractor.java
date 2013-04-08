import java.io.*;
import sun.misc.BASE64Decoder;
/**
 * Created with IntelliJ IDEA.
 * User: Pavel
 * Date: 8.4.13
 * Time: 17:32
 * To change this template use File | Settings | File Templates.
 */
public class Extractor {
    private static boolean DEBUG = true;


    private final String _filename;
    private String _links;
    private boolean _debug;

    public Extractor(String filename) {
        _filename = filename;
    }


    public String getLinks() {
        return _links;
    }

    private void setLinks(String link){
        if (link == null) {link = "";}
        _links = link;
    }

    String getFilename() {
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
            byte[] zacatekBytes = "<!--[if !IE]>-->".getBytes();
            byte[] konecBytes = "<![endif]-->".getBytes();
            byte[] hrefBytes = "data:image/jpeg;base64,".getBytes();
            boolean zacatek = false;
            boolean href = false;
            int count = 0;
            String odkaz = "";
            char znak;
            int numberOfLinks = 0;
            FileTools fileTools = new FileTools();
            int tempCount = 0;
            TimeUtils performance = new TimeUtils();
            TimeUtils partialPerf = new TimeUtils();
            performance.Start();
            partialPerf.Start();
            while ((data = (byte) file.read()) != -1) {
                /* temp count*/
                tempCount++;
                if (tempCount > 1024*20)
                {
                    tempCount = 0;
                    partialPerf.Stop();
                    genericUtils.debug(String.valueOf(1024*1000/1024/partialPerf.getTimeInMs()) + "KB/s");
                    fileTools.SaveImage(odkaz,getFilename(), numberOfLinks);
                    //partialPerf.Start();
                    break;
                }
                /* temp count end*/

                znak = (char) data;
                if (!href && count < zacatekBytes.length && znak == zacatekBytes[count])
                    count++;
                else if (!href && count < konecBytes.length && znak == konecBytes[count])
                    count++;
                else if (!href && count < hrefBytes.length && znak == hrefBytes[count] && zacatek)
                    count++;
                else if (href && znak == '\"') {
                    href = false;
                    genericUtils.debug("");
                    genericUtils.debug("konec obrazku");
                    count = 0;
                } else
                    count = 0;

                if (zacatekBytes.length == count && !zacatek) {
                    count = 0;
                    zacatek = true;
                    genericUtils.debug("");
                    genericUtils.debug("nelezen zacatek");
                }
                if (konecBytes.length == count && zacatek && count == 0) {
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
                    genericUtils.debug("nalezen obrazek");
                }

                if (href && znak != '\"') {
                    odkaz = odkaz + znak;
                } else if (href && znak == '\"') {
                    fileTools.SaveImage(odkaz,getFilename(), numberOfLinks);
                    numberOfLinks++;
                    odkaz = "";
                }
            }

            file.close();
            performance.Stop();
            GenericUtils genUtils = new GenericUtils(DEBUG);
            genUtils.debug("File processed in: " + performance.getTimeInMs() + " ms");
            setLinks(odkaz);
            System.out.println(numberOfLinks + " links found");
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            return false;
        }
        return true;
    }

}