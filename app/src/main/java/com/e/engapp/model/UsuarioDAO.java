package com.e.engapp.model;

import com.google.firebase.database.DatabaseReference;

public class UsuarioDAO implements DatabaseInterface<Usuario> {
    private DatabaseReference reference;

    public UsuarioDAO(ConnectionInterface<DatabaseReference> connection) {
        reference = connection.get().child( "user" );
    }

    @Override
    public Usuario get(final Usuario usuario) {
        return null;
    }

    @Override
    public boolean save(Usuario usuario) {
        try {
            DatabaseReference node = reference.child( usuario.getId() );
            node.setValue( usuario );
            return true;
        } catch (Exception fex) {
            fex.getStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Usuario usuario) {
        try {
            reference.child( usuario.getId() ).setValue( usuario );
            return true;
        } catch (Exception fex) {
            fex.getStackTrace();
            return false;
        }
    }
}
