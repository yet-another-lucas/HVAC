import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.function.Function;

/**
 * Created by cyndi.russell on 6/15/16.
 */
public class ServerSocketWrapper {

	private ServerSocket socketServer;
	private Function<String, String> translator;
	private Socket socket;
	BufferedReader in;
	BufferedWriter out;

    public ServerSocketWrapper(Function<String, String> dummyTranslator) {
        this.translator = dummyTranslator;
    }

    public void start(int port) throws IOException {
        socketServer = new ServerSocket(port);
        socket = socketServer.accept();
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String data = in.readLine();
        this.translator.apply(data);
    }

    public void stop() throws IOException {
        socketServer.close();
    }

    public void setTranslator(Function<String, String> translator) {
        this.translator = translator;
    }
}
