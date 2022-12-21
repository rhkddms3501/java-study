package tv;

public class TV {
	private int channel; 	//	1 ~ 255
	private int volume; 	//	0 ~ 100
	private boolean power;
	
	
	public TV(int channel, int volume, boolean power) {
		this.channel = channel;
		this.volume = volume;
		this.power = power;
	}
	
	public int getChannel() {
		return channel;
	}

	public int getVolume() {
		return volume;
	}

	public boolean isPower() {
		return power;
	}

	public void power(boolean on) {
		this.power = on;
	}
	
	public void channel(int channel) {
		if(channel > 255) this.channel = channel - 255;
		else if(channel < 1) this.channel = channel + 255;
		else this.channel = channel;
	}
	
	public void channel(boolean up) {
		if(up) {
			if(this.channel >= 255) this.channel = 1;
			else this.channel++;
		}else {
			if(up && this.channel <= 1) this.channel = 255;
			else this.channel--;
		}
	}
	
	public void volume(int volume) {
		if(volume > 100) this.volume = 100;
		else if(volume < 0) this.volume = 0;
		else this.volume = volume;
	}
	
	public void volume(boolean up) {		
		if(up) {
			if(this.volume >= 255) this.volume = 1;
			else this.volume++;
		}else {
			if(up && this.volume <= 1) this.volume = 255;
			else this.volume--;
		}
	}
	
	public void status() {
		if(this.power) {
			
		}
		System.out.println(
				"TV [ power =" + (power ? "on" : "off") + "," + 
				"channel = " + channel + "," + 
				"volume = " + volume + "]");
	}

}
