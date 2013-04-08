import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import javax.xml.bind.DatatypeConverter;

/**
 * Created with IntelliJ IDEA.
 * User: Pavel
 * Date: 8.4.13
 * Time: 17:34
 * To change this template use File | Settings | File Templates.
 */
public class FileTools {

    boolean SaveLinks(String links,String filename) throws FileNotFoundException {
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

    boolean SaveImage(String imageData,String filename, int counter) throws FileNotFoundException {
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
