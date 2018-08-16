import invertedindex.*;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;
import java.io.*;

public class QueryExpansion {
	private Map<String, FreqAndLists> map = null;
	private ScoringFunction sf;
	private Map<Double, List<String>> scoreMap = null;
	private Set<String> originalTerms = new HashSet<>();
	private static final String DFDIR = "DocFreq";
	private static Map<String, Integer> dfMap = null;
	private int qryId;
	
	/**
	 *  Construct a new QueryExpansion
	 *  @param map is the inverted index
	 *  @param sf is the ScoringFunction reference
	 *  @param dr is the relevant docID
	*/
	QueryExpansion(Map<String, FreqAndLists> map, ScoringFunction sf, int qryId) {
		this.map = map;
		this.sf = sf;
		scoreMap = new HashMap<Double, List<String>>();
		this.qryId = qryId;
	}
	
	/**
	 *  to get the expanded query
	 *  @param parameterX is the top X parameter
	 *  @param originalQuery is the original query
	 *  @return the expanded query
	*/
	String getExpandedQuery(int parameterX, String originalQuery) {
		getOriginalTerms(originalQuery);
		String [] terms = getTopX(parameterX, originalQuery);
		StringBuffer strBuffer = new StringBuffer(originalQuery);
		for (String phrase : terms) {
			strBuffer.append(" " + phrase);
		}
		return strBuffer.toString();
	}
	
	/**
	 *  to get the top X terms array
	 *  @param parameterX is the top X parameter
	 *  @param originalQuery is the original query
	 *  @return the top X terms array
	*/
	private String [] getTopX(int parameterX, String originalQuery) {
		getScoreMap();
		/*for (Map.Entry<Double, List<String>> entry : scoreMap.entrySet()) {
			System.out.println(entry.getKey());
			List<String> list = entry.getValue();
			for (String str : list) {
				System.out.println("		" + str);
			}
		}*/
		Double [] scores = scoreMap.keySet().toArray(new Double[1]);
		Arrays.sort(scores, new DoubleComparator());  // in descending order
		
		StringBuffer strBuffer = new StringBuffer();
		int numberOfTops = 1;
		for (Double score : scores) {
			if (numberOfTops > parameterX) {
				break;
			}
			List<String> list = scoreMap.get(score);
			for (String term : list) {
				if (numberOfTops > parameterX) {
					break;
				}
				if (term.equals(originalQuery)) {  // the top X terms shall not include the original query
					continue;
				}
				strBuffer.append(term + " ");
				numberOfTops++;
			}
		}
		
		return strBuffer.toString().split("\\s");
	}
	
	/**
	 *  to get the score map (which is the scores of all the terms in dr)
	 *  Map.Entry: <score, list of terms in dr>
	 *  @return the score map (which is the scores of all the terms in dr)
	*/
	private Map<Double, List<String>> getScoreMap() {
		dfMap = new HashMap<String, Integer>();
		
		for (Map.Entry<String, FreqAndLists> entry: map.entrySet()) {
			String term = entry.getKey();
			if (originalTerms.contains(term)) {  // expanded terms should not contain original terms
				continue;
			}
			double totalScore = 0;
			List<PositionList> postingList = entry.getValue().getPostingList();
			for (PositionList positionList : postingList) {
				long dr = positionList.getDocID();
				int rawDf = GetRawDf.traverseDfs(DFDIR, term);
				dfMap.put(term, rawDf);
				totalScore += sf.getScore(term, dr, rawDf);
			}
			System.out.println(term + " " + totalScore);
			if (scoreMap.containsKey(totalScore)) {
				scoreMap.get(totalScore).add(term);
			} else {
				List<String> list = new ArrayList<>();
				list.add(term);
				scoreMap.put(totalScore, list);
			}
		}
		
		/*for (Map.Entry<Double, List<String>> entry : scoreMap.entrySet()) {
			System.out.println("000 " + entry.getKey());
			List<String> list = entry.getValue();
			for (String term : list) {
				System.out.println("        " + term);
			}
		}*/
		
		writeToFile();
		return scoreMap;
	}
	
	private Set<String> getOriginalTerms(String originalQuery) {
		String[] array = originalQuery.split("\\W");
		for (String str : array) {
			str = Modifier.modify(str);
			originalTerms.add(str);
		}
		return originalTerms;
	}
	
	private void writeToFile() {
		FileWriter fw = null;
		BufferedWriter bw = null;
		
		try {
			fw = new FileWriter("relMap/df" + qryId);
			bw = new BufferedWriter(fw);
			
			for (Map.Entry<String, Integer> entry : dfMap.entrySet()) {
				bw.write(entry.getKey() + " " + entry.getValue());
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