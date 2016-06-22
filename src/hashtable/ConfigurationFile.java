package hashtable;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigurationFile {

  /**
   * Clients' IP addresses.
   */
  private static String client1;
  private static String client2;
  private static String client3;
  private static String client4;
  private static String client5;
  private static String client6;
  private static String client7;
  private static String client8;
  /**
   * Clients' port numbers.
   */
  private static int client1port;
  private static int client2port;
  private static int client3port;
  private static int client4port;
  private static int client5port;
  private static int client6port;
  private static int client7port;
  private static int client8port;
  
  /**
   * The function loads the 'config.properties' file and sets the static attributes.
   */
  public static void loadFile() {
    String filename = "config.properties";
    Properties prop = new Properties();
    try {
      InputStream in = new FileInputStream(filename);
      prop.load(in);
      client1=prop.getProperty("client1");
      client2=prop.getProperty("client2");
      client3=prop.getProperty("client3");
      client4=prop.getProperty("client4");
      client5=prop.getProperty("client5");
      client6=prop.getProperty("client6");
      client7=prop.getProperty("client7");
      client8=prop.getProperty("client8");
      client1port=Integer.parseInt(prop.getProperty("client1port"));
      client2port=Integer.parseInt(prop.getProperty("client2port"));
      client3port=Integer.parseInt(prop.getProperty("client3port"));
      client4port=Integer.parseInt(prop.getProperty("client4port"));
      client5port=Integer.parseInt(prop.getProperty("client5port"));
      client6port=Integer.parseInt(prop.getProperty("client6port"));
      client7port=Integer.parseInt(prop.getProperty("client7port"));
      client8port=Integer.parseInt(prop.getProperty("client8port"));
    } catch (FileNotFoundException e) {
      System.out.println("Configuration file was not found.");
    } catch (IOException e) {
      System.out.println("Configuration file could not be opened");
    }


  }
  /**
   * Load the file to return the client 1's IP address.
   * @return client1 - The client 1's IP address.
   */
  public static String getClient1(){
    loadFile();
    return client1;
  }

  /**
   * Load the file to return the client 2's IP address.
   * @return client2 - The client 2's IP address.
   */
  public static String getClient2() {
    loadFile();
    return client2;
  }

  /**
   * Load the file to return the client 3's IP address.
   * @return client3 - The client 3's IP address.
   */
  public static String getClient3() {
    loadFile();
    return client3;
  }

  /**
   * Load the file to return the client 4's IP address.
   * @return client4 - The client 4's IP address.
   */
  public static String getClient4() {
    loadFile();
    return client4;
  }

  /**
   * Load the file to return the client 5's IP address.
   * @return client5 - The client 5's IP address.
   */
  public static String getClient5() {
    loadFile();
    return client5;
  }

  /**
   * Load the file to return the client 6's IP address.
   * @return client6 - The client 6's IP address.
   */
  public static String getClient6() {
    loadFile();
    return client6;
  }

  /**
   * Load the file to return the client 7's IP address.
   * @return client7 - The client 7's IP address.
   */
  public static String getClient7() {
    loadFile();
    return client7;
  }

  /**
   * Load the file to return the client 8's IP address.
   * @return client8 - The client 8's IP address.
   */
  public static String getClient8() {
    loadFile();
    return client8;
  }

  /**
   * Load the file to return the client 1's port number.
   * @return client1port - The client 1's port number.
   */
  public static int getClient1Port() {
    loadFile();
    return client1port;
  }
  
  /**
   * Load the file to return the client 2's port number.
   * @return client2port - The client 2's port number.
   */
  public static int getClient2Port() {
    loadFile();
    return client2port;
  }

  /**
   * Load the file to return the client 3's port number.
   * @return client3port - The client 3's port number.
   */
  public static int getClient3Port() {
    loadFile();
    return client3port;
  }

  /**
   * Load the file to return the client 4's port number.
   * @return client4port - The client 4's port number.
   */
  public static int getClient4Port() {
    loadFile();
    return client4port;
  }

  /**
   * Load the file to return the client 5's port number.
   * @return client5port - The client 5's port number.
   */
  public static int getClient5Port() {
    loadFile();
    return client5port;
  }

  /**
   * Load the file to return the client 6's port number.
   * @return client6port - The client 6's port number.
   */
  public static int getClient6Port() {
    loadFile();
    return client6port;
  }

  /**
   * Load the file to return the client 7's port number.
   * @return client7port - The client 7's port number.
   */
  public static int getClient7Port() {
    loadFile();
    return client7port;
  }

  /**
   * Load the file to return the client 8's port number.
   * @return client8port - The client 8's port number.
   */
  public static int getClient8Port() {
    loadFile();
    return client8port;
  }
  
}
