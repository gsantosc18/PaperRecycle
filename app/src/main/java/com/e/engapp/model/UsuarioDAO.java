package com.e.engapp.model;

import android.util.Log;

import com.google.firebase.FirebaseException;
import com.google.firebase.database.DatabaseReference;

import java.util.List;

public class UsuarioDAO implements DatabaseInterface<Usuario> {
    private DatabaseReference reference;

    public UsuarioDAO(ConnectionInterface<DatabaseReference> connection) {
        reference = connection.get().child( "user" );
    }

    @Override
    public List<Usuario> list() {
        return null;
    }

    @Override
    public Usuario get(Object object) {
        return null;
    }

    @Override
    public boolean save(Usuario usuario) {
        try {
            reference.child( usuario.getID() ).setValue( usuario );
            return true;
        } catch (Exception fe) {
            Log.e("Erro method SAVE in UsuarioDAO",fe.getMessage());
            return false;
        }
    }

    @Override
    public Usuario update(Usuario object, Object param) {
        return null;
    }
}
