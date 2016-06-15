import junit.framework.Assert;
import org.junit.Test;

import java.lang.reflect.Field;

/**
 * Created by lucas.schwarz on 6/14/16.
 */
public class HVACTests {

	private Integer tooDamnCold = 64;
	private Integer tooDamnHot = 76;
	private Integer almostFreezing = 65;
	private Integer almostMelting = 75;
	private Integer cozy = 70;

	@Test
	public void cantUseFanImmediatelyAfterHeat() {
		HVAC coldHvac = new HVACImpl(tooDamnCold);
		HVAC cozyHvac = new HVACImpl(cozy);
		EnvironmentController environmentController = new EnvironmentController(coldHvac);
		int heatCooldown = environmentController.getHeatRefractoryPeriod();
		environmentController.tick(); //turns on fan and heat
		replaceHvac(environmentController, cozyHvac);
		environmentController.tick(); //turn off the fan
		replaceHvac(environmentController, coldHvac);
		for (int i = 0; i < heatCooldown; i++) {
			environmentController.tick();
			Assert.assertFalse(environmentController.getFanEnabled());
		}
		environmentController.tick();
		Assert.assertTrue(environmentController.getFanEnabled());
	}

	@Test
	public void cantUseFanImmediatelyAfterCool() {
		HVAC hotHvac = new HVACImpl(tooDamnHot);
		HVAC cozyHvac = new HVACImpl(cozy);
		EnvironmentController environmentController = new EnvironmentController(hotHvac);
		int coldCooldown = environmentController.getColdRefractoryPeriod();
		environmentController.tick(); //turn on fan and cool
		replaceHvac(environmentController, cozyHvac);
		environmentController.tick(); //turn off the fan
		replaceHvac(environmentController, hotHvac);
		for (int i = 0; i < coldCooldown; i++) {
			environmentController.tick();
			Assert.assertFalse(environmentController.getFanEnabled());
		}
		environmentController.tick();
		Assert.assertTrue(environmentController.getFanEnabled());
	}

	@Test
	public void turnOnHeatAndFanWhenTooCold() {
		HVAC hvac = new HVACImpl(tooDamnCold);
		EnvironmentController environmentController = new EnvironmentController(hvac);
		environmentController.tick();
		boolean expectedHvacStates = environmentController.getHeatEnabled() && environmentController.getFanEnabled()&& !environmentController.getColdEnabled();
		Assert.assertTrue(expectedHvacStates);
	}

	@Test
	public void turnOnCoolAndFanWhenTooHot() {
		HVAC hvac = new HVACImpl(tooDamnHot);
		EnvironmentController environmentController = new EnvironmentController(hvac);
		environmentController.tick();
		boolean expectedHvacStates = !environmentController.getHeatEnabled() && environmentController.getFanEnabled() && environmentController.getColdEnabled();
		Assert.assertTrue(expectedHvacStates);
	}

	@Test
	public void disableHvacWhenAlmostTooCold() {
		HVAC hvac = new HVACImpl(almostFreezing);
		EnvironmentController environmentController = new EnvironmentController(hvac);
		environmentController.tick();
		boolean allThingsOff = !environmentController.getColdEnabled() && !environmentController.getFanEnabled() && !environmentController.getFanEnabled();
		Assert.assertTrue(allThingsOff);	}

	@Test
	public void disableHvacWhenAlmostTooHot() {
		HVAC hvac = new HVACImpl(almostMelting);
		EnvironmentController environmentController = new EnvironmentController(hvac);
		environmentController.tick();
		boolean allThingsOff = !environmentController.getColdEnabled() && !environmentController.getFanEnabled() && !environmentController.getFanEnabled();
		Assert.assertTrue(allThingsOff);
	}

	private void replaceHvac(EnvironmentController environmentController, HVAC hvac) {
		Field field = null;
		try {
			field = environmentController.getClass().getDeclaredField("hvac");
			field.setAccessible(true);
			field.set(environmentController, hvac);
		} catch (NoSuchFieldException
			  | IllegalAccessException e) {
		e.printStackTrace();
	  }
	}
}
