package com.example.young.mycircle;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;
import client.client;
import myuntil.myMessage;
/**
 * Created by Young on 2016/10/29.
 */
public class firstActivity extends Activity implements View.OnClickListener,CompoundButton.OnCheckedChangeListener
{
    private String Result="";
    private int log_in_self = 0;
    private int remember_password = 0;
    private String password_value = "";
    private String email_value="";
    private String phone_number="";
    private Context context;
    private Handler mHandler = new Handler()
    {
        public void handleMessage(Message msg)
        {
            Bundle b = msg.getData();
            String message = b.getString("message");
            myMessage get_message = new myMessage(message);
            String[] order = get_message.decodeMessage();
            if (order[0].equals("2")) {
                if (order[1].equals("1"))   // 登陆成功
                {
                    if (remember_password == 1)  // 记住密码
                    {
                        Log.v("first",message);
                        SharedPreferences mySharedPreferences = getSharedPreferences("information", Activity.MODE_PRIVATE);
                        int pre_id = mySharedPreferences.getInt("id",-1);
                        SharedPreferences.Editor editor = mySharedPreferences.edit();
                        editor.putInt("id", Integer.parseInt(order[2]));
                        editor.putString("name", order[3]);
                        editor.putString("password", order[4]);
                        editor.putString("email", order[5]);
                        editor.putString("phone", order[6]);
                        editor.putString("adddress", order[7]);
                        editor.putInt("log_in_self", log_in_self);
                        editor.putInt("remember_password", remember_password);
                        if (pre_id==Integer.parseInt(order[2]))
                            editor.putInt("pre_next",1);
                        else
                            editor.putInt("pre_next",-1);
                        //editor.putString("IMSINumber",IMSINumber);
                        editor.commit(); //提交当前数据
                    }
                    Toast.makeText(getApplicationContext(), "登陆成功", Toast.LENGTH_SHORT).show();
                    Intent uploadservice = new Intent(firstActivity.this, upLoadService.class);
                    startService(uploadservice);
                    Intent intent = new Intent(firstActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else if (order[1].equals("-1"))  //登陆失败
                {
                    Toast.makeText(getApplicationContext(), "登陆失败", Toast.LENGTH_SHORT).show();
                } else if (order[1].equals("")) {

                }
            }

        }
    };

    @Override
    public void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        setContentView(R.layout.first_page);
        context = this;

        Button sign_in = (Button) this.findViewById(R.id.button_sign_in);
        Button log_in  = (Button) this.findViewById(R.id.button_log_in);
        CheckBox check_remember_password = (CheckBox) this.findViewById(R.id.remember_password);
        CheckBox check_log_in_self = (CheckBox) this.findViewById(R.id.log_in_self);

        sign_in.setOnClickListener(this);
        log_in.setOnClickListener(this);
        check_remember_password.setOnCheckedChangeListener(this);
        check_log_in_self.setOnCheckedChangeListener(this);

        SharedPreferences sharedpreferences = getSharedPreferences("information", Activity.MODE_PRIVATE);
        if (sharedpreferences.getInt("id",-1)!=-1)   // 非第一次登录
        {
            if (sharedpreferences.getInt("remember_password",-1)==1) // 记住了密码
            {
                if(sharedpreferences.getInt("log_in_self",-1)==1)        // 自动登录
                {
                    email_value = sharedpreferences.getString("email","");
                    password_value = sharedpreferences.getString("password","");
                    client c = new client(mHandler);
                    c.login(email_value,password_value);
                }
                else
                {
                    email_value = sharedpreferences.getString("email","");
                    password_value = sharedpreferences.getString("password","");
                    EditText user_name = (EditText) this.findViewById(R.id.edit_user_name);
                    EditText password = (EditText) this.findViewById(R.id.edit_password);
                    user_name.setText(email_value);
                    password.setText(password_value);
                    check_log_in_self.setChecked(false);
                    check_remember_password.setChecked(true);
                }
            }
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
            client c = new client(mHandler);
            c.login(email_value,password_value);
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
}
