To run this program you have to run this
```bash
javac -d ImagesToChar/target/ -sourcepath ImagesToChar/src/java ImagesToChar/src/java/fr/_42/printer/app/Program.java ImagesToChar/src/java/fr/_42/printer/logic/Transformer.java
```

`-d` specifies where to put the class files
`-sourcepath` specifies where to find input source files

`javac` will compile the java file into the target folder with their path folder

Then, copies the resources folder into the target folder

```bash
cp -r ImagesToChar/src/resources ImagesToChar/target
```

```bash
jar cfm ImagesToChar/target/images-to-chars-printer.jar ImagesToChar/src/manifest.txt -C ImagesToChar/target/ .
```

`cfm`	- `c` means to create the jar file
		- `f` means to specify the name of the jar file
		- `m` means to include the manifest file `manifest.txt`

After, to run the application

```bash
java -jar ImagesToChar/target/images-to-chars-printer.jar --white=_ --black="*"
```

`-jar` specifies a jar file


##################
The main purpose of the 'MANIFEST FILE` is to specify the entry point of the Java Program