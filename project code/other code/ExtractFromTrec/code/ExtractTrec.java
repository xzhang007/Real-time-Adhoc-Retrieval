import java.io.*;
import java.io.File;
import java.util.List;

public class ExtractTrec {
	public static void extractTrec(String dirName, String outputFile) {
		List<String> relevantDOCNOs = GetRelevantDOCNO.getRelevantDOCNOs();
		traverseDir(dirName, relevantDOCNOs, outputFile);
	}
	
	private static void traverseDir(String dirName, List<String> list, String outputFile) {
		File dir = new File(dirName);
		
		for (String file : dir.list()) {
			if (list.isEmpty()) {  // have traversed all the relevant docs
				break;
			}
			// deal with files like 20110123-0000.dat.json.trec
			if (file.matches("\\d+-\\d+.dat.json.trec") 
				|| file.matches("\\d+-\\d+.json.trec")
				|| file.matches("\\d+-\\d+-repair.json.trec")
				|| file.matches("\\d+-qrels.json.trec")
				) {  
				getDoc(dirName + "/" + file, list, outputFile);  // don't forget to add dir and "/" 
			}
			// deal with dirs like 20110123/
		    if (file.matches("\\d+")) {
		    	traverseDir(dirName + "/" + file, list, outputFile);
		    }
		}
	}
	
	private static void getDoc(String fileName, List<String> list, String outputFile) {
		FileReader fr = null;
		BufferedReader br = null;
		FileWriter fw = null;
		BufferedWriter bw = null;
		
		try {
			fr = new FileReader(fileName);
			br = new BufferedReader(fr);
			fw = new FileWriter(outputFile, true);  // append
			bw = new BufferedWriter(fw);
			
			while (true) {
				String line = br.readLine();
				if (line == null) {
					break;
				}
				if (!line.regionMatches(0, "<DOCNO>", 0, 7)) { // important, filter other lines first
					continue;
				}
				for (int i = 0; i < list.size(); i++) {
					if (line.matches("<DOCNO>" + list.get(i) + "</DOCNO>")) {
						docStart(bw);
						bw.write(line);
						bw.newLine();
				
						while (true) {
							line = br.readLine();
							if (line.regionMatches(0, "<text>", 0, 6)) {
								bw.write(line);
								bw.newLine();
								while (true) {
									line = br.readLine();
									if (line.matches("<user>")) {
										break;
									}
									bw.write(line);
									bw.newLine();
									break;
								}
								break;
							}
						}
						docEnd(bw);
						list. remove(i);
						i--;
					} // end for
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
				fr.close();
				bw.close();
				fw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private static void docStart(BufferedWriter bw) throws IOException {
		bw.write("<DOC>");
		bw.newLine();
	}
	
	private static void docEnd(BufferedWriter bw) throws IOException {
		bw.write("</DOC>");
		bw.newLine();
	}
}
