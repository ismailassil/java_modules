package fr._42.printer.logic;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class Transformer {
	private final char white, black;
	private final String path;
	private int width, height;
	private char[][] pixelData;

	public Transformer(char white, char black, String path) {
		this.white = white;
		this.black = black;
		this.path = path;
	}

	public void run() {
		try {
			transform();
			printArray();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

	private void transform() throws Exception {
		File bmpFile = new File(path);
		if (!bmpFile.exists()) {
			System.err.println("Error: <" + path + "> not found");
			System.exit(1);
		}
		BufferedImage image = ImageIO.read(bmpFile);
		width = image.getWidth();
		height = image.getHeight();

		pixelData = new char[height][width];

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				int rbg = image.getRGB(x, y);
				Color color = new Color(rbg);

				if (color.getRed() == 255 && color.getBlue() == 255 && color.getGreen() == 255) {
					pixelData[y][x] = white;
				} else {
					pixelData[y][x] = black;
				}
			}
		}
	}

	private void printArray() {
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				System.out.print(pixelData[i][j]);
			}
			System.out.println();
		}
	}
}
