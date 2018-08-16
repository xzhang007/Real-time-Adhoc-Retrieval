import java.util.Map;
import java.util.TreeMap;
import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.io.*;
import java.io.File;
import java.util.Set;
import java.util.HashSet;

/**
*  This class is to tokenize the given file and stem them 
*  to produce the dictionary and the posting lists (stored in a TreeMap).
*/
public class Tokenizer {
	private SourceReader sr = null;
	private long docID = 0;
	private String line;
	private static final String REGEX1 = "<DOC>";
	private static final String REGEX2 = "<DOCNO>";
	private static final String REGEX3 = "</DOC>";
	private Map<String, Integer> map = null;
	private int posId;
	private int numberOfDocuments;
	private static final Pattern pattern1 = Pattern.compile("<DOCNO>(\\d+)</DOCNO>");
	//private static Set<String> mapInSameDoc = null;
	
	/**
	 *  Construct a new Tokenizer
	*/
	public Tokenizer() {
	}
	
	public int traverseTokenize(String dirName) {
		int totalInFolder = 0;
		
		File dir = new File(dirName);
		
		for (String file : dir.list()) {
			// deal with files like 20110123-0000.dat.json.trec
			if (file.matches("\\d+-\\d+.dat.json.trec") 
				|| file.matches("\\d+-\\d+.json.trec")
				|| file.matches("\\d+-\\d+-repair.json.trec")
				|| file.matches("\\d+-qrels.json.trec")
				) {  
				totalInFolder += tokenize(dirName + "/" + file);  // don't forget to add dir and "/" 
			}
			// deal with dirs like 20110123/
		    if (file.matches("\\d+")) {
		    	traverseTokenize(dirName + "/" + file);
		    }
		}
		//tokenize(dirName + "/" + "20110123" + "/" + "20110123-000.dat.json.trec");
		//tokenize(dirName + "/" + "20110128" + "/" + "20110128-063.json.trec");
		return totalInFolder;
	}
	/**
	 *  tokenize the given file
	 *  @param fileName the String describing the user's source file
	*/
	public int tokenize(String fileName) {
		int docNo = 0;
		System.out.println(fileName);
		sr = new SourceReader(fileName);
		
		while (true) {
			line = sr.readLine();
			if (line == null) {
				sr.closeReaders();
				break;
			}
			if (line.length() == 0) { // blank
				continue;
			}
			if (!line.matches("<DOC>")) {  // filter other lines
				continue;
			}
			
			if (line.matches("<DOC>")) {
				docNo++;
			}
		}
		
		return docNo;
	}
	
	/*public void printMap() {
		for (Map.Entry<String, FreqAndLists> entry : map.entrySet()) {
			System.out.print(entry.getKey() + " ");
			System.out.println(entry.getValue().getDocFrequence() + "     ");
			List<PositionList> postingList = entry.getValue().getPostingList();
			for (PositionList list : postingList) {
				System.out.print("		" + list.getDocID() + ", " + list.getTf() + ": ");
				System.out.println(list.getPositionList().toString());
			}
		}
	}*/
	
	/**
	 *  get the treemap (the inverted index)
	*/
	public Map<String, Integer> getMap() {
		return map;
	}
	
	/**
	 *  get the number of documents in the given file
	*/
	public int getNumberOfDocuments() {
		return numberOfDocuments;
	}
}