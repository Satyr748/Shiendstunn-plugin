# Shiendstunn — Paper Plugin

Ein klassisches Server-Plugin für Paper/Spigot und Minecraft 1.21.1.

## Funktion
- `/fgt`: Survival wechselt zu Creative; jeder andere Spielmodus wechselt zu Survival.
- Keine OP-Rechte nötig, wenn die Standardberechtigung `shiendstunn.fgt` nicht durch ein Berechtigungs-Plugin überschrieben wird.
- Server-Konsole kann den Befehl nicht ausführen.

## Bauen
Voraussetzungen: JDK 21 und Gradle 8.10.2 oder kompatibel.

Im Projektordner ausführen:
```bash
gradle build
```

Die JAR liegt danach in `build/libs/Shiendstunn-1.0.0.jar`.

## Installieren
1. Du brauchst einen Paper- oder Spigot-Server für Minecraft 1.21.1.
2. Kopiere `Shiendstunn-1.0.0.jar` in den Serverordner `plugins`.
3. Starte den Server neu.
4. Im Spiel `/fgt` eingeben.

## Wichtig
Dieses Plugin läuft nicht direkt auf einem reinen Fabric-Server. Es benötigt Paper oder Spigot. Wenn du bei Fabric bleiben möchtest, brauchst du die Fabric-Mod.
