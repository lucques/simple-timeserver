import java.io.*;
import java.net.*;
import java.util.Date;

/**
 * Ein Objekt dieser Klasse repraesentiert einen Thread, der sich um eine einzelne TCP-Verbindung kuemmert.
 * Insbesondere werden also Anfragen nach der Uhrzeit mit der aktuellen Uhrzeit beantwortet. Es koennen dabei
 * mehrere Jobs parallel laufen, da es ja auch mehrere parallele TCP-Verbindungen gibt; hierfuer wird der
 * Job als Thread ausgefuehrt.
 */
public class TimeServerJob extends Thread {

    private Socket socket;
    private String socketName;
    private BufferedReader reader;
    private PrintWriter writer;
    
    public TimeServerJob(Socket socket) throws Exception {
        // Dies ist das Socket, also die TCP-Verbindung, fuer welche der Job zustaendig ist.
        this.socket = socket;      
        // Speichere die zugehoerigen Verbindungsnummern
        socketName = socket.getLocalSocketAddress() + " <-> " + socket.getRemoteSocketAddress();
        
        // Die TCP-Verbindung stellt einen Eingangs- und einen Ausgangs-Datenstrom zur Verfuegung.
        InputStream input = socket.getInputStream();
        OutputStream output = socket.getOutputStream();
        
        // Die folgenden Objekte erlauben es, Textzeilen über diese Datenstroeme zu versenden. Wir speichern
        // sie im Objekt, damit die Methode `run()` sie verwenden kann.
        this.reader = new BufferedReader(new InputStreamReader(input));
        this.writer = new PrintWriter(output, true);
        
        // Job als Thread starten
        this.start();
    }

    // Diese Methode wird (automatisch) aufgerufen, um sich voll der Beantwortung aller Anfragen
    // von jener TCP-Verbindung zu widmen, fuer welche dieser Job zustaendig ist.
    public void run() {
        println("Client verbunden");
    
        try {
            // In einer Endlosschleife laufen, bis der Client die Verbindung abbricht
            while (true) {
                // Zunaechst die Anfrage des Clients lesen.
                String request = reader.readLine();
                
                // ... dann interpretieren und beantworten
                if (request.equals("UHRZEIT")) {
                    writer.println(new Date().toGMTString());
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
        catch (Exception e) {
            println("Verbindung wurde getrennt");
        }
    }
    
    // Eine Log-Nachricht auf der Konsole ausgeben
    private void println(String message) {
        System.out.println("Server " + socketName + ": " + message);
    }
}
