package com.e.engapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.e.engapp.model.FirebaseConnection;
import com.e.engapp.model.Usuario;
import com.e.engapp.model.UsuarioDAO;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class CadastroActivity extends Activity {
    private TextView txtNome, txtEmail, txtSenha;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_cadastro );

        txtNome = findViewById( R.id.txtNome );
        txtEmail = findViewById( R.id.txtEmail );
        txtSenha = findViewById( R.id.txtSenha );

        Button btnSouCadastrado = (Button) findViewById( R.id.btnSouCadastrado );
        Button btnCadastrar = (Button) findViewById( R.id.btnCadastrar );

        btnSouCadastrado.setOnClickListener( pressedBtnSouCadastrado() );
        btnCadastrar.setOnClickListener( pressedBtnCadastrar() );
    }

    private View.OnClickListener pressedBtnSouCadastrado() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        };
    }

    private View.OnClickListener pressedBtnCadastrar() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            final FirebaseConnection firebaseConnection = new FirebaseConnection();
            FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

            try {

                firebaseAuth.createUserWithEmailAndPassword( txtEmail.getText().toString(), txtSenha.getText().toString() )
                    .addOnCompleteListener( CadastroActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {

                            Toast.makeText( CadastroActivity.this, "Não foi possível realizar o cadastro.", Toast.LENGTH_SHORT ).show();

                        } else {

                            FirebaseUser firebaseUser = task.getResult().getUser();
                            Usuario usuario = new Usuario();
                            UsuarioDAO usuarioDAO = new UsuarioDAO( firebaseConnection );

                            usuario.setNome( txtNome.getText().toString() );
                            usuario.setEmail( txtEmail.getText().toString() );
                            usuario.setId( firebaseUser.getUid() );

                            usuarioDAO.save( usuario );

                            Toast.makeText( CadastroActivity.this, "Cadastro realizado com sucesso!", Toast.LENGTH_SHORT ).show();

                            onBackPressed();
                        }
                        }
                    } );

            } catch (Exception ex) {
                Toast
                    .makeText(
                        CadastroActivity.this,
                        "Houve um erro no cadastro, verifique os campos informados.",
                            Toast.LENGTH_LONG
                    )
                    .show();
            }
            }
        };
    }
}
