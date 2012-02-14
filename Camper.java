import java.util.HashMap;
import java.util.Map;


public class Camper extends ConsoleApp {

	private String name;
	
	// HashMap<Friend/Enemy, Rating>...
	private HashMap<String, Integer> frienemies = new HashMap<String, Integer>();
	
	Camper(String name) {
		this.name = name;
	}
	
	boolean addFrienemy(String c, int rating) {
		if(frienemies.containsKey(c)) 
			return false;
		 frienemies.put(c, rating);
		 return true;
	}
	
	int getFrienemyRating(Camper c) {
		// no opinion is just plain 0 (though 0 may also be specified explicitly)
		if(!frienemies.containsKey(c.name))
			return 0;
		return frienemies.get(c.name);
	}
	
	@Override
	public boolean equals(Object o) {
		Camper c = (Camper) o;
		return name.equals(c.name);
	}
	
	public boolean isName(String name) {
		return this.name.equals(name);
	}
	
	@Override
	public int hashCode() {
		return name.hashCode();
	}
	
	public void print() {
		out(name+": ");
		for(Map.Entry<String, Integer> entry: frienemies.entrySet()) {
			out(String.format("  %-8s %d",entry.getKey(), entry.getValue()));
		}
	}
}
