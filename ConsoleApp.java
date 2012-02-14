

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class ConsoleApp {
	protected BufferedReader open(String file) {
		try {
			return new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			out("ERROR: couldn't find "+file);
			return null;
		}
	}
	
	protected static void out(String msg) {
		System.out.println(msg);
	}
	protected void out(String msg, boolean newline) {
		if(newline)
			System.out.println(msg);
		else
			System.out.print(msg);
	}
	protected void out(Boolean b) {
		System.out.println(b);
	}
	protected void out(Integer msg) {
		System.out.println(String.valueOf(msg));
	}
	protected void out(String [] msgs) {
		System.out.println(msgs);
	}
	protected void out(int [] msgs) {
		for(int i = 0; i < msgs.length; ++i)
			System.out.print(String.valueOf(msgs[i])+" ");
		System.out.println();
	}
	
	protected void out(char [] msgs) {
		for(int i = 0; i < msgs.length; ++i) 
			System.out.print(String.valueOf(msgs[i]+" "));
		System.out.println();
	}
	protected void out() {
		System.out.println();
	}
	
	protected void err(String msg) {
		System.err.println(msg);
	}
	
	protected void vital(boolean expression) {
		if(!expression) {
			int line = Thread.currentThread().getStackTrace()[2].getLineNumber();
			String file = Thread.currentThread().getStackTrace()[2].getFileName();
			err("["+file+":"+line+"] assertion error");
			throw new AssertionError();
		}
	}
}
