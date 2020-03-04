
public class Humidity {
	private long timestamp;
	private double hum;
	public Humidity(long timestamp, double hum) {
		this.timestamp = timestamp;
		this.hum = hum;
	}
	public long getTimestamp() {
		return this.timestamp;
		
	}
	public double getHum() {
		return this.hum;
	}
}
