import java.io.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class CountRelevantDocs {
	private static final int TOTAL = 110;
	private static String[] array = new String[TOTAL + 1];  // for index start from 1 to TOTAL, not 0 to TOTAL - 1
	private static final Pattern pattern = Pattern.compile("\\w+/relFor(\\d+) \\d+");
	
	public static int traverseFolders(String folder, String outputFile) {
		int totalDocs = 0;
		
		File dir = new File(folder);
		for (String file : dir.list()) {
			if (!file.matches("relFor\\d+")) {
				continue;
			}
			totalDocs += countDocs(folder + "/" + file);  // don't forget add dir/
		}
		
		writeToFile(outputFile);
		
		return totalDocs;
	}
	
	private static int countDocs(String file) {
		FileReader fr = null;
		BufferedReader br = null;
		int counter = 0;
		
		try {
			fr = new FileReader(file);
			br = new BufferedReader(fr);
			
			while (true) {
				String line = br.readLine();
				if (line == null) {
					break;
				}
				if (line.matches("<DOC>")) {
					counter++;
				}
			}
			
			String l = file + " " + counter;
			Matcher matcher = pattern.matcher(l);
			if (matcher.matches()) {
				int qryIndex = Integer.parseInt(matcher.group(1));
				array[qryIndex] = l;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
				fr.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return counter;
	}
	
	private static void writeToFile(String outputFile) {
		FileWriter fw = null;
		BufferedWriter bw = null;
		
		try {
			fw = new FileWriter(outputFile);
			bw = new BufferedWriter(fw);
			
			for (int i = 1; i <= TOTAL; i++) {
				if (i == 11 || i == 12 || i == 15 || i == 18 || i == 24 || 
					    i == 49 || i == 50 || i == 53 || i == 63 || i == 75 ||
						i == 76) {
						continue;
					}
				bw.write(array[i]);
				bw.newLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				bw.close();
				fw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
