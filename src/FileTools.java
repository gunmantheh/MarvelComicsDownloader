import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.List;
import javax.xml.bind.DatatypeConverter;

/**
 * Created with IntelliJ IDEA.
 * User: Pavel
 * Date: 8.4.13
 * Time: 17:34
 * To change this template use File | Settings | File Templates.
 */
public class FileTools {

    boolean SaveLinks(String links, String filename) throws FileNotFoundException {
        try {

            OutputStream file = new FileOutputStream(filename);
            file.write(links.getBytes());
            file.close();
            System.out.println("Links written to: \"" + filename + "\"");
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            return false;
        }
        return true;
    }

    boolean DownloadLinks(List<String> links, String filename) {
        for (int i = 0; i < links.size(); i++) {
            URL website = null;
            if (links.get(i) != "") {
                try {
                    website = new URL(links.get(i));
                } catch (MalformedURLException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
            }
            ReadableByteChannel rbc = null;
            if (website != null) {
                try {
                    rbc = Channels.newChannel(website.openStream());
                } catch (IOException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
            }
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream("image" + i +".svg");
            } catch (FileNotFoundException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
            try {
                System.out.println("Downloading " + website.getFile());
                fos.getChannel().transferFrom(rbc, 0, 1 << 24);
                System.out.println("Finished " + website.getFile());
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
        return true;
    }

    boolean SaveImage(String imageData, String filename, int counter) throws FileNotFoundException {
        try {

            String counterString = String.valueOf(counter);
            String formattedNumber = "%00d".format(counterString);
            String wholeFilename = filename + formattedNumber + ".jpg";
            OutputStream file = new FileOutputStream(wholeFilename);
            byte[] data = DatatypeConverter.parseBase64Binary(imageData);
            file.write(data);
            file.close();
            System.out.println("Links written to: \"" + wholeFilename + "\"");
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            return false;
        }
        return true;
    }


}
