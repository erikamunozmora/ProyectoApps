package com.example.proyectoapps;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.time.temporal.Temporal;
import java.util.Objects;

import id.zelory.compressor.Compressor;

public class estudiantes2 extends AppCompatActivity {
    private String APP_DIRECTORY = "imgServiguias/";
    private String MEDIA_DIRECTORY = APP_DIRECTORY + "media";
    private String TEMPORAL_PICTURE_NAME = "temporal.jpg";

    private static final int GALLERY_INTENT = 1;
    private final int PHOTO_CODE = 100;
    private ListView lv1;

    private Button seleccionar, enviar ;
    private DatabaseReference imgref;
    private StorageReference storagref;
    ProgressDialog cargando;

    Bitmap thum_bitmap = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estudiantes2);

        seleccionar = (Button) findViewById(R.id.seleccionar);
        enviar = (Button) findViewById(R.id.enviar);
        imgref = FirebaseDatabase.getInstance().getReference().child("Fotos subidas");
        storagref = FirebaseStorage.getInstance().getReference().child("fotos");
        cargando = new ProgressDialog( this);
/**
        seleccionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CropImage.startPickImageActivity(estudiantes2.this);
            }
        });*/
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
                                startActivityForResult(intent.createChooser(intent, "Seleccionar app de imagen"), GALLERY_INTENT);
                               // startActivityForResult(intent, SELECT_PICTURE);
                        }else if(opciones[seleccion] == "Cancelar"){
                            dialog.dismiss();
                        }
                    }
                 });
                builder.show();
            }
        });
    }  // Fin del onCreate

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

/**
         switch (requestCode){
         case PHOTO_CODE:
         if(resultCode == RESULT_OK){
         String dir = Environment.getExternalStorageDirectory() + File.separator + MEDIA_DIRECTORY + File.separator + TEMPORAL_PICTURE_NAME;
         }
         } **/

        if (requestCode == GALLERY_INTENT && resultCode == RESULT_OK) {
            final Uri uri = data.getData();
/**            Uri imagenuri = CropImage.getPickImageResultUri(this, data);

            //recortar imagen
            CropImage.activity(imagenuri)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setRequestedSize(640, 480)
                    .setAspectRatio(2, 1).start(estudiantes2.this);
 **/
        final StorageReference filePath = storagref.child(uri.getLastPathSegment());
            enviar.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View v) {
                                              cargando.setTitle("Subiendo foto...");
                                              cargando.setMessage("Espere un momento...");
                                              cargando.show();

                                              filePath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                                  @Override
                                                  public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                      cargando.dismiss();
                                                      Toast.makeText(estudiantes2.this, "Imagen cargada con exito", Toast.LENGTH_SHORT).show();
                                                  }
                                              });

                                          }
                                      });
        }
/**
            File url = new File(uri.getPath());

            //Mostrar nombre del archvo seleccionado
            //Picasso.with(this).load(uri).into(foto);
            //Comprimir imagen
            try {
                thum_bitmap = new Compressor(this)
                        .setMaxWidth(640)
                        .setMaxHeight(480)
                        .setQuality(90)
                        .compressToBitmap(url);
            } catch (IOException e) {
                e.printStackTrace();
            }

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            thum_bitmap.compress(Bitmap.CompressFormat.JPEG, 90, byteArrayOutputStream);
            final byte[] thumb_byte = byteArrayOutputStream.toByteArray();

            //Fin del compresor

            //Boton enviar
            enviar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cargando.setTitle("Subiendo foto...");
                    cargando.setMessage("Espere un momento...");
                    cargando.show();

                    final StorageReference ref = storagref.child("nombre.jpg");
                    UploadTask uploadTask = ref.putBytes(thumb_byte);

                    //Subir imagen en el storage
                    Task<Uri> uriTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                        @Override
                        public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                            if (!task.isSuccessful()) {
                                throw Objects.requireNonNull(task.getException());
                            }
                            return ref.getDownloadUrl();
                        }
                    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            Uri downloaduri = task.getResult();
                            imgref.push().child("urlfoto").setValue(downloaduri.toString());
                            cargando.dismiss();

                            Toast.makeText(estudiantes2.this, "Imagen cargada con exito", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        }**/
    }




    private void openCamera() {
        File file = new File(Environment.getExternalStorageState(), MEDIA_DIRECTORY);
        file.mkdirs();
        String path = Environment.getExternalStorageDirectory() + File.separator + MEDIA_DIRECTORY + File.separator + TEMPORAL_PICTURE_NAME;

        File newFile = new File(path);

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(newFile));
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(newFile).toString());
        startActivityForResult(intent, GALLERY_INTENT);
    }

    private void enviar(){

    }
}