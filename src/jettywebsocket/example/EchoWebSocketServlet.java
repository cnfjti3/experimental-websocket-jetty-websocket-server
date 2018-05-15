package jettywebsocket.example;

import java.io.IOException;
import java.nio.ByteBuffer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.WebSocketListener;
import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;


@SuppressWarnings("serial")
@WebServlet(name = "WebSocket Servlet", urlPatterns = { "/*" })
public class EchoWebSocketServlet extends WebSocketServlet implements WebSocketListener {

	Session session;
	
    @Override
    public void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        response.getWriter().println("HTTP GET method not implemented.");
    }

	@Override
	public void configure(WebSocketServletFactory factory) {
//        factory.getPolicy().setIdleTimeout(10000);

        factory.register(EchoWebSocketServlet.class);
		
	}

	@Override
	public void onWebSocketClose(int statusCode, String reason) {
		System.out.println(String.format("Socket closed with status %s reason %s.", statusCode, reason));
	}

	@Override
	public void onWebSocketConnect(Session session) {
		System.out.println(String.format("User %s connected.", session.getRemoteAddress().getAddress()));
		this.session = session;
	}

	@Override
	public void onWebSocketError(Throwable throwable) {
		System.err.println(throwable.getMessage());
		throwable.printStackTrace();
	}

	@Override
	public void onWebSocketBinary(byte[] array, int offset, int length) {
		System.out.println(String.format("Received bytes of length %d", length));
		try {
			this.session.getRemote().sendBytes(ByteBuffer.wrap(array, offset, length));
		} catch (IOException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
	}

	@Override
	public void onWebSocketText(String text) {
		System.out.println(String.format("Received text: %s", text));
		try {
			this.session.getRemote().sendString(text);
		} catch (IOException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
	}
}