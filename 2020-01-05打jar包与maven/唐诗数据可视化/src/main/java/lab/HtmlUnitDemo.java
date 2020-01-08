package lab;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomText;
import com.gargoylesoftware.htmlunit.html.Html;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @Description TODO
 * @Author K
 * @Date 2020/1/5 16:51
 **/
// 列表页下载提取 Demo
public class HtmlUnitDemo {
    public static void main(String[] args) throws IOException {
        // 无界面的浏览器（HTTP客户端）

        //1.请求过程
        // 声明一个浏览器对象，版本为 CHROME,try 之后会自动关闭
        try(WebClient webClient = new WebClient(BrowserVersion.CHROME)) {
            //关闭浏览器的 js 与 css 执行引擎，即不再执行网页中的 js 和 css
            //没有关闭 js 和 css 引擎的话会有很多警告
            webClient.getOptions().setJavaScriptEnabled(false);
            webClient.getOptions().setCssEnabled(false);

            //对列表页进行请求，返回一个列表页的 dom 树
            HtmlPage page = webClient.getPage("https://so.gushiwen.org/gushi/tangshi.aspx");
            //System.out.println(page);

            // 2.保存内容
//            File file = new File("唐诗三百首\\列表页.html");
//            file.delete();
//            page.save(file);

            // 3.从 HTML 中提取需要的信息（解析 - 树的结点的查找）

            HtmlElement body = page.getBody();// 取一个结点
            List<HtmlElement> elements = body.getElementsByAttribute(
                    "div", "class", "typecont");

//            for (HtmlElement e : elements) {
//                System.out.println(e);
//            }
//            //一组 typecont，取出第一个
////            HtmlElement divElement = elements.get(0);
////            List<HtmlElement> aElement = divElement.getElementsByAttribute(
////                    "a", "target", "_blank");
////            for (HtmlElement e : aElement) {
////                System.out.println(e);
////            }
////            System.out.println(aElement.size());
////            System.out.println(aElement.get(0).getAttribute("href"));

            // 提取所有的
            int count=0;
            for(HtmlElement e:elements){
                List<HtmlElement> aElements = e.getElementsByTagName("a");
                for(HtmlElement ea:aElements){
                    System.out.println(ea.getAttribute("href"));
                    count++;
                }
            }
            System.out.println(count);
        }
    }
}
