package com.e.engapp.model;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseConnection implements ConnectionInterface<DatabaseReference>{
    private DatabaseReference databaseReference;

    public FirebaseConnection() {
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    public DatabaseReference get() {
        return databaseReference;
    }
    public FirebaseAuth getInstanceAuth() {
        return FirebaseAuth.getInstance();
    }

    public FirebaseDatabase getInstance() {
        return FirebaseDatabase.getInstance();
    }
}
