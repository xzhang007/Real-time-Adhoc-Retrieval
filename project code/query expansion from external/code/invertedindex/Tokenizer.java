package invertedindex;

import java.util.Map;
import java.util.TreeMap;
import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

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
	private Map<String, FreqAndLists> map = null;
	private int posId;
	private int numberOfDocuments;
	private static final Pattern pattern1 = Pattern.compile("<DOCNO>(\\d+)</DOCNO>");
	
	/**
	 *  Construct a new Tokenizer
	*/
	public Tokenizer() {
	}
	
	/**
	 *  tokenize the given file
	 *  @param fileName the String describing the user's source file
	*/
	public void tokenize(String fileName) {
		sr = new SourceReader(fileName);
		map = new TreeMap<String, FreqAndLists>();
		
		while (true) {
			line = sr.readLine();
			if (line == null) {
				sr.closeReaders();
				break;
			}
			if (line.length() == 0) { // blank
				continue;
			}
			if (line.matches(REGEX1) || line.matches(REGEX3)) {  // <DOC> or </DOC>
				continue;
			}
			if (line.regionMatches(0, REGEX2, 0, 7)) {  // <DOCNO>
				Matcher matcher = pattern1.matcher(line);
				if (matcher.matches()) {
					docID = Long.parseLong(matcher.group(1));
					numberOfDocuments++;
					posId = 0;
				}
				continue;
			}
			
			// the cases that we want to split
			String [] strs = line.split("\\W+");
			strs = Modifier.modify(strs);
			
			for (String str : strs) {
				if (str.equals("") || str.equals("text")) {  // remove "text" as <text> or </text>
					continue;
				}
				posId++;
				if (map.containsKey(str)) {
					FreqAndLists value = map.get(str);
					List<PositionList> postingList = value.getPostingList();
					PositionList list = postingList.get(postingList.size() - 1);
					if (list.getDocID() == docID) { // the same word in the same docID
						list.setTf(list.getTf() + 1);
						List<Integer> positionList = list.getPositionList();
						positionList.add(posId);
						continue;
					}
					value.setDocFrequence(value.getDocFrequence() + 1);  // the same word shows in a new docID
					postingList.add(new PositionList(docID, 1, posId));
				} else {
					map.put(str, new FreqAndLists(1, new PositionList(docID, 1, posId)));
				}
			}
		}
	}
	
	public void printMap() {
		for (Map.Entry<String, FreqAndLists> entry : map.entrySet()) {
			System.out.print(entry.getKey() + " ");
			System.out.println(entry.getValue().getDocFrequence() + "     ");
			List<PositionList> postingList = entry.getValue().getPostingList();
			for (PositionList list : postingList) {
				System.out.print("		" + list.getDocID() + ", " + list.getTf() + ": ");
				System.out.println(list.getPositionList().toString());
			}
		}
	}
	
	/**
	 *  get the treemap (the inverted index)
	*/
	public Map<String, FreqAndLists> getMap() {
		return map;
	}
	
	/**
	 *  get the number of documents in the given file
	*/
	public int getNumberOfDocuments() {
		return numberOfDocuments;
	}
}