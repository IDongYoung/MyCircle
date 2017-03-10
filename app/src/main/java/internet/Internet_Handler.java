package internet;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;

/**
 * Created by Young on 2017/3/10.
 */
public class Internet_Handler extends IoHandlerAdapter
{
    IoSession mysession = null;
    Handler myhandler = null;
    public void setHandler(Handler h)
    {
        myhandler = h;
    }
    //当与服务端链接成功时Session会被创建，同时会触发该方法
    public void sessionOpened(IoSession session) throws Exception
    {
        mysession = session;
    }
    //当接收到服务端发送来的消息时，会触发该方法
    @Override
    public void messageReceived(IoSession session, Object message) throws Exception
    {
        Log.v("Internet_Handler","get something");
        String msg = message.toString();
        Log.v("Internet_Handler",msg);
        Message m = new Message();
        Bundle b = new Bundle();
        b.putString("message",msg);
        m.setData(b);
        myhandler.sendMessage(m);
    }
    public void sendMessage(String s)
    {
        while (mysession == null);
        mysession.write(s);
        Log.v("Internet","发送消息" + s + "\"给服务端成功");
    }
    @Override
    public void exceptionCaught(IoSession session, Throwable cause)
            throws Exception {
        session.close(false) ;  //当发送异常，就关闭session
    }

}

