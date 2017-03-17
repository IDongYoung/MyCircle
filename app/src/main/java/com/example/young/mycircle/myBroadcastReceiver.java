package com.example.young.mycircle;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Young on 2017/3/14.
 */
public class myBroadcastReceiver extends BroadcastReceiver
{
    @Override
    public void onReceive(Context context, Intent intent)
    {
        String action = intent.getAction();
        if (action.equals("upload_classes")) // 更新班级
        {

        }
        else if (action.equals("upload_users")) // 更新成员
        {

        }
    }
}
