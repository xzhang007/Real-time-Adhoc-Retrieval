
public class CountRelevantDocsMain {
	public static void main(String[] args) {
		String dir = "rel";
		String outputFile = "CountRel";
		int totalRelevantDocs = CountRelevantDocs.traverseFolders(dir, outputFile);
		System.out.println(totalRelevantDocs);
	}
}
