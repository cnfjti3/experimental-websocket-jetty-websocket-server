package jettywebsocket.example;

import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.StatusCode;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

@WebSocket(maxTextMessageSize = 1024 * 1024 * 1024)
public class TestWebSocket {
    @SuppressWarnings("unused")
	private Session session;

    public TestWebSocket() {
    }

    @OnWebSocketClose
    public void onClose(int statusCode, String reason) {
    	System.out.println(String.format("[CLIENT] Socket closed with status %s reason %s.", statusCode, reason));
        this.session = null;
    }

    @OnWebSocketConnect
    public void onConnect(Session session) {
    	this.session = session;
    	System.out.println(String.format("[CLIENT] User %s connected.", session.getRemoteAddress().getAddress()));
        this.session = session;
        try
        {
            Future<Void> fut;
            fut = session.getRemote().sendStringByFuture("Hello");
            fut.get(2, TimeUnit.SECONDS); // wait for send to complete.

            fut = session.getRemote().sendStringByFuture("World");
            fut.get(2,TimeUnit.SECONDS); // wait for send to complete.

            session.close(StatusCode.NORMAL, "Completed");
        }
        catch (Throwable t)
        {
            t.printStackTrace();
        }
    }

    @OnWebSocketMessage
    public void onMessage(String message) {
        System.out.println(String.format("[CLIENT] Received text: %s", message));
    }
}
