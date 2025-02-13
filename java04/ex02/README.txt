To run this program you have to run this
```bash
javac -d ImagesToChar/target/ -sourcepath ImagesToChar/src/java -cp "ImagesToChar/lib/JCDP-4.0.2.jar:ImagesToChar/lib/JColor-5.5.1.jar:ImagesToChar/lib/jcommander-1.82.jar" ImagesToChar/src/java/fr/_42/printer/app/Program.java ImagesToChar/src/java/fr/_42/printer/logic/Transformer.java ImagesToChar/src/java/fr/_42/printer/logic/CommandLine.java
```

`-d` specifies where to put the class files
`-sourcepath` specifies where to find input source files

`javac` will compile the java file into the target folder with their path folder

Then, copies the resources folder into the target folder

```bash
cp -r ImagesToChar/src/resources ImagesToChar/target
unzip -o ImagesToChar/lib/jcommander-1.82.jar -d ImagesToChar/target
unzip -o ImagesToChar/lib/JCDP-4.0.2.jar -d ImagesToChar/target
unzip -o ImagesToChar/lib/JColor-5.5.1.jar -d ImagesToChar/target
```

```bash
jar cfm ImagesToChar/target/images-to-chars-printer.jar ImagesToChar/src/manifest.txt -C ImagesToChar/target/ . -C ImagesToChar/target/ .
```

`cfm`	- `c` means to create the jar file
		- `f` means to specify the name of the jar file
		- `m` means to include the manifest file `manifest.txt`

After, to run the application

```bash
java -jar ImagesToChar/target/images-to-chars-printer.jar --white=RED --black=BLUE
```

`-jar` specifies a jar file
