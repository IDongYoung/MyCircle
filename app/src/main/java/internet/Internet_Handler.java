package internet;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.util.HashMap;

import client.my_information;

import static java.lang.Thread.sleep;

/**
 * Created by Young on 2017/3/10.
 */
public class Internet_Handler extends IoHandlerAdapter
{
    IoSession mysession = null;
    Handler myhandler = null;
    Context context;
    //当与服务端链接成功时Session会被创建，同时会触发该方法
    public void sessionOpened(IoSession session) throws Exception
    {
        mysession = session;
        Log.v("Internet_handler","重连成功");
    }
    //当接收到服务端发送来的消息时，会触发该方法
    @Override
    public void messageReceived(IoSession session, Object message) throws Exception
    {
        Log.v("Internet_Handler","get something");
        String msg = message.toString();
        Log.v("Internet_Handler",msg);
        Bundle b =new Bundle();
        b.putString("message",msg);
        Message m = new Message();
        m.setData(b);
        myhandler.sendMessage(m);
    }
    public void sendMessage(String s)
    {
        while (mysession == null);
        mysession.write(s);
        Log.v("Internet","发送消息" + s + "\"给服务端成功");
    }
    public void setHandler(Handler h)
    {
        myhandler = h;
    }
    public void setContext(Context c)
    {
        context = c;
    }

    @Override
    public void sessionClosed(IoSession session)
    {
        System.out.println("session"+"连接断开");
        final IoSession temp = session;
        Thread checkNetwork = new Thread(new Runnable() {
            @Override
            public void run()
            {
                Log.v("Internet_handler","check线程开始");
                try {
                    while(true)
                    {
                        sleep(3000);
                        if(isOpenNetwork())  // 网络链接正常
                        {
                            connect(temp);
                            Log.v("Internet_handler","check线程结束");
                            break;
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        if(this.isOpenNetwork())  // 网络链接正常
        {
            Log.v("Internet_handler","开始重连网络");
            this.connect(session);
        }
        else  // 网络链接断开
        {
            Log.v("Internet_handler","无网络");
            checkNetwork.start();
        }
    }
    private void connect(IoSession session)
    {
        while(true)
        {
            try {
                NioSocketConnector connector = new NioSocketConnector();
                connector.getFilterChain().addLast("codec", new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("UTF-8"))));  //添加编码解码器
                connector.setHandler(this);
                ConnectFuture future = connector.connect(new InetSocketAddress(
                        Internet_Connect.HOSTNAME, Internet_Connect.PORT));
                future.awaitUninterruptibly();// 等待连接创建完成
                session = future.getSession();
                if (session.isConnected())
                {
                    my_information me = new my_information(context);
                    session.write("21|"+me.getID());
                    break;
                } else {
                    System.out.println("失败");
                    Toast.makeText(context.getApplicationContext(), "请检查网络", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }




    @Override
    public void exceptionCaught(IoSession session, Throwable cause)
            throws Exception {
        session.close(false) ;  //当发送异常，就关闭session
    }

    private boolean isOpenNetwork()
    {
        ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connManager != null)
        {
            Log.v("Internet_handler","not null");
            NetworkInfo activeNetInfo = connManager.getActiveNetworkInfo();
            if (activeNetInfo!=null)
            {
                Log.v("Internet_handler","ac not null");
                return activeNetInfo.isAvailable();
            }
            Log.v("Internet_handler","ac is null");
        }
        else
        {
            Log.v("Internet_handler","is null");
        }
        return false;
    }
}

