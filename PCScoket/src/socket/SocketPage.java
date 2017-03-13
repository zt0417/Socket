package socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
//import java.io.UnsupportedEncodingException;
import java.net.ServerSocket;
import java.net.Socket;
//import javax.swing.JOptionPane;



public class SocketPage extends Thread{
	private Socket clientSocket = null;
	  private ServerSocket serverSocket = null;
	  private InputStream inputStream = null;
	  private byte[] buffer = null;
	   
	  @Override
	  public void run() {
	    // TODO Auto-generated method stub
	     
	    try {
	      serverSocket = new ServerSocket(9001);
	      System.out.println("Port is already");
	      //block
	      clientSocket = serverSocket.accept();
	      System.out.println("User connected");
	      inputStream = clientSocket.getInputStream();
	       
	      BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
	      String str;
	       
	      while((str = br.readLine())!= null){
	        System.out.println(str);
	      }
	       
	    }catch (IOException e) {
	      // TODO Auto-generated catch block
	      e.printStackTrace();
	    }finally{
	      try {
	        inputStream.close();
	        serverSocket.close();
	      } catch (IOException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	      }
	    }
	     
	     
	  }
	 
	}