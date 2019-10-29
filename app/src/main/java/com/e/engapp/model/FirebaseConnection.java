package com.e.engapp.model;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

class FirebaseConnection implements ConnectionInterface<DatabaseReference>{
    private DatabaseReference databaseReference;

    public FirebaseConnection() {
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    public DatabaseReference get() {
        return databaseReference;
    }
}
