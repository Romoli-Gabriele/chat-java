# chat-java

PROTOCOLLO CHAT

TUTTE LE INFORMAZIONI PASSATE TERMINANO CON UNO \n

CLIENT
la prima cosa che deve fare è scrivere il proprio nome da mandare al server
scrive la prima linea a chi vuole inviare il messaggio( G x inviarlo a tutti) (Command Line Interface)
Il client riceve sempre messaggi dal server anche mentre sta scrivendo
per chiudere la chat si invia al server il messaggio fine
se il messaggio è per tutti si scrive come destinatario G
per chiudere tutta la chat si invia il messaggio stop (solamente il primo entrato nella chat può inviarlo se gli altri lo fanno riceve un output di mancanza di permessi)
quando un client si unisce riceve una Stringa con tutti gli utenti
per scrivere il messaggio bisogna andare nel campo di testo apposito per inviarlo cliccare il messaggio invio (Modalità GUI)
i campi di testo sono 2 uno per il destinatario e uno per il messaggio 
quando premo invio prima viene inviato il destinatario e poi il messaggio
sopra i campi di testo ci vuole uno schermo che stampa i messaggi (GUI)


SERVER
il server chiede il nome al client 
Controlla se il nome è valido
crea un thread dedicato e lo aggiunge a un vector 
quando scrive un messaggio separa:   
privato NomeMittente> messaggio
pubblico NomeMittente.@g> messaggio
quando qualcuno si disconnette viene mandato il messaggio NomeMittente si è disconnesso
quando un utente si unisce il server invia la lista con tutti gli altri utenti all’interno della chat e invia il messaggio a tutti i client che si è unito un nuovo utente
le informazioni inviate agli utenti dal server hanno come mittente G
