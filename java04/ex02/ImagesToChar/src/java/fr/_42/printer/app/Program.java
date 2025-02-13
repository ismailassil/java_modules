package fr._42.printer.app;

import fr._42.printer.logic.CommandLine;
import fr._42.printer.logic.Transformer;
import com.beust.jcommander.JCommander;

public class Program {
    public static void main(String[] args) {
        CommandLine program = new CommandLine();

        try {
            JCommander.newBuilder()
                    .addObject(program)
                    .build()
                    .parse(args);

            Transformer transformObj = new Transformer(program.getWhite(), program.getBlack());
            transformObj.run();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}