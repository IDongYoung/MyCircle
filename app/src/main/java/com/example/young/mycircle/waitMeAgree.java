package com.example.young.mycircle;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import client.mysqlite;
import client.waitMeAgreeAdapter;
import client.waitagree;
import myuntil.myMessage;

public class waitMeAgree extends AppCompatActivity {

    private String id = "";
    private String password = "";
    private ListView listview = null;
    private Context context = this;
    private upLoadService iService;
    private ServiceConnection con = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.v("waitMeAgree","绑定成功");
            upLoadService.myBinder mybinder = (upLoadService.myBinder) iBinder;
            iService = mybinder.getService();
            iService.addHandler("waitMeAgree",mHandler);
            iService.updataWaitMe(id,password);
            Log.v("firstActivity","链接成功");
        }
        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };
    private Handler mHandler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            Bundle b = msg.getData();
            String message = b.getString("message");
            myMessage get_message = new myMessage(message);
            String[] order = get_message.decodeMessage();
            if (order[0].equals("24"))  // 加载所有申请加入的项
            {
                if (order[1].equals("1"))  // 成功
                {
                    mysqlite my = new mysqlite(context);
                    waitagree[] w = my.getAllWaitMe();
                    my.close();
                    waitMeAgreeAdapter wadapter = new waitMeAgreeAdapter(context,w,mHandler);
                    listview.setAdapter(wadapter);
                }
            }
            else if(order[0].equals("23"))
            {
                mysqlite my = new mysqlite(context);
                waitagree[] w = my.getAllWaitMe();
                my.close();
                waitMeAgreeAdapter wadapter = new waitMeAgreeAdapter(context,w,mHandler);
                listview.setAdapter(wadapter);
            }
            else if (order[0].equals("agree"))   // 同意加入
            {
                int user_id = Integer.parseInt(b.getString("user_id"));
                int class_id = Integer.parseInt(b.getString("class_id"));
                String an_name = b.getString("an_name");
                iService.agree_join(Integer.parseInt(id),password,class_id,user_id);
                mysqlite my = new mysqlite(context);
                my.delete_class_user_temp(user_id+"",class_id+"");
                my.addclass_user(user_id+"",class_id+"",an_name);
                waitagree[] w = my.getAllWaitMe();
                my.close();
                waitMeAgreeAdapter wadapter = new waitMeAgreeAdapter(context,w,mHandler);
                listview.setAdapter(wadapter);
                Toast.makeText(getApplicationContext(), "同意成功", Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wait_me_agree);
        Intent intent = new Intent(waitMeAgree.this,upLoadService.class);
        bindService(intent,con,BIND_AUTO_CREATE);         // 绑定服务
        SharedPreferences share = getSharedPreferences("information", Activity.MODE_PRIVATE);
        id = share.getInt("id",-1)+"";
        password = share.getString("password","");

        mysqlite my = new mysqlite(this);
        waitagree[] w = my.getAllWaitMe();
        my.close();
        waitMeAgreeAdapter wadapter = new waitMeAgreeAdapter(this,w,mHandler);
        listview = (ListView) this.findViewById(R.id.waitMeAgree_list_view);
        listview.setAdapter(wadapter);
    }
}
