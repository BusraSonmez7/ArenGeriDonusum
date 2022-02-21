package com.project.arengeridonusum.Yonetim.Urunler;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

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
import com.project.arengeridonusum.MainPage.Fiyat.Urunler;
import com.project.arengeridonusum.MainPage.MainPageAvtivity;
import com.project.arengeridonusum.R;
import com.project.arengeridonusum.Yonetim.YonetimMainPage;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;

public class UrunListesiAdapter extends RecyclerView.Adapter<UrunListesiAdapter.CardHolder> {

    private ArrayList<Urunler> urunlerList;
    private Context mContext;

    private FirebaseFirestore firebaseFirestore;
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;
    private FirebaseAuth auth;

    public UrunListesiAdapter(ArrayList<Urunler> urunler, Context mContext){
        this.urunlerList = urunler;
        this.mContext = mContext;
    }

    @NonNull
    @NotNull
    @Override
    public CardHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.urun_listesi_item,parent,false);
        return new UrunListesiAdapter.CardHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull UrunListesiAdapter.CardHolder holder, int position) {
        auth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();

        Urunler urunler = urunlerList.get(position);
        holder.urun_basligiitxt.setText(urunler.getBaslik());
        holder.urun_fiyatitxt.setText(urunler.getFiyat() + " ₺");

        if(!urunler.getUrunImage().equals("default")){
            Glide.with(mContext).load(urunler.getUrunImage()).into(holder.urun_resimimg);

        }
        else {
            holder.urun_resimimg.setImageResource(R.drawable.arenlogo);

        }



        holder.sil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UrunSil(position,holder);
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(holder.itemView.getContext(), UrunOzellikleri.class);
                intent.putExtra("urun_adi",urunler.getBaslik());
                intent.putExtra("urun_fiyati",urunler.getFiyat());
                intent.putExtra("urun_resim",urunler.getUrunImage());
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return urunlerList.size();
    }

    public void UrunSil(int position,@NonNull @NotNull UrunListesiAdapter.CardHolder holder){
        Urunler urunler = urunlerList.get(position);

        firebaseFirestore.collection("Urunler").whereEqualTo("urun_adi",urunler.getBaslik()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        DocumentReference washingtonRef = firebaseFirestore.collection("Urunler").document(document.getId());
                        washingtonRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(mContext,"Ürün silindi",Toast.LENGTH_SHORT).show();

//                                Intent intent = new Intent(holder.itemView.getContext(), YonetimMainPage.class);
//                                intent.putExtra("fragment","urunler");
//                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                                holder.itemView.getContext().startActivity(intent);

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

    public class CardHolder extends RecyclerView.ViewHolder{

        public ImageView urun_resimimg, sil;
        public TextView urun_basligiitxt, urun_fiyatitxt;


        public CardHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            urun_resimimg = itemView.findViewById(R.id.urun_resimimg);
            urun_basligiitxt = itemView.findViewById(R.id.urun_basligiitxt);
            urun_fiyatitxt = itemView.findViewById(R.id.urun_fiyatitxt);
            sil = itemView.findViewById(R.id.sil);

        }
    }
}
