package invertedindex;

import java.io.*;
import java.util.Map;
import java.util.List;

public class BuildIndex {
	private static final int TOTAL = 110;
	
	public static void main(String[] args) {
		for (int i = 1; i <= TOTAL; i++) {
			// jump those queryNos which don't have relevant docs
			if (i == 11 || i == 12 || i == 15 || i == 18 || i == 24 || 
			    i == 49 || i == 50 || i == 53 || i == 63 || i == 75 ||
			    i == 76 || i == 30 || i == 55 || i == 58 || i == 66 ||
			    i == 80) {
				continue;
			}
			buildIndex(i);
		}
	}
	
	private static void buildIndex(int index) {
		Tokenizer tnz = new Tokenizer();
		String fileName = "rel/relFor" + index;
		String outputIndex = "10/invertedindex/invertedindexFor" + index;
		tnz.tokenize(fileName);
		//tnz.printMap();
		Map<String, FreqAndLists> map = tnz.getMap();
		
		FileWriter fw = null;
		BufferedWriter bw = null;
		
		try {
			fw = new FileWriter(outputIndex);
			bw = new BufferedWriter(fw);
			
			for (Map.Entry<String, FreqAndLists> entry : map.entrySet()) {
				String line = entry.getKey() + " | " + entry.getValue().getDocFrequence() + "		";
				bw.write(line);
				bw.newLine();
				List<PositionList> postingList = entry.getValue().getPostingList();
				for (PositionList list : postingList) {
					line = "		" + list.getDocID() + ", " + list.getTf() + ": " + list.getPositionList().toString();
					bw.write(line);
					bw.newLine();
				}
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
		//System.out.println(System.getProperty("java.library.path"));
	}
}
