default: build

build:
	javac -g *.java

run:
	java Main

clean:
	-rm *.class