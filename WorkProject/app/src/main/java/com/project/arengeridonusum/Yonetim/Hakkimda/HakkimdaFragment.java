package com.project.arengeridonusum.Yonetim.Hakkimda;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.project.arengeridonusum.R;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.UUID;


public class HakkimdaFragment extends Fragment {

    private FirebaseAuth auth;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;

    TextView btnkaydet;

    private MultiAutoCompleteTextView edthakkinda;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable  Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.y_hakkimda_fragment,container,false);

        edthakkinda = view.findViewById(R.id.edthakkinda);
        btnkaydet = view.findViewById(R.id.txtkaydet);

        auth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();

        getHakkinda();

        btnkaydet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HakkindaGuncelle();
            }
        });

        return view;
    }

    private void getHakkinda(){

        firebaseFirestore.collection("ArenGeriDonusum").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error !=null){
                    Toast.makeText(getActivity(),error.getLocalizedMessage().toString(),Toast.LENGTH_LONG).show();
                }

                if(value != null){

                    for(DocumentSnapshot snapshot : value.getDocuments()){
                        //bildirimlerArrayList.clear();
                        HashMap<String,Object> data = (HashMap<String, Object>) snapshot.getData();
                        String hakkinda2 = (String)  data.get("hakkinda");

//                                profilee = "Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE" +
//                                        "://" + "getResources().getResourcePackageName(R.drawable.profile)"
//                                        + '/' + "getResources().getResourceTypeName(R.drawable.profile)" + '/' + "getResources().getResourceEntryName(R.drawable.profile) )";
                        edthakkinda.setText(hakkinda2.toString());

                    }

                }
            }
        });
    }

    private void HakkindaGuncelle(){
        firebaseFirestore.collection("ArenGeriDonusum").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        DocumentReference washingtonRef = firebaseFirestore.collection("ArenGeriDonusum").document(document.getId());
                        washingtonRef.update("hakkinda", edthakkinda.getText().toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(getActivity(),"Güncellendi",Toast.LENGTH_LONG).show();

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