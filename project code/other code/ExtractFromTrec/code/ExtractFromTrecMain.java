
public class ExtractFromTrecMain {
	public static void main(String[] args) {
		String dirName = "microblogTrack2011";
		String fileName = "relevantQrels";
		
		for (int queryNo = 104; queryNo <= 110; queryNo++) {
			String outputFile = "rel/" + "relFor" + queryNo;
			GetRelevantDOCNO.clearList();
			GetRelevantDOCNO.getRelevantDOCNO(queryNo, fileName);
			ExtractTrec.extractTrec(dirName, outputFile);
		}
		
	}
}
