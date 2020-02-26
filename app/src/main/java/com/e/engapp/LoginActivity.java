package com.e.engapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.e.engapp.model.FirebaseConnection;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends Activity {
    private TextView txtEmail, txtSenha;
    private Button btnEntrar, btnIrCadastro;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_login );

        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            Intent i = new Intent( LoginActivity.this, HomeActivity.class );
            startActivity( i );
            finish();
        }

        btnIrCadastro = (Button) findViewById( R.id.btnIrCadastro );
        btnEntrar = (Button) findViewById( R.id.btnEntrar );

        txtEmail = findViewById( R.id.txtEmail );
        txtSenha = findViewById( R.id.txtSenha );

        btnIrCadastro.setOnClickListener( pressedBtnIrCadastro() );
        btnEntrar.setOnClickListener( pressedBtnEntrar() );
    }

    private View.OnClickListener pressedBtnIrCadastro() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( LoginActivity.this, CadastroActivity.class );
                startActivity( intent );
            }
        };
    }

    private View.OnClickListener pressedBtnEntrar() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( txtEmail.getText().toString().isEmpty() || txtSenha.getText().toString().isEmpty() ) {
                    Toast.makeText(LoginActivity.this,"Email ou Senha está em branco.", Toast.LENGTH_LONG).show();
                    return;
                }

                String email = txtEmail.getText().toString(),
                        senha = txtSenha.getText().toString();

                Toast.makeText( LoginActivity.this, "Aguarde enquanto verifico suas informações", Toast.LENGTH_LONG ).show();
                btnEntrar.setEnabled( false );
                btnIrCadastro.setEnabled( false );
                FirebaseConnection connection = new FirebaseConnection();
                connection.getInstanceAuth().signInWithEmailAndPassword( email, senha ).addOnCompleteListener( LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        btnEntrar.setEnabled( true );
                        btnIrCadastro.setEnabled( true );
                        if (!task.isSuccessful()) {
                            Toast.makeText( LoginActivity.this, "Email ou Senha inválidos.", Toast.LENGTH_LONG ).show();
                            return;
                        }

                        Intent i = new Intent( LoginActivity.this, HomeActivity.class );

                        startActivity( i );
                        finish();
                    }
                } );
            }
        };
    }
}
