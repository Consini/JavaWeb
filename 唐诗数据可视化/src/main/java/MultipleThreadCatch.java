import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomText;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.mysql.jdbc.jdbc2.optional.MysqlConnectionPoolDataSource;
import org.ansj.domain.Term;
import org.ansj.splitWord.analysis.NlpAnalysis;

import javax.sql.DataSource;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description TODO
 * @Author K
 * @Date 2020/1/9 18:37
 **/
public class MultipleThreadCatch {
    private static class Job implements Runnable{
        private String url;
        private DataSource dataSource;

        public Job(String url, DataSource dataSource){
            this.url = url;
            this.dataSource = dataSource;
        }

        @Override
        public void run() {
            WebClient client = new WebClient(BrowserVersion.CHROME);
            client.getOptions().setCssEnabled(false);
            client.getOptions().setJavaScriptEnabled(false);

            try {
                MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
                HtmlPage page = client.getPage(url);
                HtmlElement body = page.getBody();

                // 朝代
                String xPath = "//div[@class='cont']/p[@class='source']/a[1]/text()";
                DomText domText = (DomText) body.getByXPath(xPath).get(0);
                String dynasty = domText.asText();

                // 题目
                xPath = "//div[@class='cont']/h1/text()";
                domText = (DomText) page.getBody().getByXPath(xPath).get(0);
                String title = domText.asText();

                // 作者
                xPath = "//div[@class='cont']/p[@class='source']/a[2]/text()";
                domText = (DomText) body.getByXPath(xPath).get(0);
                String author = domText.asText();

                // 正文
                xPath = "//div[@class='cont']/div[@class='contson']";
                HtmlElement element = (HtmlElement) body.getByXPath(xPath).get(0);
                String content = element.getTextContent().trim();

                // sha256
                String s = title + content;
                StringBuilder sha256 = new StringBuilder();
                messageDigest.update(s.getBytes("UTF-8"));//加密
                byte[] result = messageDigest.digest();//运算
                for(byte b : result){
                    sha256.append(String.format("%02x",b));
                }

                // 分词
                List<Term> termList = new ArrayList<>();
                List<String> word = new ArrayList<>();
                termList.addAll(NlpAnalysis.parse(title).getTerms());
                termList.addAll(NlpAnalysis.parse(content).getTerms());
                for (Term term : termList) {
                    if(term.getNatureStr().equalsIgnoreCase("w")
                            || term.getNatureStr().equalsIgnoreCase("null")
                            || (term.getRealName().length() < 2)){
                        continue;
                    }
                    word.add(term.getRealName());
                }
                // 将一组词用 ， 分开
                String words = String.join(",",word);

                try (Connection connection = dataSource.getConnection()) {
                    String sql = "INSERT INTO tangshi " +
                            "(sha256, dynasty, title, author, " +
                            "content, words) " +
                            "VALUES (?, ?, ?, ?, ?, ?)";
                    // 诗词入库
                    try (PreparedStatement statement = connection.prepareStatement(sql)) {
                        statement.setString(1, sha256.toString());
                        statement.setString(2, dynasty);
                        statement.setString(3, title);
                        statement.setString(4, author);
                        statement.setString(5, content);
                        statement.setString(6, words);
                        com.mysql.jdbc.PreparedStatement mysqlStatment = (com.mysql.jdbc.PreparedStatement)statement;
                        System.out.println(mysqlStatment.asSql());
                        statement.executeUpdate();
                    }
                }
            }catch (SQLException e) {
                e.printStackTrace();
            }catch (IOException e){
                e.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }
    }
    public static void main(String[] args) throws IOException{
        WebClient webClient = new WebClient(BrowserVersion.CHROME);
        webClient.getOptions().setJavaScriptEnabled(false);
        webClient.getOptions().setCssEnabled(false);

        String baseURL = "https://so.gushiwen.org";
        String pathURL = "/gushi/tangshi.aspx";

        List<String> detailUrlList = new ArrayList<>();
        {
            String url = baseURL + pathURL;
            HtmlPage page = webClient.getPage(url);
            List<HtmlElement> divElements = page.getBody().getElementsByAttribute(
                    "div", "class", "typecont");
            for(HtmlElement div : divElements){
                List<HtmlElement> as = div.getElementsByTagName("a");
                for(HtmlElement a : as){
                    String detailUrl = a.getAttribute("href");
                    String ret = baseURL + detailUrl;
                    detailUrlList.add(ret);
                }
            }
        }

        MysqlConnectionPoolDataSource dataSource = new MysqlConnectionPoolDataSource();//带有连接池
        dataSource.setServerName("127.0.0.1");
        dataSource.setPort(3306);
        dataSource.setUser("root");
        dataSource.setPassword("mysql");
        dataSource.setDatabaseName("tangshi");
        dataSource.setUseSSL(false);
        dataSource.setCharacterEncoding("UTF8");

        for(String url : detailUrlList){
            Thread thread = new Thread(new Job(url,dataSource));
            thread.start();
        }
    }
}

