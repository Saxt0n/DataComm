import java.io.*; 
import java.net.*;
import java.util.*;


class FTPServer {

    public static void main(String args[]) throws Exception{
        
        String fromClient;
        String clientCommand;
        byte[] data;
        ServerSocket welcomeSocket = new ServerSocket(12000);
        String frstln;
        int port;
	Boolean closed = false;

	while (true) {

            Socket connectionSocket = welcomeSocket.accept();
            DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
            BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));

	    while (!closed) {
		fromClient = inFromClient.readLine();
		StringTokenizer tokens = new StringTokenizer(fromClient);
		frstln = tokens.nextToken();
		port = Integer.parseInt(frstln);
		clientCommand = tokens.nextToken();


		if (clientCommand.equals("list")) {
		    System.out.println("Listing files...");
		    System.out.println(port);
		    Socket dataSocket = new Socket(connectionSocket.getInetAddress(), port);

		    System.out.println("Data Socket opened.");

		    DataOutputStream dataOutToClient = new DataOutputStream(dataSocket.getOutputStream());
		    BufferedReader dataInFromClient = new BufferedReader(new InputStreamReader(dataSocket.getInputStream()));
		    String fileList = "";
		    File folder = new File(".");  //the folder for this process
		    File[] listOfFiles = folder.listFiles();  //this object contains all files AND folders in the current directory

		    /* Iterate through and add the name to the list only if the file object is indeed a file (not a directory) */
		    if (fileList.length() != 0) {  
			for (File file : listOfFiles) {
			    if (file.isFile()) {
				fileList += file.getName() + ", ";
			    }
                	}

			/* If the list of files isn't empty, then trim the last ", " off the list */
			if (fileList.length() != 0) {
			    fileList = fileList.substring(0, fileList.length() - 2);
                	}

			/* Send list to client */
			dataOutToClient.writeBytes(fileList);
			dataSocket.close();
		    }
		    else {
			System.out.print("file list is empty");
		    }
		} else if (clientCommand.equals("quit")) {
		    System.out.println(connectionSocket.getInetAddress() + " has disconnected");
		    closed = true;
		} else {
		    System.out.println("Invalid Command");
		}
           
	    }
	}
       
//
//			.................
//                    if(clientCommand.equals("retr:")) {
//
//
//			}
    }
}
