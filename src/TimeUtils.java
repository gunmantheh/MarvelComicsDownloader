import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Pavel
 * Date: 8.4.13
 * Time: 14:59
 * To change this template use File | Settings | File Templates.
 */
public class TimeUtils {
    private Date _startTime;
    private Date _stopTime;

    public TimeUtils(){
    }

    public void Start(Date time){
        set_startTime(time);
    }
    public void Start(){
        set_startTime(new Date());
    }

    public void Stop(Date time){
        set_stopTime(time);
    }

    public void Stop(){
        set_stopTime(new Date());
    }

    public long getTimeInMs(){
        return get_stopTime().getTime() - get_startTime().getTime();
    }

    private Date get_startTime() {
        return _startTime;
    }

    private void set_startTime(Date _startTime) {
        this._startTime = _startTime;
    }

    private Date get_stopTime() {
        return _stopTime;
    }

    private void set_stopTime(Date _stopTime) {
        this._stopTime = _stopTime;
    }
}
