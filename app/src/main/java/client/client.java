package client;

/**
 * Created by Young on 2016/10/27.
 */
import android.os.Handler;

import internet.Internet_Service;

public class client {

    private Internet_Service iService = null;
    public client(Handler handler)
    {
        iService = new Internet_Service(handler);
    }
    // 注册
    public void regist(String name,String email,String phone,String address,String date)
    {
        iService.send("1|"+name+"|"+email+"|"+phone+"|"+address+"|"+date);
    }
    // 登陆
    public String login(String email,String password)
    {
        iService.send("2|"+email+"|"+password);
        return "";
    }
    // 创建班级
    public int create_class(String user_id,String password,String class_name,String information,String date)
    {

        return 1;
    }
    //搜索班级    参数(user_id password class_number) 返回值:-1（不存在）("name|the_value|creater_name|the_value"(名称),(创建者名字));
    public String serch_class(int user_id,String password,int class_number)
    {

        return "";
    }
    //加入班级    参数(user_id password class_number) 返回值:1(申请成功) -1(申请失败)
    public int join_class(int user_id,String password,int class_number)
    {

        return 1;
    }
    //退出班级    参数(user_id password class_number) 返回值:1(退出成功) -1(退出失败)
    public int exit_class(int user_id,String password,int class_number)
    {

        return 1;
    }
    //同意加入    参数(user_id password class_number  an_user_id) 返回值:1(同意成功) -1(同意失败)
    public int agree_join(int user_id,String password,int class_number,int an_user_id)
    {

        return 1;
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



    public static void main (String[] avg)
    {
        /*System.out.println("Hello!");
        client c = new client();
		int i = c.regist("李东阳", "417020264@qq.com", "15542339529", "北京市丰台区", "2016-7-714:20");
		if (i == 1) System.out.println("成功");
		else if (i == 2) System.out.println("该邮箱已被注册");
		else if (i == 3) System.out.println("邮箱地址错误或网络连接失败");

		int j = c.login("417020264@qq.com", "830704");
		if (j==1) System.out.println("登陆成功");
		else if (j==-1) System.out.println("登陆失败");

		int j = c.create_class(1+"", "830704", "软1316", "weare伐木累!" ,"2016-7-8-12:00:00");
		if (j==1) System.out.println("创建成功");

        int id=2;
        String password="102028";
        int c_id=1;


		else if (j==-1) System.out.println("创建失败");
        Scanner in = new Scanner(System.in);
        int flag = 0;
        while(true)
        {
            int o = in.nextInt();
            switch(o)
            {
                case 0:flag=1;break;
                case 1:System.out.println("加入班级!"+c.join_class(id, password, c_id)); break;
                case 2:System.out.println("同意加入!"+c.agree_join(id, password, c_id, 1)); break;
                case 3:System.out.println("获得班级列表!"+c.get_class_list(id, password)); break;
                case 4:System.out.println("获得成员列表!"+c.get_persons_list(id,password, c_id)); break;
                case 5:System.out.println("搜索班级!"+c.serch_class(id,password, c_id)); break;
                case 6:System.out.println("添加班级名片!"+c.add_new_name(id,password,c_id, "lidongyang")); break;
                case 7:System.out.println("退出班级!"+c.exit_class(id,password,c_id)); break;
                case 8:System.out.println("创建班级!"+c.create_class(id+"",password,"软1316","weare伐木累!","2016-7-8-12:00:00")); break;
                case 9:System.out.println("注册!"+c.regist("李东阳", "417020264@qq.com", "15542339529", "北京市丰台区", "2016-7-714:20")); break;
                case 10:System.out.println("登陆!"+c.login("417020264@qq.com", password)); break;
                case 11: break;
                case 12: break;
                case 13: break;
                case 14: break;
                default : break;
            }
            if (flag==1) break;
        }


        //System.out.println("加入班级!"+c.join_class(1, "830704", 6));
        //System.out.println("同意加入!"+c.agree_join(1, "830704", 6, 1));
        //System.out.println("获得班级列表!"+c.get_class_list(1, "830704"));
        //System.out.println("获得成员列表!"+c.get_persons_list(1, "", 6));
        //System.out.println("搜索班级!"+c.serch_class(1, "", 6));
        //System.out.println("添加班级名片!"+c.add_new_name(1, "", 6, "lidongyang"));
        //System.out.println("退出班级!");

        System.out.println("Over!");*/
    }

}
