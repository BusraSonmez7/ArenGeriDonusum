package com.project.arengeridonusum.MainPage.Fiyat;

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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.project.arengeridonusum.R;

import java.util.ArrayList;
import java.util.HashMap;


public class FiyatFragment extends Fragment {

    private RecyclerView recylerView;
    public UrunAdapter urunlerAdapter;
    public ArrayList<Urunler> urunlerList;

    private FirebaseAuth auth;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable  Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fiyat_fragment,container,false);
        recylerView = view.findViewById(R.id.rcv_main);

        auth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();

        urunlerList = new ArrayList<>();

        recylerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        urunlerAdapter = new UrunAdapter(urunlerList,getActivity());
        recylerView.setLayoutManager(layoutManager);
        recylerView.setAdapter(urunlerAdapter);

        auth = FirebaseAuth.getInstance();

        getUrunler();
        return view;
    }

    public void getUrunler(){

        firebaseFirestore.collection("Urunler").addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable @org.jetbrains.annotations.Nullable QuerySnapshot value, @Nullable @org.jetbrains.annotations.Nullable FirebaseFirestoreException error) {
                    if(error !=null){
                        Toast.makeText(getActivity(),error.getLocalizedMessage().toString(),Toast.LENGTH_LONG).show();
                    }

                    if(value != null){
                        for(DocumentSnapshot snapshot : value.getDocuments()){
                            //bildirimlerArrayList.clear();
                            HashMap<String,Object> data = (HashMap<String, Object>) snapshot.getData();
                            String baslik = (String) data.get("urun_adi");
                            String fiyat = (String) data.get("fiyat");
                            String resim = (String) data.get("resim");

                            Urunler p = new Urunler(resim,baslik,fiyat);
                            urunlerList.add(p);

                            // Toast.makeText(getActivity(),"count: "+count1,Toast.LENGTH_SHORT).show();


                        }
                        if(!urunlerList.isEmpty()){
                            urunlerAdapter.notifyDataSetChanged();
                            // bildirimlerArrayList.clear();
                        }
                        else{
                            //Toast.makeText(getActivity(),"Liste boş",Toast.LENGTH_SHORT).show();
                        }

                    }
                }
            });
        urunlerList.clear();

    }


    }
