package com.example.young.mycircle;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import client.mysqlite;
import client.user;
import client.userAdapter;

/**
 * Created by Young on 2016/10/31.
 */
public class userActivity extends Activity
{
    private Handler myHandler = new Handler()
    {
        @Override
        public void handleMessage(Message message)
        {
            if (message.what==0)   // 拨打电话
            {
                Bundle b = message.getData();
                String phone_number = b.getString("phone_number");
                Log.v("user","进入打电话");
                Intent intent = new Intent(Intent.ACTION_CALL);
                Uri data = Uri.parse("tel:" + phone_number);
                intent.setData(data);
                if (ActivityCompat.checkSelfPermission(mycontext, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    Log.v("user","没有权限");
                    String str = "无拨打电话权限\r\n请给该应用授权\r\n否则无法使用该功能";
                    Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
                    return;
                }
                startActivity(intent);
            }
            else if (message.what==1)   // 发送短信
            {

            }


        }
    };

    private Context mycontext = this;
    @Override
    public void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        this.setContentView(R.layout.userlayout);
        int id = getIntent().getIntExtra("item_id",-1);
        mysqlite my = new mysqlite(this);
        user[] u = null;
        if (id!=-1) u = my.getAllUsersByClassId(id);
        userAdapter useradapter = new userAdapter(this,u,myHandler);
        ListView listview = (ListView) this.findViewById(R.id.user_listview);
        listview.setAdapter(useradapter);
    }
}
