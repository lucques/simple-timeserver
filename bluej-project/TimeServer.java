import java.io.*;
import java.net.*;

/**
 * Objekte dieser Klasse repraesentieren einen TimeServer. Nach Aufruf von `startServer()` wird ein
 * Socket geoeffnet, welches TCP-Verbindungen annimmt und dann an `TimeServerJob`-Threads delegiert.
 */
public class TimeServer extends Thread {

    private int port;

    public TimeServer(int port) {
        this.port = port;
    }

    // Diese Methode wird automatisch aufgerufen von this.start(), wenn der Thread gestartet wird.
    public void run() {
        // Server-Empfangs-Socket oeffnen und auf Anfragen warten
        try (ServerSocket serverSocket = new ServerSocket(port)) {

            System.out.println("Server ist bereit fuer Verbindungen auf Port " + port);

            // In einer Endlosschleife eine Anfrage nach der anderen beantworten
            while (true) {
                // Die naechste Zeile blockiert so lange, bis eine neue Anfrage hereinkommt.
                // Diese stellt eine neue Verbindung dar, welche ueber 'socket' verwendet werden kann.
                Socket socket = serverSocket.accept();
                
                // Fuer jede Verbindung wird ein neuer TimeServerJob erstellt und in einem eigenen Thread gestartet.
                // Dadurch koennen mehrere Jobs parallel laufen.
                TimeServerJob job = new TimeServerJob(socket);
                job.start();
            }
        }
        // Fehlerbehandlung: Abbruch, wenn die Verbindung fehlschlaegt.
        catch (Exception ex) {
            System.out.println("Server: Fehler" + ex.getMessage());
        }
    }
      
    // Dies ist nur eine Methode, die das Starten des Servers hervorhebt.
    public void startServer() {
        this.start();
    }
}