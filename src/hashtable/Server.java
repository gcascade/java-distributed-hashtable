package hashtable;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Hashtable;

public class Server implements Runnable {
/**
 * The client number
 */
  private int clientNumber;
  /**
   * A hashtable to store keys and values
   */
  private Hashtable<String,String> hashtable;
  
  /**
   * Constructor to initialize a server with the right server number.
   * It gets the hashtable ready.
   * @param clientNumber
   */
  public Server(int clientNumber) {
    this.clientNumber = clientNumber;
    hashtable = new Hashtable<String,String>();
  }
  /**
   * The default constructor initialize the server number with a wrong number.
   * Should never be called.
   */
  public Server() {
    this(0);
    hashtable = new Hashtable<String,String>();
  }
  
  /**
   * Initialize the server to listen on the right port.
   * When someone connects, run the Connection thread.
   */
  @SuppressWarnings("resource")
  public void run() {
    try {
      ServerSocket server = null;
      switch(clientNumber) {
      case 1: 
        server = new ServerSocket(ConfigurationFile.getClient1Port());
        break;
      case 2: 
        server = new ServerSocket(ConfigurationFile.getClient2Port());
        break;
      case 3: 
        server = new ServerSocket(ConfigurationFile.getClient3Port());
        break;
      case 4: 
        server = new ServerSocket(ConfigurationFile.getClient4Port());
        break;
      case 5: 
        server = new ServerSocket(ConfigurationFile.getClient5Port());
        break;
      case 6: 
        server = new ServerSocket(ConfigurationFile.getClient6Port());
        break;
      case 7: 
        server = new ServerSocket(ConfigurationFile.getClient7Port());
        break;
      case 8: 
        server = new ServerSocket(ConfigurationFile.getClient8Port());
        break;
      default: 
        server = new ServerSocket(ConfigurationFile.getClient1Port());
        break;
      }
      while(true) {
        Socket socket =server.accept();
        Thread thread = new Thread(new Connection(socket,hashtable));
        thread.start();
      }
    }
    catch(IOException e) {
      System.out.println("Server socket could not be created. Check configuration file.");
    }
  }

}
