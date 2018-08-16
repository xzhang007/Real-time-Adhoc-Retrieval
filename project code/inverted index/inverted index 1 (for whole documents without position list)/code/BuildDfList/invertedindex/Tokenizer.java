package invertedindex;

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
	
	public void traverseTokenize(String dirName) {
		map = new TreeMap<String, Integer>();
		
		File dir = new File(dirName);
		
		for (String file : dir.list()) {
			// deal with files like 20110123-0000.dat.json.trec
			if (file.matches("\\d+-\\d+.dat.json.trec") 
				|| file.matches("\\d+-\\d+.json.trec")
				|| file.matches("\\d+-\\d+-repair.json.trec")
				|| file.matches("\\d+-qrels.json.trec")
				) {  
				tokenize(dirName + "/" + file);  // don't forget to add dir and "/" 
			}
			// deal with dirs like 20110123/
		    if (file.matches("\\d+")) {
		    	traverseTokenize(dirName + "/" + file);
		    }
		}
		//tokenize(dirName + "/" + "20110123" + "/" + "20110123-000.dat.json.trec");
		//tokenize(dirName + "/" + "20110128" + "/" + "20110128-063.json.trec");
	}
	/**
	 *  tokenize the given file
	 *  @param fileName the String describing the user's source file
	*/
	public void tokenize(String fileName) {
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
			if (!line.regionMatches(0, "<text>", 0, 6)) {  // filter other lines
				continue;
			}
			
			if (!line.matches("<text>.*</text>")) {
				line += " " + sr.readLine();
			}
			// the cases that we want to split
			Set<String> mapInSameDoc = new HashSet<>();
			String [] strs = line.split("\\W+");
			strs = Modifier.modify(strs);
			
			for (String str : strs) {
				if (str.equals("") || str.equals("text")) {  // remove "text" as <text> or </text>
					continue;
				}
				mapInSameDoc.add(str);  // ensure one term just add once in the doc freq
			}
			
			for (String str : mapInSameDoc) {
				//if (line.equals("<text>@freirejuanma Olé olé. A ver si @lindamirada se anima y pone más tweets que Kanye West.</text>")) {
					//System.out.println(str);
				//}
				if (map.containsKey(str)) {
					Integer oldDf = map.get(str);
					map.put(str, oldDf+1);
				} else {
					map.put(str, 1);
				}
			}
		}
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