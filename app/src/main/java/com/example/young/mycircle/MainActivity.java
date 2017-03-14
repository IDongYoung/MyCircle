package com.example.young.mycircle;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;

import client.myAdapter;
import client.myclass;
import client.mysqlite;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener ,
        AdapterView.OnItemClickListener
{

    private myclass[] test=null;
    private ListView listview=null;
    private Context myconnext=this;
    MyReceiver receiver=null;
    public class MyReceiver extends BroadcastReceiver//作为内部类的广播接收者
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            if (intent.getAction().equals("upload"))
            {
                Log.i("MainActivity","成功收到广播");
                mysqlite mysql = new mysqlite(myconnext);
                SharedPreferences sharedpreferences = getSharedPreferences("information", Activity.MODE_PRIVATE);
                int id = sharedpreferences.getInt("id",-1);
                test = mysql.getAllDataByUserId(id);
                mysql.close();
                myAdapter my = new myAdapter(myconnext,test);
                listview.setAdapter(my);
            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        receiver = new MyReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("upload");
        this.registerReceiver(receiver, filter);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        /*******************************************************************************/
        listview = (ListView) this.findViewById(R.id.listview);
        mysqlite mysql = new mysqlite(this);
        SharedPreferences sharedpreferences = getSharedPreferences("information", Activity.MODE_PRIVATE);
        int id = sharedpreferences.getInt("id",-1);
        test = mysql.getAllDataByUserId(id);
        mysql.close();
        myAdapter my = new myAdapter(this,test);
        listview.setAdapter(my);
        listview.setOnItemClickListener(this);

        /*******************************************************************************/

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.createClass) // 创建班级
        {
            Intent intent = new Intent(MainActivity.this,createClassActivity.class);
            startActivity(intent);
            return true;
        }
        else if (id == R.id.searchClass) // 搜索班级
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.user_information)   // 获取个人信息
        {
            // Handle the camera action
        }
        else if (id == R.id.setting)       // 设置
        {

        }

        else if (id == R.id.logout)         // 退出
        {
            SharedPreferences mySharedPreferences= getSharedPreferences("information", Activity.MODE_PRIVATE);
            SharedPreferences.Editor editor = mySharedPreferences.edit();
            editor.putInt("log_in_self",0);
            editor.commit();

            Intent intent = new Intent(MainActivity.this,firstActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent(MainActivity.this, userActivity.class);
        intent.putExtra("item_id", test[i].id);
        startActivity(intent);
    }
    @Override
    public void onDestroy()
    {
        super.onDestroy();
        this.unregisterReceiver(receiver);
    }

}
