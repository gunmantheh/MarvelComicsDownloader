import java.io.*;

public class test1 {





    public static void main(String[] args) {

        if (args.length == 1) {
            Linker MarvelDownloader = new Linker(args[0],args[0] + ".links.txt",false);
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

}
