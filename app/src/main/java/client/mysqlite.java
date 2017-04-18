package client;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import myuntil.myMessage;

/**
 * Created by Young on 2016/10/31.
 */
public class mysqlite extends SQLiteOpenHelper
{
    public mysqlite(Context context)
    {
        super(context, "test.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase)
    {
        String sql = "create table user(id int primary key," +
                     "                  name varchar(100)," +
                     "                  email varchar(100)," +
                     "                  phone varchar(11)," +
                     "                  address varchar(100)," +
                     "                  date varchar(30));";
        sqLiteDatabase.execSQL(sql);
               sql = "create table classes(id int primary key ," +
                     "                     user_id int ," +
                     "                     name varchar(100) ," +
                     "                     information varchar(1000) ," +
                     "                     date varchar(30));";
        sqLiteDatabase.execSQL(sql);
               sql = "create table class_user(user_id int," +
                     "                        class_id int," +
                     "                        an_name varchar(100)," +
                     "                        primary key (user_id,class_id));";
        sqLiteDatabase.execSQL(sql);
                sql = "create table class_user_temp(user_id int," +
                     "                        class_id int," +
                     "                        an_name varchar(100)," +
                     "                        primary key (user_id,class_id));";
        sqLiteDatabase.execSQL(sql);

        /*sqLiteDatabase.execSQL("insert into user values(1,'li1','417020264@qq.com','15542339529','beijing','2016-10-31');");
        sqLiteDatabase.execSQL("insert into user values(2,'li2','417020264@qq.com','15542339529','beijing','2016-10-31');");
        sqLiteDatabase.execSQL("insert into user values(3,'li3','417020264@qq.com','15542339529','beijing','2016-10-31');");
        sqLiteDatabase.execSQL("insert into user values(4,'li4','417020264@qq.com','15542339529','beijing','2016-10-31');");
        sqLiteDatabase.execSQL("insert into user values(5,'li5','417020264@qq.com','15542339529','beijing','2016-10-31');");
        sqLiteDatabase.execSQL("insert into user values(6,'li6','417020264@qq.com','15542339529','beijing','2016-10-31');");
        sqLiteDatabase.execSQL("insert into user values(7,'li7','417020264@qq.com','15542339529','beijing','2016-10-31');");
        sqLiteDatabase.execSQL("insert into user values(8,'li8','417020264@qq.com','15542339529','beijing','2016-10-31');");
        sqLiteDatabase.execSQL("insert into user values(9,'li9','417020264@qq.com','15542339529','beijing','2016-10-31');");

        sqLiteDatabase.execSQL("insert into classes values(1,1,'1311','we edg lgd','2016-10-31');");
        sqLiteDatabase.execSQL("insert into classes values(2,1,'1312','we edg lgd','2016-10-31');");
        sqLiteDatabase.execSQL("insert into classes values(3,1,'1313','we edg lgd','2016-10-31');");
        sqLiteDatabase.execSQL("insert into classes values(4,1,'1314','we edg lgd','2016-10-31');");
        sqLiteDatabase.execSQL("insert into classes values(5,1,'1315','we edg lgd','2016-10-31');");
        sqLiteDatabase.execSQL("insert into classes values(6,1,'1316','we edg lgd','2016-10-31');");

        sqLiteDatabase.execSQL("insert into class_user values(1,1,'hello');");
        sqLiteDatabase.execSQL("insert into class_user values(1,2,'hello');");
        sqLiteDatabase.execSQL("insert into class_user values(1,3,'hello');");
        sqLiteDatabase.execSQL("insert into class_user values(2,2,'hello');");
        sqLiteDatabase.execSQL("insert into class_user values(3,4,'hello');");
        sqLiteDatabase.execSQL("insert into class_user values(4,4,'hello');");
        sqLiteDatabase.execSQL("insert into class_user values(5,4,'hello');");
        sqLiteDatabase.execSQL("insert into class_user values(1,4,'hello');");
        sqLiteDatabase.execSQL("insert into class_user values(1,5,'hello');");
        sqLiteDatabase.execSQL("insert into class_user values(1,6,'hello');");*/
    }
    public myclass[] getAllDataByUserId(int id)
    {
        myclass[] r=null;
        int num = 0;
        String sql = "select count(*) from classes,class_user where classes.id = class_user.class_id and class_user.user_id = "+id;
        Cursor c = this.getWritableDatabase().rawQuery(sql,null);
        if (c.moveToNext()) num = c.getInt(0);
        if (num!=0)
        {
            r =new myclass[num];
            int i=0;
            sql = "select classes.id,classes.user_id,classes.name,classes.information,classes.date" +
                    " from classes,class_user where classes.id = class_user.class_id and class_user.user_id = "+id;
            c = this.getReadableDatabase().rawQuery(sql,null);
            while(c.moveToNext())
            {
                r[i] = new myclass();
                r[i].id = c.getInt(0);
                r[i].user_id = c.getInt(1);
                r[i].name = c.getString(2);
                r[i].information = c.getString(3);
                r[i].date = c.getString(4);
                i++;
            }
        }
        return r;
    }
    public user[] getAllUsersByClassId(int id)
    {
        user[] r=null;
        int num = 0;
        String sql = "select count(*) from user,class_user where user.id = class_user.user_id and class_user.class_id = "+id;
        Cursor c = this.getReadableDatabase().rawQuery(sql,null);
        if (c.moveToNext()) num = c.getInt(0);
        if (num!=0)
        {
            r =new user[num];
            int i=0;
            sql = "select user.id,user.name,user.email,user.phone,user.address,user.date" +
                    " from user,class_user where user.id = class_user.user_id and class_user.class_id = "+id;
            c = this.getReadableDatabase().rawQuery(sql,null);
            while(c.moveToNext())
            {
                Log.v("mysqlite name = ",c.getString(1));
                r[i] = new user();
                r[i].id = c.getInt(0);
                r[i].name = c.getString(1);
                r[i].email = c.getString(2);
                r[i].phone = c.getString(3);
                r[i].address = c.getString(4);
                r[i].date = c.getString(5);
                i++;
            }
        }
        return r;
    }
    public waitagree[] getAllWaitMe()
    {
        waitagree[] w=null;
        int num = 0;
        String sql = "select count(*) from class_user_temp cut,classes c,user u where cut.user_id = u.id and cut.class_id = c.id;";
        Cursor c = this.getReadableDatabase().rawQuery(sql,null);
        if (c.moveToNext()) num = c.getInt(0);
        if (num!=0)
        {
            w = new waitagree[num];
            int i=0;
            sql = "select u.id,u.name,c.id,c.name from class_user_temp cut,classes c,user u " +
                    "where cut.user_id = u.id and cut.class_id = c.id;";
            c = this.getReadableDatabase().rawQuery(sql,null);
            while(c.moveToNext())
            {
                w[i] = new waitagree();
                w[i].user_id = c.getInt(0)+"";
                w[i].user_name = c.getString(1);
                w[i].class_id = c.getInt(2)+"";
                w[i++].class_name = c.getString(3);
            }
        }
        return w;
    }
    public void adduser(String id,String name,String email,String phone,String address,String date)
    {
        String sql1 = "select * from user where id = "+id+";";
        Cursor c = this.getWritableDatabase().rawQuery(sql1,null);
        if(c.moveToNext()) return;
        String sql = "insert into user values("+id+",'"+name+"','"+email+"','"+phone+"','"+address+"','"+date+"');";
        this.getWritableDatabase().execSQL(sql);
    }
    public void addclass(String id,String user_id,String name,String information,String date)
    {
        String sql1 = "select * from classes where id = "+id+";";
        Cursor c = this.getWritableDatabase().rawQuery(sql1,null);
        if(c.moveToNext()) return;
        String sql = "insert into classes values("+id+","+user_id+",'"+name+"','"+information+"','"+date+"');";
        this.getWritableDatabase().execSQL(sql);
    }
    public void addclass_user(String user_id,String class_id,String an_name)
    {
        String sql1 = "select * from class_user where user_id = "+user_id+" and class_id = "+class_id+";";
        Cursor c = this.getWritableDatabase().rawQuery(sql1,null);
        if(c.moveToNext()) return;
        String sql = "insert into class_user values("+user_id+","+class_id+",'"+an_name+"');";
        this.getWritableDatabase().execSQL(sql);
    }
    public void addclass_user_temp(String user_id,String class_id,String an_name)
    {
        String sql1 = "select * from class_user_temp where user_id = "+user_id+" and class_id = "+class_id+";";
        Cursor c = this.getWritableDatabase().rawQuery(sql1,null);
        if(c.moveToNext()) return;
        String sql = "insert into class_user_temp values("+user_id+","+class_id+",'"+an_name+"');";
        this.getWritableDatabase().execSQL(sql);
    }
    public void delete_class_user_temp(String user_id,String class_id)
    {
        String sql = "delete from class_user_temp where user_id = "+user_id+" and class_id = "+class_id+";";
        this.getWritableDatabase().execSQL(sql);
    }
    public void update_phone(String id,String phone)
    {
        String sql = "update user set phone = '"+phone+"' where id = "+id+";";
        this.getWritableDatabase().execSQL(sql);
    }
    public String getClassInformation(String id)
    {
        String sql = "select u.name,c.name,c.information,c.date from classes c,user u " +
                     " where c.user_id=u.id and c.id = "+id+";";
        Cursor c = this.getReadableDatabase().rawQuery(sql,null);
        if(c.moveToNext())
        {
            String[] s = new String[4];
            s[0] = c.getString(0);
            s[1] = c.getString(1);
            s[2] = c.getString(2);
            s[3] = c.getString(3);
            myMessage my = new myMessage(s);
            String r = my.codeMessage();
            Log.v("mysqlite",r);
            return r;
        }
        return -1+"";
    }
    public void cleanall()
    {
        String sql = "delete from user;";
        this.getWritableDatabase().execSQL(sql);
        sql = "delete from classes;";
        this.getWritableDatabase().execSQL(sql);
        sql = "delete from class_user;";
        this.getWritableDatabase().execSQL(sql);
    }

    public void closemysqlite()
    {
        this.getWritableDatabase().close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1)
    {

    }
}
