/**
 * Created by lucas.schwarz on 6/14/16.
 */
public class EnvironmentController {


	public EnvironmentController(HVAC hvac) {
		this.hvac = hvac;
	}

	public EnvironmentController(HVAC hvac, Integer heatRefractoryPeriod, Integer coldRefractoryPeriod) {
		this.hvac = hvac;
		this.heatRefractoryPeriod = heatRefractoryPeriod;
		this.coldRefractoryPeriod = coldRefractoryPeriod;
	}

	private HVAC hvac;
	private Integer ticksUntilFanIsReady = 0;
	private Integer heatRefractoryPeriod = 5;
	private Integer coldRefractoryPeriod = 3;

	public Integer getHeatRefractoryPeriod() {
		return heatRefractoryPeriod;
	}

	public Integer getColdRefractoryPeriod() {
		return coldRefractoryPeriod;
	}

	private Integer temp;

	private boolean heatEnabled = false;
	private boolean coldEnabled = false;
	private boolean fanEnabled = false;

	private void setHeatEnabled(boolean heatEnabled) {
		if(this.heatEnabled && !heatEnabled) {
			//toggle from on to off and set the cooldown
			ticksUntilFanIsReady = heatRefractoryPeriod;
		}
		this.heatEnabled = heatEnabled;
	}

	public boolean getHeatEnabled() {
		return this.heatEnabled;
	}

	private void setColdEnabled(boolean coldEnabled) {
		if(this.coldEnabled && !coldEnabled) {
			//toggle from on to off and set the cooldown
			ticksUntilFanIsReady = coldRefractoryPeriod;
		}
		this.coldEnabled = coldEnabled;
	}

	public boolean getColdEnabled() {
		return this.coldEnabled;
	}

	private void setFanEnabled(boolean fanEnabled) {
		if(!this.fanEnabled && fanEnabled) {
			//toggle from off to on and check the cooldown
			if(ticksUntilFanIsReady > 0) {
				//too soon
				ticksUntilFanIsReady--;
			}
			else {
				//now is the time
				ticksUntilFanIsReady = 0;
				this.fanEnabled = fanEnabled;
			}
		}
		else{
			//toggle from on to off is not a special case
			this.fanEnabled = fanEnabled;
		}
	}

	public boolean getFanEnabled() {
		return this.fanEnabled;
	}


	public void tick() {
		temp = hvac.temp();
		if (temp < 65) {
			//chillax mode
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
		tellTheHvac();
	}

	private void turnItUp() {
		setHeatEnabled(true);
		setColdEnabled(false);
		setFanEnabled(true);

	}

	private void turnItDown() {
		setHeatEnabled(false);
		setColdEnabled(true);
		setFanEnabled(true);
	}

	private void shutItOff() {
		setHeatEnabled(false);
		setColdEnabled(false);
		setFanEnabled(false);
	}

	private void tellTheHvac() {
		hvac.heat(getHeatEnabled());
		hvac.cool(getColdEnabled());
		hvac.fan(getFanEnabled());
	}

}
