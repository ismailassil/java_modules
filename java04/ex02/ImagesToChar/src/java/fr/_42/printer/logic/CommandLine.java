package fr._42.printer.logic;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

@Parameters(separators = "=")
public class CommandLine {
    @Parameter(names = "--white", description = "White color", required = true)
    private String white;

    @Parameter(names = "--black", description = "Black color", required = true)
    private String black;

    public String getWhite() {
        return white.toLowerCase();
    }

    public String getBlack() {
        return black.toLowerCase();
    }
}