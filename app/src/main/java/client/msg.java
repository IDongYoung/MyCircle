package client;

import android.content.Context;

/**
 * Created by Young on 2017/5/7.
 */

public class msg
{
    public String user_id;
    public String user_name;
    public String message;
    public msg(String i,String n,String m)
    {
        user_id = i;
        user_name = n;
        message = m;
    }
    public msg(String i,String m,Context c)
    {
        user_id = i;
        message = m;
        mysqlite my = new mysqlite(c);
        user_name = my.getUserNameById(i);
        my.close();
    }
}
