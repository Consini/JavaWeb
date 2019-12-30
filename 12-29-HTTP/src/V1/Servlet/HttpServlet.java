package V1.Servlet;
import V1.Request;
import V1.Response;

import java.io.IOException;

public abstract class HttpServlet {
    public abstract void doGet(Request req, Response resp) throws IOException;
}
