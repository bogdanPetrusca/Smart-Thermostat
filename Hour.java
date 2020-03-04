
import java.util.Comparator;
import java.util.SortedSet;
import java.util.TreeSet;

public class Hour {
	private long timestamp;
	private SortedSet<Temp> set;
	public Hour(long timestamp) {
		this.timestamp = timestamp;
		Comparator<Temp> comp = Comparator.comparing(Temp::getTimestamp);
		this.set = new TreeSet<Temp>(comp);
	}
	public long getTimestamp() {
		return this.timestamp;
	}
	public SortedSet<Temp> getSet() {
		return this.set;
	}
	
	public void display() {
		for(Temp t: this.set)
			System.out.print(" " + t.getTemp());
		
	}
}
