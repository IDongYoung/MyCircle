package com.example.young.mycircle;

import android.app.Activity;
import client.client;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Young on 2016/10/28.
 */
public class signinActivity extends Activity implements View.OnClickListener
{

    private Handler mHandler = new Handler(){

        public void handleMessage(Message msg) {
            switch (msg.what)
            {
                case 1:Toast.makeText(getApplicationContext(), "注册成功", Toast.LENGTH_SHORT).show();break;
                case 2:Toast.makeText(getApplicationContext(), "该邮箱已被注册", Toast.LENGTH_SHORT).show();break;
                default:Toast.makeText(getApplicationContext(), "注册失败", Toast.LENGTH_SHORT).show();break;
            }
        };
    };

    @Override
    public void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        this.setContentView(R.layout.signinlayout);
        Button button = (Button) this.findViewById(R.id.button_ok);
        button.setOnClickListener(this);
    }
    public void onStart()
    {
        super.onStart();
    }

    @Override
    public void onClick(View view) {
        int view_id = view.getId();
        if (view_id == R.id.button_ok)    // 注册
        {
            final String name = ((EditText) this.findViewById(R.id.edit_name)).getText().toString();
            final String email = ((EditText) this.findViewById(R.id.edit_email)).getText().toString();
            final String address = ((EditText) this.findViewById(R.id.edit_address)).getText().toString();

            new Thread(new Runnable() {
                @Override
                public void run()
                {
                    client c = new client();
                    int r = c.regist(name,email,"15542339529",address,"2018");
                    Message msg = new Message();
                    msg.what = r;
                    mHandler.sendMessage(msg); // 向Handler发送消息,更新UI
                }
            }).start();

        }
    }
}
