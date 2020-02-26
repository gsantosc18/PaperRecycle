package com.e.engapp.model;

import com.google.firebase.database.DatabaseReference;


public class ChatDAO implements DatabaseInterface<Chat> {
    private DatabaseReference reference;

    public ChatDAO (ConnectionInterface<DatabaseReference> connection) {
        reference = connection.get().child( "chat" );
    }

    @Override
    public Chat get(Chat object) {
        return null;
    }

    @Override
    public boolean save(Chat chat) {
        try {
            reference
                .push()
                .setValue( chat );
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean update(Chat object) {
        return false;
    }
}
