package com.e.engapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.icu.util.GregorianCalendar;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.e.engapp.model.Bloco;
import com.e.engapp.model.Chat;
import com.e.engapp.model.ChatDAO;
import com.e.engapp.model.EmailSend;
import com.e.engapp.model.FirebaseConnection;
import com.e.engapp.model.Notification;
import com.e.engapp.model.Setor;
import com.e.engapp.model.Usuario;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private static String CORINGA = "Selecionar";

    private FirebaseConnection firebaseConnection;
    private List<String> blocos, setores;
    private List<Bloco> blocoData;
    private ArrayAdapter blocoAdapter, setorAdapter;

    private Spinner blocoSelect, setorSelect;
    private Button btnEncheu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_home );

        // Configuracoes do Barra de Acao do topo
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setDisplayShowTitleEnabled(true);

        // Recupera os elementos da tela
        blocoSelect = (Spinner) findViewById( R.id.blocoSelect );
        setorSelect = (Spinner) findViewById( R.id.setorSelect );
        btnEncheu = (Button) findViewById( R.id.btnEncheu );

        // Inicia a conexao com o banco de dados
        firebaseConnection = new FirebaseConnection();

        // Inicia as variaveis que irao receber as informacoes
        blocos = new ArrayList<>();
        setores = new ArrayList<>();
        blocoData = new ArrayList<>();

        // Inicia os adaptadores dos spinners
        blocoAdapter = new ArrayAdapter<String>( this, android.R.layout.simple_spinner_dropdown_item, blocos );
        setorAdapter = new ArrayAdapter<String>( this, android.R.layout.simple_spinner_dropdown_item, setores );

        // Adiciona valores padroes para as listas
        blocos.add(CORINGA);
        setores.add(CORINGA);

        // Seta os adaptadores para os spinners
        blocoSelect.setAdapter( blocoAdapter );
        setorSelect.setAdapter( setorAdapter );

        firebaseConnection.get().child( "bloco" ).addValueEventListener( getBlocos( ) );

        // Evento ativado quando selecionar um item
        blocoSelect.setOnItemSelectedListener( onSelectedItem() );

        btnEncheu.setOnClickListener( pressBtnEncheu() );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected( item );
            switch (item.getItemId()) {
                case R.id.btnSairMenu:
                    FirebaseAuth.getInstance().signOut();
                    Intent i = new Intent( HomeActivity.this, MainActivity.class );
                    startActivity( i );
                    finish();
                    break;
            }
        return true;
    }

    private ValueEventListener getBlocos() {
        return new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                blocoData.clear();
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()) {
                    Bloco bloco = dataSnapshot1.getValue( Bloco.class );
                    blocos.add( bloco.getNome() );
                    blocoData.add( bloco );
                }
                blocoAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        };
    }

    private AdapterView.OnItemSelectedListener onSelectedItem() {
        return new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if ( blocoData.isEmpty() ) return;

                if ( position == 0 && !setores.get( 0 ).equals( CORINGA ) ) {
                    setores.clear();
                    setores.add( CORINGA );
                    setorAdapter.notifyDataSetChanged();
                    return;
                }

                firebaseConnection
                    .get()
                    .child( "setor" )
                    .orderByChild( "bloco" )
                    .equalTo( blocoData.get( position-1 ).getId() )
                    .addListenerForSingleValueEvent(
                        new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                setores.clear();
                                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()) {
                                    Setor setor = dataSnapshot1.getValue( Setor.class );
                                    setores.add( setor.getNome() );
                                }
                                setorAdapter.notifyDataSetChanged();
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                setores.clear();
                                setores.add( CORINGA );
                                setorAdapter.notifyDataSetChanged();
                            }
                        }
                    );
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        };
    }

    private View.OnClickListener pressBtnEncheu() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String bloco, setor;

                bloco = blocoSelect.getSelectedItem().toString();
                setor = setorSelect.getSelectedItem().toString();

                if ( bloco.equalsIgnoreCase( CORINGA ) || setor.equalsIgnoreCase( CORINGA ) ) {
                    Toast.makeText(
                            HomeActivity.this,
                            "Selecione um bloco e um setor.",
                            Toast.LENGTH_LONG
                    ).show();
                    return;
                }

                final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                firebaseConnection.get()
                    .child( "user" )
                    .orderByChild( "email" )
                    .equalTo(user.getEmail())
                    .addListenerForSingleValueEvent( new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            String nomeUsuario = user.getDisplayName(),
                                    email = user.getEmail();
                            for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()) {
                                Usuario usuario = dataSnapshot1.getValue( Usuario.class );
                                nomeUsuario = usuario.getNome();
                            }

                            String mensagem = "@"+nomeUsuario+"("+email+") informa que a caixa do "+bloco+" e do " +
                                    "setor "+ setor+", encheu!";

//                            EmailSend emailSend = new EmailSend(
//                                    "aidaalmeidasc@gmail.com",
//                                    "A caixa encheu! "+bloco+" ("+setor+")",
//                                    mensagem
//                            );
//
//                            emailSend.execute();
                            Chat chat = new Chat();
                            chat.setMensagem( mensagem );
                            chat.setData( GregorianCalendar.getInstance().getTime().toString() );
                            chat.setUser( nomeUsuario );

                            try{
                                new ChatDAO( firebaseConnection ).save( chat );
                                Notification.make(
                                        HomeActivity.this,
                                        "Obrigado!",
                                        "Sua atitude nos ajuda a criar uma UNIFAMAZ mais sustentável",
                                        Notification.SUCCESS
                                );
                            } catch (Exception ex) {
                                Notification.make(
                                        HomeActivity.this,
                                        "Ops",
                                        "Houve um erro o aplicativo, por favor contate o responsável.",
                                        Notification.ERROR
                                );
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    } );
            }
        };
    }
}
