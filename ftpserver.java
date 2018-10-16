import java.io.*; 
import java.net.*;
import java.util.*;


class FTPServer {

    public static void main(String args[]) throws Exception{

	ServerSocket welcomeSocket;
	int port = 12000;

	try
	    {
		welcomeSocket  = new ServerSocket(port);
		while (true) {
		    Socket connectionSocket = welcomeSocket.accept();

		    System.out.println("\nNew client connected.");

		    ClientHandler handler = new ClientHandler(connectionSocket);

		    handler.start();
		}
	    }
	catch (IOException ioEX)
	    {
		System.out.println("\nError in setting up port.");
		System.exit(1);
	    }

    }
}

class ClientHandler extends Thread
{
    private DataOutputStream outToClient;
    private BufferedReader inFromClient;
    private String fromClient;
    private String clientCommand;
    private String clientFileName = "";
    private byte[] data;
    private String frstln;
    private Boolean closed = false;
    private Socket connectionSocket;
    private int port;
	
    public ClientHandler(Socket socket) {
	try
	    {
		connectionSocket = socket;
		outToClient = new DataOutputStream(connectionSocket.getOutputStream());
		inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
		port = connectionSocket.getPort();
	    }
	catch (IOException ioEx)
	    {
		ioEx.printStackTrace();
	    }
    }
		
    public void run() {
	try
	    {
		do
		    {
			fromClient = inFromClient.readLine();
			StringTokenizer tokens = new StringTokenizer(fromClient);
			frstln = tokens.nextToken();
			port = Integer.parseInt(frstln);
			clientCommand = tokens.nextToken();
			DataOutputStream dataOutToClient;
			BufferedReader dataInFromClient;

			if (clientCommand.equals("list")) {
			    System.out.println("Listing files...");
			    System.out.println(port);
			    Socket dataSocket = new Socket(connectionSocket.getInetAddress(), port);


			    System.out.println("Data Socket opened.");

			    dataOutToClient = new DataOutputStream(dataSocket.getOutputStream());
			    // dataInFromClient = new BufferedReader(new InputStreamReader(dataSocket.getInputStream()));

			    String fileList = getFiles();

			    //Testing
			    System.out.println(fileList);

			    dataOutToClient.writeBytes(fileList);
			    dataSocket.close();
			    System.out.println("Data Socket closed");System.out.println("Listing files...");
			    System.out.println(port);
			    dataSocket = new Socket(connectionSocket.getInetAddress(), port);

			    System.out.println("Data Socket opened.");

			    dataOutToClient = new DataOutputStream(dataSocket.getOutputStream());
			    dataInFromClient = new BufferedReader(new InputStreamReader(dataSocket.getInputStream()));
			    fileList = "";
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
			} else if (clientCommand.equals("retr:")) {
			    try {
				clientFileName = tokens.nextToken();
				Socket dataSocket = new Socket(connectionSocket.getInetAddress(), port);


				System.out.println("Data Socket opened.");

				dataOutToClient = new DataOutputStream(dataSocket.getOutputStream());
				System.out.println("retr socket open");
				System.out.println(clientFileName);

				File f = new File("/" + clientFileName);


				dataSocket.close();
			    }
			    catch (Exception e) {
				System.out.println("Error opening file.");
			    }
			} else if (clientCommand.equals("quit")) {
			    closed = true;
			} else {
			    System.out.println("Invalid Command");
			}
		
		
		    } while (!clientCommand.equals("quit"));
	    }
	catch(IOException ioEx)
	    {
		ioEx.printStackTrace();
	    }
	try
	    {
		if (connectionSocket != null)
		    {
			String inetadd = "" + connectionSocket.getInetAddress();
			System.out.println("\n" + inetadd.substring(1) + " has disconnected");
			connectionSocket.close();
		    }
	    }
	catch (IOException ioEX)
	    {
		System.out.println("Unable to disconnect.");
	    }
    }

    private String getFiles() {
        String files = "";
        File cwd = new File(".");

        File[] filesList = cwd.listFiles();
        if (filesList.length == 0) {
            return "No files available.";
        } else {
            for (File f : filesList) {
                if (f.isFile()) {
                    files = files + f.getName() + " ";
                }
            }
            return files;
        }
    }
}
