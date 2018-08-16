import java.io.*;
import invertedindex.*;
import java.util.Map;

public class Test3 {
	private static final int TOTAL = 110;
	private static String[] originalQueries;
	private static String[] newQueries = new String[TOTAL + 1];
	private static final int EXPANDEDNUM = 10;
	private static final int MAXEXPANDED = 20;
	private static final int MAXEXPANDEDFROMEXTERNAL = 5;
	
	public static void main(String[] args) {
		String queryFile = "queries.txt";
		originalQueries = GetQueries.getQueries(queryFile);
		for (int i = 1; i <= TOTAL; i++) {
		//for (int i = 1; i <= 2; i++) {
			// jump those queryNos which don't have relevant docs
			if (i == 11 || i == 12 || i == 15 || i == 18 || i == 24 || 
			    i == 49 || i == 50 || i == 53 || i == 63 || i == 75 ||
				i == 76 || i == 30 || i == 55 || i == 58 || i == 66 ||
				i == 80) {
				newQueries[i] = originalQueries[i];
				continue;
			}
		//int i = 16;
			//if ((i >= 1 && i <= 9)|| i == 16) {
			if (i != 47 && i != 53 && i != 69 && i != 70 && i != 72 &&
				i != 76 && i != 87 && i != 108) {
			doForEachQuery(i);
			}
		}

		//String newQueryFile = "indri1000Proximity/expandedQueries.txt";
		//String newQueryFile = "indri200/newQueries200.txt";
		String newQueryFile = "addedTermsFromExternal.txt";
		writeNewQueries(newQueryFile);
	}
	
	private static void doForEachQuery(int qryId) {
		// tokenize the given file and get the inverted index (map) and the number of documents
		System.out.println("Processing for Qery: " + qryId);
		//String fileName = "indri1000Proximity/rel1000/relFor" + qryId;
		String fileName = "external/" + qryId;
		Tokenizer tnz = new Tokenizer();
		tnz.tokenize(fileName);
		Map<String, FreqAndLists> map = tnz.getMap();
		int numberOfDocuments = tnz.getNumberOfDocuments();
		
		// computer the score, rank and expand the query and rank again
		ScoringFunction sf = new ScoringFunction(map, numberOfDocuments);
		//int parameterX = sf.getNumberOfDocuments();
		//parameterX = parameterX < EXPANDEDNUM ? EXPANDEDNUM : parameterX;
		//parameterX = parameterX > MAXEXPANDED ? MAXEXPANDED : parameterX;
		int parameterX = MAXEXPANDEDFROMEXTERNAL;
		String originalQuery = originalQueries[qryId];
	    QueryExpansion queryExpansion = new QueryExpansion(map, sf, qryId);
	    String expandedQuery = queryExpansion.getExpandedQuery(parameterX, originalQuery);
	    //System.out.println(expandedQuery);
	    newQueries[qryId] = expandedQuery;
	}
	
	/**
	 *  to get the query map by reading the query.xml
	 *  @param queryFile is the name of query file (such as "query.xml")
	 *  @return the query map (<qryID, original query> for each entry)
	*/
	static private void writeNewQueries(String queryFile) {
		FileWriter fw = null;
		BufferedWriter bw = null;
		
		
		try {
			fw = new FileWriter(queryFile);
			bw = new BufferedWriter(fw);
			
			for (int i = 1; i <= TOTAL; i++) {
			//for (int i = 1; i <= 2; i++) {
				//if ((i >= 1 && i <= 9)|| i == 16) {
				if (i != 47 && i != 53 && i != 69 && i != 70 && i != 72 &&
					i != 76 && i != 87 && i != 108) {
				bw.write("<number>" + i + "</number>");
				bw.newLine();
				bw.write(newQueries[i]);
				bw.newLine();
				}
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
