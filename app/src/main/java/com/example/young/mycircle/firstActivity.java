package com.example.young.mycircle;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import myuntil.myMessage;
import client.PhoneInfo;
/**
 * Created by Young on 2016/10/29.
 */
public class firstActivity extends Activity implements View.OnClickListener,CompoundButton.OnCheckedChangeListener
{
    private int log_in_self = 0;
    private int remember_password = 0;
    private String password_value = "";
    private String email_value="";
    private Context context = this;
    private boolean login=false;
    private upLoadService iService;
    private ServiceConnection con = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.v("firstActivity","绑定成功");
            upLoadService.myBinder mybinder = (upLoadService.myBinder) iBinder;
            iService = mybinder.getService();
            iService.addHandler("first",mHandler);
            if (login) iService.login(email_value,password_value);
            login = false;
            Log.v("firstActivity","链接成功");
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };

    private Handler mHandler = new Handler()
    {
        public void handleMessage(Message msg)
        {
            Bundle b = msg.getData();
            String message = b.getString("message");
            myMessage get_message = new myMessage(message);
            String[] order = get_message.decodeMessage();
            if (order[0].equals("2"))     // 关于登陆的回应
            {
                if (order[1].equals("1"))   // 登陆成功
                {
                    SharedPreferences mySharedPreferences = getSharedPreferences("information", Activity.MODE_PRIVATE);
                    int pre_id = mySharedPreferences.getInt("id",-1);
                    String pre_phone = mySharedPreferences.getString("phone","");
                    final String id = order[2];
                    final String ps = order[4];
                    if (remember_password == 1)  // 记住密码
                    {
                        Log.v("first",message);
                        SharedPreferences.Editor editor = mySharedPreferences.edit();
                        editor.putInt("id", Integer.parseInt(order[2]));
                        editor.putString("name", order[3]);
                        editor.putString("password", order[4]);
                        editor.putString("email", order[5]);
                        editor.putString("phone", order[6]);
                        editor.putString("address", order[7]);
                        editor.putInt("log_in_self", log_in_self);
                        editor.putInt("remember_password", remember_password);
                        if (pre_id==Integer.parseInt(order[2]))
                            editor.putInt("pre_next",1);
                        else
                            editor.putInt("pre_next",-1);
                        editor.commit(); //提交当前数据
                    }
                    Toast.makeText(getApplicationContext(), "登陆成功", Toast.LENGTH_SHORT).show();
                    iService.showMyId();
                    PhoneInfo myphone = new PhoneInfo(context);
                    String IMSI = myphone.getIMSINumber();
                    String pre_IMSI = mySharedPreferences.getString("IMSI","");
                    if(!pre_IMSI.equals(IMSI))
                    {
                        SharedPreferences.Editor editor = mySharedPreferences.edit();
                        editor.putString("IMSI",IMSI);
                        editor.commit();
                    }
                    if (!pre_IMSI.equals(IMSI)||!pre_phone.equals(order[6]))
                    {
                        String phone_number = myphone.getNativePhoneNumber();
                        if (phone_number.equals("no_phone_number"))
                        {
                            final EditText editText = new EditText(firstActivity.this);
                            AlertDialog.Builder inputDialog =
                                    new AlertDialog.Builder(firstActivity.this);
                            inputDialog.setTitle("检测到您更换手机号码，请输入：").setView(editText);
                            inputDialog.setPositiveButton("确定",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which)
                                        {
                                            String my_phone = editText.getText().toString();
                                            iService.updataPhone(id,ps,my_phone);
                                            Intent intent = new Intent(firstActivity.this, MainActivity.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                    }).show();
                        }
                        else                     // 可自动获取号码
                        {
                            iService.updataPhone(id,ps,phone_number);
                            Intent intent = new Intent(firstActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        iService.uploaddata(false);
                    }
                    else            // 没有更换号码
                    {
                        iService.uploaddata(false);
                        Intent intent = new Intent(firstActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
                else if (order[1].equals("-1"))  //登陆失败
                {
                    Toast.makeText(getApplicationContext(), "登陆失败", Toast.LENGTH_SHORT).show();
                }
            }
            else if (order[0].equals("20"))
            {
                if(order[1].equals("1"))
                {
                    Toast.makeText(getApplicationContext(), "号码更新成功", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "号码更新失败", Toast.LENGTH_SHORT).show();
                }
            }
        }
    };

    @Override
    public void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        Intent intent = new Intent(firstActivity.this,upLoadService.class);
        bindService(intent,con,BIND_AUTO_CREATE);         // 绑定服务

        SharedPreferences sharedpreferences = getSharedPreferences("information", Activity.MODE_PRIVATE);
        if (sharedpreferences.getInt("id",-1)!=-1                         // 非第一次登录
                &&sharedpreferences.getInt("remember_password",-1)==1     // 记住了密码
                &&sharedpreferences.getInt("log_in_self",-1)==1)          // 自动登录
        {

            email_value = sharedpreferences.getString("email", "");
            password_value = sharedpreferences.getString("password", "");
            if (iService == null) login = true;
            else iService.login(email_value,password_value);
        }
        else
        {
            setContentView(R.layout.first_page);
            Button sign_in = (Button) this.findViewById(R.id.button_sign_in);
            Button log_in = (Button) this.findViewById(R.id.button_log_in);
            CheckBox check_remember_password = (CheckBox) this.findViewById(R.id.remember_password);
            CheckBox check_log_in_self = (CheckBox) this.findViewById(R.id.log_in_self);

            sign_in.setOnClickListener(this);
            log_in.setOnClickListener(this);
            check_remember_password.setOnCheckedChangeListener(this);
            check_log_in_self.setOnCheckedChangeListener(this);

            email_value = sharedpreferences.getString("email","");
            password_value = sharedpreferences.getString("password","");
            int rem = sharedpreferences.getInt("remember_password",-1);
            EditText user_name = (EditText) this.findViewById(R.id.edit_user_name);
            EditText password = (EditText) this.findViewById(R.id.edit_password);
            user_name.setText(email_value);
            password.setText(password_value);
            check_log_in_self.setChecked(false);
            check_remember_password.setChecked(rem==-1?false:true);
        }
    }

    @Override
    public void onClick(View view)
    {
        int view_id = view.getId();
        if (view_id == R.id.button_sign_in)   // 注册
        {
            Intent intent = new Intent(firstActivity.this,signinActivity.class);
            this.startActivity(intent);
        }
        else if (view_id == R.id.button_log_in)  // 登陆
        {
            EditText user_name = (EditText) this.findViewById(R.id.edit_user_name);
            EditText password = (EditText) this.findViewById(R.id.edit_password);
            password_value = password.getText().toString();
            email_value = user_name.getText().toString();
            if (iService == null) login = true;
            else iService.login(email_value,password_value);
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b)
    {
        int view_id = compoundButton.getId();
        if (view_id == R.id.remember_password)
        {
            if (b)
            {
                remember_password = 1; // 选中
            }
            else
            {
                remember_password = 0;
                CheckBox check_log_in_self = (CheckBox) this.findViewById(R.id.log_in_self);
                check_log_in_self.setChecked(false);
            }
        }
        else if (view_id == R.id.log_in_self)
        {
            if (b)
            {
                log_in_self = 1; // 选中
                CheckBox check_remember_password = (CheckBox) this.findViewById(R.id.remember_password);
                check_remember_password.setChecked(true);
            }
            else
            {
                log_in_self = 0;
            }
        }
    }
    @Override
    public void onDestroy()
    {
        super.onDestroy();
        unbindService(con);         // 解除绑定服务
    }
}
