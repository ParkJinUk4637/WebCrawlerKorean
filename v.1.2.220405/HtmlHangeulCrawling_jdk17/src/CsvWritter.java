import java.io.*;
import java.text.SimpleDateFormat;

public class CsvWritter {
    String fileName = new SimpleDateFormat("yyyyMMdd_HHmmss_S").format(System.currentTimeMillis()) + ".csv";

    File file = null;
    BufferedWriter bw = null;

    public CsvWritter(){

    }
    void saveStringToCsv(String inputUrl){
        try{
            file = new File(fileName);
            bw = new BufferedWriter(new FileWriter(file));
            bw.write(new WebCrawler().getHtmlKoreanString(inputUrl));
            bw.flush();
            bw.close();
        } catch (IOException e) {
        }
    }

    String getFileName(){
        return fileName;
    }
}