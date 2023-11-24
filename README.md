# Client-Server 8Ball in java

## Sequence Diagram
![](/UML/out/Sequence.svg)

## Client

### Comandi
Freccia in SU: Aumenta potenza di tiro
Freccia in GIU: Diminuisce potenza di tiro
Destra e Sinistra: Sposta la stecca intorno alla pallina bianca

### Class Diagram
![](/UML/out/Client_ClassDiagram.svg)

## Server

### Class Diagram
![](/UML/out/Server_ClassDiagram.svg)

### Debug
Nella classe GameManager e' presente una varibile debugMode che se impostata a true permette di utilizzare il gioco con un solo giocatore.
Sara' quindi necessario un solo client e durante il gioco sara' sempre il suo turno.

## TODO
- Migliorare interfaccia grafica del Client
- Aggiungere sezione iniziale per decidere il server su quale giocare
- Aggiungere la possibilita' di hostare piu' partite da uno stesso server
- Comunicazione al giocatore del fallo commesso.
