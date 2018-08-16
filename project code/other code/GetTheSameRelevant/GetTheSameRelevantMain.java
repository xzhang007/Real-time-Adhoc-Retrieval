
public class GetTheSameRelevantMain {
	public static void main(String[] args) {
		String fileName1 = "indri1000/result_file";
		String fileName2 = "relevantQrels";
		String outputFile = "indri1000/sameRelevant";
		GetTheSameRelevant.getTheSameRelevant(fileName1, fileName2, outputFile);
	}
}
