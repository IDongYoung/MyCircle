package com.example.young.mycircle;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

import myuntil.myMessage;

/**
 * Created by Young on 2016/10/28.
 */
public class signinActivity extends Activity implements View.OnClickListener
{
    private boolean login = false;
    String name = "";
    String email = "";
    String address = "";
    String date = "";
    String phone = "wu";
    private upLoadService iService = null;
    private ServiceConnection con = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.v("firstActivity","绑定成功");
            upLoadService.myBinder mybinder = (upLoadService.myBinder) iBinder;
            iService = mybinder.getService();
            iService.addHandler("signin",mHandler);
            if (login) iService.regist(name,email,phone,address,date);
            login = false;
            Log.v("firstActivity","链接成功");
        }
        @Override
        public void onServiceDisconnected(ComponentName componentName)
        {

        }
    };

    private Handler mHandler = new Handler(){

        public void handleMessage(Message msg)
        {
            Bundle b = msg.getData();
            String message = b.getString("message");
            myMessage get_message = new myMessage(message);
            String[] order = get_message.decodeMessage();
            if (order[0].equals("1"))    // 对注册的回应
            {
                if (order[1].equals("1"))
                {
                    Toast.makeText(getApplicationContext(), "注册成功,密码已发至邮箱", Toast.LENGTH_SHORT).show();
                }
                else if (order[1].equals("2"))
                {
                    Toast.makeText(getApplicationContext(), "该邮箱已被注册", Toast.LENGTH_SHORT).show();
                }
                else if (order[1].equals("3"))
                {
                    Toast.makeText(getApplicationContext(), "注册失败", Toast.LENGTH_SHORT).show();
                }
                finish();
            }
        }
    };

    @Override
    public void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        this.setContentView(R.layout.signinlayout);
        Intent intent = new Intent(signinActivity.this,upLoadService.class);
        bindService(intent,con,BIND_AUTO_CREATE);         // 绑定服务
        Button button = (Button) this.findViewById(R.id.button_ok);
        button.setOnClickListener(this);
    }
    public void onStart()
    {
        super.onStart();
    }
    @Override
    public void onDestroy()
    {
        super.onDestroy();
        unbindService(con);
    }

    @Override
    public void onClick(View view)
    {
        int view_id = view.getId();
        if (view_id == R.id.button_ok)    // 注册
        {
            name = ((EditText) this.findViewById(R.id.edit_name)).getText().toString();
            email = ((EditText) this.findViewById(R.id.edit_email)).getText().toString();
            address = ((EditText) this.findViewById(R.id.edit_address)).getText().toString();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");  //设置日期格式
            date = df.format(new Date());            // new Date()为获取当前系统时间
            if (name==null||email==null||address==null||name.equals("")||email.equals("")||address.equals(""))
            {
                Toast.makeText(getApplicationContext(), "请将信息填写完整", Toast.LENGTH_SHORT).show();
            }
            else
            {
                if (iService == null)
                {
                    login = true;
                }
                else
                {
                    iService.regist(name,email,phone,address,date);
                }
            }
        }
    }
}
