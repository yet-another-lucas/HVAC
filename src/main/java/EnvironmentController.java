/**
 * Created by lucas.schwarz on 6/14/16.
 */
public class EnvironmentController {

	private HVAC hvac;
	private Integer ticks = 0;
	private Integer temp;


	public EnvironmentController(HVAC hvac) {
		this.hvac = hvac;
	}
	public void tick() {
		temp = this.hvac.temp();
		if (temp < 65) {
			turnItUp();
		}
		if (temp > 75) {
			//inferno mode
			turnItDown();
		}
		if (temp <= 75 && temp >= 65) {
			//goldilocks mode
			shutItOff();
		}
	}

	private void turnItUp() {
		hvac.heat(true);
		hvac.fan(true);
	}

	private void turnItDown() {
		hvac.heat(true);
		hvac.fan(true);
	}

	private void shutItOff() {
		hvac.heat(false);
		hvac.cool(false);
		hvac.fan(false);
	}
}
