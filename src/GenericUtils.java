/**
 * Created with IntelliJ IDEA.
 * User: Pavel
 * Date: 8.4.13
 * Time: 17:53
 * To change this template use File | Settings | File Templates.
 */
public class GenericUtils {

    private boolean _debug;

    public GenericUtils(boolean debug) {
        _debug = debug;
    }

    public void SetDebug(boolean debug){
        _debug = debug;
    }

    void debug(String message) {
        if (_debug) System.out.println(message);
    }
}
