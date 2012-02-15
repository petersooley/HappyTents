import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;


public class MakeHappy extends ConsoleApp{

	private ArrayList<Tent> tents = new ArrayList<Tent>();
	private ArrayList<Camper> campers = new ArrayList<Camper>();
	
	// defined in handleArgs()
	private Random rand;

	private void setupTents(String tentListFile) {
		/*
		if(small2big) {
			tents.add(new Tent(2));
			tents.add(new Tent(3));
			tents.add(new Tent(3));
			tents.add(new Tent(4));
			tents.add(new Tent(4));
		}
		else {
			tents.add(new Tent(4));
			tents.add(new Tent(4));
			tents.add(new Tent(3));
			tents.add(new Tent(3));
			tents.add(new Tent(2));
		}*/
	}
	
	private void setupCampers(String prefTableFile)  {
		/*
		// 2. Set up the campers and their preferences	
		BufferedReader in = open("prefTableFile.txt");
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
			*/
	}
	
	public boolean search() {
		
		return false;
	}

	public static void main(String[] args) throws IOException {
		MakeHappy mh = new MakeHappy();
		
		mh.handleArgs(args);

		mh.search();
	}
	
	public void usage() {
		usage(null);
	}
	public void usage(String errorMsg) {
		if(errorMsg != null) {
			out();
			out("ERROR: "+errorMsg);
			out();
		}

		out("MakeHappy [prefTableFile] [tentListTable]");
		out();
		out("This program attempts to solve the problem defined here: http://drdobbs.com/184410645.");
		out("Default preference table: \"prefTable.txt\".");
		out("Default tent list table: \"tentList.txt\".");
		out("Default ordering of tents and campers is the order in the files.");
		out();
		out("NOTE: It doesn't matter which order you specify the instance data files. We'll figure it out!");
		out();
		out("Optimizations:");
		out("  -t		order tents smallest to biggest");
		out("  -T		order tents biggest to smallest");
		out("  -c		order campers by preference sums lowest to highest");
		out("  -C		order campers by preference sums highest to lowest");
		out("  -c0		order campers by least picky to most picky");
		out("  -C0		order campers by most picky to least picky");
		out("  -r [seed]	shuffle the tents and campers");
		out();
		System.exit(1);
	}
	
	public void handleArgs(String args[]) throws IOException {
		
		// Defaults:
		long seed = System.currentTimeMillis();
		String prefTableFile = "prefTableFile.txt";
		String tentListFile = "tentList.txt";
		
		for(int i = 0; i < args.length; ++i) {
			String arg = args[i];
			int len = arg.length() - 1;
			out("arg: "+arg);
			
			// filter options
			if(arg.charAt(0) == '-') {
				for(int j = 1; j <= len; ++j) {
					switch(arg.charAt(j)) {
					case 't':
						out("Sorting tents smallest to biggest...");
						break;
					case 'T':
						out("Sorting tents biggest to smallest...");
						break;
					case 'c':
						if(arg.charAt(j + 1) == '0') {
							out("Sorting campers by zeros low to high...");
							++j;
						}
						else {
							out("Sorting campers by prefs low to high...");
						}
						break;
					case 'C':
						if(arg.charAt(j + 1) == '0') {
							out("Sorting campers by zeros high to low...");
							++j;
						}
						else {
							out("Sorting campers by prefs high to low...");
						}
						break;
					case 'r':
							out("Shuffling tents and campers...");
							if(i + 1 < args.length && args[i + 1].charAt(0) != '-' && args[i + 1].matches("[0-9]+")) {
								try {
									seed = Long.parseLong(args[++i]);
								}catch(NumberFormatException e) {
									usage("What kind of seed is that???");
								}
							}
						break;
					case 'h':
							usage();
						break;
					default:
							usage("Weird argument. Just too weird.");
					}
				}
			}	
			
			// Filter files
			BufferedReader in = open(arg);
			String str;
			while((str = in.readLine()) != null) {
				if(str.matches("[a-zA-Z]+\\s*[a-zA-Z]+\\s*[-0-9]+")){
					prefTableFile = arg;
					break;
				}
				if(str.matches("\\s*\\d+\\s*")) {
					tentListFile = arg;
					break;
				}
			}
			
		}

		out("Seed: "+seed);
		rand = new Random(seed);
		out("Tent list file: "+tentListFile);
		setupTents(tentListFile);
		out("Preference table file: "+prefTableFile);
		setupCampers(prefTableFile);
		
	}

}
