package fr._42.printer.logic;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import com.diogonunes.jcolor.Attribute;
import com.diogonunes.jcolor.Ansi;

public class Transformer {
	private static final Map<String, Attribute> colorMap = new HashMap<>();
	private Attribute white = Attribute.NONE();
	private Attribute black = Attribute.NONE();
	private final String imageName = "image.bmp";
	private int width, height;

	static {
		colorMap.put("red", Attribute.RED_TEXT());
		colorMap.put("blue", Attribute.BLUE_TEXT());
		colorMap.put("green", Attribute.GREEN_TEXT());
		colorMap.put("yellow", Attribute.YELLOW_TEXT());
		colorMap.put("purple", Attribute.MAGENTA_TEXT());
		colorMap.put("cyan", Attribute.CYAN_TEXT());
		colorMap.put("white", Attribute.WHITE_TEXT());
		colorMap.put("black", Attribute.BLACK_TEXT());
	}

	public Transformer(String white, String black) {
		for (Map.Entry<String, Attribute> color : colorMap.entrySet()) {
			if (color.getKey().equals(white)) {
				this.white = color.getValue();
			}
			if (color.getKey().equals(black)) {
				this.black = color.getValue();
			}
		}
		if (this.white == null || this.black == null) {
			System.err.println("Unknown Color");
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
		ClassLoader classLoader = getClass().getClassLoader();
		URL resource = classLoader.getResource(imageName);
		if (resource == null) {
			throw new RuntimeException("Error: <" + imageName + "> not found");
		}
		File bmpFile = new File(resource.toURI());
		if (!bmpFile.exists()) {
			System.err.println("Error: <" + imageName + "> not found");
			System.exit(1);
		}
		BufferedImage image = ImageIO.read(bmpFile);
		width = image.getWidth();
		height = image.getHeight();

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				int rbg = image.getRGB(x, y);
				Color color = new Color(rbg);

				if (color.getRed() == 255 && color.getBlue() == 255 && color.getGreen() == 255) {
					System.out.println(white);
				} else {
					System.out.println(black);
				}
			}
		}
	}
}
