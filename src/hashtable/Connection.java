package hashtable;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Arrays;
import java.util.Hashtable;

public class Connection implements Runnable {
  /**
   * The socket connected to the server.
   */
  private Socket socket;
  /**
   * The table containing the keys and values.
   */
  private Hashtable<String,String> hashtable;
  
  /**
   * The constructor associating the socket and hashtable to existing ones.
   * @param socket - A socket 
   * @param hashtable - A hashtable
   */
  public Connection(Socket socket, Hashtable<String,String> hashtable) {
    this.socket = socket;
    this.hashtable=hashtable;
  }
  /**
   * Put an item in the hashtable represented by its key and value.
   * Sends an error message to the client in case the item could not be added.
   * Sends a success message if the item could be added.
   * @param key - The key of the item to be added.
   * @param value - The value of the item to be added.
   */
  public void put(String key, String value) {
    if(key == null || value == null) {
      returnError();
    }
    else {
      this.hashtable.put(key, value);
      returnSuccess(value);
    }
  }
  /**
   * Sends the value of a String in the hashtable represented by a key to the client.
   * Sends an error message if their is no value.
   * @param key - The key of the value to be sent.
   */
  public void get(String key) {
    String value =hashtable.get(key);
    if(value !=null) {
      returnSuccess(value);
    }
    else {
      returnError();
    }
  }
  /**
   * Delete the value from the hashtable represented by its key.
   * Sends a message to the client to tell him the operation was a success/an error.
   * @param key - The key corresponding to the value to be deleted in the table.
   */
  public void del(String key) {
    this.hashtable.remove(key);
    if(this.hashtable.get(key) == null) {
      returnSuccess();
    }
    else {
      returnError();
    }
  }
  
  /**
   * Sends a message to tell that the operation did not succeed.
   */
  public void returnError() {
    DataOutputStream out;
    String msg_out=Constant.ERROR_HEADER;
    try {
      out = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
      byte[] message = new byte[1024];
      message=msg_out.getBytes();
      out.write(message);
      out.flush();
    } catch (IOException e) {
      System.out.println("The server tried to send a message but could not.");
    }
  }
  
  /**
   * Sends a message to tell the operation was a success with an added another message to the message.
   * The added message is separated by a token.
   */
  public void returnSuccess(String msg) {
    DataOutputStream out;
    try {
      out = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
      byte[] header = new byte[4];
      byte[] key = new byte[20];
      byte[] value = new byte[1000];
      byte[] message = new byte[1024];
      header=Constant.SUCCESS_HEADER.getBytes();
      key="".getBytes();
      value=msg.getBytes();
      System.arraycopy(header, 0, message, 0, header.length);
      System.arraycopy(key, 0, message, 4, key.length);
      System.arraycopy(value, 0, message, 24, value.length);
      out.write(message);
      out.flush();
    } catch (IOException e) {
      System.out.println("The server tried to send a message but could not.");
    }
  }
  /**
   * Sends a message to tell the operation was a success.
   */
  public void returnSuccess() {
      returnSuccess("");
  }
  
  /**
   * The run() function of the thread. It reads an incoming message and takes the right decision (put, get or del).
   * It returns an error message if the message received could not be decrypted.
   */
  public void run() {
    while(true) {
      BufferedReader in;
      try {
        in = new BufferedReader (new InputStreamReader (socket.getInputStream()));
        char buffer[] = new char[1024];
        in.read(buffer);
        String header=String.valueOf(Arrays.copyOfRange(buffer,0,4));
        String key=String.valueOf(Arrays.copyOfRange(buffer,4,24));
        String value=String.valueOf(Arrays.copyOfRange(buffer,24,1024));
        if (header.equals(Constant.PUT_HEADER)) {
          put(key,value);
        }
        else if(header.equals(Constant.GET_HEADER)) {
          get(key);
        }
        else if(header.equals(Constant.DEL_HEADER)) {
          del(key);
        }
        else {
          returnError();
        }
      } catch (IOException e) {
        System.out.println("The server tried to receive a message but could not.");
      }
    }
  }

}
