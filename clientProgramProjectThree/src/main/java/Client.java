import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.function.Consumer;

public class Client {
	
	// Create the variables
	TheClient client;
	Socket socketClient;
	ObjectOutputStream out;
	ObjectInputStream in;
	private Consumer<Serializable> callback;
	int port_number;
	
	Client(Consumer<Serializable> call, int port) {
		
		callback = call;
		client = new TheClient();
		port_number = port;
		client.start();
		
	}
	
	public void sendMessage(Category message) {
		try {
			out.writeObject(message);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public class TheClient extends Thread {
		
		public void run() {
			
			try {
				socketClient= new Socket("127.0.0.1", port_number); // This is how a client socket should be declared
				
			    out = new ObjectOutputStream(socketClient.getOutputStream());
			    in = new ObjectInputStream(socketClient.getInputStream());
			    socketClient.setTcpNoDelay(true);
			}
			catch(Exception e) {}
			
			while(true) {
				try {
					Category dataManager = (Category) in.readObject();
					callback.accept(dataManager);
					System.out.println("Client received something from the server");
					System.out.println("Client got: " + dataManager.country_guessed_pressed);
					System.out.println("State attempt left " + dataManager.inside_country_attempts_left);
					
				}
					catch(Exception e) {}
			}
		
	    }
		
	}

}
