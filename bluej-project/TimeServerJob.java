import java.io.*;
import java.net.*;
import java.util.Date;

/**
 * Objekte dieser Klasse repraesentieren einen "Job" auf Server-Seite. Pro TCP-Verbindung gibt es einen Job, der sich um diese Verbindung kuemmert.
 */
public class TimeServerJob extends Thread {

  private Socket socket;
  private BufferedReader reader;
  private PrintWriter writer;
  
  public TimeServerJob(Socket socket) throws Exception {
      // Dies ist das Socket, also die TCP-Verbindung, fuer welche der Job zustaendig ist.
      this.socket = socket;
      
      // Die TCP-Verbindung stellt einen Eingangs- und einen Ausgangs-Datenstrom zur Verfuegung.
      InputStream input = socket.getInputStream();
      OutputStream output = socket.getOutputStream();
      
      // Die folgenden Objekte erlauben es, Textzeilen über diese Stroeme zu versenden. 
      reader = new BufferedReader(new InputStreamReader(input));
      writer = new PrintWriter(output, true);
  }

  public void run() {
      println("Client verbunden");
  
      try {
          // In einer Endlosschleife laufen, bis der Client die Verbindung abbricht
          while (true) {
              // Zunaechst die Anfrage des Clients lesen.
              String request = reader.readLine();
            
              // ... dann interpretieren und beantworten
              if (request.equals("UHRZEIT")) {
                  writer.println(new Date().getTime());
              }
              else if (request.equals("JAHR")) {
                  writer.println(new Date().getYear());
              }
              else if (request.equals("TSCHUESS")) {
                  socket.close();
                  println("Client getrennt");
                  break;
              }
              else {
                  writer.println("Anfrage nicht verstanden");
              }
          }
      }
      // Fehlerbehandlung: Abbruch, wenn die Verbindung fehlschlaegt.
      catch (Exception ex) {
          println("Fehler" + ex.getMessage());
      }
  }
  
  // Eine Log-Nachricht auf der Konsole ausgeben
  private void println(String message) {
      System.out.println("Server job " + socket.getLocalSocketAddress() + " serving " + socket.getRemoteSocketAddress() + ": " + message);
  }
}
