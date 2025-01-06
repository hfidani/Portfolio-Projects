# Directories
SRC_DIR = src
CLASSES_DIR = classes
TEST_DIR = test
JUNIT_JAR = junit-console.jar

# Java compiler
JC = javac

# Compilation options
JFLAGS = -d $(CLASSES_DIR)

# Get the list of sources dynamically
SOURCES = $(shell find $(SRC_DIR) -name "*.java")
TESTS = $(shell find $(TEST_DIR) -name "*.java")

# Main class name
MAIN = vlille.Main

# Default target
all: run

# Compile classes
classes: $(SOURCES)
	$(JC) $(JFLAGS) $(SOURCES)

# Compile tests
test-classes: classes $(TESTS)
	$(JC) -classpath $(JUNIT_JAR):$(CLASSES_DIR) $(TESTS)

# Generate Javadoc
javadoc:
	javadoc -cp $(CLASSES_DIR) $(SRC_DIR)/vlille/*.java -d docs

# Run the application
run: classes
	java -cp $(CLASSES_DIR) $(MAIN)

# Run tests
test: test-classes
	java -jar $(JUNIT_JAR) -classpath $(TEST_DIR):$(CLASSES_DIR) -scan-classpath

# Clean compiled files
clear:
	rm -rf $(CLASSES_DIR)/**/*.class

# Create JAR file
jar: classes
	jar cvfe Main.jar $(MAIN) -C $(CLASSES_DIR) .

.PHONY: all run test clear jar

