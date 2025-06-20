## Περίπτωση Χρήσης 7: Αξιολόγηση πάρκινγκ ##
**Βασική ροή:**
- Ο χρήστης εκφράζει την επιθυμία του να αξιολογήσει έναν χώρο στάθμευσης.
- Το σύστημα ελέγχει σε ποια πάρκινγκ έχει κάνει κράτηση ο χρήστης και του τα εμφανίζει.
- Ο χρήστης επιλέγει ένα πάρκινγκ.
- Το σύστημα φορτώνει την φόρμα αξιολόγησης.
- Ο χρήστης επιλέγει τον αριθμό των αστεριών για το πάρκινγκ που αξιολογεί.
- Ο χρήστης εκφράζει την άποψή του εντός ορίων (200 χαρακτήρες) για το πάρκινγκ που αξιολογεί.
- Το σύστημα την αποθηκεύει και την εισάγει στην λίστα με τις υπόλοιπες κριτικές για το πάρκινγκ.
- Το σύστημα στέλνει την κριτική στο αντίστοιχο πάρκινγκ σε μορφή email.

**Εναλλακτική Ροή 1:**  
7.α.1 Ο χρήστης δεν έχει κάνει κράτηση ακόμη μέσω της εφαρμογής.  
7.α.2 Το σύστημα ενημερώνει τον χρήστη ότι δεν βρήκε κάποια κράτηση σε πάρκινγκ, άρα δεν μπορεί να αξιολογήσει ακόμα.  
7.α.3 Το σύστημα ανακατευθύνει τον χρήστη στην "Αρχική Οθόνη".  

**Εναλλακτική Ροή 2:**  
7.β.1 Ο χρήστης έχει ξεπεράσει το όριο στην γραπτή αξιολόγηση.  
7.β.2 Το σύστημα ενημερώνει τον χρήστη ότι ξεπέρασε το επιτρεπόμενο όριο χαρακτήρων.  
7.β.3 Η περίπτωση χρήσης συνεχίζεται στο βήμα 6 της βασικής ροής.  
