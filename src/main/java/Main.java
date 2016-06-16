import java.io.IOException;
import java.util.function.Function;

/**
 * Created by cyndi.russell on 6/15/16.
 */
public class Main {

	public static void main(String [] args) {
		for (String arg : args) {
			System.out.println("arg: " + arg);
		}
		
		EnvironmentController environmentController = new EnvironmentController(new HvacRealImpl ());

		//ugh parse the arbs
		Integer lowerBound = 0;
		Integer upperBound = 0;
		if (args != null) {
			if (args[0] != null) {
				upperBound = Integer.parseInt(args[0]);
			}
			if (args[0] != null) {
				lowerBound = Integer.parseInt(args[1]);
			}
		}
		if (!upperBound.equals(lowerBound)) {
			environmentController.setUpperBound(upperBound);
			environmentController.setLowerBound(lowerBound);
		}
		System.out.println("upperBound: " + environmentController.getUpperBound());
		System.out.println("lowerBound: " + environmentController.getLowerBound());
		Function<String, String> dummyTranslator = in -> in + "hello world\n";
		ServerSocketWrapper wrapper = new ServerSocketWrapper(dummyTranslator);
				new Thread(() -> {
					try {
						wrapper.start(9000);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}).start();
		System.out.println("works for me");
//		wrapper.stop();
		//get the value of a request
		//and try to apply it to the environment controller's upper and lower bounds
	}








}
