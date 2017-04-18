package com.example.young.mycircle;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import client.class_information;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Intent intent = this.getIntent();
        Bundle b = intent.getExtras();
        int id = b.getInt("id");
        if (id!=-1)
        {
            class_information c = new class_information(id+"",getBaseContext());
            TextView c_id = (TextView) this.findViewById(R.id.class_id_value);
            TextView c_na = (TextView) this.findViewById(R.id.class_name_value);
            TextView c_us = (TextView) this.findViewById(R.id.class_user_name_value);
            TextView c_in = (TextView) this.findViewById(R.id.class_information_value);
            TextView c_da = (TextView) this.findViewById(R.id.class_date_value);
            c_id.setText(c.getId());
            c_na.setText(c.getName());
            c_us.setText(c.getUserName());
            c_in.setText(c.getInformation());
            c_da.setText(c.getDate());
            setTitle(c.getName());
        }
    }
}
