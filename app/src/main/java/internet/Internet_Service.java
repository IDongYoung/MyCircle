package internet;

import android.os.Handler;

/**
 * Created by Young on 2017/3/10.
 */
public class Internet_Service {
    private Internet_Handler internet = new Internet_Handler();
    public Internet_Service(Handler h)
    {
        Internet_Connect.setHandler(internet);
        internet.setHandler(h);
        new Thread(new Runnable() {
            @Override
            public void run() {
                Internet_Connect.connect();
            }
        }).start();
    }
    public final void send(String s)
    {
        final String msg =s;
        new Thread(new Runnable() {
            @Override
            public void run() {
                internet.sendMessage(msg);
            }
        }).start();
    }
}
