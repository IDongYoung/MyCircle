package client;

import android.content.Context;
import android.telephony.TelephonyManager;

/**
 * 读取手机设备信息测试代码
 */
public class PhoneInfo {

    private TelephonyManager telephonyManager;
    /**
     * 国际移动用户识别码
     */
    private String IMSI;
    private Context cxt;
    public String imsi = "no imsi";
    public String pnum = "no phone number";
    public PhoneInfo(Context context) {
        cxt=context;
        telephonyManager = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
    }

    /**
     * 获取电话号码
     */
    public String getNativePhoneNumber() {
        String NativePhoneNumber=pnum;
        try
        {
            String temp=telephonyManager.getLine1Number();
            if(temp != null && !temp.equals("")) NativePhoneNumber = temp;
        }catch (Exception e) { }
        return NativePhoneNumber;
    }

    public String getIMSINumber()
    {
        String simSer = imsi;
        try
        {
            String temp = telephonyManager.getSubscriberId();
            if(temp != null && !temp.equals("")) simSer = temp;
        }catch (Exception e) { }
        return simSer;
    }

    /**
     * 获取手机服务商信息
     */
    public String getProvidersName() {
        String ProvidersName = "N/A";
        try{
            IMSI = telephonyManager.getSubscriberId();
            // IMSI号前面3位460是国家，紧接着后面2位00 02是中国移动，01是中国联通，03是中国电信。
            System.out.println(IMSI);
            if (IMSI.startsWith("46000") || IMSI.startsWith("46002")) {
                ProvidersName = "中国移动";
            } else if (IMSI.startsWith("46001")) {
                ProvidersName = "中国联通";
            } else if (IMSI.startsWith("46003")) {
                ProvidersName = "中国电信";
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return ProvidersName;
    }
}