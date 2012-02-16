

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class ConsoleApp {
	protected BufferedReader open(String file) {
		try {
			return new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException e) {
			out("ERROR: couldn't find file: "+file);
			return null;
		}
	}
	
	/*
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
	*/
	
	protected int [] reverse(int [] array) {
		int [] newArray = new int[array.length];
		for(int i = 0, j = array.length - 1; i < array.length; ++i, --j) {
			newArray[i] = array[j];
		}
		return newArray;
	}
	
	protected void out(Object o) {
		System.out.println(o);
		System.out.flush();
	}
	protected void out() {
		System.out.println();
		System.out.flush();
	}
	
	protected void err(String msg) {
		System.err.println(msg);
		System.err.flush();
	}
	
	protected void err() {
		System.err.println();
		System.err.flush();
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
