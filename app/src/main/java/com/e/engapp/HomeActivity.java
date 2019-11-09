package com.e.engapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.e.engapp.model.Bloco;
import com.e.engapp.model.FirebaseConnection;
import com.e.engapp.model.Setor;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private static String CORINGA = "NENHUM";

    private FirebaseConnection firebaseConnection;
    private List<String> blocos, setores;
    private List<Bloco> blocoData;
    private ArrayAdapter blocoAdapter, setorAdapter;

    private Spinner blocoSelect, setorSelect;
    private Button btnEnvia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_home );

        // Configuracoes do Barra de Acao do topo
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.mipmap.ic_launcher);
        actionBar.setDisplayShowTitleEnabled(true);

        // Recupera os spinner de selecao da tela
        blocoSelect = (Spinner) findViewById( R.id.blocoSelect );
        setorSelect = (Spinner) findViewById( R.id.setorSelect );

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

        blocoSelect.setOnItemSelectedListener( onSelectedItem() );
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
                Log.e("Item selecionado", blocos.get( position ));
                setores.clear();
                firebaseConnection
                    .get()
                    .child( "setor" )
                    .orderByChild( "bloco" )
                    .equalTo( blocoData.get( position ).getId() )
                    .addListenerForSingleValueEvent(
                        new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                Log.e("Setores",dataSnapshot.toString());
                                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()) {
                                    Setor setor = dataSnapshot1.getValue( Setor.class );
                                    setores.add( setor.getNome() );
                                }
                                setorAdapter.notifyDataSetChanged();
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {}
                        }
                    );
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        };
    }
}
