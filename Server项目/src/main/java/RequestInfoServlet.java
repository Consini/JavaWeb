import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;

@WebServlet("/info")
public class RequestInfoServlet extends HttpServlet {
    private void printMessage(HttpServletResponse resp, String message) throws IOException {
        resp.getWriter().printf("<p>%s</p>", message);
        System.out.println(message);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html; charset=utf-8");

        {
            String method = req.getMethod();
            String message = "方法: " + method;
            printMessage(resp, message);
        }

        {
            String path = req.getRequestURI();
            String message = "路径: " + path;
            printMessage(resp, message);
        }

        {
            String version = req.getProtocol();
            String message = "版本: " + version;
            printMessage(resp, message);
        }

        {
            printMessage(resp, "所有的请求头信息: ");
            Enumeration<String> headerNames = req.getHeaderNames();
            while (headerNames.hasMoreElements()) {
                String key = headerNames.nextElement();
                String value = req.getHeader(key);
                printMessage(resp, key + " = " + value);
            }
        }

        {
            InputStream in = req.getInputStream();
            printMessage(resp, "请求体被封装成的输入流: " + in);
        }
    }
}
