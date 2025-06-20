## Περίπτωση Χρήσης 12 : Προβολή στατιστικών ##

**Βασική Ροή:**

- Ο υπάλληλος επιλέγει  "Προβολή Στατιστικών" από το σύστημα διαχείρισης στάθμευσης.
- Το σύστημα αναζητά τις κατηγορίες που έχει διαθέσιμες για ανάλυση.
- Το σύστημα ζητά από τον χρήστη να επιλέξει τον τύπο αναφοράς για τον οποίο επιθυμεί να δει τα στατιστικά στοιχεία (π.χ κρατήσεις, έσοδα).
- Ο χρήστης επιλέγει έναν τύπο αναφοράς από τις διαθέσιμες επιλογές.
- Το σύστημα ανακτά τα δεδομένα για τον επιλεγμένο τύπο αναφοράς από τη βάση δεδομένων. 
- Το σύστημα επεξεργάζεται τα δεδομένα.
- Το σύστημα παρουσιάζει τα στατιστικά στοιχεία στην οθόνη του χρήστη, εμφανίζοντας τις σχετικές πληροφορίες και μετρήσεις.
- Ο χρήστης ολοκληρώνει την προβολή της αναφοράς και επιστρέφει στο κύριο μενού.

**Εναλλακτική Ροή 1 :**  
12.α.1  Στο βήμα 4 της βασικής ροής, το σύστημα προσπαθεί να ανακτήσει δεδομένα για τον τύπο αναφοράς που επέλεξε ο χρήστης και διαπιστώνει ότι δεν υπάρχουν διαθέσιμα δεδομένα για την αναφορά αυτή για την τρέχουσα χρονική περίοδο ή για τα κριτήρια που έχουν οριστεί από προεπιλογή.  
12.α.2 Το σύστημα εμφανίζει ένα ενημερωτικό μήνυμα στον χρήστη, όπως "Δεν υπάρχουν πληροφορίες για την αναφορά αυτή, στην παρούσα χρονική στιγμή." και επιστρέφει στο κύριο μενού.  
