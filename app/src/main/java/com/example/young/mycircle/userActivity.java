package com.example.young.mycircle;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import client.mysqlite;
import client.user;
import client.userAdapter;

/**
 * Created by Young on 2016/10/31.
 */
public class userActivity extends Activity
{
    @Override
    public void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        this.setContentView(R.layout.userlayout);
        int id = getIntent().getIntExtra("item_id",-1);
        mysqlite my = new mysqlite(this);
        user[] u = null;
        if (id!=-1) u = my.getAllUsersByClassId(id);
        userAdapter useradapter = new userAdapter(this,u);
        ListView listview = (ListView) this.findViewById(R.id.user_listview);
        listview.setAdapter(useradapter);

    }
}
