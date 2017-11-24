package jirapinya58070014.kmitl.unify;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class FirebaseConnection {
    public FirebaseConnection() {
    }

    public DatabaseReference getDatabase() {
        return FirebaseDatabase.getInstance().getReference();
    }

    public DatabaseReference getDatabase(String string) {
        return FirebaseDatabase.getInstance().getReference().child(string);
    }

    public StorageReference getStorage() {
        return FirebaseStorage.getInstance().getReference();
    }

    public StorageReference getStorage(String string) {
        return FirebaseStorage.getInstance().getReference().child(string);
    }

}