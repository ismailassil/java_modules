To run this program you have to run this

```bash
javac -d ImagesToChar/target -sourcepath ImagesToChar/src/java ImagesToChar/src/java/fr/_42/printer/app/Program.java ImagesToChar/src/java/fr/_42/printer/logic/Transformer.java
```

`-d` specifies where to put the class files
`-sourcepath` specifies where to find input source files

`javac` will compile the java file into the target folder with their path folder

After, to run the application

```bash
java -cp ImagesToChar/target fr._42.printer.app.Program --white=_ --black="*" --path=/Users/iassil/java_modules/java04/ex00/it.bmp
```

`-cp` --class-path will search for the class files and JAR archives and ZIP archives and run the Program specified