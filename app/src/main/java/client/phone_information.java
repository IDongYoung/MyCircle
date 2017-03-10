package client;

import android.content.Context;
import android.telephony.TelephonyManager;
import android.widget.TextView;

/**
 * Created by Young on 2016/11/1.
 */
public class phone_information
{
    Context context;
    public String phonenumber="";
    public phone_information(Context c)
    {
        context = c;
    }
    public String getPhoneNumber()
    {
        PhoneInfo siminfo = new PhoneInfo(context);
        phonenumber = siminfo.pnum;
        String pnum = siminfo.getNativePhoneNumber();
        return pnum;
    }
    public String getIMSINumber()
    {
        PhoneInfo siminfo = new PhoneInfo(context);
        String pnum = siminfo.getIMSINumber();
        return pnum;
    }
    public String getAddress()
    {
        return "上海市";
    }
}
