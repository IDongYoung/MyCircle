package myuntil;

/**
 * Created by Young on 2017/3/10.
 */
public class myMessage
{
    private String message;
    private String[] messages;
    public myMessage(String s)
    {
        message=s;
    }
    public myMessage(String[] s)
    {
        messages=s;
    }
    public String[] decodeMessage()
    {
        String[] result = null;
        result = message.split("\\|");
        return result;
    }
    public String codeMessage()
    {
        String result=messages[0];
        for (int i=1;i<messages.length;i++)
        {
            result=result+"|"+messages[i];
        }
        return result;
    }
}
