import java.io.*; 
import java.net.*;
import java.util.*;


class FTPServer {

    public static void main(String args[]) throws Exception{

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

    // Returns the contents of the file in a byte array.
    public static byte[] getBytesFromFile(File file) throws IOException {
        InputStream is = new FileInputStream(file);
        byte[] bytes;

        try {
            // Get the size of the file
            long length = file.length();

            // You cannot create an array using a long type.
            // It needs to be an int type.
            // Before converting to an int type, check
            // to ensure that file is not larger than Integer.MAX_VALUE.
            if (length > Integer.MAX_VALUE) {
                // File is too large (>2GB)
            }

            // Create the byte array to hold the data
            bytes = new byte[(int)length];

            // Read in the bytes
            int offset = 0;
            int numRead = 0;
            while (offset < bytes.length
                    && (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) {
                offset += numRead;
            }

            // Ensure all the bytes have been read in
            if (offset < bytes.length) {
                throw new IOException("Could not completely read file " + file.getName());
            }
        }
        finally {
            // Close the input stream and return bytes
            is.close();
        }
        return bytes;
    }
}
