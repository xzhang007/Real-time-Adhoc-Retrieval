package invertedindex;

import java.io.*;
import java.util.Map;
import java.util.List;

public class BuildIndex {
	
	public static void main(String[] args) {
		String dir = "microblogTrack2011/20110201";
		String outputFile = "DocFreq20110201";
		buildIndex(dir, outputFile);
	}
	
	private static void buildIndex(String dir, String outputFile) {
		Tokenizer tnz = new Tokenizer();
		tnz.traverseTokenize(dir);
		//tnz.printMap();
		Map<String, Integer> map = tnz.getMap();
		
		FileWriter fw = null;
		BufferedWriter bw = null;
		
		try {
			fw = new FileWriter(outputFile);
			bw = new BufferedWriter(fw);
			
			for (Map.Entry<String, Integer> entry : map.entrySet()) {
				String line = entry.getKey() + "		" + entry.getValue();
				bw.write(line);
				bw.newLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				bw.close();
				fw.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
}
