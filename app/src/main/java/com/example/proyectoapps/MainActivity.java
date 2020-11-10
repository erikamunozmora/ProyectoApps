package com.example.proyectoapps;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Spinner spinner1;
    private EditText usu;
    private EditText con;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinner1 = (Spinner)findViewById(R.id.spinner);
        usu = (EditText)findViewById(R.id.user);
        con = (EditText)findViewById(R.id.pass);

        String [] opciones ={"Profesor", "Estudiante"};
        ArrayAdapter <String> adapter = new ArrayAdapter<String>( this, android.R.layout.simple_spinner_item, opciones);
        spinner1.setAdapter(adapter);
    }

    //Acción del botón
    public void Ingresar(View view){
        String usuario = usu.getText().toString();
        String contraseña = con.getText().toString();
        String rol = spinner1.getSelectedItem().toString();
        if(rol.equals("Estudiante")){
            // Entonces ingresar al modulo de estudiante
            Intent intent = new Intent(this, estudiantes1.class);
            startActivity(intent);
        }else if(rol.equals("Profesor")){
            // Entonces ingresar al modulo de profesor
        }else{
            // Mensaje de error "Por favor seleccione su rol"
            Toast.makeText(this, "Por favor seleccione su rol", Toast.LENGTH_LONG).show();
        }

    }
}