package com.example.young.mycircle;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import java.util.HashMap;

import internet.Internet_Service;
import myuntil.myMessage;
import client.mysqlite;

/**
 * Created by Young on 2017/3/13.
 */
public class upLoadService extends Service
{
    private Context mycontext=this;
    private HashMap<String,Handler> AllHandler = new HashMap<String,Handler>();
    public Handler my = new Handler()
    {
        public void handleMessage(Message msg)
        {
            Bundle b = msg.getData();
            String message = b.getString("message");
            myMessage get_message = new myMessage(message);
            Log.v("upload",message);
            String[] order = get_message.decodeMessage();
            if (order[0].equals("1"))            // 注册的回应
            {
                Message m =new Message();
                m.setData(b);
                Handler h = AllHandler.get("signin");
                if (h!=null) h.sendMessage(m);
            }
            else if (order[0].equals("2"))       // 登陆的回应
            {
                Message m =new Message();
                m.setData(b);
                Handler h = AllHandler.get("first");
                if (h!=null) h.sendMessage(m);
            }
            else if (order[0].equals("3"))       // 创建班级的回应
            {
                Message m =new Message();
                m.setData(b);
                Handler h = AllHandler.get("createClass");
                if (h!=null) h.sendMessage(m);
                if (order[1].equals("1"))
                {
                    Toast.makeText(getApplicationContext(), "创建成功", Toast.LENGTH_SHORT).show();
                    mysqlite my = new mysqlite(mycontext);
                    my.addclass(order[2],order[3],order[4],order[5],order[6]);
                    my.addclass_user(order[3],order[2],order[7]);
                    my.close();
                }

            }
            else if (order[0].equals("4"))       // 搜索班级的回应
            {
                Message m =new Message();
                m.setData(b);
                Handler h = AllHandler.get("searchClass");
                if (h!=null) h.sendMessage(m);
            }
            else if (order[0].equals("5"))      // 申请加入班级
            {
                Message m =new Message();
                m.setData(b);
                Handler h = AllHandler.get("searchClass");
                if (h!=null) h.sendMessage(m);
            }
            else if (order[0].equals("2"))
            {

            }
            else if (order[0].equals("2"))
            {

            }
            else if (order[0].equals("2"))
            {

            }
            else if (order[0].equals("2"))
            {

            }
            else if (order[0].equals("2"))
            {

            }
            else if (order[0].equals("2"))
            {

            }
            else if (order[0].equals("2"))
            {

            }
            else if (order[0].equals("2"))
            {

            }
            else if (order[0].equals("2"))
            {

            }
            else if (order[0].equals("2"))
            {

            }
            else if (order[0].equals("2"))
            {

            }
            else if (order[0].equals("2"))
            {

            }
            else if (order[0].equals("2"))
            {

            }
            else if (order[0].equals("2"))
            {

            }
            else if (order[0].equals("26"))    // 遇到好友跟新号码
            {
                String the_id = order[1];
                String the_phone = order[2];
                mysqlite my = new mysqlite(mycontext);
                my.update_phone(the_id,the_phone);
                Message m =new Message();
                m.setData(b);
                Handler h = AllHandler.get("user"); //
                if (h!=null) h.sendMessage(m);
                Toast.makeText(getApplicationContext(), order[1]+"更新号码为："+order[2], Toast.LENGTH_SHORT).show();

            }
            else if (order[0].equals("25"))    // 被同意加入
            {
                Toast.makeText(getApplicationContext(), "被同意加入"+order[1], Toast.LENGTH_SHORT).show();
                uploaddata(true);
            }
            else if (order[0].equals("24"))    // 加载所有申请加入的项
            {
                if(order[1].equals("1"))  // 成功
                {
                    int k=2;
                    int num = Integer.parseInt(order[k++]);
                    mysqlite my = new mysqlite(mycontext);
                    for(int i=0;i<num;i++)
                    {
                        String temp_user_id = order[k++];
                        String temp_class_id = order[k++];
                        String temp_an_name = order[k++];
                        my.addclass_user_temp(temp_user_id,temp_class_id,temp_an_name);
                    }
                    num = Integer.parseInt(order[k++]);
                    for (int i=0;i<num;i++)
                    {
                        String id = order[k++];
                        String name = order[k++];
                        String email = order[k++];
                        String phone = order[k++];
                        String address  = order[k++];
                        String date  = order[k++];
                        my.adduser(id,name,email,phone,address,date);
                    }
                    my.close();
                    Message m =new Message();
                    m.setData(b);
                    Handler h = AllHandler.get("waitMeAgree");
                    if (h!=null) h.sendMessage(m);
                }
                else if (order[1].equals("0"))   // 无人申请
                {
                    Toast.makeText(getApplicationContext(), order[1]+"暂时无人申请", Toast.LENGTH_SHORT).show();
                }

            }
            else if (order[0].equals("23"))   // 有人申请加入我的班级
            {
                mysqlite my = new mysqlite(mycontext);
                my.addclass_user_temp(order[1],order[3],order[2]);
                my.close();
                Message m =new Message();
                m.setData(b);
                Handler h = AllHandler.get("waitMeAgree");
                if (h!=null) h.sendMessage(m);
                Toast.makeText(getApplicationContext(), order[1]+"申请加入", Toast.LENGTH_SHORT).show();
            }
            else if (order[0].equals("19"))  // 加载信息 回应
            {
                if (order[1].equals("1")) // 成功
                {
                    int i=2;
                    Log.v("upload","2");
                    mysqlite my = new mysqlite(mycontext);
                    if (mycontext==null) Log.v("upload","null");
                    else Log.v("upload","not null");
                    int num = Integer.parseInt(order[i++]);
                    for(int j=0;j<num;j++)
                    {
                        String id = order[i++];
                        String name = order[i++];
                        String email = order[i++];
                        String phone = order[i++];
                        String address = order[i++];
                        String date = order[i++];
                        my.adduser(id,name,email,phone,address,date);
                    }
                    num = Integer.parseInt(order[i++]);
                    for(int j=0;j<num;j++)
                    {
                        String id = order[i++];
                        String user_id = order[i++];
                        String name = order[i++];
                        String information = order[i++];
                        String date = order[i++];
                        my.addclass(id,user_id,name,information,date);
                    }
                    num = Integer.parseInt(order[i++]);
                    for(int j=0;j<num;j++)
                    {
                        String user_id = order[i++];
                        String class_id = order[i++];
                        String an_name = order[i++];
                        my.addclass_user(user_id,class_id,an_name);
                    }
                    my.close();
                    Toast.makeText(getApplicationContext(), "更新成功", Toast.LENGTH_SHORT).show();
                    Intent intent = new  Intent();  // 发送广播更新组件
                    intent.setAction("upload");
                    sendBroadcast(intent);
                }
                else                    // 失败
                {
                    Toast.makeText(getApplicationContext(), "更新失败", Toast.LENGTH_SHORT).show();
                }
                stopSelf();
            }

        }
    };

    private Internet_Service iService = new Internet_Service(my);
    public class myBinder extends Binder
    {
        public upLoadService getService()
        {
            return upLoadService.this;
        }
    }
    private myBinder mybinder = new myBinder();
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.v("uploadService","开始服务");
        return mybinder;
    }
    @Override
    public void onCreate()
    {
        super.onCreate();
        Log.v("uploadService","服务创建");
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        Log.v("uploadService","服务开始");
        return 1;
    }
    public void addHandler(String name,Handler h)
    {
        AllHandler.put(name,h);
    }
    public void showMyId()
    {
        SharedPreferences sharedpreferences = getSharedPreferences("information", Activity.MODE_PRIVATE);
        int id = sharedpreferences.getInt("id",-1);
        iService.send(21+"|"+id);
    }
    public void regist(String name,String email,String phone,String address,String date)
    {
        iService.send("1|"+name+"|"+email+"|"+phone+"|"+address+"|"+date);
    }
    public void login(String email,String password)
    {
        iService.send("2|"+email+"|"+password);
    }
    // 创建班级
    public int create_class(String user_id,String password,String class_name,String information,String date)
    {
        iService.send("3|"+user_id+"|"+password+"|"+class_name+"|"+information+"|"+date);
        return 1;
    }
    //搜索班级    参数(user_id password class_number) 返回值:-1（不存在）("name|the_value|creater_name|the_value"(名称),(创建者名字));
    public void serch_class(int user_id,String password,int class_number)
    {
        iService.send(4+"|"+user_id+"|"+password+"|"+class_number);
    }
    //加入班级    参数(user_id password class_number) 返回值:1(申请成功) -1(申请失败)
    public void join_class(int user_id,String password,int class_number)
    {
        iService.send(5+"|"+user_id+"|"+password+"|"+class_number);
    }
    //退出班级    参数(user_id password class_number) 返回值:1(退出成功) -1(退出失败)
    public int exit_class(int user_id,String password,int class_number)
    {

        return 1;
    }
    //同意加入    参数(user_id password class_number  an_user_id) 返回值:1(同意成功) -1(同意失败)
    public void agree_join(int user_id,String password,int class_number,int an_user_id)
    {
        iService.send(7+"|"+user_id+"|"+password+"|"+class_number+"|"+an_user_id);
    }
    //获得班级列表    参数(user_id password) 返回值:1(获取成功) -1(获取失败) "num"(班级数量) "name0"(第一个班级名) "id0"(第一个班级id)...
    public String get_class_list(int user_id,String password)
    {

        return "";
    }
    //获得成员列表    参数(user_id password class_number) 返回值:1(获取成功) -1(获取失败) "num"(成员数量) "name0"(第一个成员名) "phone0"(第一个成员手机号码) "address0"(第一个成员地理位置) "date0"(第一个成员信息更新时间)...
    public String get_persons_list(int user_id,String password,int class_number)
    {

        return "";
    }
    //创建人权限移交    参数(user_id password class_number an_user_id) 返回值:1(移交成功) -1(移交失败)
    public int founder_hand_over(int user_id,String password,int class_number,int an_user_id)
    {

        return 1;
    }
    //创建人解散班群    参数(user_id password class_number) 返回值:1(解散成功) -1(解散失败)
    public int destory_class(int user_id,String password,int class_number)
    {

        return 1;
    }
    //添加群名片    参数(user_id password class_number an_name) 返回值:1(添加成功) -1(添加失败)
    public int add_new_name(int user_id,String password,int class_number,String an_name)
    {

        return 1;
    }
    //获得成员信息    参数(user_id password an_user_id) 返回值:1(获取成功) -1(获取失败) "name"(成员名) "phone"(成员手机号码) "address"(成员地理位置)
    public int get_person_information(int user_id,String password,int an_user_id)
    {

        return 1;
    }
    //修改密码    参数(user_id password new_password) 返回值:1(修改成功) -1(修改失败)
    public int update_password(int user_id,String password,String new_password)
    {

        return 1;
    }
    //踢掉某人    参数(user_id password class_number an_user_id) 返回值:1(踢出成功) -1(踢出失败)
    public int delte_person(int user_id,String password,int class_number,int an_user_id)
    {

        return 1;
    }
    //获得班级信息    参数(user_id password class_number) 返回值:1(获取成功) -1(获取失败)
    public int get_class_information(int user_id,String password,int class_number)
    {

        return 1;
    }
    //修改个人信息    参数(user_id password name) 返回值:1(修改成功) -1(修改失败)
    public int update_person_information(int user_id,String password,String name)
    {

        return 1;
    }
    //修改班级信息    参数(user_id password class_number name information) 返回值:1(修改成功) -1(修改失败)
    public int update_class_information(int user_id,String password,int class_number,String name,String information)
    {

        return 1;
    }
    public void uploaddata(boolean flag)
    {
        SharedPreferences sharedpreferences = getSharedPreferences("information", Activity.MODE_PRIVATE);
        if (flag||sharedpreferences.getInt("pre_next",-1)==-1) // 本地数据库 与 账号 不一致
        {
            mysqlite mysql = new mysqlite(mycontext);
            mysql.cleanall();
            mysql.close();

            int id = sharedpreferences.getInt("id", -1);
            String password = sharedpreferences.getString("password", "");
            iService.send("19|"+id+"|"+password);
        }
    }
    public void updataPhone(String id,String password,String phone)
    {
        iService.send("20|"+id+"|"+password+"|"+phone);
    }
    public void updataWaitMe(String id,String password)
    {
        iService.send("24|"+id+"|"+password);
    }


    @Override
    public void onDestroy()
    {
        super.onDestroy();
    }
}
