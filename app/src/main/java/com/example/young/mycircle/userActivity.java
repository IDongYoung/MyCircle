package com.example.young.mycircle;

import android.Manifest;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import client.class_information;
import client.mysqlite;
import client.user;
import client.userAdapter;
import myuntil.myMessage;

/**
 * Created by Young on 2016/10/31.
 */
public class userActivity extends AppCompatActivity
{
    private class_information the_class_information = null;
    private ListView listview = null;
    private Context mycontext = this;
    private upLoadService iService;
    private ServiceConnection con = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.v("firstActivity","绑定成功");
            upLoadService.myBinder mybinder = (upLoadService.myBinder) iBinder;
            iService = mybinder.getService();
            iService.addHandler("user",mHandler1);
            Log.v("firstActivity","链接成功");
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };

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

    private Handler mHandler1 = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            Bundle b = msg.getData();
            String message = b.getString("message");
            myMessage get_message = new myMessage(message);
            String[] order = get_message.decodeMessage();
            if (order[0].equals("26"))     // 关于有好友更新号码的回应
            {
                mysqlite my = new mysqlite(mycontext);
                user[] u = null;
                int id = Integer.parseInt(the_class_information.getId());
                if (id!=-1) u = my.getAllUsersByClassId(id);
                userAdapter useradapter = new userAdapter(mycontext,u,myHandler);
                listview.setAdapter(useradapter);

            }
        }
    };

    @Override
    public void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        this.setContentView(R.layout.userlayout);
        Intent intent = new Intent(userActivity.this,upLoadService.class);
        bindService(intent,con,BIND_AUTO_CREATE);         // 绑定服务
        int id = getIntent().getIntExtra("item_id",-1);
        the_class_information = new class_information(id+"",getBaseContext());
        setTitle(the_class_information.getName());

        mysqlite my = new mysqlite(this);
        user[] u = null;
        if (id!=-1) u = my.getAllUsersByClassId(id);
        userAdapter useradapter = new userAdapter(this,u,myHandler);
        listview = (ListView) this.findViewById(R.id.user_listview);
        listview.setAdapter(useradapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(userActivity.this,ChatActivity.class);
                intent.putExtra("id",the_class_information.getId());
                startActivity(intent);
            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.usersettingmenu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int item_id = item.getItemId();
        if(item_id == R.id.the_class_information)
        {
            int id = Integer.parseInt(the_class_information.getId());
            Intent i = new Intent(userActivity.this,Main2Activity.class);
            Bundle b = new Bundle();
            b.putInt("id",id);
            i.putExtras(b);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onDestroy()
    {
        super.onDestroy();
        unbindService(con);
    }
}
