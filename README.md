# 🏃 MyFitnessTracker – Android App (Schulprojekt)

Ein vollständiger GPS-Fitness-Tracker für Android, entwickelt mit **Kotlin** und **Android Studio**,
speziell optimiert für das **Google Pixel 8**.

---

## 📱 Features

| Feature | Beschreibung |
|---|---|
| 🗺️ **Live-Karte** | Google Maps mit automatisch gezeichneter Route als blaue Linie |
| 📍 **GPS-Tracking** | Hochpräzises GPS alle 3 Sekunden, Foreground Service bleibt aktiv |
| 👟 **Schrittzähler** | Hardware Step Counter Sensor des Pixel 8 |
| 📏 **Distanz** | Echtzeit-Distanz in Metern / Kilometern |
| ⚡ **Geschwindigkeit** | Momentangeschwindigkeit über GPS-Speed |
| 📊 **Dashboard** | Tages- und Wochenübersicht mit Balkendiagramm |
| 💾 **Lokale DB** | Alle Daten in Room-Datenbank (kein Server nötig) |
| 🔔 **Notification** | Persistente Benachrichtigung zeigt Distanz und Tempo |

---

## 🏗️ Architektur (MVVM + Clean Architecture)

```
app/
├── data/
│   ├── model/
│   │   └── Models.kt          ← Run & RoutePoint (Room Entities)
│   └── db/
│       ├── Dao.kt             ← RunDao & RoutePointDao
│       ├── Database.kt        ← FitnessDatabase (Singleton)
│       └── Database.kt        ← FitnessRepository
│
├── service/
│   └── TrackingService.kt     ← Foreground Service (GPS + Sensoren)
│
└── ui/
    ├── MainActivity.kt        ← Navigation Host + Permissions
    ├── tracking/
    │   ├── MapFragment.kt     ← Google Maps + Route zeichnen
    │   └── TrackingViewModel.kt
    └── dashboard/
        ├── DashboardFragment.kt  ← Stats + Diagramm
        └── RunHistoryAdapter.kt  ← RecyclerView
```

### Verwendete Sensoren (Google Pixel 8)

| Sensor | Android API | Verwendung |
|---|---|---|
| `TYPE_STEP_COUNTER` | `SensorManager` | Absolute Schrittanzahl seit Gerätestart |
| GPS (FusedLocationProvider) | Google Play Services | Präzise Position, Höhe, Geschwindigkeit |

---

## 🚀 Einrichtung in Android Studio

### 1. Voraussetzungen

- Android Studio Hedgehog (2023.1.1) oder neuer
- Google Pixel 8 (oder Emulator mit GPS-Unterstützung)
- Google Maps API Key

### 2. Google Maps API Key erstellen

1. Gehe zu [Google Cloud Console](https://console.cloud.google.com/)
2. Erstelle ein neues Projekt (z. B. "MyFitnessTracker")
3. Aktiviere die **Maps SDK for Android**
4. Erstelle einen API-Key unter **APIs & Services → Credentials**
5. Kopiere die Datei `secrets.properties.template` → `secrets.properties`
6. Trage deinen Key ein:
   ```
   MAPS_API_KEY=AIzaSy...deinKey...
   ```
7. Füge `secrets.properties` zu `.gitignore` hinzu!

### 3. Projekt öffnen und starten

```bash
# Projekt öffnen
File → Open → MyFitnessTracker/

# Sync
File → Sync Project with Gradle Files

# Auf Pixel 8 deployen
Run → Run 'app'
```

### 4. Berechtigungen auf dem Gerät

Beim ersten Start werden folgende Berechtigungen angefragt:
- ✅ **Standort (genau)** – für GPS
- ✅ **Körperliche Aktivität** – für Schrittzähler  
- ✅ **Benachrichtigungen** – für Tracking-Notification
- ✅ **Standort im Hintergrund** – damit GPS auch bei minimierter App läuft

---

## 🛠️ Verwendete Bibliotheken

| Bibliothek | Version | Zweck |
|---|---|---|
| `play-services-maps` | 18.2.0 | Google Maps |
| `play-services-location` | 21.1.0 | FusedLocationProvider (GPS) |
| `room` | 2.6.1 | Lokale SQLite-Datenbank |
| `lifecycle-viewmodel` | 2.7.0 | MVVM ViewModel |
| `navigation-fragment` | 2.7.6 | Fragment-Navigation |
| `MPAndroidChart` | 3.1.0 | Balkendiagramm |
| `kotlinx-coroutines` | 1.7.3 | Asynchrone Programmierung |

---

## 📐 Datenfluss (Schematisch)

```
GPS-Sensor (alle 3s)
      │
      ▼
TrackingService (Foreground)
      │  ├── distanceM  (LiveData)
      │  ├── speedKmh   (LiveData)
      │  ├── stepCount  (LiveData)
      │  └── elapsedSec (LiveData)
      │
      ├──► Room DB (RoutePoint speichern)
      │
      ▼
TrackingViewModel (beobachtet LiveData)
      │
      ├──► MapFragment    (Route zeichnen, Stats anzeigen)
      └──► DashboardFragment (Tages-/Wochenüberblick)
```

---

## 🐛 Bekannte Einschränkungen (für Schulprojekt)

- Kalorien-Berechnung ist eine Schätzung (60 kcal/km bei ~70 kg)
- Höhenmeter werden noch nicht berechnet (Daten vorhanden in `RoutePoint.altitude`)
- Kein Benutzerprofil (Gewicht, Alter) → kann leicht ergänzt werden

---

## 💡 Erweiterungsideen

- [ ] Benutzerprofil (Name, Gewicht) für genauere Kalorienwerte
- [ ] Höhenprofil-Diagramm für einzelne Runs
- [ ] Export als GPX-Datei
- [ ] Zielzone (z. B. 5 km Ziel setzen)
- [ ] Herzfrequenz via Bluetooth-Brustgurt
- [ ] Dark Mode

---

*Entwickelt als Schulprojekt | Klasse: [Deine Klasse] | Datum: [Datum]*
