import java.util.ArrayList;
import java.util.HashSet;


public class Tent {

	private int capacity = 0;
	private int camperCount = 0;
	private HashSet<Camper> campers = new HashSet<Camper>();
	
	Tent(int capacity) {
		this.capacity = capacity;
	}
	
	public boolean addCamper(Camper c) {
		if(camperCount == capacity)
			return false;
		return campers.add(c);
	}
	
	public boolean removeCamper(Camper c) {
		if(camperCount <= 0)
			return false;
		boolean rt = campers.remove(c);
		if(rt == true) 
			--camperCount;
		return rt;
			
	}
}
