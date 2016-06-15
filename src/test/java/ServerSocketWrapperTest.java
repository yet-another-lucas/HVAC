import junit.framework.Assert;
import org.junit.Test;

import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;

/**
 * Created by cyndi.russell on 6/15/16.
 */
public class ServerSocketWrapperTest {

    private final static int PORT = 8080;
    private final static String HOST = "localHost";

    @Test
    public void startServer() throws IOException {
        ServerSocketWrapper wrapper = new ServerSocketWrapper();
        wrapper.start(PORT);
        Socket socket = new Socket(HOST, PORT);
        Assert.assertTrue("We did not find your connection", socket.isConnected());
    }

    @Test
    public void canSendData(){

    }

    @Test (expected = ConnectException.class)
    public void stopServer() throws IOException {
        ServerSocketWrapper wrapper = new ServerSocketWrapper();
        wrapper.start(PORT);
        wrapper.stop();
        Socket socket = new Socket(HOST, PORT);



    }



}
