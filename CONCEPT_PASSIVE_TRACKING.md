# Konzept: Passives Hintergrund-Tracking & Aktivitätserkennung

## 1. Kernziel
Die App soll den Nutzer den ganzen Tag über begleiten. Es gibt eine strikte Trennung zwischen bewusstem Training (Sport) und dem passiven Loggen des Alltags.

## 2. Navigations-Struktur: Das Hauptmenü
Beim Start der App landet der Nutzer in einem zentralen Auswahlmenü (oder Dashboard mit Fokus auf diese zwei Modi):

* **A. Alltags-Modus (Passiv)**:
    * "Set & Forget": Einmal einschalten, die App läuft im Hintergrund.
    * Erstellt eine Timeline des Tages.
* **B. Sport-Modus (Aktiv)**:
    * Manuelle Auswahl der Sportart (Laufen, Radfahren, Gehen).
    * Manueller Start/Stopp für präzise Leistungsdaten.

## 3. Tracking-Modi im Detail

### Alltags-Modus (Alltag)
* **Intervall**: Dynamisch gesteuert durch die Activity Recognition API (Akkuschonend).
* **Logik**: Automatische Erkennung von Stillstand, Gehen, Radfahren und Fahrzeugen.
* **Dokumentation**: Alle Routen des Tages werden in einer "Tages-Timeline" zusammengefasst.
* **Ziel**: Ein lückenloses Protokoll der Mobilität ohne Nutzerinteraktion.

### Sport-Modus (Training)
* **Intervall**: Jede Sekunde (höchste Präzision).
* **Sportarten**: Laufen, Fahrradfahren, Gehen (Power-Walking).
* **Features**: Live-Karte (Route mit Start- & Endpunkt), Sprachausgabe, detaillierte Statistiken (Distanz, Durchschnittsgeschwindigkeit, Pace, Kalorien, Höhenmeter).

## 4. Automatische Aktivitätserkennung & Optimierungen
Einsatz der Google *Activity Recognition API* mit optimierter Akkuschonung und System-Integrität:
* **STILL**: GPS wird komplett abgeschaltet (Löst das Problem des dauerhaften Abfragens bei Inaktivität).
* **WALKING / RUNNING / ON_BICYCLE**: GPS wird aktiviert und trackt im dynamischen 10-Sekunden-Intervall.
* **IN_VEHICLE**: Erkennt Autofahrten (Wichtig für die saubere Trennung von Sport und Transport).
* **Hintergrund-Stabilität (Android-Restriktionen)**: Implementierung eines persistenten *Foreground Services* mit einer dezenten, permanenten Benachrichtigung ("Passives Tracking aktiv"), um ein Beenden durch das Betriebssystem zu verhindern.

## 5. Daten-Management & Komprimierung
Um die lokale Datenbank vor Datenmüll und Überlastung zu schützen, greift nach 24 Stunden eine automatische Nachbearbeitung:
* **Alltags-Punkte**: Werden zu einem glatten Pfad (Vektoren) komprimiert. Redundante Stillstands-Punkte werden vollständig bereinigt.
* **Sport-Sessions**: Behalten dauerhaft ihre maximale 1-Sekunden-Präzision für eine detaillierte Historie.

## 6. Timeline-View (Ende des Tages)
Eine übersichtliche Ansicht, die dem Nutzer chronologisch zeigt:
* **Die Tageskarte**: Alle zurückgelegten Wege des Tages (inkl. Routenansicht mit Start- und Endpunkt), farblich nach Aktivität sortiert (z. B. Auto vs. Fußwege).
* **Die Segment-Liste**: Eine saubere Aufschlüsselung der Mobilität mit Statistiken (Zeitraum, Dauer, Distanz, Durchschnittsgeschwindigkeit):
    * *08:15 - 08:45 Uhr* (30 Min.): Pendeln (Auto, 12km, Ø 24 km/h)
    * *12:00 - 12:30 Uhr* (30 Min.): Spaziergang (Gehen, 2km, Ø 4 km/h)
    * *18:00 - 19:00 Uhr* (60 Min.): **Sport: Laufen** (Manuelle Session, 10km, Ø 10 km/h – visuell hervorgehoben)

## 7. Offene Fragen / Design-Entscheidungen
* Wenn ein Nutzer im Alltags-Modus längere Zeit zügig zu Fuß unterwegs ist (ohne den Sport-Modus explizit zu starten): Soll dieses Segment in der Timeline einfach als standardmäßiges „Gehen (Alltag)“ benannt werden oder soll es begrifflich (z. B. „Aktive Bewegung“) von kurzen Wegen (z. B. 2 Minuten zum Auto) abgegrenzt werden?