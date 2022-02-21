package com.project.arengeridonusum.Yonetim.Urunler;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.project.arengeridonusum.R;

import java.util.HashMap;

public class UrunOzellikleri extends AppCompatActivity {

    private FirebaseAuth auth;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;

    MultiAutoCompleteTextView isim, fiyat;
    ImageView resim;
    TextView btnguncelle;

    String urun_adi, urun_fiyati, urun_resim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_urun_ozellikleri);

        isim = findViewById(R.id.urun_isim);
        fiyat = findViewById(R.id.urun_fiyat);
        resim = findViewById(R.id.imageadd);
        btnguncelle = findViewById(R.id.btnguncelle);

        auth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();

        Intent intent = getIntent();
        urun_adi = intent.getStringExtra("urun_adi");
        urun_fiyati = intent.getStringExtra("urun_fiyati");
        urun_resim = intent.getStringExtra("urun_resim");


       getUrunBilgisi();

        btnguncelle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UrunGuncelle();
            }
        });

    }

    public void getUrunBilgisi(){

        isim.setText(urun_adi);
        fiyat.setText(urun_fiyati);

        if(!urun_resim.equals("default")){
            Glide.with(getApplicationContext()).load(urun_resim).into(resim);

        }
        else {
            resim.setImageResource(R.drawable.arenlogo);

        }
    }


    private void UrunGuncelle(){

        firebaseFirestore.collection("Urunler").whereEqualTo("urun_adi",urun_adi).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        DocumentReference washingtonRef = firebaseFirestore.collection("Urunler").document(document.getId());

                        String isim1, fiyat1;
                        isim1 = isim.getText().toString();
                        fiyat1 = fiyat.getText().toString();


                        washingtonRef.update("urun_adi", isim1,"fiyat",fiyat1).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(getApplicationContext(),"Güncellendi",Toast.LENGTH_LONG).show();

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                            }
                        });
                    }
                } else {
                }
            }
        });

    }
}