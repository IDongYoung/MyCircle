package com.example.young.mycircle;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import client.class_information;

public class ChatActivity extends AppCompatActivity {

    class_information the_class_information=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        String id = getIntent().getStringExtra("id");
        the_class_information = new class_information(id+"",getBaseContext());
        setTitle(the_class_information.getName());
        LinearLayout l = (LinearLayout) this.findViewById(R.id.chat_message);
        TextView t = new TextView(this);
        t.setText("helllo");
        l.addView(t);

    }
}
