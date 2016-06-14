import junit.framework.Assert;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

/**
 * Created by lucas.schwarz on 6/14/16.
 */
public class HVACTests {

	private Integer tooDamnCold = 64;
	private Integer tooDamnHot = 76;
	private Integer cozy = 70;
	private List<Integer> goldilocks = Arrays.asList(65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75);


	@Test
	public void canInstantiateHVAC() {
		HVAC hvac = new HVACImpl();
		Assert.assertNotNull(hvac);
	}

		@Test
	public void canInstantiateEnvironmentController() {
		HVAC hvac = new HVACImpl();
		EnvironmentController environmentController = new EnvironmentController(hvac);
		Assert.assertNotNull(environmentController);
	}

	@Test
	public void turnOnHeatAndFanWhenBelow65() {
		HVAC hvac = new HVACImpl();
		setTemp(hvac, tooDamnCold);
		EnvironmentController environmentController = new EnvironmentController(hvac);
		environmentController.tick();
		boolean heatEnabled = getHeatEnabled(hvac);
		boolean fanEnabled = getFanEnabled(hvac);
		Assert.assertTrue(heatEnabled);
		Assert.assertTrue(fanEnabled);
	}

	@Test
	public void turnOnCoolAndFanWhenAbove75() {
		HVAC hvac = new HVACImpl();
		setTemp(hvac, tooDamnHot);
		EnvironmentController environmentController = new EnvironmentController(hvac);
		environmentController.tick();
		boolean coldEnabled = getHeatEnabled(hvac);
		boolean fanEnabled = getFanEnabled(hvac);
		Assert.assertTrue(coldEnabled && fanEnabled);
	}

	@Test
	public void disableHvacWhenCozy() {
		HVAC hvac = new HVACImpl();
		setTemp(hvac, cozy);
		EnvironmentController environmentController = new EnvironmentController(hvac);
		environmentController.tick();
		boolean heatEnabled = getHeatEnabled(hvac);
		boolean coldEnabled = getHeatEnabled(hvac);
		boolean fanEnabled = getFanEnabled(hvac);
		Assert.assertFalse(heatEnabled && coldEnabled && fanEnabled);
	}


	private void setTemp(HVAC hvac, int temp) {
		Field field;
		try {
			field = hvac.getClass().getDeclaredField("temperature");
			field.set(hvac, temp);
			} catch (NoSuchFieldException
			  | IllegalAccessException e) {
		e.printStackTrace();
	  }
	}

	private boolean getFanEnabled(HVAC hvac) {
		Field field = _getField(hvac, "fanEnabled");
		return _getBoolean(hvac, field);
	}

	private boolean getHeatEnabled(HVAC hvac) {
		Field field = _getField(hvac, "heatEnabled");
		return _getBoolean(hvac, field);
	}

	private boolean getColdEnabled(HVAC hvac) {
		Field field = _getField(hvac, "coldEnabled");
		return _getBoolean(hvac, field);
	}

	private boolean _getBoolean(HVAC hvac, Field field) {
		boolean boo = false; //so sorry
		try {
			boo =  (boolean)field.get(hvac);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return boo;
	}

	private Field _getField(HVAC hvac, String fieldName) {
		Field field = null;
		try {
			field =  hvac.getClass().getDeclaredField(fieldName);
			} catch (NoSuchFieldException e) {
		e.printStackTrace();
	  }
	  return field;
	}
}
