package client;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Young on 2016/10/29.
 */
public class datil_String
{
    public Map<String,String> getvalue(String s)
    {
        Map<String,String> M = new HashMap<String,String>();
        String temp0,temp1;
        int i;
        while(true)
        {
            i= s.indexOf("|");
            temp0 = s.substring(0, i);
            s = s.substring(i+1,s.length());

            i = s.indexOf("|");
            if (i==-1)
            {
                temp1 = s;
                M.put(temp0,temp1);
                break;
            }
            temp1 = s.substring(0, i);
            s = s.substring(i+1,s.length());
            M.put(temp0,temp1);
        }
        return M;
    }
    public static void main (String[] avg)
    {
        System.out.println("hello!");
        String s = "lastname|李|firstname|东阳";
        datil_String t = new datil_String();
        Map<String,String> M = t.getvalue(s);
        System.out.println(M.size());
        System.out.println("over!");
    }
}
