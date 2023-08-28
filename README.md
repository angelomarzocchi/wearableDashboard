# wearableDashboard

Istruzioni per l' installazione : 

REQUISITI: 
1. MySql Server
2. MySql Connector/J
3. (Opzionale) MySql Workbench
4. Un IDE per Java (Es.: Intellij IDEA).

Istruzioni: 
1. Scaricare il codice sorgente
2. Modificare il file application.properties affinchè ci siano i dati del proprio database e della directory dei measurements
3. Modificare la voce 'spring.jpa.hibernate.ddl-auto' da 'none' a 'update' almeno per la prima volta che si fa girare il server


Le API senza autentificazione sono specificate in AuthenticationController, mentre tutte le altre sono definite in
MeasurementController e PatientCOntroller 
