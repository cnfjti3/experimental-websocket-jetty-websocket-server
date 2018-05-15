package jettywebsocket.example;

import java.net.URI;

import org.eclipse.jetty.websocket.client.ClientUpgradeRequest;
import org.eclipse.jetty.websocket.client.WebSocketClient;

public class TestClient {

	public static void main(String[] args) throws Exception {
		TestWebSocket socket = new TestWebSocket();
		WebSocketClient client = new WebSocketClient();
		client.start();
		URI echoUri = new URI("ws://localhost:8000/");
        ClientUpgradeRequest request = new ClientUpgradeRequest();
        client.connect(socket, echoUri, request);
        Thread.sleep(5000);
        client.stop();
	}

}
