import junit.framework.Assert;
import org.junit.Test;

/**
 * Created by cyndi.russell on 6/15/16.
 */
public class EnvironmentControllerTests {



    @Test
    public void canSetUpperBound(){
        Integer upperBound = 70;
        EnvironmentController environmentController = new EnvironmentController(new HVACImpl(upperBound +1));
        environmentController.setUpperBound(upperBound);
        environmentController.tick();
        boolean expectedHvacStates = !environmentController.getHeatEnabled() && environmentController.getFanEnabled() && environmentController.getColdEnabled();
        Assert.assertTrue(expectedHvacStates);

    }

    @Test
    public void canSetLowerBound(){
        Integer lowerBound = 70;
        EnvironmentController environmentController = new EnvironmentController(new HVACImpl(lowerBound -1));
        environmentController.setLowerBound(lowerBound);
        environmentController.tick();
        boolean expectedHvacStates = environmentController.getHeatEnabled() && environmentController.getFanEnabled()&& !environmentController.getColdEnabled();
        Assert.assertTrue(expectedHvacStates);

    }

    @Test
    public void cantSetUpperTooCloseToLower(){
        EnvironmentController environmentController = new EnvironmentController(new HVACImpl(0));
        Integer lower = 60;
        Integer upper = lower + 4;
        environmentController.setLowerBound(lower);
        environmentController.setUpperBound(upper);
        Assert.assertNotSame("It obeyed our command", environmentController.getUpperBound(), upper );
    }

    @Test
    public void cantSetLowerTooCloseToUpper(){
        EnvironmentController environmentController = new EnvironmentController(new HVACImpl(0));
        Integer upper = 70;
        Integer lower = upper - 4;
        environmentController.setUpperBound(upper);
        environmentController.setLowerBound(lower);
        Assert.assertNotSame("It obeyed our command", environmentController.getLowerBound(), lower );

    }

    @Test
    public void canSetUpperAsCloseAsPossibleToLower(){
        EnvironmentController environmentController = new EnvironmentController(new HVACImpl(0));
        Integer lower = 60;
        Integer upper = lower + 5;
        environmentController.setLowerBound(lower);
        environmentController.setUpperBound(upper);
        Assert.assertEquals("It didn't honor the request", environmentController.getUpperBound(), upper );
    }

    @Test
    public void canSetLowerAsCloseAsPossibleToUpper(){
        EnvironmentController environmentController = new EnvironmentController(new HVACImpl(0));
        Integer upper = 70;
        Integer lower = upper - 5;
        environmentController.setUpperBound(upper);
        environmentController.setLowerBound(lower);
        Assert.assertEquals("It didn't honor the request", environmentController.getLowerBound(), lower );
    }



}
