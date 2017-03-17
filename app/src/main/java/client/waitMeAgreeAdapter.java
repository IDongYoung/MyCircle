package client;

import android.content.Context;
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
 * Created by Young on 2017/3/17.
 */
public class waitMeAgreeAdapter extends BaseAdapter
{
    waitagree[] data;
    Context context;
    Handler handler;
    public waitMeAgreeAdapter(Context c, waitagree[] w, Handler h)
    {
        this.data = w;
        context = c;
        handler = h;
    }
    @Override
    public int getCount() {
        if (data == null) return 0;
        else return data.length;
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
        View v = inflater.inflate(R.layout.waitmeagreeitem,null);
        TextView textview = (TextView) v.findViewById(R.id.wait_me_agree_information);
        textview.setText(data[i].user_name+"("+data[i].user_id+")"+"申请加入"+data[i].class_name+"("+data[i].class_id+")");
        Button button = (Button) v.findViewById(R.id.wait_me_agree_ok);
        button.setId(i);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                int i = view.getId();
                Bundle b = new Bundle();
                b.putString("message","agree");
                b.putString("user_id",data[i].user_id);
                b.putString("class_id",data[i].class_id);
                b.putString("an_name",data[i].user_name);
                Message m = new Message();
                m.setData(b);
                handler.sendMessage(m);
            }
        });
        return v;
    }
}
