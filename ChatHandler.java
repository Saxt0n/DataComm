import java.net.*; 
import java.io.*; 
import java.util.*;

public class ChatHandler extends Thread { 
    
   Socket socket; 
   DataInputStream in; 
   DataOutputStream out;
   String name;
   protected static Vector handlers = new Vector ();
    
   public ChatHandler (String name, Socket socket) throws IOException { 
      this.name = name;
      this.socket = socket; 
      in = new DataInputStream (?);
      out = new DataOutputStream (?);
   } 
    
   public void run () { 

      try { 
         broadcast(name+" entered");
         handlers.addElement (this); 

         while (true) { 
?
            ? 
         } 

      } catch (IOException ex) { 
         System.out.println("-- Connection to user lost.");
      } finally { 
         handlers.removeElement (this); 
         ?
         try { 
            ?
         } catch (IOException ex) { 
            System.out.println("-- Socket to user already closed ?");
         }  
      }
   }
    

   protected static void broadcast (String message) { 
      synchronized (handlers) { 
         Enumeration e = handlers.elements (); 
         while (e.?) { 
            ChatHandler handler = (ChatHandler) ?; 
            try { 
 ?
               ?
            } catch (IOException ex) { 
               handler.stop (); 
            } 
         }
      }
   } 
}

