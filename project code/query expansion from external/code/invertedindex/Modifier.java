package invertedindex;

import org.lemurproject.kstem.KrovetzStemmer;
import java.util.HashSet;
import java.lang.StringBuffer;

/**
*  This class is to modify (case normaloziation, stemming, remove stop words) 
*  for the given term or term array.
*/
public class Modifier {
	private static KrovetzStemmer kStemmer = new KrovetzStemmer();;
	private static final HashSet<String> STOPWORDS = new HashSet<>();
	
	static {
		STOPWORDS.add("the");
		STOPWORDS.add("is");
		STOPWORDS.add("at");
		STOPWORDS.add("of");
		STOPWORDS.add("on");
		STOPWORDS.add("and");
		STOPWORDS.add("a");
		STOPWORDS.add("by");
		STOPWORDS.add("to");
		STOPWORDS.add("in");
		STOPWORDS.add("it");
		STOPWORDS.add("an");
		STOPWORDS.add("are");
		STOPWORDS.add("as");
		STOPWORDS.add("be");
		STOPWORDS.add("for");
		STOPWORDS.add("from");
		STOPWORDS.add("has");
		STOPWORDS.add("he");
		STOPWORDS.add("its");
		STOPWORDS.add("that");
		STOPWORDS.add("was");
		STOPWORDS.add("were");
		STOPWORDS.add("will");
		STOPWORDS.add("with");
		
		STOPWORDS.add("http");
		STOPWORDS.add("s");
		STOPWORDS.add("can");
		STOPWORDS.add("t");
		STOPWORDS.add("ly");
		STOPWORDS.add("u");
		STOPWORDS.add("n");
		STOPWORDS.add("us");
		STOPWORDS.add("over");
		STOPWORDS.add("have");
		STOPWORDS.add("via");
		STOPWORDS.add("this");
		STOPWORDS.add("into");
		STOPWORDS.add("me");
		STOPWORDS.add("would");
		STOPWORDS.add("against");
		STOPWORDS.add("any");
		STOPWORDS.add("just");
		STOPWORDS.add("about");
		STOPWORDS.add("she");
		STOPWORDS.add("her");
		STOPWORDS.add("his");
		STOPWORDS.add("like");
		STOPWORDS.add("you");
		STOPWORDS.add("i");
		STOPWORDS.add("com");
		STOPWORDS.add("back");
		STOPWORDS.add("off");
		STOPWORDS.add("him");
		STOPWORDS.add("down");
		STOPWORDS.add("out");
		STOPWORDS.add("after");
		STOPWORDS.add("much");
		STOPWORDS.add("now");
		STOPWORDS.add("bit");
		STOPWORDS.add("your");
		STOPWORDS.add("www");
		STOPWORDS.add("r");
		STOPWORDS.add("always");
		STOPWORDS.add("tinyurl");
		STOPWORDS.add("around");
		STOPWORDS.add("ap");
		STOPWORDS.add("been");
		STOPWORDS.add("co");
		STOPWORDS.add("here");
		STOPWORDS.add("had");
		STOPWORDS.add("there");
		STOPWORDS.add("fb");
		STOPWORDS.add("up");
		STOPWORDS.add("am");
		STOPWORDS.add("mine");
		STOPWORDS.add("my");
		STOPWORDS.add("yours");
		STOPWORDS.add("amp");
		STOPWORDS.add("tumblr");
		STOPWORDS.add("but");
		STOPWORDS.add("im");
		STOPWORDS.add("so");
		STOPWORDS.add("m");
		STOPWORDS.add("when");
		STOPWORDS.add("some");
		STOPWORDS.add("if");
		STOPWORDS.add("too");
		STOPWORDS.add("who");
		STOPWORDS.add("what");
		STOPWORDS.add("how");
		STOPWORDS.add("maybe");
		STOPWORDS.add("should");
		STOPWORDS.add("shall");
		STOPWORDS.add("would");
		STOPWORDS.add("may");
		STOPWORDS.add("might");
		STOPWORDS.add("or");
		STOPWORDS.add("than");
		STOPWORDS.add("according");
		STOPWORDS.add("all");
		STOPWORDS.add("also");
		STOPWORDS.add("while");
		STOPWORDS.add("could");
		STOPWORDS.add("do");
		STOPWORDS.add("did");
		STOPWORDS.add("don");
		STOPWORDS.add("didn");
		STOPWORDS.add("doesn");
		STOPWORDS.add("d");
		STOPWORDS.add("done");
		STOPWORDS.add("they");
		STOPWORDS.add("them");
		
		STOPWORDS.add("get");
		STOPWORDS.add("go");
		STOPWORDS.add("give");
		STOPWORDS.add("no");
		STOPWORDS.add("not");
		STOPWORDS.add("good");
		STOPWORDS.add("see");
		STOPWORDS.add("gl");
		STOPWORDS.add("goo");
		STOPWORDS.add("dlvr");
		STOPWORDS.add("link");
		STOPWORDS.add("make");
		STOPWORDS.add("lt");
		STOPWORDS.add("say");
		STOPWORDS.add("use");
		STOPWORDS.add("new");
		STOPWORDS.add("news");
	}
	
	/**
	 *  modify a term (case normalizatin and stemming)
	 *  @param token
	 *  @return token
	*/
	public static String modify(String token) {
		token = token.toLowerCase();   // case normalization
		token = kStemmer.stem(token); // Stemming
		
		return token;
	}
	
	/**
	 *  modify a term array (case normalizatin and stemming as well as removing the stop words)
	 *  @param tokens array
	 *  @return tokens array
	*/
	public static String [] modify(String [] tokens) {
		StringBuffer strs = new StringBuffer();
		for (String str : tokens) {
			str = modify(str);
			// remove stopwords
			if (STOPWORDS.contains(str)) {
				continue;
			}
			strs.append(str + " ");
		}
		return strs.toString().split("\\s+");
	}
}
