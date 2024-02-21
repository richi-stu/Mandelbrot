# Mandelbrot-PVS
Darstellung vom Mandelbrot via verteilter Berechnung

Client: src/Client/Mandebrot_Client/src

Server: src/Server/Mandebrot_Server/src

## Starten des Servers: 
- In der Konsole "java Main Zoomfactor MidPointX MidPointY" eingeben.
- Zoomfactor > 1.0
- MidPointX Beispielwert = -0.743643887036151
- MidPointY Beispielwert = 0.13182590420533
- Alternativ in IntelliJ: Die Main.java auswählen und als Application starten.

## Starten der Clients:
- In der Konsole "java Main IP-Adresse" eingeben.

## Funktionalität
- Die Grundfunktionaliät steht soweit. Das Programm läuft auf verschiedenen Systemen. Die Bilder werden richtig dargestellt und in richtiger Reihenfolge berechnet.
- Fehler: Meistens aller 400 - 500 Gesamtiterationen gibt es Fehler beim Indexieren. Die Lücke entsteht immer nur beim letzten Client.
- Vermutung: Es ist schwer zu sagen, ob es am Server oder am Client liegt. Die Tendenz neigt sich aber mehr zur Server-Richtung.
- Konsequenz: Das Bild bleibt stehen. (Durch Code) Der Server schließt sich. Die Bilder werden dann sehr schnell berechnet, außer beim letzten Client.
