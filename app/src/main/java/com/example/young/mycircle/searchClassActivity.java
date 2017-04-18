package com.example.young.mycircle;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import myuntil.myMessage;

public class searchClassActivity extends AppCompatActivity {

    TextView c_n;
    TextView c_n_v;
    TextView u_n;
    TextView u_n_v;
    TextView n_c;
    TextView data;
    Button b_join;

    private upLoadService iService;
    private ServiceConnection con = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.v("searchClass","绑定成功");
            upLoadService.myBinder mybinder = (upLoadService.myBinder) iBinder;
            iService = mybinder.getService();
            iService.addHandler("searchClass",h);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };

    private Handler h = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            Bundle b = msg.getData();
            String message = b.getString("message");
            myMessage get_message = new myMessage(message);
            String[] order = get_message.decodeMessage();
            if (order[0].equals("4"))   // 搜索班级
            {
                if (order[1].equals("1"))  // 存在
                {
                    String class_name = order[2];
                    String user_name = order[3];

                    c_n.setVisibility(View.VISIBLE);
                    c_n_v.setVisibility(View.VISIBLE);
                    u_n.setVisibility(View.VISIBLE);
                    u_n_v.setVisibility(View.VISIBLE);
                    b_join.setVisibility(View.VISIBLE);
                    c_n_v.setText(class_name);
                    u_n_v.setText(user_name);
                }
                else   // 不存在
                {
                    n_c.setVisibility(View.VISIBLE);
                }

            }
            else if (order[0].equals("5"))
            {
                if (order[1].equals("1"))    // 成功
                {
                    String str = "申请成功";
                    Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
                }
                else if (order[1].equals("2"))  // 已在该班级
                {
                    String str = "您已在该班级";
                    Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
                }
                else if (order[1].equals("3"))    // 已申请
                {
                    String str = "您已经申请";
                    Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
                }
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_class);
        Intent intent = new Intent(searchClassActivity.this,upLoadService.class);
        bindService(intent,con,BIND_AUTO_CREATE);         // 绑定服务


        Button b = (Button) this.findViewById(R.id.search_class_ok);
        final EditText e = (EditText) this.findViewById(R.id.edit_class_id);
        c_n = (TextView) this.findViewById(R.id.search_class_name);
        c_n_v = (TextView) this.findViewById(R.id.search_class_name_val);
        u_n = (TextView) this.findViewById(R.id.search_user_name);
        u_n_v = (TextView) this.findViewById(R.id.search_user_name_val);
        n_c = (TextView) this.findViewById(R.id.no_this_class);
        data = (TextView) this.findViewById(R.id.class_number);
        b_join = (Button) this.findViewById(R.id.search_class_join_ok);
        b.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                c_n.setVisibility(View.INVISIBLE);
                c_n_v.setVisibility(View.INVISIBLE);
                u_n.setVisibility(View.INVISIBLE);
                u_n_v.setVisibility(View.INVISIBLE);
                n_c.setVisibility(View.INVISIBLE);
                b_join.setVisibility(View.INVISIBLE);

                SharedPreferences s = getSharedPreferences("information", Activity.MODE_PRIVATE);
                int id = s.getInt("id",-1);
                String password = s.getString("password","");
                String c_id = e.getText().toString();
                try
                {
                    int cid = Integer.parseInt(c_id);
                    data.setText(cid+"");
                    iService.serch_class(id,password,cid);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "请检查输入内容", Toast.LENGTH_SHORT).show();
                }

            }
        });
        b_join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                SharedPreferences mySharedPreferences = getSharedPreferences("information", Activity.MODE_PRIVATE);
                int id = mySharedPreferences.getInt("id",-1);
                String password = mySharedPreferences.getString("password","");
                try
                {
                    int cid = Integer.parseInt(data.getText().toString());
                    iService.join_class(id,password,cid);
                }catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });
    }
    @Override
    public void onDestroy()
    {
        super.onDestroy();
        unbindService(con);
    }
}
