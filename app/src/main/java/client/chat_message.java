package client;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Young on 2017/5/3.
 */

public class chat_message
{
    public String class_id;
    public ArrayList<msg> message = new ArrayList<msg>();
    public chat_message(Context context,String c_id)
    {
        class_id = c_id;
        mysqlite my = new mysqlite(context);
        Cursor c = my.get_chat_message(class_id);
        while(c.moveToNext())
        {
            msg t = new msg(c.getInt(0)+"",c.getString(1),c.getString(2));
            message.add(t);
        }
        my.close();
    }
}
