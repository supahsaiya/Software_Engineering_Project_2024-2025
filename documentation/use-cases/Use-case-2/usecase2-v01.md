## Περίπτωση Χρήσης 2: Προβολή λεπτομερειών στάθμευσης ##
**Βασική Ροή:**
- Το σύστημα αρχικά ανακτά και εμφανίζει τις βασικές πληροφορίες για την επιλεγμένη τοποθεσία στάθμευσης (π.χ όνομα, διεύθυνση, ωράριο λειτουργίας-κλειστό/ανοιχτό).
- Το σύστημα ελέγχει και επιβεβαιώνει ότι υπάρχει διαθέσιμη η ενημέρωση διαθεσιμότητας σε πραγματικό χρόνο για το πάρκινγκ.
- Το σύστημα εμφανίζει σε πραγματικό χρόνο τον αριθμό των διαθέσιμων θέσεων για την επιλεγμένη τοποθεσία στάθμευσης.
- Ο χρήστης ζητά να δει περισσότερες πληροφορίες για το πάρκινγκ.
- Το σύστημα εμφανίζει πρόσθετες λεπτομέρειες όπως αναλυτικός τιμοκατάλογος, παροχές, φωτογραφίες, κριτικές και αξιολογήσεις.
- Ο χρήστης θέλει να κάνει κράτηση θέσης για το επιλεγμένο πάρκινγκ.
- Το σύστημα επιβεβαιώνει την τοποθεσία.
- Καλείται η περίπτωση χρήσης “Κράτηση θέσης Στάθμευσης”.

**Εναλλακτική Ροή 1:**  
2.α.1: Η πληροφορία διαθεσιμότητας σε πραγματικό χρόνο δεν είναι διαθέσιμη για την επιλεγμένη τοποθεσία στάθμευσης.  
2.α.2: Το σύστημα εμφανίζει ένα μήνυμα που ενημερώνει τον χρήστη ότι η διαθεσιμότητα σε πραγματικό χρόνο δεν είναι προς το παρόν διαθέσιμη για αυτήν την τοποθεσία και εμφανίζει την τελευταία γνωστή διαθεσιμότητα (αν υπάρχει) με ένδειξη της ώρας ενημέρωσης.  
2.α.3: Η περίπτωση χρήσης συνεχίζεται από το βήμα 1 της βασικής ροής.

**Εναλλακτική Ροή 2:**  
2.β.1: Ο χρήστης δεν ικανοποιείται με το επιλεγμένο πάρκινγκ ή δεν θέλει να κάνει κράτηση από την εφαρμογή.  
2.β.2: Το σύστημα ανακατευθύνει τον χρήστη στην “Αρχική Οθόνη”.
