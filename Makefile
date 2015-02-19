#--------------------------------------------
# Hugh McDonald (mcdonalh)
# 2-16-15
# CS 480 Makefile
# Milestone III
#--------------------------------------------
JFLAGS = -d .
JC = javac
PROFTEST = proftest.in
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

LEXER = \
	src/lexer/Token.java \
	src/lexer/Type.java \
	src/lexer/Boolconst.java \
	src/lexer/Boolop.java \
	src/lexer/Genop.java \
	src/lexer/Identifier.java \
	src/lexer/Intconst.java \
	src/lexer/Keyword.java \
	src/lexer/Mathop.java \
	src/lexer/Realconst.java \
	src/lexer/Realmathop.java \
	src/lexer/Relop.java \
	src/lexer/Stringvalue.java \
	src/lexer/Lexer.java

PARSER = src/parser/Parser.java

MAIN = src/main/Main.java

default: lexer parser main

lexer: $(LEXER:.java=.class)
parser: $(PARSER:.java=.class)
main: $(MAIN:.java=.class)

stutest.out: default
	cat stutest1.in
	-java main.Main stutest1.in > stutest1.out
	cat stutest1.out

	cat stutest2.in
	-java main.Main stutest2.in > stutest2.out
	cat stutest2.out

	cat stutest3.in
	-java main.Main stutest3.in > stutest3.out
	cat stutest3.out

	cat stutest4.in
	-java main.Main stutest4.in > stutest4.out
	cat stutest4.out

proftest.out: default
	cat $(PROFTEST)
	-java main.Main $(PROFTEST) > proftest.out
	cat proftest.out
