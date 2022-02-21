package com.project.arengeridonusum.Yonetim.Oneri;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.project.arengeridonusum.MainPage.Fiyat.UrunAdapter;
import com.project.arengeridonusum.MainPage.Fiyat.Urunler;
import com.project.arengeridonusum.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;


public class OneriFragment extends Fragment {

    private RecyclerView recylerView;
    public OneriAdapter onerilerAdapter;
    public ArrayList<Oneriler> onerilerList;

    private FirebaseAuth auth;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable  Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.y_oneri_fragment,container,false);
        recylerView = view.findViewById(R.id.rcv_main);

        auth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();

        onerilerList = new ArrayList<>();

        recylerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        onerilerAdapter = new OneriAdapter(onerilerList,getActivity());
        recylerView.setLayoutManager(layoutManager);
        recylerView.setAdapter(onerilerAdapter);

        auth = FirebaseAuth.getInstance();

        getOneriler();
        return view;
    }

    public void getOneriler(){

        firebaseFirestore.collection("Oneriler").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable @org.jetbrains.annotations.Nullable QuerySnapshot value, @Nullable @org.jetbrains.annotations.Nullable FirebaseFirestoreException error) {
                if(error !=null){
                    Toast.makeText(getActivity(),error.getLocalizedMessage().toString(),Toast.LENGTH_LONG).show();
                }

                if(value != null){

                    recylerView.removeAllViews();
                    onerilerList.clear();

                    for(DocumentSnapshot snapshot : value.getDocuments()){
                        HashMap<String,Object> data = (HashMap<String, Object>) snapshot.getData();
                        String baslik = (String) data.get("baslik");
                        String aciklama = (String) data.get("aciklama");
                        String key = (String) data.get("key");
                        String okundu = (String) data.get("okundu");



                        if(data.get("tarih") != null){
                            Timestamp time = (Timestamp) data.get("tarih");
                            Date date = time.toDate();
                            String tarih = date.toString();
                            String pattern = "dd/MM/yyyy kk:mm";
                            SimpleDateFormat format = new SimpleDateFormat(pattern);

                            Oneriler p = new Oneriler(format.format(date),baslik,aciklama,key,okundu);
                            onerilerList.add(p);
                        }

                    }
                    if(!onerilerList.isEmpty()){
                        onerilerAdapter.notifyDataSetChanged();
                        // bildirimlerArrayList.clear();
                    }
                    else{
                        //Toast.makeText(getActivity(),"Liste boş",Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
        onerilerList.clear();

    }
}