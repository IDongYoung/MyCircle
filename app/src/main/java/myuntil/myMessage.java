package myuntil;

/**
 * Created by Young on 2017/3/10.
 */
public class myMessage
{
    private String message;
    public myMessage(String s)
    {
        message=s;
    }
    public String[] decodeMessage()
    {
        String[] result = null;
        result = message.split("\\|");
        return result;
    }
    public String codeMessage(String[] s)
    {
        String result=s[0];
        for (int i=1;i<s.length;i++)
        {
            result=result+"|"+s[i];
        }
        return result;
    }
}
