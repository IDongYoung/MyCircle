package client;

/**
 * Created by Young on 2016/10/29.
 */
public class my_information
{
    private static int id;
    private static String name;
    private static String password;
    private static String email;
    private static String phone;
    private static String address;
    private static int is_log_in;
    private static int is_remember_password;

    public static int get_id()
    {
        return id;
    }
    public static String get_name()
    {
        return name;
    }
    public static String get_password()
    {
        return password;
    }
    public static String get_email()
    {
        return email;
    }
    public static String get_phone()
    {
        return phone;
    }
    public static String get_address()
    {
        return address;
    }
    public static int get_is_log_in() {return is_log_in;}
    public static int get_is_remember_password() { return is_remember_password;}

    public static void set_id(int i) {id = i;}
    public static void set_name(String n) {name = n;}
    public static void set_password(String p) {password = p;}
    public static void set_email(String e) {email = e;}
    public static void set_phone(String ph) {phone = ph;}
    public static void set_address(String a) {address = a;}
    public static void set_is_log_in(int i) {is_log_in = i;};
    public static void set_is_remember_password(int i){is_remember_password = i;}

}
