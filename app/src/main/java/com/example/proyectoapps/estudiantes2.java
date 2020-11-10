package com.example.proyectoapps;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.time.temporal.Temporal;

public class estudiantes2 extends AppCompatActivity {
    private String APP_DIRECTORY = "imgServiguias/";
    private String MEDIA_DIRECTORY = APP_DIRECTORY + "media";
    private String TEMPORAL_PICTURE_NAME = "temporal.jpg";

    private final int SELECT_PICTURE = 200;
    private final int PHOTO_CODE = 100;
    private ListView lv1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estudiantes2);

        Button seleccionar = (Button) findViewById(R.id.seleccionar);

        seleccionar.setOnClickListener(new View.OnClickListener() {
/**
            if (ContextCompat.checkSelfPermission(estudiantes2.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(estudiantes2.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(estudiantes2.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, 1000);
            }
            **/
            @Override
            public void onClick(View v) {
                final CharSequence[] opciones = {"Tomar foto","Elegir de galeria","Cancelar"};
                final AlertDialog.Builder builder = new AlertDialog.Builder(estudiantes2.this);
                builder.setTitle("Seleccione una opción");
                builder.setItems(opciones, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int seleccion) {
                        if(opciones[seleccion] == "Tomar foto") {
                            openCamera();
                        } else if(opciones[seleccion] == "Elegir de galeria"){
                                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                intent.setType("image/*");
                                startActivityForResult(intent.createChooser(intent, "Seleccionar app de imagen"), SELECT_PICTURE);
                        }else if(opciones[seleccion] == "Cancelar"){
                            dialog.dismiss();
                        }
                    }
                 });
                builder.show();
            }
        });
    }
/**
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case PHOTO_CODE:
                if(resultCode == RESULT_OK){
                    String dir = Environment.getExternalStorageDirectory() + File.separator + MEDIA_DIRECTORY + File.separator + TEMPORAL_PICTURE_NAME;
                }
        }
    }
**/
    private void openCamera() {
        File file = new File(Environment.getExternalStorageState(), MEDIA_DIRECTORY);
        file.mkdirs();
        String path = Environment.getExternalStorageDirectory() + File.separator + MEDIA_DIRECTORY + File.separator + TEMPORAL_PICTURE_NAME;

        File newFile = new File(path);

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(newFile));
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(newFile).toString());
        startActivityForResult(intent, PHOTO_CODE);
    }

    private void enviar(){

    }
}