import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;

public class InstaSpider {
    /* This constant keeps the id of the closest DIV tag to the picture or the video */
    private final String MEDIA_CONTENT_DIV_TAG_ID = "_2di5p"; //"._mck9w._gvoze._tn0ps";
    private final long SCROLL_PAUSE_TIME = 500;

    private ArrayList<String> pictureLinkList;
    private ArrayList<String> videoLinkList;

    public InstaSpider() {
        pictureLinkList = new ArrayList<String>();
        videoLinkList = new ArrayList<String>();
    }

    private void crawl(String url) {
        WebDriver webDriver = new ChromeDriver();

        webDriver.get(url);

        JavascriptExecutor js = (JavascriptExecutor) webDriver;

        //This will scroll the web page till end.
        js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
        //scrollPage(js);
        List<WebElement> divTags = webDriver.findElements(By.className(MEDIA_CONTENT_DIV_TAG_ID));
        //String s = divTags.get(1).getAttribute("src");
        Iterator<WebElement> iterator = divTags.iterator();
        while (iterator.hasNext()){
            WebElement element = iterator.next();
            String s = element.getAttribute("src");
            pictureLinkList.add(s);
        }
    }

    private void scrollPage(JavascriptExecutor js) {
        long lastHeight = (Long) js.executeScript("return document.body.scrollHeight");
        long newHeight;

        while (true) {
            try {
                js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
                Thread.sleep(SCROLL_PAUSE_TIME);
                newHeight = (Long) js.executeScript("return document.body.scrollHeight");
                if(newHeight == lastHeight)
                    break;
                lastHeight = newHeight;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public String[] getPictureLinkList(String url) {
        crawl(url);

        return pictureLinkList.isEmpty()? null : pictureLinkList.toArray(new String[pictureLinkList.size()]);
    }

    public String[] getVideoLinkList(String url) {
        return videoLinkList.isEmpty() ? null : videoLinkList.toArray(new String[videoLinkList.size()]);
    }
}
