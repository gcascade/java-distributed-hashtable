package hashtable;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Action implements Runnable {
  
  /**
   * The list of sockets.
   */
  private List<Socket> socket;
  /**
   * Default constructor. Get the socket list ready.
   */
  public Action() {
    this.socket = new ArrayList<Socket>();
  }

  /**
   * The function initiates a socket connection to a server among the 8 listed in the configuration file.
   * @param serverNb - The number of the server to connect.
   * @return
   */
  public Socket connectToServer(int serverNb) {
    Socket socket=null;
    switch(serverNb) {
      case 1:
        try {
          socket = new Socket(InetAddress.getByName(ConfigurationFile.getClient1()),
              ConfigurationFile.getClient1Port());
        } catch (UnknownHostException e) {
          System.out.println("Server 1 could not be found");
        } catch (IOException e) {
          System.out.println("Connection error : Server 1");
        }
        break;
      case 2:
        try {
          socket = new Socket(InetAddress.getByName(ConfigurationFile.getClient2()),
              ConfigurationFile.getClient2Port());
        } catch (UnknownHostException e) {
          System.out.println("Server 2 could not be found");
        } catch (IOException e) {
          System.out.println("Connection error : Server 2");
        }
        break;
      case 3:
        try {
          socket = new Socket(InetAddress.getByName(ConfigurationFile.getClient3()),
              ConfigurationFile.getClient3Port());
        } catch (UnknownHostException e) {
          System.out.println("Server 3 could not be found");
        } catch (IOException e) {
          System.out.println("Connection error : Server 3");
        }
        break;
      case 4:
        try {
          socket = new Socket(InetAddress.getByName(ConfigurationFile.getClient4()),
              ConfigurationFile.getClient4Port());
        } catch (UnknownHostException e) {
          System.out.println("Server 4 could not be found");
        } catch (IOException e) {
          System.out.println("Connection error : Server 4");
        }
        break;
      case 5:
        try {
          socket = new Socket(InetAddress.getByName(ConfigurationFile.getClient5()),
              ConfigurationFile.getClient5Port());
        } catch (UnknownHostException e) {
          System.out.println("Server 5 could not be found");
        } catch (IOException e) {
          System.out.println("Connection error : Server 5");
        }
        break;
      case 6:
        try {
          socket = new Socket(InetAddress.getByName(ConfigurationFile.getClient6()),
              ConfigurationFile.getClient6Port());
        } catch (UnknownHostException e) {
          System.out.println("Server 6 could not be found");
        } catch (IOException e) {
          System.out.println("Connection error : Server 6");
        }
        break;
      case 7:
        try {
          socket = new Socket(InetAddress.getByName(ConfigurationFile.getClient7()),
              ConfigurationFile.getClient7Port());
        } catch (UnknownHostException e) {
          System.out.println("Server 7 could not be found");
        } catch (IOException e) {
          System.out.println("Connection error : Server 7");
        }
        break;
      case 8:
        try{
          socket = new Socket(InetAddress.getByName(ConfigurationFile.getClient8()),
              ConfigurationFile.getClient8Port());
        } catch(UnknownHostException e) {
          System.out.println("Server 8 could not be found");
        } catch (IOException e) {
          System.out.println("Connection error : Server 7");
        }
        break;
      default :
      }
    return socket;
  }
  
  /**
   * The function finds the server having the corresponding key.
   * @param key - The key.
   * @return - serverNb - The number of the server.
   */
  public int getServer(String key) {
    int hashcode = Integer.parseInt(key);
    int serverNb=0;
    if (hashcode < Constant.MAX/8 && hashcode >= 0) {
      serverNb=1;
    }
    else if (hashcode < 2*Constant.MAX/8) {
      serverNb=2;
    }
    else if (hashcode < 3*Constant.MAX/8) {
      serverNb=3;
    }
    else if (hashcode < 4*Constant.MAX/8) {
      serverNb=4;
    }
    else if (hashcode < 5*Constant.MAX/8) {
      serverNb=5;
    }
    else if (hashcode < 6*Constant.MAX/8) {
      serverNb=6;
    }
    else if (hashcode < 7*Constant.MAX/8) {
      serverNb=7;
    }
    else if (hashcode < Constant.MAX) {
      serverNb=8;
    }
    return serverNb;
  }
  
  /**
   * The function sends a message to a server through a socket. The first 4 bits correspond to the header.
   * The next 20 bits correspond to the key and the last 1000 bits correspond to the value.
   * @param socket - The socket connected to the server.
   * @param header - The header sent before the message.
   * @param key - The key.
   * @param value - The value.
   * @return result - True or false depending if the message was sent.
   */
  public boolean sendMessage(Socket socket,int header, String key, String value) {
    boolean result=false;
    DataOutputStream out;
    try {
      out = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
      byte[] headerByte = new byte[4];
      byte[] keyByte = new byte[20];
      byte[] valueByte = new byte[1000];
      byte[] message = new byte[1024];
      switch(header) {
      case 1:
        headerByte=Constant.PUT_HEADER.getBytes();
        break;
      case 2:
        headerByte=Constant.GET_HEADER.getBytes();
        break;
      case 3:
        headerByte=Constant.DEL_HEADER.getBytes();
        break;
      default:
        return false;
      }
      keyByte=key.getBytes();
      valueByte=value.getBytes();
      System.arraycopy(headerByte, 0, message, 0, headerByte.length);
      System.arraycopy(keyByte, 0, message, 4, keyByte.length);
      System.arraycopy(valueByte, 0, message, 24, valueByte.length);
      out.write(message);
      out.flush();
      return true;
    } catch (IOException e) {
      System.out.println("The client tried to sent a message but it failed.");
    }
    return result;
  }
  
  /**
   * The function receives a message from a server and returns it as a String.
   * @param socket - The socket connected to the server.
   * @return message - The message from the server.
   */
  public String receiveMessage(Socket socket) {
    BufferedReader in;
    String message=null;
    try {
      in = new BufferedReader (new InputStreamReader (socket.getInputStream()));
      char buffer[] = new char[1024];
      in.read(buffer);
      String header=String.valueOf(Arrays.copyOfRange(buffer,0,4));
      String key=String.valueOf(Arrays.copyOfRange(buffer,4,24));
      String value=String.valueOf(Arrays.copyOfRange(buffer,24,1024));
      message=header+Constant.TOKEN+key+Constant.TOKEN+value;
    } catch (IOException e) {
      System.out.println("The client tried to receive a message but no message were received.");
    }
    return message;
  }
  /**
   * The put() function is used to store a String into the Distributed Hash Table.
   * @param key - The key to be stored.
   * @param value - The value to be stored.
   * @return - result - A boolean determining the result of the operation.
   */
  public boolean put(String key, String value) {
    boolean result=false;
    int serverNb=getServer(key);
    if(serverNb==0) {
      return false;
    }
    Socket socket = this.socket.get(serverNb-1);
    result = sendMessage(socket,1,key,value);
    if(result) {
      String message_in = receiveMessage(socket);
      if(message_in !=null) {
        StringTokenizer st = new StringTokenizer(message_in,Constant.TOKEN);
        if(st.nextToken().equals(Constant.SUCCESS_HEADER)) {
          result = true;
        }
        else {
          result = false;
        }
      }
    }
    return result;
  }
  /**
   * The get() function of the Distributed Hash Table. It returns the value corresponding to a key.
   * @param key - The key.
   * @return value - The value.
   */
  public String get(String key) {
    int serverNb=getServer(key);
    if(serverNb==0) {
      return null;
    }
    Socket socket = this.socket.get(serverNb-1);
    String value=null;
    String message=null;
    if(sendMessage(socket,2,key,"")) {
      message = receiveMessage(socket);
    }
    StringTokenizer st = new StringTokenizer(message,Constant.TOKEN);
    if(st.nextToken().equals(Constant.SUCCESS_HEADER)) {
      st.nextToken();
      value=st.nextToken();
    }
    return value;
  }
  /** 
   * The del() function of the Distributed Hash Table. It deletes a value from the table from its known key.
   * @param key - The key.
   * @return result - A boolean corresponding to the result of the operation.
   */
  public boolean del(String key) {
    boolean result=false;
    int serverNb=getServer(key);
    if(serverNb==0) {
      return false;
    }
    Socket socket = this.socket.get(serverNb-1);
    result = sendMessage(socket,3,key,"");
    if(result) {
      String message_in = receiveMessage(socket);
      if(message_in !=null) {
        StringTokenizer st = new StringTokenizer(message_in,Constant.TOKEN);
        if(st.nextToken().equals(Constant.SUCCESS_HEADER)) {
          result = true;
        }
        else {
          result = false;
        }
      }
    }
    return result;
  }
  /**
   * The hash function. It transforms a value into a key.
   * @param value - The value.
   * @return key - The key.
   */
  public String hash(String value) {
    String key=null;
    int nb=value.hashCode()%Constant.MAX;
    if(nb < 0) {
      nb=-nb;
    }
    key = Integer.toString(nb);
    return key;
  }
  /**
   * The method asks for the user to choose an action.
   * @return action - The chosen action : 1,2 or 3 corresponding to a put(), get() or del() action. Else 0 but it should be unreachable.
   */
  @SuppressWarnings("resource")
  public int chooseAction() {
    System.out.println("What do you want to do ?");
    System.out.println("Type s to search an item.");
    System.out.println("Type p to put an item.");
    System.out.println("Type d to delete an item.");
    String choice="";
    Scanner sc = new Scanner(System.in);
    while(!choice.equals("d") && !choice.equals("D") && !choice.equals("s") &&
        !choice.equals("S") && !choice.equals("p") && !choice.equals("P")) {
      choice=sc.next();
    }
    if(choice.equals("d") || choice.equals("D")) {
      return 3;
    }
    else if(choice.equals("s") || choice.equals("S")) {
      return 2;
    }
    else if(choice.equals("p") || choice.equals("P")) {
      return 1;
    }
    else {
      return 0;
    }
  }
  
  /**
   * The run() function asks the user for an action. Initiate connections with the servers if this was not already done.
   * It connects to one server to do a put(), get() or del() operation.
   * It is never ending.
   */
  @SuppressWarnings("resource")
  public void run() {
    while(true) {
      int action = chooseAction();
      if(socket.isEmpty()) {
        for(int i=0;i<8;i++) {
          socket.add(connectToServer(i+1));
        }
      }
      switch(action) {
      case 1: 
        System.out.println("Enter the value you want to add to the network.");
        Scanner scP = new Scanner(System.in);
        String valueP = scP.nextLine();
        String keyP = hash(valueP);
        System.out.println("The String "+valueP+" was converted into key "+keyP+".");
        if(put(keyP,valueP)==true) {
          System.out.println("The value "+valueP+" was added to the network.");
        }
        else {
          System.out.println("The value "+valueP+" could not be added to the network.");
        }
        break;
      case 2: 
        System.out.println("Enter the key of the value you want to search.");
        Scanner scG = new Scanner(System.in);
        String keyG = scG.next();
        System.out.println("Searching for key "+keyG+"...");
        String valueG = get(keyG);
        if(valueG == null) {
          System.out.println("The key "+keyG+" does not correspond to any value.");
        }
        else {
          System.out.println("The key "+keyG+" corresponds to the value "+valueG+".");
        }
        break;
      case 3:
        boolean isSure=false;
        String keyD=null;
        while(!isSure) {
          System.out.println("Enter the key of the value you want to delete.");
          Scanner scD = new Scanner(System.in);
          keyD = scD.next();
          System.out.println("You typed "+keyD+". Are you sure ? (y/n)");
          String sure = scD.next();
          if(sure.equals("y") || sure.equals("Y")) {
            isSure = true;
          }
        }
        if(del(keyD)==true) {
          System.out.println("The value corresponding to the key "+keyD+" was deleted from the network.");
        }
        else {
          System.out.println("The value corresponding to the key "+keyD+" could not be deleted from the network. They key you entered might not exist.");
        }
        break;
      default:
        System.out.println("Error");
      }
    }
  }

}
