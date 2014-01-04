import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class ArrayFormation {
	public static int readFileToArray(int[] array, int array_len, String filename) throws IOException {
		File file = new File(filename);
		BufferedReader br = new BufferedReader(new FileReader(file));
		String line = br.readLine();
		int i = 0;

		while (line != null && i<array_len) {
			array[i] = Integer.parseInt(line.trim());
			line = br.readLine();
			i++;
		}

		br.close();
		return i;
	}
}
