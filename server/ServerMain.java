package server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;


public class ServerMain {
    
    public static void main(String[] args){
    	
    	ServerSocket serverSocket = null;
    	List<PrintWriter> listWriters = new ArrayList<PrintWriter>();
   
        try{ 
        	serverSocket = new ServerSocket();
        	 String hostAddress = InetAddress.getLocalHost().getHostAddress();
        	 System.out.println(hostAddress);
             serverSocket.bind( new InetSocketAddress(hostAddress, 8080) );
             System.out.println("waiting..");
             //connect success to client
            while(true) {
                Socket socket = serverSocket.accept(); 
                System.out.println("Client IP:"+socket.getInetAddress());
                
                new ServerThread(socket, listWriters).start();
                // One Client, one thread
               
            }
        }catch(IOException e) {
            e.printStackTrace();
            
        } finally {
            try {
                if( serverSocket != null && !serverSocket.isClosed() ) {
                    serverSocket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
}
    
}
