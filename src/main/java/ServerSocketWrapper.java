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

	public void start(int port) {
		try {
			socketServer = new ServerSocket(port);
			socket = socketServer.accept();
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			String data = in.readLine();
			processInput(data);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}



	public void stop() {
		try {
			socketServer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setTranslator(Function<String, String> translator) {
		this.translator = translator;
	}

	public String processInput(String data) {
		String processedInput = this.translator.apply(data);
		try {
			out.write(processedInput);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return processedInput;
	}
}
