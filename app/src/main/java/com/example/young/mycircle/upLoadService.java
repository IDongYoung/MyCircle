package com.example.young.mycircle;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import client.client;
import myuntil.myMessage;
import client.mysqlite;

/**
 * Created by Young on 2017/3/13.
 */
public class upLoadService extends Service
{
    private Context mycontext=this;
    public Handler my = new Handler()
    {
        public void handleMessage(Message msg)
        {
            Bundle b = msg.getData();
            String message = b.getString("message");
            myMessage get_message = new myMessage(message);
            Log.v("upload",message);
            String[] order = get_message.decodeMessage();
            if (order[0].equals("19"))  // 加载信息 结果
            {
                if (order[1].equals("1")) // 成功
                {
                    int i=2;
                    Log.v("upload","2");
                    mysqlite my = new mysqlite(mycontext);
                    if (mycontext==null) Log.v("upload","null");
                    else Log.v("upload","not null");
                    int num = Integer.parseInt(order[i++]);
                    for(int j=0;j<num;j++)
                    {
                        String id = order[i++];
                        String name = order[i++];
                        String email = order[i++];
                        String phone = order[i++];
                        String address = order[i++];
                        String date = order[i++];
                        my.adduser(id,name,email,phone,address,date);
                    }
                    num = Integer.parseInt(order[i++]);
                    for(int j=0;j<num;j++)
                    {
                        String id = order[i++];
                        String user_id = order[i++];
                        String name = order[i++];
                        String information = order[i++];
                        String date = order[i++];
                        my.addclass(id,user_id,name,information,date);
                    }
                    num = Integer.parseInt(order[i++]);
                    for(int j=0;j<num;j++)
                    {
                        String user_id = order[i++];
                        String class_id = order[i++];
                        String an_name = order[i++];
                        my.addclass_user(user_id,class_id,an_name);
                    }
                    my.close();
                    Toast.makeText(getApplicationContext(), "更新成功", Toast.LENGTH_SHORT).show();
                    Intent intent = new  Intent();  // 发送广播更新组件
                    intent.setAction("upload");
                    sendBroadcast(intent);
                }
                else                    // 失败
                {
                    Toast.makeText(getApplicationContext(), "更新失败", Toast.LENGTH_SHORT).show();
                }
                stopSelf();
            }

        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public void onCreate()
    {
        super.onCreate();
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        SharedPreferences sharedpreferences = getSharedPreferences("information", Activity.MODE_PRIVATE);
        if (sharedpreferences.getInt("pre_next",-1)==-1) // 本地数据库 与 账号 不一致
        {
            mysqlite mysql = new mysqlite(mycontext);
            mysql.cleanall();
            mysql.close();

            int id = sharedpreferences.getInt("id", -1);
            String password = sharedpreferences.getString("password", "");
            client c = new client(my);
            c.uploaddata(id + "", password);
        }
        return 1;
    }
    @Override
    public void onDestroy()
    {
        super.onDestroy();
    }
}
