import java.io.*; 
import java.net.*;
import java.util.*;


class FTPServer {

    public static void main(String args[]) throws Exception {

        String fromClient;
        String clientCommand;
        String clientFileName = "";
        byte[] data;
        ServerSocket welcomeSocket = new ServerSocket(12000);
        String frstln;
        int port;


        Socket connectionSocket = welcomeSocket.accept();
        DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
        BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));

        while (true) {
            fromClient = inFromClient.readLine();
            StringTokenizer tokens = new StringTokenizer(fromClient);
            frstln = tokens.nextToken();
            port = Integer.parseInt(frstln);
            clientCommand = tokens.nextToken();

            if (clientCommand.equals("retr:") || clientCommand.equals("stor:")) {
                try {
                    clientFileName = tokens.nextToken();
                } catch (Exception e) {
                    System.out.println("Invalid input");
                }
            }


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
            } else if (clientCommand.equals("retr:")) {
                Socket dataSocket = new Socket(connectionSocket.getInetAddress(), port);


                System.out.println("Data Socket opened.");

                DataOutputStream dataOutToClient = new DataOutputStream(dataSocket.getOutputStream());
                System.out.println("retr socket open");
                System.out.println(clientFileName);
                try {
                    File f = new File("/" + clientFileName);
                } catch (Exception e) {
                    System.out.println("Error opening file.");
                }

                dataSocket.close();
            } else if (clientCommand.equals("quit")) {
                System.out.println("Closing connection.")

            }


            System.out.println("Data Socket closed");
        }

//
//    if(clientCommand.equals("retr:")) {
//
//
//			}

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
