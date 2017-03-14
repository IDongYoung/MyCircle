package client;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.young.mycircle.R;

/**
 * Created by Young on 2016/10/31.
 */
public class userAdapter extends BaseAdapter{

    private Context context;
    private user[] users;
    private Handler myHandler;
    public userAdapter(Context c, user[] u, Handler my)
    {
        context = c;
        users = u;
        myHandler = my;
    }
    @Override
    public int getCount() {
        if (users == null) return 0;
        else return users.length;

    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.useritem,null);
        TextView textview = (TextView) v.findViewById(R.id.user_name);
        textview.setText(users[i].name);
        Button phone = (Button) v.findViewById(R.id.phone);
        phone.setId(i);
        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                int id = view.getId();
                Bundle b = new Bundle();
                b.putString("phone_number",users[id].phone);
                Message m = new Message();
                m.what=0;
                m.setData(b);
                myHandler.sendMessage(m);
            }
        });
        return v;
    }
}
