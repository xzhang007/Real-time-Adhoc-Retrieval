
public class ExtractFromTrecMain {
	public static void main(String[] args) {
		String dirName = "microblogTrack2011";
		String fileName = "10/result_file";
		
		for (int queryNo = 1; queryNo <= 110; queryNo++) {
			// filter those without noneffective queries
			if (queryNo == 11 || queryNo == 12 || queryNo == 15 || queryNo == 18 ||
			    queryNo == 24 || queryNo == 49 || queryNo == 50 || queryNo == 53 ||
			    queryNo == 63 || queryNo == 75 || queryNo == 76 || queryNo == 30 ||
			    queryNo == 55 || queryNo == 58 || queryNo == 66 || queryNo == 80) {
				continue;
			}
			String outputFile = "10/rel/" + "relFor" + queryNo;
			GetRelevantDOCNO.clearList();
			GetRelevantDOCNO.getRelevantDOCNO(queryNo, fileName);
			ExtractTrec.extractTrec(dirName, outputFile);
		}
		
	}
}
