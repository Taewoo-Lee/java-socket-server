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
		            System.out.println("Lost connection from client");
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
                else if("MLogIn".equals(tokens[0])) {
                	String pass = DBManagers.managers_load(tokens[1]);
                	
                	printWriter.println(pass);
                	printWriter.flush();
                }
                
                else if("Register".equals(tokens[0])) {
                	DBMembers.members_insert(tokens[1], tokens[2], tokens[3], tokens[4]);
                }
                
                else if("checkID".equals(tokens[0])) {
                	String check = String.valueOf(DBMembers.IDcheck(tokens[1]));
                	
                	printWriter.println(check);
                	printWriter.flush();
                }
                
                else if("myInfo".equals(tokens[0])) {
                	String InfoList = DBMembers.load_myInfo(tokens[1]);
                	printWriter.println(InfoList);
                	printWriter.flush();
                }

		    }
		}
			catch(IOException e) {
				System.out.println(this.id + "exit from server");
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

        String data = id + "connect";
        broadcast(data);

        //save to writer pool
        addWriter(writer);
    }
	
    private void doQuit(PrintWriter writer) {
        removeWriter(writer);

        String data = this.id + "End Connection";
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