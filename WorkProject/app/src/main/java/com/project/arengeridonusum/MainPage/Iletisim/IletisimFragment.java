package com.project.arengeridonusum.MainPage.Iletisim;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
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

    TextView tel, email, adres, facebook, instagram, twitter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable  Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.iletisim_fragment,container,false);

        tel = view.findViewById(R.id.tel);
        email = view.findViewById(R.id.email);
        adres = view.findViewById(R.id.adres);
        facebook = view.findViewById(R.id.facebooktxt);
        instagram = view.findViewById(R.id.instagram);
        twitter = view.findViewById(R.id.twitter);

        auth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();

        getIletisim();
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
}