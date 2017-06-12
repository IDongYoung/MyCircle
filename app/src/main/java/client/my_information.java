package client;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Young on 2017/4/19.
 */

public class my_information
{
    private String id;
    private String name = "";
    private String pas = "";
    private String email="";
    private String address="";
    private String date="";
    private String phone="";
    private Context context;
    public my_information(Context c)
    {
        context = c;
        SharedPreferences sharedpreferences = c.getSharedPreferences("information", Activity.MODE_PRIVATE);
        name = sharedpreferences.getString("name","");
        id = sharedpreferences.getInt("id",-1)+"";
        pas = sharedpreferences.getString("password","");
        email = sharedpreferences.getString("email","");
        address = sharedpreferences.getString("address","");
        date = sharedpreferences.getString("date","");
        phone = sharedpreferences.getString("phone","");
    }
    public String getName()
    {
        return name;
    }
    public String getID()
    {
        return id;
    }
    public String getPassword()
    {
        return pas;
    }
    public String getEmail()
    {
        return email;
    }
    public String getAddress()
    {
        return address;
    }
    public String getDate()
    {
        return date;
    }
    public String getPhone()
    {
        return phone;
    }
}
