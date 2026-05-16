# 🏃 MyFitnessTracker – Android App (Schulprojekt)

Ein vollständiger GPS-Fitness-Tracker für Android, entwickelt mit **Kotlin** und **Android Studio**,
speziell optimiert für moderne Smartphones (z.B. **Google Pixel 8**).

---

## 📱 Features

| Feature | Beschreibung                                                                            |
|---|-----------------------------------------------------------------------------------------|
| 🗺️ **Live-Karte** | Google Maps mit automatisch gezeichneter Route als blaue Linie.                         |
| 📍 **GPS-Tracking** | Hochpräzises GPS (3s Intervall), Foreground Service für Tracking im Hintergrund.        |
| 👟 **Schrittzähler** | Nutzung des Schrittzählers vom Handy für präzise Messungen.                             |
| 🔥 **Kalorien-Rechner**| Berechnung basierend auf Distanz, Gewicht und Aktivitätsfaktor (0.9).                   |
| 📊 **Dashboard** | Tages-Stats, Wochenbericht (Balkendiagramm) und Schnellzugriff auf das letzte Training. |
| ⏱️ **Historie** | Eigene Seite für alle vergangenen Läufe mit detaillierter Zeitspanne (von-bis).         |
| 🗺️ **Routen-Details**| Klick auf Training zeigt gelaufene Strecke auf einer Karte an.                          |
| 📤 **GPX Export** | Export der Läufe als standardisierte GPX-Datei zum Teilen oder für Strava/Komoot.       |
| 🌙 **Dark Mode** | Vollständige Unterstützung für dunkles Design, inklusive angepasstem Google Maps Stil.  |
| 🔔 **Interaktive Notif**| Steuerung (Pause/Stopp) und Live-Daten direkt in der Statusleiste.                      |

---

## 🏗️ Architektur

```
app/
├── data/
│   ├── model/
│   │   └── Models.kt          ← Run & RoutePoint (Room Entities)
│   └── db/
│       ├── Dao.kt             ← RunDao & RoutePointDao
│       ├── Database.kt        ← FitnessDatabase & Repository
│
├── service/
│   └── TrackingService.kt     ← Foreground Service (GPS, Sensoren, Notifications)
│
├── util/
│   ├── ProfileManager.kt      ← SharedPreferences (Name, Gewicht, Ziele)
│   ├── GpxExporter.kt         ← GPX XML-Generierung & FileProvider
│   └── MockDataManager.kt     ← Simulationsdaten-Generator
│
└── ui/
    ├── MainActivity.kt        ← Navigation Host & Permissions
    ├── tracking/
    │   ├── MapFragment.kt     ← Live-Karte & Tracking-UI
    │   └── TrackingViewModel.kt
    ├── history/
    │   ├── HistoryFragment.kt ← Liste aller Trainingseinheiten
    │   └── RunDetailsDialogFragment.kt ← Routenvorschau & GPX-Teilen
    └── dashboard/
        └── DashboardFragment.kt  ← Wochen-Diagramm & Tages-Zusammenfassung
```

### Verwendete Sensoren

| Sensor | Android API | Verwendung |
|---|---|---|
| `TYPE_STEP_COUNTER` | `SensorManager` | Hardware-basierte Schrittzählung seit Systemstart. |
| GPS (FusedLocationProvider) | Google Play Services | Präzise Position, Geschwindigkeit und Distanzberechnung. |

---

## 🚀 Einrichtung in Android Studio

### 1. Voraussetzungen

- Android Studio Jellyfish (2023.3.1) oder neuer
- Java 17 (Gradle JDK Einstellung)
- Google Maps API Key mit aktivierter "Maps SDK for Android"

### 2. Google Maps API Key konfigurieren

1. Erstelle einen API-Key in der [Google Cloud Console](https://console.cloud.google.com/).
2. Füge deinen **SHA-1 Fingerprint** (aus `./gradlew signingReport`) zum Key hinzu.
3. Erstelle die Datei `secrets.properties` im Hauptverzeichnis:
   ```properties
   MAPS_API_KEY=AIzaSy...deinKey...
   ```

### 3. Demo-Daten laden

Um die App sofort mit Diagrammen und Routen zu testen:
1. App starten und zum **Dashboard** gehen.
2. Oben auf die **Profil-Karte** klicken.
3. Den Button **"Demo-Daten laden"** wählen.

---

## 🛠️ Verwendete Bibliotheken

- **Google Maps SDK**: Kartenanzeige & Routenzeichnung.
- **Jetpack Room**: Lokale SQLite-Datenbank für Runs und Wegpunkte.
- **Navigation Component**: Fragment-Navigation via Bottom-Nav.
- **MPAndroidChart**: Visualisierung des Wochenberichts.
- **AndroidX Media**: Modernes Layout für die Tracking-Notification.

---

## 💡 Besondere Funktionen für das Projekt

- **GPX Export**: Zeigt die Verwendung von XML-Standards und dem Android `FileProvider`.
- **Foreground Service**: Garantiert unterbrechungsfreies Tracking auch bei gesperrtem Bildschirm.
- **Dark Mode Integration**: Dynamische Farbanpassung der UI und der Google Maps API.
- **Snapshot-Speicherung**: Verhindert Datenverlust beim Beenden der App über die Nachrichtenleiste.

---

*Entwickelt als Schulprojekt bei Herrn Reimann dem Top G!
