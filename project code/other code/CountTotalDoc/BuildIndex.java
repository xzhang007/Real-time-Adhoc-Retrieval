import java.io.*;
import java.util.Map;
import java.util.List;

public class BuildIndex {
	
	public static void main(String[] args) {
		String dir = "microblogTrack2011/20110201";
		int totalDocNo = getDocNo(dir);
		System.out.println(totalDocNo);
	}
	
	private static int getDocNo(String dir) {
		Tokenizer tnz = new Tokenizer();
		return tnz.traverseTokenize(dir);
		
		
		
	}
}
