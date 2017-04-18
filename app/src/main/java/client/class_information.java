package client;

import android.content.Context;
import android.util.Log;

import myuntil.myMessage;

/**
 * Created by Young on 2017/4/18.
 */
public class class_information
{
    private String id="no this class";
    private String u_name;
    private String name;
    private String information;
    private String date;
    public class_information(String id, Context c)
    {
        this.id = id;
        mysqlite my = new mysqlite(c);
        String r = my.getClassInformation(id);
        myMessage m = new myMessage(r);
        String[] y = m.decodeMessage();
        Log.v("错误信息",r);
        if(!y[0].equals("-1"))
        {
            this.u_name = y[0];
            this.name = y[1];
            this.information = y[2];
            this.date = y[3];
        }
    }
    public String  getId()
    {
        return id+"";
    }
    public String  getName()
    {
        return name;
    }
    public String  getUserName()
    {
        return u_name;
    }
    public String  getInformation()
    {
        return information;
    }
    public String  getDate()
    {
        return date;
    }
}
