# Erklärungen

## Alles ist ein Objekt
Da Java eine objektorientierte Programmiersprache ist, gilt die Devise „Alles ist ein Objekt“. Wir haben hier folgende Komponenten, die als Objekte dargestellt werden:
- `TimeServer`: Ein Server-Programm
- `TimeServerJob`: Ein Unterprogramm des Servers, der für eine einzelne TCP-Verbindung zuständig ist
- `TimeClient`: Eine Client-Programmschnittstelle für den Benutzer
- `Socket`: Ein Socket (also ein Zugang zu einer TCP-Verbindung)
- `(Print)` `Writer`: Ein Objet, welches ein `Socket`-Objekt ummantelt. Es nimmt Text entgegen und verschickt diese über das `Socket`, also über die TCP-Verbindung
- `(Buffered)` `Reader`: Ein Objekt, welches ein `Socket`-Objekt ummantelt. Es liest Textzeilen aus dem `Socket`, empfängt diese also über die TCP-Verbindung

## Threads
In Java kann ein Programm sogenannte Threads erzeugen. Es handelt sich um Unterprogramme, welche nebeneinander laufen (man sagt auch „nebenläufig“). Wenn unser Rechner also mehrere CPU-Kerne besitzt, so können die Threads echt parallel ausgeführt werden. Das Konzept von Threads wird für den Server verwendet:

- Der Server selbst läuft in einem eigenen Thread (er läuft also unabhängig im Hintergrund)
- Für jede Client-Verbindung, die der Server eingeht, wird ein eigener Thread gestartet, ein sogenannter „Job“, der sich um diese eine Client-Verbindung kümmert. Dies ist wichtig, damit mehrere Client-Verbindungen parallel bedient werden können.

Ein Aufruf von `start()` sorgt dafür, dass der Thread gestartet wird. Der Code, der dann nebenläufig ausgeführt wird, wird in der `run()`-Methode definiert.

## Komponenten

Drei der oben genannten Objekt-Typen, also Klassen, bilden die Hauptkomponenten und werden im Folgenden genauer beschrieben.
- `TimeServer`: Ein `TimeServer`-Objekt soll einen Server repräsentieren.
    - *Attribute*:
        - `port`: Auf welchem Port soll der Server gestartet werden?
    - *Verwendung*: Bei der Objektkonstruktion muss das `port`-Attribut festgelegt werden. In der `run()`-Methode wird dann der Server gestartet, indem ein TCP-Socket mit Portnummer `port` geöffnet wird. Anschließend wird auf Verbindungsanfragen gehorcht. Für jede Anfrage wird ein `TimeServerJob`-Objekt erzeugt.
- `TimeServerJob`: Ein `TimeServer`-Objekt soll für die Bedienung einer TCP-Verbindung stehen.
    - *Attribute*:
        - `socket`: Das Socket, welches die TCP-Verbindung repräsentiert
        - `socketName`: Enthält IP-Adressen und Portnummern
        - `reader`: Ein Reader-Objekt, welches die Methode `println()` anbietet, um Text über das Socket zu versenden
        - `writer`: Ein Writer-Objekt, welches die Method `readLine()` anbietet, um Text über das Socket zu lesen
    - *Verwendung*: Bei der Objektkonstruktion wird das `socket`-Attribut übergeben, für welches der Job zuständig ist. Anschließend wird so lange das Uhrzeit-Abfrage-Protokoll ausgeführt (s.a. [`README.md`](README.md)), bis die Verbindung beendet wird. 
- `TimeClient`: Ein `TimeClient`-Objekt soll einen Client repräsentieren.
    - *Attribute*:
        - `socket`: Das Socket, welches die TCP-Verbindung repräsentiert
        - `socketName`: Enthält IP-Adressen und Portnummern
        - `reader`: Ein Reader-Objekt, welches die Methode `println()` anbietet, um Text über das Socket zu versenden
        - `writer`: Ein Writer-Objekt, welches die Method `readLine()` anbietet, um Text über das Socket zu lesen
    - *Verwendung*: Bei der Objektkonstruktion wird eine neue TCP-Verbindung gestartet. Mit der Methode `askTime()` kann dann die Zeit vom Server erfragt werden und mit der Methode `shutdown` die TCP-Verbindung beendet werden. 