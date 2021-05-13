import java.net.*;
import java.io.*;
 
/**
 * Objekte dieser Klasse repraesentieren einen Client. Eine TCP-Verbindung wird direkt bei Objekterzeugung aufgebaut.
 */
public class TimeClient {

    private String serverName;
    private int serverPort;
    
    private Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;

    public TimeClient(int serverPort) throws Exception {
        // Bei Objekterzeugung die Port-Nr. merken
        this.serverName = "localhost"; // Zu Test-Zwecken laeuft der Server auf dem eigenen Rechner
        this.serverPort = serverPort;
            
        // Starte die TCP-Verbindung zum Server
        socket = new Socket(serverName, serverPort);
        
        // Die TCP-Verbindung stellt einen Eingangs- und einen Ausgangs-Datenstrom zur Verfuegung.
        InputStream input = socket.getInputStream();
        OutputStream output = socket.getOutputStream();

        // Die folgenden Objekte erlauben es, Textzeilen über diese Stroeme zu versenden. 
        reader = new BufferedReader(new InputStreamReader(input));
        writer = new PrintWriter(output, true);
        
        println("Verbindung gestartet!");
    }
    
    // Frag den Server nach der Uhrzeit
    public void askTime() throws Exception {
        // Hier kann nun der Client Nachrichten an den Server schicken.
        // Die folgenden Zeilen koennen nun auch durch Benutzereingaben ersetzt werden!
        writer.println("UHRZEIT");
        String response = reader.readLine();
    
        println("Der Server teilt die Uhrzeit mit: " + response);
    }
    
    public void shutdown() throws Exception {
        socket.close();
        println("Verbindung getrennt.");
    }
    
    // Eine Log-Nachricht auf der Konsole ausgeben
    private void println(String message) {
        System.out.println("Client " + socket.getLocalSocketAddress() + ": " + message);
    }
}