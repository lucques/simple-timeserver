# Demo einer Client-Server-Architektur: Timeserver

Dieses Projekt demonstriert eine einfache Client-Server-App, die auf TCP aufbaut und in Java geschrieben ist. Implementiert ist eine einfache Abfrage der Uhrzeit. Das Projekt dient als Vorlage für kreative Erweiterungen von Client und Server. Erklärungen für TCP/Java-Anfänger(innen) finden sich unter [`erklaerungen.md`](erklaerungen.md).

## Verwendung

1) Öffne den Ordner [`simple-timeserver`](simple-timeserver) mit BlueJ.
2) Erzeuge ein neues `TimeServer`-Objekt und gib den gewünschten Port ein, z.B. `1234`. Dies startet unmittelbar den Server in einem eigenen Thread.
3) Erzeuge ein neues `TimeClient`-Objekt und gib den Port des Servers ein (hier z.B. `1234`). Eine Verbindung zum Server wird unmittelbar aufgebaut.
4) Erfrage die Uhrzeit mit der `askTime()`-Methode des Clients.

Die Ausgaben werden in der Konsole angezeigt.

![CFG](misc/screenshot.png)

## Anwendungsschicht-Protokoll

Folgendes Anwendungsschicht-Protokoll wird vom `TimeServer` implementiert.
- Anfrage `UHRZEIT` wird beantwortet mit bspw. `13 May 2021 14:11:28 GMT` (GMT-Format)
- Anfrage `JAHR` wird beantwortet mit bspw. `2021`
- Anfrage `TSCHUESS` bewirkt die serverseitige Trennung der Verbindung

Ideen für mögliche Erweiterungen:
- Eine Abfrage des aktuellen Wochentags
- Bei der Abfrage soll die Zeitzone mit angegeben werden können. Der Server antwortet dann mit der Uhrzeit für die angegebene Zeitzone.
- Ein Client kann sich als Administrator anmelden. Er muss dafür ein Passwort senden und kann, falls er sich erfolgreich authentifiziert hat, die Uhrzeit manipulieren.

## Hinweise

Das Projekt wurde mit Fokus auf die Kommunikation via Sockets erstellt. Der Java-Code wurde so einfach wie möglich gehalten, damit auch Java-Anfänger einen guten Einblick erhalten.

## Lizenz

MIT