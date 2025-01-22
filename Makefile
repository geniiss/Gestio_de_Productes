SRC_DIR = FONT
BIN_DIR = EXE
LIB_DIR = FONT/lib
JARS = $(LIB_DIR)/junit-4.13.2.jar:$(LIB_DIR)/mockito-core-4.8.1.jar:$(LIB_DIR)/hamcrest-core-1.3.jar:$(LIB_DIR)/byte-buddy-1.12.22.jar:$(LIB_DIR)/byte-buddy-agent-1.12.22.jar:$(LIB_DIR)/objenesis-3.2.jar

# Troba tots els fitxers .java al directori de codi font
SOURCES = $(shell find $(SRC_DIR) -name "*.java")

# Genera una llista de fitxers .class a partir dels .java
CLASSES = $(SOURCES:$(SRC_DIR)/%.java=$(BIN_DIR)/%.class)

# Defineix la comanda per compilar amb el classpath de les llibreries
JAVAC = javac -cp $(JARS)
MAIN_CLASS = Main

# Regla per compilar tots els fitxers .java i generar documentació
all: $(CLASSES) doc

# Regla per compilar cada fitxer .java al seu corresponent .class
$(BIN_DIR)/%.class: $(SRC_DIR)/%.java
	mkdir -p $(dir $@)
	$(JAVAC) -d $(BIN_DIR) -sourcepath $(SRC_DIR) $<

# Regla per executar el programa
run: $(CLASSES)
	java -cp $(BIN_DIR):$(JARS) $(MAIN_CLASS)

# Regla per executar el driver dels algoritmes
driver: $(BIN_DIR)/drivers/DriverAlgoritmo.class
	java -cp $(BIN_DIR):$(JARS) drivers.DriverAlgoritmo

# Regla per esborrar fitxers compilats
clean:
	rm -rf $(BIN_DIR)/*
	rm -rf DOC/javadoc

# Regla per executar els tests individualment
testSupermercat: $(BIN_DIR)/tests/Runner.class $(BIN_DIR)/tests/TestSupermercat.class
	java -cp $(BIN_DIR):$(JARS) tests.Runner tests.TestSupermercat

testPrim: $(BIN_DIR)/tests/Runner.class $(BIN_DIR)/tests/TestPrim.class
	java -cp $(BIN_DIR):$(JARS) tests.Runner tests.TestPrim

testGreedy: $(BIN_DIR)/tests/Runner.class $(BIN_DIR)/tests/TestGreedy.class
	java -cp $(BIN_DIR):$(JARS) tests.Runner tests.TestGreedy

testTSP: $(BIN_DIR)/tests/Runner.class $(BIN_DIR)/tests/TestTSP.class
	java -cp $(BIN_DIR):$(JARS) tests.Runner tests.TestTSP

testDFS: $(BIN_DIR)/tests/Runner.class $(BIN_DIR)/tests/TestDFS.class
	java -cp $(BIN_DIR):$(JARS) tests.Runner tests.TestDFS

testComputeCost: $(BIN_DIR)/tests/Runner.class $(BIN_DIR)/tests/TestComputeCost.class
	java -cp $(BIN_DIR):$(JARS) tests.Runner tests.TestComputeCost

testBruteForce: $(BIN_DIR)/tests/Runner.class $(BIN_DIR)/tests/TestBruteForce.class
	java -cp $(BIN_DIR):$(JARS) tests.Runner tests.TestBruteForce

testBruteForce2: $(BIN_DIR)/tests/Runner.class $(BIN_DIR)/tests/TestBruteForce2.class
	java -cp $(BIN_DIR):$(JARS) tests.Runner tests.TestBruteForce2

# Regla per executar tots els tests (menys el BruteForce, que triga molt)
tests: $(CLASSES)
	java -cp $(BIN_DIR):$(JARS) tests.Runner tests.TestSupermercat tests.TestPrim tests.TestGreedy tests.TestTSP tests.TestDFS tests.TestComputeCost tests.TestBruteForce2

# Regla per generar documentació
doc:
	javadoc -d DOC/javadoc -sourcepath FONT -classpath FONT/lib/junit-4.13.2.jar -subpackages algoritmo controladores dades drivers interfaces structures views tests

CntrlView: $(BIN_DIR)/controladores/CntrlView.class
	java -cp $(BIN_DIR):$(JARS) controladores.CntrlView
