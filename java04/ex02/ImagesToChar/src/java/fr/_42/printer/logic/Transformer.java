package fr._42.printer.logic;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import java.util.HashMap;
import java.util.Map;

import com.diogonunes.jcolor.Attribute;
import com.diogonunes.jcolor.Ansi;
import java.security.KeyStore;
import javax.print.AttributeException;

public class Transformer {
	private static final Map<String, Attribute> colorMap = new HashMap<>();
	private Attribute white = Attribute.NONE();
	private Attribute black = Attribute.NONE();
	private final String imageName = "image.bmp";
	private int width, height;

	static {
		colorMap.put("black", Attribute.BLACK_TEXT());
		colorMap.put("red", Attribute.RED_TEXT());
		colorMap.put("green", Attribute.GREEN_TEXT());
		colorMap.put("yellow", Attribute.YELLOW_TEXT());
		colorMap.put("blue", Attribute.BLUE_TEXT());
		colorMap.put("purple", Attribute.MAGENTA_TEXT());
		colorMap.put("cyan", Attribute.CYAN_TEXT());
		colorMap.put("white", Attribute.WHITE_TEXT());

		colorMap.put("bright_black", Attribute.BRIGHT_BLACK_TEXT());
		colorMap.put("bright_red", Attribute.BRIGHT_RED_TEXT());
		colorMap.put("bright_green", Attribute.BRIGHT_GREEN_TEXT());
		colorMap.put("bright_yellow", Attribute.BRIGHT_YELLOW_TEXT());
		colorMap.put("bright_blue", Attribute.BRIGHT_BLUE_TEXT());
		colorMap.put("bright_purple", Attribute.BRIGHT_MAGENTA_TEXT());
		colorMap.put("bright_cyan", Attribute.BRIGHT_CYAN_TEXT());
		colorMap.put("bright_white", Attribute.BRIGHT_WHITE_TEXT());
	}

	public Transformer(String white, String black) {
		this.white = colorMap.get(white.toLowerCase());
		this.black = colorMap.get(black.toLowerCase());

		if (this.white == null || this.black == null) {
			System.err.println("Error: Unknown Colors");
			System.exit(1);
		}
	}

	public void run() {
		try {
			transform();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

	private void transform() throws Exception {
		File bmpFile = new File("ImagesToChar/target/resources/" + imageName);
		if (!bmpFile.exists()) {
			throw new RuntimeException("Error: <" + imageName + "> not found");
		}

		BufferedImage image = ImageIO.read(bmpFile);
		width = image.getWidth();
		height = image.getHeight();

		System.out.println();
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				int rgb = image.getRGB(x, y);
				Color color = new Color(rgb);

				if (color.getRed() + color.getGreen() + color.getBlue() > 600) {
					System.out.print(Ansi.colorize("██", white));
				} else {
					System.out.print(Ansi.colorize("██", black));
				}
			}
			System.out.println();
		}
	}
}
