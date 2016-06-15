/**
 * Created by lucas.schwarz on 6/14/16.
 */
public class HVACImpl implements HVAC {

	public HVACImpl (int temp) {
		this.temperature = temp;
	}

	private int temperature;
	@Override
	public void heat(boolean on) {

	}

	@Override
	public void cool(boolean on) {

	}

	@Override
	public void fan(boolean on) {

	}

	@Override
	public int temp() {
		return temperature;
	}
}
