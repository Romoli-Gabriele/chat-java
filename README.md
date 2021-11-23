#PROTOCOLLO CHAT

TUTTE LE INFORMAZIONI PASSATE TERMINANO CON UNO \n
CLIENT
la prima cosa che deve fare è scrivere il proprio nome da mandare al server
se è valido va avanti altrimenti richiede un nuovo nome (non valido se già presente oppure se è uguale a “close” o “fine”)
Il client riceve sempre messaggi dal server anche mentre sta scrivendo
per abbandonare la chat si clicca l’apposito bottone che invierà nel campo destinatario la stringa “fine”
se il messaggio è per tutti si scrive come destinatario G
per chiudere tutta la chat si utilizza l’apposito bottone che invierà nel campo destinatario “stop” (solamente il primo entrato nella chat può se gli altri lo fanno riceve un output di mancanza di permessi)
quando un client si unisce riceve una Stringa con tutti gli utenti
Un combo box permette di selezionare il destinatario
per scrivere il messaggio bisogna andare nel campo di testo apposito per inviarlo cliccare il messaggio invio
quando premo invio prima viene inviato il destinatario e poi il messaggio
sopra i campi di testo c’è una area di testo che salva:
   tutti i messaggi con i relativi destinatari 
   se sono stati inviati a tutti o in privato
   l'entrata e l'uscita degli utenti
SERVER
il server chiede il nome al client 
Controlla se il nome è valido
se non è valido risponde “il nome già utilizzato” e attende l'invio del nuovo nome
crea un thread dedicato e lo aggiunge a un vector 
quando scrive un messaggio separa:   
privato NomeMittente> messaggio
pubblico NomeMittente.@g> messaggio
quando qualcuno si disconnette viene mandato il messaggio NomeMittente si è disconnesso
quando un utente si unisce il server invia la lista con tutti gli altri utenti all’interno della chat e invia il messaggio a tutti i client che si è unito un nuovo utente
le informazioni inviate agli utenti dal server hanno come mittente G
se gli arriva nel destinatario “fine” il client che lo ha inviato viene disconnesso
   //      “stop” e se il client che lo ha inviato è amministratore la chat viene chiusa
