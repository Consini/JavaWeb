package lab;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomText;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import java.io.IOException;
import java.util.List;

/**
 * @Description TODO
 * @Author K
 * @Date 2020/1/7 19:38
 **/
// 详情页下载提取 Demo
public class XPathDemo {
    public static void main(String[] args) throws IOException {
        try(WebClient webClient = new WebClient(BrowserVersion.CHROME)) {
            webClient.getOptions().setJavaScriptEnabled(false);
            webClient.getOptions().setCssEnabled(false);
            HtmlPage page = webClient.getPage("https://so.gushiwen.org/shiwenv_e9b1a8b4def0.aspx");
            HtmlElement body = page.getBody();

            // 得到这一页的所有详情
//            List<HtmlElement> elements = body.getElementsByAttribute(
//                    "div",
//                    "class",
//                    "contson"
//            );
//            for (HtmlElement element : elements) {
//                System.out.println(element);
//            }
//            // 打印第一首诗的正文
//            System.out.println(elements.get(0).getTextContent().trim());

            {
                String xPath = "//div[@class='cont']/h1/text()";
                Object o = body.getByXPath(xPath).get(0);
                DomText domText = (DomText) o;
                System.out.println("题目: " + domText.asText());
            }
            {
                String xpath = "//div[@class='cont']/p[@class='source']/a[1]/text()";
                Object o = body.getByXPath(xpath).get(0);
                DomText domText = (DomText) o;
                System.out.println("朝代: " + domText.asText());
            }
            {
                String xpath = "//div[@class='cont']/p[@class='source']/a[2]/text()";
                Object o = body.getByXPath(xpath).get(0);
                DomText domText = (DomText) o;
                System.out.println("作者: " + domText.asText());
            }
            {
                String xPath = "//div[@class='cont']/div[@class='contson']";
                Object o = body.getByXPath(xPath).get(0);
                HtmlElement element = (HtmlElement) o;
                System.out.println(element.getTextContent().trim());
            }
        }
    }
}
