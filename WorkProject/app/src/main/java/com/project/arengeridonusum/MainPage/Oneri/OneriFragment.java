package com.project.arengeridonusum.MainPage.Oneri;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.project.arengeridonusum.R;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;


public class OneriFragment extends Fragment {

    EditText oneri_baslik;
    MultiAutoCompleteTextView oneriicerigi;
    TextView txtgonder;

    private FirebaseAuth auth;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable  Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.oneri_fragment,container,false);

        oneri_baslik = view.findViewById(R.id.oneri_baslik);
        oneriicerigi = view.findViewById(R.id.oneriicerigi);
        txtgonder = view.findViewById(R.id.txtgonder);


        auth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();

        txtgonder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OneriEkle();
            }
        });


        return view;
    }

    public void OneriEkle(){
        FirebaseUser user = auth.getCurrentUser();
        String email = user.getEmail();

        String downloadUrl = "default";
        String baslik = oneri_baslik.getText().toString();
        String aciklama = oneriicerigi.getText().toString();
        String key = email+"_"+baslik+"_"+FieldValue.serverTimestamp();


        HashMap<String,Object> subjectData = new HashMap<>();
        subjectData.put("baslik",baslik);
        subjectData.put("aciklama",aciklama);
        subjectData.put("tarih", FieldValue.serverTimestamp());
        subjectData.put("key", key);
        subjectData.put("okundu", "0");


        firebaseFirestore.collection("Oneriler").add(subjectData).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(getActivity(),"Şikayet veya öneriniz alınmıştır. Teşekkür ederiz.",Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                Toast.makeText(getActivity(),"The word could not be registered",Toast.LENGTH_LONG).show();

            }
        });
    }
}