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


	@Before
	public void setUp() {
		Function<String, String> dummyTranslator = in -> "";
		wrapper = new ServerSocketWrapper(dummyTranslator);
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

	@Test (expected = ConnectException.class)
	public void stopServer() throws IOException, InterruptedException {
		startWrapper();
		Thread.sleep(1);
		wrapper.stop();
		new Socket(HOST, PORT);
	}
	
	@Test
	public void acceptDataFromSocket()  {
		final String[] actualData = new String[1];
		String testData = "data";
		Function<String, String> translator = in -> {
			actualData[0] = in;
			return in;
		};
		wrapper.setTranslator(translator);

		startWrapper();
		sendDataToSocket(testData);

		int retries = 0;
		while(actualData[0] == null && retries < 5) {
			nap(1);
			retries++;
		}
		Assert.assertEquals(actualData[0], "data");
	}

	private void startWrapper()  {
		new Thread(() -> {
				wrapper.start(PORT);
		}).start();
	}

	public void sendDataToSocket(String data) {
		startWrapper();
		Socket socket = null;
		PrintWriter out = null;
		try {
			socket = new Socket(HOST, PORT);
			out = new PrintWriter(socket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		out.println(data);
		out.flush();
	}

	public void nap(int sleepInMs) {
		try {
			Thread.sleep(sleepInMs);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}



}
