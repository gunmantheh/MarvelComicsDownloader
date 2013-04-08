/**
 * Created with IntelliJ IDEA.
 * User: Pavel
 * Date: 8.4.13
 * Time: 17:47
 * To change this template use File | Settings | File Templates.
 */
public class FileProcessor {
    private char data;
    private byte[] zacatekBytes;
    private byte[] konecBytes;
    private byte[] hrefBytes;
    private boolean zacatek;
    private boolean href;
    private int count;
    private String odkaz;
    private int numberOfLinks;
    private GenericUtils genericUtils;

    public FileProcessor(char data, byte[] zacatekBytes, byte[] konecBytes, byte[] hrefBytes, boolean zacatek, boolean href, int count, String odkaz, int numberOfLinks, boolean debug) {
        this.data = data;
        this.zacatekBytes = zacatekBytes;
        this.konecBytes = konecBytes;
        this.hrefBytes = hrefBytes;
        this.zacatek = zacatek;
        this.href = href;
        this.count = count;
        this.odkaz = odkaz;
        this.numberOfLinks = numberOfLinks;
        this.genericUtils = new GenericUtils(debug);
    }

    public boolean isZacatek() {
        return zacatek;
    }

    public boolean isHref() {
        return href;
    }

    public int getCount() {
        return count;
    }

    public String getOdkaz() {
        return odkaz;
    }

    public int getNumberOfLinks() {
        return numberOfLinks;
    }

    /*public FileProcessor invoke() {
        char znak;
        znak = data;
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
        } else if (href && znak == '\"') {
            odkaz = odkaz + "\n";
            numberOfLinks++;
        }
        return this;
    }*/

    private FileProcessor Process(char data, byte[] zacatekBytes, byte[] konecBytes, byte[] hrefBytes, boolean zacatek, boolean href, int count, String odkaz, int numberOfLinks) {
        char znak;
        znak = data;
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
        } else if (href && znak == '\"') {
            odkaz = odkaz + "\n";
            numberOfLinks++;
        }
        return this;
    }
}
