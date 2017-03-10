package client;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.young.mycircle.R;

import java.util.zip.Inflater;

/**
 * Created by Young on 2016/10/29.
 */
public class myAdapter extends BaseAdapter {

    myclass[] date;
    Context context;
    public myAdapter(Context c,myclass[]avg)
    {
        date = avg;
        context = c;
    }
    @Override
    public int getCount() {
        if (date == null) return 0;
        else return date.length;
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
        View v = inflater.inflate(R.layout.listitemlayout,null);
        TextView textview = (TextView) v.findViewById(R.id.text);
        textview.setText(date[i].name);
        return v;
    }
}
