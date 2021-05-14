import java.io.*;
import java.net.*;

/**
 * Ein Objekt dieser Klasse repraesentiert einen Server, welcher die Uhrzeit ausliefern kann. Nach Aufruf von `startServer()` wird ein
 * Socket geoeffnet, welches TCP-Verbindungen annimmt und die Bearbeitung der Anfragen dann an `TimeServerJob`-Threads delegiert.
 */
class TimeServer extends Thread {

    // Auf welchem Port wird der Server gestartet?
    private int port;

    // Dieser sogenannte Konstruktor wird fuer die Erzeugung eines `TimeServer`-Objekts aufgerufen. Der Nutzer
    // muss angeben, auf welchem Port der Server gestartet werden soll.
    TimeServer(int port) {
        this.port = port;
        
        // Server als Thread starten
        this.start();
    }

    // Diese Methode wird (automatisch) aufgerufen, wenn der Server-Thread gestartet wird.
    public void run() {
        // Server-Empfangs-Socket oeffnen und auf Anfragen warten
        // Das `try` sorgt dafuer, dass falls ein Fehler in der Verbindung in `serverSocket` auftritt,
        // z.B. die Verbindung abbricht, der TimeServer damit umgehen kann (Fehlermeldung auf dem
        // Bildschirm ausgeben).
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server ist bereit fuer Verbindungen auf Port " + port);

            // In einer Endlosschleife eine Anfrage nach der anderen beantworten
            while (true) {
                // Die naechste Zeile blockiert so lange, bis eine neue Verbindungsanfrage hereinkommt.
                // Die neu ausgehandelte TCP-Verbindung kann dann ueber `socket` verwendet werden.
                Socket socket = serverSocket.accept();
                
                // Fuer jede Verbindung wird ein neues `TimeServerJob`-Objekt erstellt und in einem eigenen Thread gestartet.
                // Somit koennen mehrere Jobs parallel laufen.
                TimeServerJob job = new TimeServerJob(socket);
            }
        }
        // Fehlerbehandlung: Abbruch, wenn die Verbindung fehlschlaegt.
        catch (Exception e) {
            System.out.println("Server: Fehler" + e.getMessage());
        }
    }
}