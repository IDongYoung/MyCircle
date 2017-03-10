package com.example.young.mycircle;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;
import client.datil_String;
import client.my_information;
import client.phone_information;

import java.util.Map;

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
            if (order[0].equals("2"))
            {
                if(order[1].equals("1"))   // 登陆成功
                {
                    if(remember_password==1)  // 记住密码
                    {

                    }
                    Toast.makeText(getApplicationContext(), "登陆成功", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(firstActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                else if (order[1].equals("-1"))  //登陆失败
                {
                    Toast.makeText(getApplicationContext(), "登陆失败", Toast.LENGTH_SHORT).show();
                }
                else if (order[1].equals(""))
                {

                }
            }

            /*datil_String d = new datil_String();
            if (Result==null||Result.equals("")||Result.equals("-1"))
            {
                Toast.makeText(getApplicationContext(), "登陆失败", Toast.LENGTH_SHORT).show();
            }
            else if (msg.what == 1)
            {
                phone_information p = new phone_information(context);
                Map M = d.getvalue(Result);
                String my_id = M.get("my_id").toString();
                String my_name = M.get("my_name").toString();
                String address = p.getAddress();
                String IMSINumber = p.getIMSINumber();

                my_information.set_id(Integer.parseInt(my_id));
                my_information.set_name(my_name);
                my_information.set_password(password_value);
                my_information.set_email(email_value);
                my_information.set_is_log_in(log_in_self);
                my_information.set_is_remember_password(remember_password);
                my_information.set_phone(phone_number);
                my_information.set_address(address);

                if (remember_password == 0) password_value="";
                SharedPreferences mySharedPreferences= getSharedPreferences("test", Activity.MODE_PRIVATE);
                //实例化SharedPreferences.Editor对象（第二步）
                SharedPreferences.Editor editor = mySharedPreferences.edit();
                //用putString的方法保存数据
                editor.putInt("id",Integer.parseInt(my_id));
                editor.putString("name", my_name);
                editor.putString("password", password_value);
                editor.putString("email", email_value);
                editor.putString("phone", phone_number);
                editor.putString("adddress", address);
                editor.putInt("log_in_self",log_in_self);
                editor.putInt("remember_password",remember_password);
                editor.putString("IMSINumber",IMSINumber);
                editor.commit(); //提交当前数据

                Toast.makeText(getApplicationContext(), "登陆成功"+phone_number, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(firstActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
            else if(msg.what == 0)
            {
                phone_information p = new phone_information(context);
                String temp = p.getPhoneNumber();
                if (temp.equals(p.phonenumber))
                {
                    LayoutInflater inflater = LayoutInflater.from(context);
                    final View v = inflater.inflate(R.layout.mydiag,null);
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("警告").setIcon(android.R.drawable.ic_dialog_info)
                            .setView(v)
                            .setNegativeButton("Cancel", null);
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which)
                        {
                            String value = ((EditText) v.findViewById(R.id.hello)).getText().toString();
                            phone_number = value;
                            Message msg = new Message();
                            msg.what = 1;
                            mHandler.sendMessage(msg);
                        }
                    });
                    builder.show();
                }
                else
                {
                    phone_number = temp;
                    Message m = new Message();
                    m.what = 1;
                    mHandler.sendMessage(m);
                }
            }*/
        };
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
    public void onClick(View view) {
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
            if (b) remember_password = 1; // 选中
            else remember_password = 0;
        }
        else if (view_id == R.id.log_in_self)
        {
            if (b) log_in_self = 1; // 选中
            else log_in_self = 0;
        }
    }
}
