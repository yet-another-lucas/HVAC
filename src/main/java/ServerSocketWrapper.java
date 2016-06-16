import java.io.IOException;
import java.net.ServerSocket;
import java.util.function.Function;

/**
 * Created by cyndi.russell on 6/15/16.
 */
public class ServerSocketWrapper {

    private ServerSocket serverSocket;

    public void start(int port) throws IOException {
        serverSocket = new ServerSocket(port);
    }

    public void stop() throws IOException {
        serverSocket.close();
    }
}
