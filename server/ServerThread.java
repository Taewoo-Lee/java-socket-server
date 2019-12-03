package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.List;

import database.*;

public class ServerThread extends Thread {
	private Socket socket = null;
	private String id = null;
	List<PrintWriter> listWriters = null;
	
	public ServerThread(Socket socket, List<PrintWriter> listWriters) {
		this.socket = socket;
		this.listWriters = listWriters;
	}
	
	@Override
	public void run() {
		try {
		    BufferedReader buffereedReader =
		            new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));

		    PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));

		    while(true) {
		        String request = buffereedReader.readLine();

		        if( request == null) {
		            System.out.println("클라이언트로부터 연결 끊김");
		            doQuit(printWriter);
		            break;
		        }
		        
		        String[] tokens = request.split(":");
                if("join".equals(tokens[0])) {
                    doJoin(tokens[1], printWriter);
                }
                else if("quit".equals(tokens[0])) {
                    doQuit(printWriter);
                }
                else if("LogIn".equals(tokens[0])) {
                	String pass = DBMembers.members_load(tokens[1]);
                	
                	printWriter.println(pass);
                	printWriter.flush();
                }
                else if("Register".equals(tokens[0])) {
                	DBMembers.members_insert(tokens[1], tokens[2], tokens[3], tokens[4]);
                }

		    }
		}
			catch(IOException e) {
				System.out.println(this.id + "접속 종료");
		}
	}
	
    private void removeWriter(PrintWriter writer) {
        synchronized (listWriters) {
            listWriters.remove(writer);
        }
    }
    private void addWriter(PrintWriter writer) {
        synchronized (listWriters) {
            listWriters.add(writer);
        }
    }
    
    private void doJoin(String id, PrintWriter writer) {
        this.id = id;

        String data = id + "접속";
        broadcast(data);

        // writer pool에 저장
        addWriter(writer);
    }
	
    private void doQuit(PrintWriter writer) {
        removeWriter(writer);

        String data = this.id + "접속 종료";
        broadcast(data);
    }
    
    private void broadcast(String data) {
        synchronized (listWriters) {
            for(PrintWriter writer : listWriters) {
                writer.println(data);
                writer.flush();
            }
        }
    }
}