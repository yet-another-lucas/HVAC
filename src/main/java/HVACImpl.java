/**
 * Created by lucas.schwarz on 6/14/16.
 */
public class HVACImpl implements  HVAC {



	public int temperature;
	public boolean heatEnabled = false;
	public boolean coldEnabled = false;
	public boolean fanEnabled = false;



	@Override
	public void heat(boolean on) {
		heatEnabled = on;
		coldEnabled = !on;
	}

	@Override
	public void cool(boolean on) {
		coldEnabled = on;
		heatEnabled = !on;
	}

	@Override
	public void fan(boolean on) {
		fanEnabled = on;
	}

	@Override
	public int temp() {
		return temperature;
	}
}
