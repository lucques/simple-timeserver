import java.net.*;
import java.io.*;
 
/**
 * Ein Objekt dieser Klasse repraesentiert einen Client, welcher die Uhrzeit von einem `TimeServer` erfragen kann.
 * Eine TCP-Verbindung wird direkt bei Objekterzeugung aufgebaut.
 */
class TimeClient {
    
    private Socket socket;
    private String socketName;
    private BufferedReader reader;
    private PrintWriter writer;

    // Dieser sogenannte Konstruktor wird fuer die Erzeugung eines `TimeClient`-Objekts aufgerufen. Der Nutzer
    // muss angeben, auf welchem Port der Server erreichbar ist, mit dem die Verbindung hergestellt weden soll.
    // Statt einer IP-Adresse des Servers wird hier zu Testzwecken "localhost" angegeben, d.h., der Server
    // befindet sich auf demselben Rechner wie der hier zu startende Client.
    TimeClient(int serverPort) throws Exception {
        // Zu Test-Zwecken wurde der Server auf dem eigenen Rechner gestartet, auch als `localhost` bezeichnet.
        // Hier kann aber auch die IP-Adresse eines anderen Rechners eingegeben werden, z.B. "192.168.0.2"
        String serverName = "localhost";

        // Starte die TCP-Verbindung zum Server
        socket = new Socket(serverName, serverPort);
        // Speichere die zugehoerigen Verbindungsnummern
        socketName = socket.getLocalSocketAddress() + " <-> " + socket.getRemoteSocketAddress();
        
        // Die TCP-Verbindung stellt einen Eingangs- und einen Ausgangs-Datenstrom zur Verfuegung.
        InputStream input = socket.getInputStream();
        OutputStream output = socket.getOutputStream();

        // Die folgenden Objekte erlauben es, Textzeilen über diese Datenstroeme zu versenden. Wir speichern
        // sie im Objekt, damit z.B. die Methode `askTime()` sie verwenden kann.
        this.reader = new BufferedReader(new InputStreamReader(input));
        this.writer = new PrintWriter(output, true);
        
        println("Verbindung gestartet.");
    }
    
    // Frage den Server nach der Uhrzeit
    void askTime() throws Exception {
        // Hier nun ein Beispiel fuer eine Nachricht, die der Client an den Server schicken kann.
        writer.println("UHRZEIT");
        String response = reader.readLine();
    
        println("Der Server teilt die Uhrzeit mit: " + response);
    }
    
    
    // Trenne die Verbindung zum Server
    void shutdown() throws Exception {
        writer.println("TSCHUESS");
        
        reader.close();
        writer.close();
        println("Verbindung getrennt.");
    }
    
    // Eine Nachricht auf der Konsole ausgeben
    private void println(String message) {
        System.out.println("Client " + socketName + ": " + message);
    }
}