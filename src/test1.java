import java.io.*;

public class test1 {

    public static void main(String[] args) {

        if (args.length >= 2) {
            String argument = args[1];
            switch (argument.toLowerCase()) {
                case "-l":
                    Linker MarvelDownloader = new Linker(args[0], args[0] + ".links.txt", false);
                    try {
                        if (MarvelDownloader.GetLinks()) {
                            FileTools fileTools = new FileTools();
                            fileTools.DownloadLinks(MarvelDownloader.getLinks(), MarvelDownloader.getFilename());
                        }


                    } catch (IOException e) {
                        e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                    }
                    break;
                case "-e":
                    Extractor MarvelExtractor = new Extractor(args[0]);
                    try {
                        if (MarvelExtractor.GetLinks()) {
                        }
                    } catch (IOException e) {
                        e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                    }

                    break;
            }
        } else {
            System.out.println("Please supply filename as parameter");
        }
    }

}
