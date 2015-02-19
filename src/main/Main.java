/*
 * Hugh McDonald
 * 2/16/15
 */
package main;

import java.io.*;
import lexer.*;
import parser.*;

public class Main {

	public static void main(String[] args) throws IOException {
		String file = "";
		try {
			file = args[0];
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.printf("Error: Must provide a filename.\n");
			return;
		}
		Lexer lex = new Lexer(file);
		Parser par = new Parser(lex);
		
		try {
			par.parse();
		} catch (EOFException e) {
			System.out.printf("EOF ERROR\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		/* LEXER DEBUG
		Token t = new Token();
		try {
			t = lex.nextToken();
			while (t != null) {
				System.out.printf("%s\n", t.toString());
				t = lex.nextToken();
			}
		} catch (EOFException e) {
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		*/
	}
	
}
