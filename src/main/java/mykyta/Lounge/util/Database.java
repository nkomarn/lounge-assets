package mykyta.Lounge.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.ExecutionException;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.FieldValue;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;

import org.bukkit.OfflinePlayer;

public class Database {
    public static Firestore db;

    public void initialize() {
        try {
            InputStream serviceAccount = new FileInputStream(Paths.get("").toAbsolutePath().toString() + File.separator + "Firebase.json");
            GoogleCredentials credentials = GoogleCredentials.fromStream(serviceAccount);
            FirebaseOptions options = new FirebaseOptions.Builder().setCredentials(credentials).build();
            FirebaseApp.initializeApp(options);
            db = FirestoreClient.getFirestore();
        }
        catch (IOException e) {e.printStackTrace();}
    }

    public String getDiscordID(String p) {
        try {
           ApiFuture<QuerySnapshot> future = db.collection("users").whereEqualTo("minecraft", p.toLowerCase()).get();
           List<QueryDocumentSnapshot> documents = future.get().getDocuments();
           for (DocumentSnapshot document : documents) {
               return document.getId();
           }
           return "";
        } 
        catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return "";
        }
    }

    public long getCoins(OfflinePlayer p) {
        String discord = this.getDiscordID(p.getName());
        if (discord.length() == 0) return 0;
        try {
            DocumentReference ref = db.collection("users").document(discord);
            ApiFuture<DocumentSnapshot> future = ref.get();
            DocumentSnapshot snapshot = future.get();
            return snapshot.getLong("coins").intValue();
        } 
        catch (InterruptedException | ExecutionException e) {
            e.printStackTrace(); 
            return 0;
        }
    }

    public void incrementCoins(OfflinePlayer p, double amount) {
        String discord = this.getDiscordID(p.getName());
        if (discord.length() == 0) return;
        db.collection("users").document(discord).update("coins", FieldValue.increment(amount));
    }
}