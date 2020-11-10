package com.example.proyectoapps;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class estudiantes1 extends AppCompatActivity {

    private TextView tv1;
    private ListView lv1;
    private String seleccionado;

    private String nombres[] = {"Sistemas e Informatica"};
    private String horario[] = {"7am - 9am"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estudiantes1);

        tv1 = (TextView)findViewById(R.id.tv2);
        lv1 = (ListView)findViewById(R.id.lv1);

        ArrayAdapter <String> adapter = new ArrayAdapter<String>(this, R.layout.list_item_materias, nombres);
        lv1.setAdapter(adapter);

        lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //tv1.setText("La meteria que" + lv1.getItemAtPosition(position) + "ss: " + horario[position]);
                Intent intent = new Intent(estudiantes1.this, estudiantes2.class );
                startActivity(intent);
            }
        });

    }


}