package client;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.young.mycircle.R;

/**
 * Created by Young on 2016/10/31.
 */
public class userAdapter extends BaseAdapter {

    private Context context;
    private user[] users;
    public userAdapter(Context c,user[] u)
    {
        context = c;
        users = u;
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
        return v;
    }
}
