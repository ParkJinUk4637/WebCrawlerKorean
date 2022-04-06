import java.net.*;
import java.io.*;
import java.util.regex.*;

public class WebCrawler {
    URL url;
    BufferedReader br;
    StringBuffer sourceCode;
    Pattern kor = Pattern.compile("^*[가-힣]\s*");

    String sourceLine = "";

    public WebCrawler() {}

    String getHtmlKoreanString(String inputUrl) throws IOException {
        url = new URL(inputUrl);
        br = new BufferedReader(new InputStreamReader(url.openStream()));
        sourceCode = new StringBuffer();
        while((sourceLine=br.readLine())!=null){
            if(sourceLine!=null && sourceLine.length()!=0) {
                Matcher kor_m = kor.matcher(sourceLine);
                while(kor_m.find()){
                    sourceCode.append(kor_m.group());
                }
            }
            if(kor.matcher(sourceLine).find())sourceCode.append("\n");
        }
        return sourceCode.toString();
    }
}
