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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

import client.mysqlite;
import myuntil.myMessage;

public class createClassActivity extends AppCompatActivity implements View.OnClickListener{

    private Context context = this;
    private String id="";
    private String psd="";
    private String name_value="";
    private String information_value="";
    private String str="";
    private String an_name="";

    private upLoadService iService;
    private ServiceConnection con = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            upLoadService.myBinder mybinder = (upLoadService.myBinder) iBinder;
            iService = mybinder.getService();
            iService.addHandler("createClass",myHandle);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };

    private Handler myHandle = new Handler()
    {
        @Override
        public void handleMessage(Message message)
        {
            Bundle b = message.getData();
            String m = b.getString("message");
            myMessage get_message = new myMessage(m);
            String[] order = get_message.decodeMessage();
            if (order[0].equals("3"))  // 创建班级的回复
            {
                if (order[1].equals("1"))  // 创建成功
                {
                    Intent intent = new  Intent();  // 发送广播更新组件
                    intent.setAction("upload");
                    sendBroadcast(intent);
                }
                else                       // 创建失败
                {
                    Toast.makeText(getApplicationContext(), "创建失败", Toast.LENGTH_SHORT).show();
                }
                finish();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_class);

        Intent intent = new Intent(createClassActivity.this,upLoadService.class);
        bindService(intent,con,BIND_AUTO_CREATE);         // 绑定服务

        Button ok = (Button) this.findViewById(R.id.create_class_ok);
        ok.setOnClickListener(this);
    }

    @Override
    public void onClick(View view)
    {
        EditText name = (EditText) this.findViewById(R.id.create_class_name);
        EditText information = (EditText) this.findViewById(R.id.create_class_information);
        name_value = name.getText().toString();
        information_value = information.getText().toString();
        if (name_value == null || name_value.equals("") || information == null || information_value.equals(""))
        {
            Toast.makeText(getApplicationContext(), "信息不可空", Toast.LENGTH_SHORT).show();
        }
        else
        {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
            Date curDate = new Date(System.currentTimeMillis());//获取当前时间
            str = formatter.format(curDate);
            SharedPreferences mySharedPreferences = getSharedPreferences("information", Activity.MODE_PRIVATE);
            id = mySharedPreferences.getInt("id",-1)+"";
            an_name = mySharedPreferences.getString("name","")+"";
            psd = mySharedPreferences.getString("password","");
            iService.create_class(id,psd,name_value,information_value,str);
        }
    }
    @Override
    public void onDestroy()
    {
        super.onDestroy();
        unbindService(con);
    }
}
