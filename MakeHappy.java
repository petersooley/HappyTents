import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;


public class MakeHappy extends ConsoleApp{

	private HashSet<Tent> tents = new HashSet<Tent>();
	private HashSet<Camper> campers = new HashSet<Camper>();

	MakeHappy() throws IOException{
		// 1. Set up the tents according to the problem instance.
		tents.add(new Tent(2));
		tents.add(new Tent(3));
		tents.add(new Tent(3));
		tents.add(new Tent(4));
		tents.add(new Tent(4));
		
		// 2. Set up the campers and their preferences	
		BufferedReader in = open("instents.txt");
		String str;
		String [] fields = new String[3];
		String current = "";
		Camper c = null;
		while((str = in.readLine()) != null) {
			// fields: "camper", "friend", "rating of friend"
			fields = str.split("\\s+", 3);
			
			// Have we hit a new camper that we need to create?
			if(!current.equals(fields[0])) {
				// If this isn't the first camper we're creating, then
				// save the current one to our list before continuing
				if(!current.equals(""))
					campers.add(c);
				c = new Camper(fields[0]);
				current = fields[0];
			}
			
			// For each line, we are adding frienemies
			c.addFrienemy(fields[1], Integer.valueOf(fields[2]));
		}
		campers.add(c);
		
		for(Camper camper : campers) 
			camper.print();
		
		
	}
	
	
	public boolean search() {
		
		return false;
	}

	public static void main(String[] args) throws IOException {
		MakeHappy mh = new MakeHappy();
		mh.search();
	}

}
