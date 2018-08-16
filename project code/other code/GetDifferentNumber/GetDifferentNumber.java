import java.io.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class GetDifferentNumber {
	private static final int TOTAL = 110;
	private static int[] diffArray = new int[TOTAL + 1];
	private static int[] totalArray = new int[TOTAL + 1];
	
	public static void getDifferentNums(String fileName, String fileName2, String outputFile) {
		for (int i = 1; i <= TOTAL; i++) {
			if (i == 11 || i == 12 || i == 15 || i == 18 || i == 24 || 
				    i == 49 || i == 50 || i == 53 || i == 63 || i == 75 ||
					i == 76) {
					continue;
			}
			int sameRelNum = getSameRelNum(i, fileName);
			int allRel = getAllRel(i, fileName2);
			int diff = allRel - sameRelNum;
			diffArray[i] = diff;
		}
		writeToFile(outputFile);
	}
	
	private static int getSameRelNum(int qryIndex, String fileName) {
		FileReader fr = null;
		BufferedReader br = null;
		
		int counter = 0;
		try {
			fr = new FileReader(fileName);
			br = new BufferedReader(fr);
			
			while (true) {
				String line = br.readLine();
				if (line == null) {
					break;
				}
				Pattern pattern = Pattern.compile(qryIndex + " Q0 .*");
				Matcher matcher = pattern.matcher(line);
				if (matcher.matches()) {
					counter++;
				}
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
	
	private static int getAllRel(int qryIndex, String fileName) {
		FileReader fr = null;
		BufferedReader br = null;
		
		int counter = 0;
		try {
			fr = new FileReader(fileName);
			br = new BufferedReader(fr);
			
			while (true) {
				String line = br.readLine();
				if (line == null) {
					break;
				}
				Pattern pattern = Pattern.compile("rel/relFor" + qryIndex + " (\\d+)");
				Matcher matcher = pattern.matcher(line);
				if (matcher.matches()) {
					counter = Integer.parseInt(matcher.group(1));
					totalArray[qryIndex] = counter;
					return counter;
				}
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
	
	private static void writeToFile(String fileName) {
		FileWriter fw = null;
		BufferedWriter bw = null;
		
		try {
			fw = new FileWriter(fileName);
			bw = new BufferedWriter(fw);
			
			for (int i = 1; i <= TOTAL; i++) {
				if (i == 11 || i == 12 || i == 15 || i == 18 || i == 24 || 
					    i == 49 || i == 50 || i == 53 || i == 63 || i == 75 ||
						i == 76) {
						continue;
				}
				bw.write("QeryNum: " + i + " (total: " + totalArray[i] + ")" + "	" + diffArray[i]);
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
