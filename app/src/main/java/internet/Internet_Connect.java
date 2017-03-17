package internet;

import org.apache.mina.core.RuntimeIoException;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

/**
 * Created by Young on 2017/3/10.
 */
public class Internet_Connect {
    public static final int PORT = 8888;
    public static final String HOSTNAME = "115.159.124.98";
    private static Internet_Handler client;
    public static final void setHandler(Internet_Handler c)
    {
        client=c;
    }
    public static final void connect()
    {
        NioSocketConnector connector = new NioSocketConnector();
        connector.getFilterChain().addLast("codec",new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("UTF-8"))));  //添加编码解码器
        connector.setHandler(client);
        IoSession session;
        ConnectFuture future = null;
        try {
            future = connector.connect(new InetSocketAddress(HOSTNAME, PORT));
            future.awaitUninterruptibly();
            session = future.getSession();
            session.getCloseFuture().awaitUninterruptibly();
            connector.dispose();
        } catch (RuntimeIoException e) {
            System.err.println("Failed to connect.");
            e.printStackTrace();
        }
    }
}
