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
             serverSocket.bind( new InetSocketAddress(hostAddress, 8080) );
             System.out.println("접속 대기중");
             //클라이언트와 연결 성공 후 스레드 start
            while(true) {
                Socket socket = serverSocket.accept(); 
                System.out.println(socket.getInetAddress() +"로부터 연결요청이 들어왔습니다.");
                
                new ServerThread(socket, listWriters).start();
               
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
