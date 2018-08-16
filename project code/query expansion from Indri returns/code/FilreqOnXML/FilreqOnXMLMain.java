
public class FilreqOnXMLMain {
	public static void main(String[] args) {
		String fileName = "10-10External/expandedQuery_param.xml";
		String outputFile = "10-10External/expandedQuery_param_before10.xml";
		FilreqOnXML.addFilreqOnXML(fileName, outputFile, GetQueryTweetTime.getQueryTweetTime());
	}
}
