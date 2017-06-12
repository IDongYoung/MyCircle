package com.example.young.mycircle;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import client.chat_message;
import client.class_information;
import client.msg;
import client.my_information;
import myuntil.myMessage;

public class ChatActivity extends AppCompatActivity {

    class_information the_class_information=null;
    EditText edit = null;
    Button button = null;
    LinearLayout l = null;
    ScrollView s = null;
    Context context = this;
    String id = "";

    private upLoadService iService;
    private ServiceConnection con = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.v("chatActivity", "绑定成功");
            upLoadService.myBinder mybinder = (upLoadService.myBinder) iBinder;
            iService = mybinder.getService();
            iService.addHandler("chat", mHandler);
            Log.v("chatActivity", "链接成功");
        }
        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            Bundle b = msg.getData();
            String message = b.getString("message");
            myMessage get_message = new myMessage(message);
            String[] order = get_message.decodeMessage();
            if (order[0].equals("28"))     // 关于chat信息的回应
            {
                msg t = new msg(order[1],order[3],context);
                my_information me = new my_information(context);
                if(t.user_id.equals(me.getID()))
                {
                    say_in_left(t.user_name,t.message,false);
                }
                else
                {
                    say_in_left(t.user_name,t.message,true);
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(ChatActivity.this,upLoadService.class);
        bindService(intent,con,BIND_AUTO_CREATE);         // 绑定服务

        setContentView(R.layout.activity_chat);
        id = getIntent().getStringExtra("id");
        the_class_information = new class_information(id+"",getBaseContext());
        setTitle(the_class_information.getName());
        l = (LinearLayout) this.findViewById(R.id.message_list);
        s = (ScrollView) this.findViewById(R.id.message_group);
        edit = (EditText) this.findViewById(R.id.editText);
        button = (Button) this.findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                my_information me = new my_information(context);
                String u_id = me.getID();
                String strs = edit.getText().toString();
                iService.chat(u_id,me.getPassword(),id,strs);
                edit.setText("");
            }
        });

        chat_message m = new chat_message(this,id);
        my_information me = new my_information(context);
        for (int i=0;i<m.message.size();i++)
        {
            msg t = (msg)m.message.get(i);
            if(t.user_id.equals(me.getID()))
            {
                say_in_left(t.user_name,t.message,false);
            }
            else
            {
                say_in_left(t.user_name,t.message,true);
            }
        }
    }
    public void say_in_left(String name_val,String mes_val,boolean isLeft)
    {
        LayoutInflater inflater = LayoutInflater.from(context);
        View myv = null;
        if (isLeft) myv = inflater.inflate(R.layout.messageleft,null);
        else myv = inflater.inflate(R.layout.message,null);
        TextView name =(TextView) myv.findViewById(R.id.my_name);
        TextView mess =(TextView) myv.findViewById(R.id.my_message);
        name.setText(name_val);
        mess.setText(mes_val);
        l.addView(myv);

        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                s.fullScroll(ScrollView.FOCUS_DOWN); //滚动到底部
            }
        });
    }
    @Override
    public void onDestroy()
    {
        super.onDestroy();
        unbindService(con);         // 解除绑定服务
    }
}
