import com.sun.xml.internal.bind.v2.TODO;
import junit.framework.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;
import java.util.function.Function;

/**
 * Created by cyndi.russell on 6/15/16.
 */
public class ServerSocketWrapperTest {

    private final static int PORT = 8080;
    private final static String HOST = "localHost";
    private ServerSocketWrapper wrapper;

    private void startWrapper() throws InterruptedException {
        new Thread(() -> {
            try {
                wrapper.start(PORT);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }).start();
    }

    public void sendDataToSocket(String data) throws InterruptedException, IOException {
        startWrapper();
        Socket socket = new Socket(HOST, PORT);
        PrintWriter out = new PrintWriter(socket.getOutputStream());
        out.println(data);
        out.flush();
    }
    @Before
    public void setUp() {
        Function<String, String> dummyTranslator = in -> "";
        wrapper = new ServerSocketWrapper();
    }

    @After
    public void tearDown() throws IOException {
        wrapper.stop();
    }


    @Test
    public void startServer() throws IOException, InterruptedException {
        startWrapper();
        Socket socket = new Socket(HOST, PORT);
        Assert.assertTrue("We did not find your connection", socket.isConnected());
    }

    //Need to fix
    
//    @Test
//    public void sendDataToSocket(){
//        sendDataToSocket();
//    }


    @Test (expected = ConnectException.class)
    public void stopServer() throws IOException, InterruptedException {
        startWrapper();
        Thread.sleep(1);
        wrapper.stop();
        Socket socket = new Socket(HOST, PORT);



    }



}
