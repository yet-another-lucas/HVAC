/**
 * Created by cyndi.russell on 6/15/16.
 */
public class Main {

    public static void main(String [] args) {
        for (String arg : args) {
            System.out.println("arg: " + arg);
        }

        Integer upperBound = Integer.parseInt(args[0]);
        Integer lowerBound = Integer.parseInt(args[1]);
        EnvironmentController environmentController = new EnvironmentController(new HvacRealImpl ());
        environmentController.setUpperBound(upperBound);
        environmentController.setLowerBound(lowerBound);
        System.out.println("upperBound: " + environmentController.getUpperBound());
        System.out.println("lowerBound: " + environmentController.getLowerBound());

    }








}
