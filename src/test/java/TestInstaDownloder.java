import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class TestInstaDownloder {

    public static void main (String[] args) {
        InstaSpider spider = new InstaSpider();
        String[] url  = spider.getPictureLinkList("https://www.instagram.com/lovelyjoohee/");
        String path = "C:\\InstaPics\\";
        try {
            for(int i=0; i<url.length; i++) {
                //connectionTimeout, readTimeout = 10 seconds
                FileUtils.copyURLToFile(new URL(url[i]), new File(path + "lovelyjoohee" +i + ".jpg"), 10000, 10000);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
