import java.util.HashMap;
import java.util.Map;


public class Camper extends Utilities {

	private String name;
	private int numPrefs = 0; // not including zero ratings
	private int sumPrefs = 0; 
	
	// HashMap<Mate_name, Rating>...
	private HashMap<Camper, Integer> mates = new HashMap<Camper, Integer>();
	
	Camper(String name) {
		this.name = name;
	}
	
	boolean rateMate(Camper c, int rating) {
		if(mates.containsKey(c)) 
			return false;
		 mates.put(c, rating);
		 if(rating != 0) {
			 ++numPrefs;
		 }
		 sumPrefs += rating;
		 return true;
	}
	
	public int getNumberPrefs() {
		return numPrefs;
	}
	public int getSumPrefs() {
		return sumPrefs;
	}
	
	int getMateRating(Camper c) {
		// no opinion is just plain 0 (though 0 may also be specified explicitly)
		if(!mates.containsKey(c))
			return 0;
		return mates.get(c);
	}
	
	public boolean isName(String name) {
		return this.name.equals(name);
	}
	
	
	public void print() {
		out(name+": numPrefs("+numPrefs+") sumPrefs("+sumPrefs+")");
		for(Map.Entry<Camper, Integer> entry: mates.entrySet()) {
			out(String.format("  %-8s %d",entry.getKey().name, entry.getValue()));
		}
	}
	public void printv() {
		vout(name+": numPrefs("+numPrefs+") sumPrefs("+sumPrefs+")");
		for(Map.Entry<Camper, Integer> entry: mates.entrySet()) {
			vout(String.format("  %-8s %d",entry.getKey().name, entry.getValue()));
		}
	}
	
	@Override
	public Object clone() {
		Camper c = new Camper(name);
		for(Map.Entry<Camper, Integer> entry : mates.entrySet()) {
			c.rateMate(entry.getKey(), entry.getValue());
		}
		return c;
	}
}
