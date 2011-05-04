package RestServlet;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import junit.framework.Assert;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.junit.Before;
import org.junit.Test;

public class RestServletTest {

    private static final int PORT = 8089;
    private static final String CONTEXTPATH = "/";

    private static final String GET_URI_1 = "asdf/sf";

    public static final String LINE1 = "<html>";
    public static final String LINE2 = "<h3> Das ist das TimeSync Servlet! </h3>";
    public static final String LINE3 = "</html>";

    public static final String[] LINES = { LINE1, LINE2, LINE3 };

    private Server server;

    @Before
    public void startUp() throws Exception {
        server = new Server(PORT);
        ServletContextHandler servletContextHandler = new ServletContextHandler(server, CONTEXTPATH,
                ServletContextHandler.SESSIONS);
        servletContextHandler.addServlet(new ServletHolder(new RestServlet()), CONTEXTPATH + "*");
        server.start();
    }

    @Test
    public void testGetRequest() throws Exception {
        URL url = new URL("http://localhost:" + PORT + CONTEXTPATH + GET_URI_1);
        URLConnection urlConnection = url.openConnection();
        BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

        Assert.assertEquals("/" + GET_URI_1, in.readLine());

        in.close();
    }
}