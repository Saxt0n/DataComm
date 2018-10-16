import java.io.*; 
import java.net.*;
import java.util.*;
import java.text.*;
import java.lang.*;
import javax.swing.*;
import java.util.Scanner;
class FTPClient {

    public static void main(String argv[]) throws Exception {
        String sentence;
        String modifiedSentence;
        boolean isOpen = true;
        int number = 1;
        boolean notEnd = true;
        String statusCode;
        boolean clientgo = true;
        int port = 12000;

        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("To connect to server, enter \"connect\" followed by the server's IP and port.");
        sentence = inFromUser.readLine();
        StringTokenizer tokens = new StringTokenizer(sentence);

        if (sentence.startsWith("connect")) {
            String serverName = tokens.nextToken(); // pass the connect command
            serverName = tokens.nextToken();
            int port1 = Integer.parseInt(tokens.nextToken());
            System.out.println("You are connected to " + serverName);

            Socket ControlSocket = new Socket(serverName, port1);

            System.out.println("\nWhat would you like to do? \n list || retr: file.txt ||stor: file.txt  || close");

            while (isOpen == true && clientgo) {
                DataOutputStream outToServer = new DataOutputStream(ControlSocket.getOutputStream());

                DataInputStream inFromServer = new DataInputStream(new BufferedInputStream(ControlSocket.getInputStream()));

                sentence = inFromUser.readLine();

                if (sentence.equals("list")) {

                    port = port + 2;
                    System.out.println(port);
                    outToServer.writeBytes(port + " " + sentence + " " + '\n');
                    ServerSocket welcomeData = new ServerSocket(port);
                    Socket dataSocket = welcomeData.accept();

                    DataInputStream inData = new DataInputStream(new BufferedInputStream(dataSocket.getInputStream()));

                    System.out.println("There is data to read");
                    modifiedSentence = inData.readLine();
                    System.out.println("Listing Files: ");
                    System.out.println(modifiedSentence);

                    welcomeData.close();
                    dataSocket.close();
                    System.out.println("\nWhat would you like to do next? \n list || retr: file.txt || stor: file.txt || close");

                } else if (sentence.startsWith("retr:")) {
                    StringTokenizer tokens2 = new StringTokenizer(sentence);
                    tokens2.nextToken();
                    String filename = tokens2.nextToken();

                    port = port + 2;
                    System.out.println(port);
                    outToServer.writeBytes(port + " " + sentence + " " + '\n');
                    ServerSocket welcomeData = new ServerSocket(port);
                    Socket dataSocket = welcomeData.accept();

                    DataInputStream inData = new DataInputStream(new BufferedInputStream(dataSocket.getInputStream()));

                } 
//                 else if (sentence.startsWith("stor:")){
//                     FTPClient client = new FTPClient();
//                     FileInputStream fis = null;
//                     try {
//                         //
//                         // Create an InputStream of the file to be uploaded
//                         //
//                         System.out.println("Enter your filename: ");
//                         Scanner sc = new Scanner(System.in);
//                         String filename =sc.next();
//                         fis = new FileInputStream(filename);
// 
//                         //
//                         // Store file to server
//                         //
//                         client.storeFile(filename, fis);
//                     } catch (IOException e) {
//                         e.printStackTrace();
//                     } finally {
//                         try {
//                             if (fis != null) {
//                                 fis.close();
//                             }
//                         } catch (IOException e) {
//                             e.printStackTrace();
//                         }
//                     }
//                 }

                    else if (sentence.startsWith("quit")) {
                    System.out.print("qutting");
                    outToServer.writeBytes(port + " " + sentence + " " + '\n');
                    isOpen = false;
                    }
            }
                ControlSocket.close();
        }
    }
}
