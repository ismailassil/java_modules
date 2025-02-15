import java.io.BufferedReader;
import java.io.FileReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.SortedSet;
import java.util.TreeSet;

public class Dictionary {
	private String[] line1, line2;
	private double similarity = 0.0;
	private final SortedSet<String> dic = new TreeSet<>();
	private final ArrayList<Integer> aVector = new ArrayList<>();
	private final ArrayList<Integer> bVector = new ArrayList<>();

	public void run() {
		setDictionnary();
		setOccurency();
		calculateSimilarity();

		BigDecimal bd = new BigDecimal(similarity);
		bd = bd.setScale(2, RoundingMode.FLOOR);

		System.out.printf("Similarity = %.2f\n", bd.doubleValue());
	}

	public void readTexts(String[] args) throws Exception {
		if (args.length != 2 || args[0].isEmpty() || args[1].isEmpty()) {
			throw new RuntimeException("Format Invalid - java Program <file> <file>");
		}
		Path file1 = Paths.get(args[0]);
		Path file2 = Paths.get(args[1]);

		if (Files.readAttributes(file1, BasicFileAttributes.class).size() > 10000
				|| Files.readAttributes(file2, BasicFileAttributes.class).size() > 10000) {
			throw new RuntimeException("File size too big");
		}

		try (FileReader fileReader1 = new FileReader(args[0]);
				FileReader fileReader2 = new FileReader(args[1]);
				BufferedReader br1 = new BufferedReader(fileReader1);
				BufferedReader br2 = new BufferedReader(fileReader2)) {

			String line_1 = br1.lines().reduce("", (a, b) -> a + b).trim();
			String line_2 = br2.lines().reduce("", (a, b) -> a + b).trim();
			if (line_1 == null || line_1.isEmpty() || line_2 == null || line_2.isEmpty()) {
				throw new RuntimeException("Empty file or white-spaces-only");
			}

			line1 = line_1.trim().split("\\s+");
			line2 = line_2.trim().split("\\s+");

			if (line1.length == 0 || line2.length == 0) {
				throw new RuntimeException("Empty file");
			}
		}
	}

	private void setDictionnary() {
		dic.addAll(Arrays.asList(line1));
		dic.addAll(Arrays.asList(line2));
	}

	private void setOccurency() {
		aVector.ensureCapacity(dic.size());
		bVector.ensureCapacity(dic.size());

		for (String cha : dic) {
			int aVec = 0, bVec = 0;
			for (String elem : line1) {
				if (cha.equals(elem)) {
					aVec++;
				}
			}
			for (String elem : line2) {
				if (cha.equals(elem)) {
					bVec++;
				}
			}
			aVector.add(aVec);
			bVector.add(bVec);
		}
	}

	private void calculateSimilarity() {
		double dotProduct = computeDotProduct();
		double magnitude = computeMagnitudes();

		similarity = dotProduct / magnitude;
	}

	private double computeDotProduct() {
		double sum = 0.0;
		for (int i = 0; i < aVector.size() && i < bVector.size(); i++) {
			sum += aVector.get(i) * bVector.get(i);
		}
		return sum;
	}

	private double computeMagnitudes() {
		double aMagnitude = 0.0;
		double bMagnitude = 0.0;
		for (int i = 0; i < aVector.size() && i < bVector.size(); i++) {
			aMagnitude += Math.pow(aVector.get(i), 2);
			bMagnitude += Math.pow(bVector.get(i), 2);
		}

		return Math.sqrt(aMagnitude) * Math.sqrt(bMagnitude);
	}
}
