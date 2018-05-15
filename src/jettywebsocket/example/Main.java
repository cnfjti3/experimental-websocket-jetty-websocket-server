package jettywebsocket.example;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;

public class Main {
	public static void main(String[] args) throws Exception {
        Server server = new Server(8000);
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);
        context.addServlet(EchoWebSocketServlet.class, "/*");
        server.start();
        server.join();
	}
}
