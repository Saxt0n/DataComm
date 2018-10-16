import java.io.*; 
import java.net.*;
import java.util.*;


class FTPServer {

    public static void main(String args[]) throws Exception {

        String fromClient;
        String clientCommand;
        String clientFileName = "";
        byte[] data;

        String frstln;
        int port;

        while (true) {
        System.out.println("hi");
        ServerSocket welcomeSocket = new ServerSocket(12000);
        boolean isConnected = true;
        Socket connectionSocket = welcomeSocket.accept();
        DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
        BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));

            while (isConnected == true) {
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
                // BufferedReader dataInFromClient = new BufferedReader(new InputStreamReader(dataSocket.getInputStream()));

                String fileList = getFiles();

                //Testing
                System.out.println(fileList);

                dataOutToClient.writeBytes(fileList);
                dataSocket.close();
                System.out.println("Data Socket closed");
            
            } else if (clientCommand.equals("retr:")) {
                try {
                clientFileName = tokens.nextToken();
                Socket dataSocket = new Socket(connectionSocket.getInetAddress(), port);


                System.out.println("Data Socket opened.");

                DataOutputStream dataOutToClient = new DataOutputStream(dataSocket.getOutputStream());
                System.out.println("retr socket open");
                System.out.println(clientFileName);

                    File f = new File("/" + clientFileName);


                dataSocket.close();
                }
                catch (Exception e) {
                    System.out.println("Error opening file.");
                }
            } 
            
            else if (clientCommand.equals("quit")) {
                System.out.println(connectionSocket.getInetAddress() + "has disconnected");
                isConnected = false;
                welcomeSocket.close();

            }

        }
      }
    }


    

    private static String getFiles() {
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
