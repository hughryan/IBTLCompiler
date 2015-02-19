/*
 * Hugh McDonald
 * 2/16/15
 */
package parser;

import java.io.*;
import lexer.*;

public class Parser {

	private Lexer lex;
	private Token peek;
	private int tabs;
	
	public Parser(Lexer l) throws IOException {
		tabs = 0;
		lex = l;
		step();
	}
	
	void step() throws IOException {
		try {
			peek = lex.nextToken();
		} catch (EOFException e) {
			peek = new Token("$");
		} catch (IOException e) {
			throw e;
		}
	}
	
	void error(String s) {
		throw new Error("near line " + lex.line + ": " + s);
	}
	
	boolean match(String t) throws IOException {
		switch (t) {
		case "constant":
			if (peek.id.equals("intconst") || peek.id.equals("realconst") || peek.id.equals("boolconst") || peek.id.equals("stringvalue")) {
				break;
			}
			return false;
		case "identifier":
			if (peek.id.equals("identifier")) {
				break;
			}
			return false;
		case ")":
			if (peek.id.equals("keyword")) {
				Keyword temp = (Keyword)peek;
				if (temp.value.equals(")")) {
					break;
				}
			}
			return false;
		case "(":
			if (peek.id.equals("keyword")) {
				Keyword temp = (Keyword)peek;
				if (temp.value.equals("(")) {
					break;
				}
			}
			return false;
		case ":=":
			if (peek.id.equals("keyword")) {
				Keyword temp = (Keyword)peek;
				if (temp.value.equals(":=")) {
					break;
				}
			}
			return false;
		case "-":
			if (peek.id.equals("mathop")) {
				Keyword temp = (Keyword)peek;
				if (temp.value.equals("-")) {
					break;
				}
			}
			return false;
		case "binaryop":
			if (peek.id.equals("mathop") || peek.id.equals("boolop") || peek.id.equals("relop") || peek.id.equals("genop")) {
				break;
			}
			return false;
		case "unaryop":
			if (peek.id.equals("realmathop")) {
				break;
			} else if (peek.id.equals("boolop")) {
				Keyword temp = (Keyword)peek;
				if (temp.value.equals("not")) {
					break;
				}
			}
			return false;
		case "stdout":
			if (peek.id.equals("keyword")) {
				Keyword temp = (Keyword)peek;
				if (temp.value.equals("stdout")) {
					break;
				}				
			}
			return false;
		case "if":
			if (peek.id.equals("keyword")) {
				Keyword temp = (Keyword)peek;
				if (temp.value.equals("if")) {
					break;
				}				
			}
			return false;
		case "while":
			if (peek.id.equals("keyword")) {
				Keyword temp = (Keyword)peek;
				if (temp.value.equals("while")) {
					break;
				}				
			}	
			return false;
		case "let":
			if (peek.id.equals("keyword")) {
				Keyword temp = (Keyword)peek;
				if (temp.value.equals("let")) {
					break;
				}			
			}
			return false;
		case "type":
			if (peek.id.equals("type")) {
				break;
			}
			return false;
		case "$":
			if (peek.id.equals("$")) {
				break;
			}
			return false;
		}
		
		return true;
	}
	
	private void printToken() {
		for (int i = 0; i < tabs; i++) {
			System.out.printf("\t");
		}
		System.out.printf("%s\n", peek.toString());
	}
	
	private void rightParen() throws IOException {
		if (match(")")) {
			tabs--;
			printToken();
			step();
		} else {
			error("expected )");
		}
	}
	
	private void leftParen() throws IOException {
		if (match("(")) {
			printToken();
			step();
			tabs++;
		} else {
			error("expected (");
		}
	}	
	
	public void parse() throws IOException {
		start();
		if (!match("$")) {
			error("excess tokens");
		}
	}
	
	private void start() throws IOException {
		if (match("(")) {
			leftParen();
			startDoublePrime();
		} else if (match("constant")) {
			printToken();
			step();
			startPrime();
		} else if (match("identifier")) {
			printToken();
			step();
			startPrime();
		} else {
			error("unexpected token");
		}
	}
	
	private void startPrime() throws IOException {
		if (match("(") || match("constant") || match("identifier")) {
			start();
			startPrime();
		} else if (match(")") || match("$")) {
			// Epsilon
		} else {
			error("unexpected token");
		}
		
	}
	
	private void startDoublePrime() throws IOException {
		if (match(")")) {
			rightParen();
			startPrime();
		} else if (match("(") || match("constant") || match("identifier")) {
			start();
			rightParen();
			startPrime();
		} else if (match(":=") || match("binaryop") || match("unaryop") || match("stdout") || match("if") || match("while") || match("let") || match("-")) {
			expressionPrime();
			startPrime();
		} else {
			error("unexpected token");
		}
	}
	
	private void expression() throws IOException {
		if (match("(")) {
			leftParen();
			expressionPrime();
		} else if (match("constant")) {
			printToken();
			step();
		} else if (match("identifier")) {
			printToken();
			step();
		} else {
			error("unexpected token");
		}
	}

	private void expressionPrime() throws IOException {
		if (match(":=") || match("binaryop") ||match("unaryop") || match("-")) {
			operationPrime();
		} else if (match("stdout") || match("if") || match("while") || match("let")) {
			statement();
		} else {
			error("unexpected token");
		}
	}
	
	private void operation() throws IOException {
		if (match("(")) {
			leftParen();
			operationPrime();
		} else if (match("constant")) {
			printToken();
			step();
		} else if (match("identifier")) {
			printToken();
			step();
		} else {
			error("unexpected token");
		}
	}
	
	private void operationPrime() throws IOException {
		if (match(":=")) {
			printToken();
			step();
			if (match("identifier")) {
				printToken();
				step();
			} else {
				error("expected variable name");
			}
			operation();
			rightParen();
		} else if (match("binaryop")) {
			printToken();
			step();
			operation();
			operation();
			rightParen();
		} else if (match("unaryop")) {
			printToken();
			step();
			operation();
			rightParen();
		} else if (match("-")) {
			printToken();
			step();
			operation();
			operationDoublePrime();
		} else {
			error("unexpected token");
		}
	}
	
	private void operationDoublePrime() throws IOException {
		if (match("(") || match("constant") || match("identifier")) {
			operation();
			rightParen();
		} else if (match(")")) {
			rightParen();
		} else {
			error("unexpected token");
		}
	}
	
	private void statement() throws IOException {
		if (match("stdout")) {
			printStatement();
		} else if (match("while")) {
			whileStatement();
		} else if (match("if")) {
			ifStatement();
		} else if (match("let")) {
			letStatement();
		} else {
			error("unexpected token");
		}
	}
	
	private void printStatement() throws IOException {
		if (match("stdout")) {
			printToken();
			step();
			operation();
			rightParen();
		} else {
			error("unexpected token");
		}
	}
	
	private void ifStatement() throws IOException {
		if (match("if")) {
			printToken();
			step();
			expression();
			expression();
			ifStatementPrime();
		} else {
			error("unexpected token");
		}
	}
	
	private void ifStatementPrime() throws IOException {
		if (match(")")) {
			rightParen();
		} else if (match("(") || match("constant") || match("identifier")) {
			expression();
			rightParen();
		} else {
			error("unexpected token");
		}
	}
	
	private void whileStatement() throws IOException {
		if (match("while")) {
			printToken();
			step();
			expression();
			expressionList();
			rightParen();
		} else {
			error("unexpected token");
		}
	}
	
	private void expressionList() throws IOException {
		if (match("(") || match("constant") || match("identifier")) {
			expression();
			expressionListPrime();
		} else {
			error("unexpected token");
		}
	}
	
	private void expressionListPrime() throws IOException {
		if (match("(") || match("constant") || match("identifier")) {
			expressionList();
		} else if (match(")")) {
			// Epsilon
		} else {
			error("unexpected token");
		}
	}
	
	private void letStatement() throws IOException {
		if (match("let")) {
			printToken();
			step();
			leftParen();
			variableList();
			rightParen();
			rightParen();
		} else {
			error("unexpected token");
		}
	}
	
	private void variableList() throws IOException {
		if (match("(")) {
			leftParen();
			if (match("identifier")) {
				printToken();
				step();
			} else {
				error("expected identifier");
			}
			if (match("type")) {
				printToken();
				step();
			} else {
				error("expected type");
			}
			rightParen();
			variableListPrime();
		} else {
			error("unexpected token");
		}
	}
	
	private void variableListPrime() throws IOException {
		if (match("(")) {
			variableList();
		} else if (match(")")) {
			// Epsilon
		} else {
			error("unexpected token");
		}
	}
}
