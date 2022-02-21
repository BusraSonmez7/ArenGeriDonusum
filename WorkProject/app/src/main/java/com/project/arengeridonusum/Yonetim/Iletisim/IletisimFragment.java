package com.project.arengeridonusum.Yonetim.Iletisim;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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


public class IletisimFragment extends Fragment {

    private FirebaseAuth auth;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;

    EditText tel, email, adres, facebook, instagram, twitter;
    TextView btnkaydet;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable  Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.y_iletisim_fragment,container,false);

        tel = view.findViewById(R.id.tel);
        email = view.findViewById(R.id.email);
        adres = view.findViewById(R.id.adres);
        facebook = view.findViewById(R.id.facebooktxt);
        instagram = view.findViewById(R.id.instagram);
        twitter = view.findViewById(R.id.twitter);
        btnkaydet = view.findViewById(R.id.btnkaydet);

        auth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();

        getIletisim();
        btnkaydet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IletisimGuncelle();
            }
        });


        return view;
    }

    private void getIletisim(){

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
                        String tel2 = (String)  data.get("tel");
                        String email2 = (String)  data.get("email");
                        String adres2 = (String)  data.get("adres");
                        String face2 = (String)  data.get("facebook");
                        String insta2 = (String)  data.get("instagram");
                        String twitter2 = (String)  data.get("twitter");

//                                profilee = "Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE" +
//                                        "://" + "getResources().getResourcePackageName(R.drawable.profile)"
//                                        + '/' + "getResources().getResourceTypeName(R.drawable.profile)" + '/' + "getResources().getResourceEntryName(R.drawable.profile) )";
                        tel.setText(tel2.toString());
                        email.setText(email2.toString());
                        adres.setText(adres2.toString());
                        facebook.setText(face2.toString());
                        instagram.setText(insta2.toString());
                        twitter.setText(twitter2.toString());

                    }

                }
            }
        });
    }

    private void IletisimGuncelle(){
        firebaseFirestore.collection("ArenGeriDonusum").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        DocumentReference washingtonRef = firebaseFirestore.collection("ArenGeriDonusum").document(document.getId());

                        String tel2, email2, adres2, facebook2, instagram2, twitter2;
                        tel2 = tel.getText().toString();
                        email2 = email.getText().toString();
                        adres2 = adres.getText().toString();
                        facebook2 = facebook.getText().toString();
                        instagram2 = instagram.getText().toString();
                        twitter2 = twitter.getText().toString();


                        washingtonRef.update("tel", tel2,"email",email2,"adres",adres2,"facebook",facebook2,"instagram",instagram2,"twitter",twitter2).addOnSuccessListener(new OnSuccessListener<Void>() {
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