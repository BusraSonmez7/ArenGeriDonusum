package com.project.arengeridonusum.Yonetim.Oneri;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.project.arengeridonusum.MainPage.Fiyat.Urunler;
import com.project.arengeridonusum.R;
import com.project.arengeridonusum.Yonetim.Urunler.UrunListesiAdapter;
import com.project.arengeridonusum.Yonetim.Urunler.UrunOzellikleri;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class OneriAdapter extends RecyclerView.Adapter<OneriAdapter.CardHolder> {

    private ArrayList<Oneriler> oneriList;
    private Context mContext;

    private FirebaseFirestore firebaseFirestore;
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;
    private FirebaseAuth auth;

    public OneriAdapter(ArrayList<Oneriler> oneriler, Context mContext){
        this.oneriList = oneriler;
        this.mContext = mContext;
    }

    @NonNull
    @NotNull
    @Override
    public OneriAdapter.CardHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.oneri_item,parent,false);
        return new OneriAdapter.CardHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull OneriAdapter.CardHolder holder, int position) {
        auth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();

        Oneriler oneriler = oneriList.get(position);
        holder.tarih.setText(oneriler.getTarih());
        holder.baslik.setText(oneriler.getBaslik());
        holder.aciklama.setText(oneriler.getAciklama());

        if(oneriler.getOkundu().equals("1")){
            holder.item_linear.setBackgroundResource(R.drawable.urun_item_tasarim_okundu);
            holder.okundu.setEnabled(false);
            holder.okundu.setVisibility(View.INVISIBLE);
        }

        holder.okundu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                UrunGuncelle(position,holder);
                //holder.item_linear.setBackgroundColor(Color.argb(255,225,225,225));
                holder.item_linear.setBackgroundResource(R.drawable.urun_item_tasarim_okundu);
                holder.okundu.setEnabled(false);
                holder.okundu.setVisibility(View.INVISIBLE);



            }
        });

        holder.sil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OneriSil(position,holder);
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.itemView.getContext(), OneriIcerigi.class);
                intent.putExtra("oneri_baslik",oneriler.getBaslik());
                intent.putExtra("oneri_icerik",oneriler.getAciklama());
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    public void OneriSil(int position,@NonNull @NotNull OneriAdapter.CardHolder holder){
        Oneriler oneriler = oneriList.get(position);

        firebaseFirestore.collection("Oneriler").whereEqualTo("key",oneriler.getKey()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        DocumentReference washingtonRef = firebaseFirestore.collection("Oneriler").document(document.getId());
                        washingtonRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(mContext,"Öneri silindi",Toast.LENGTH_SHORT).show();

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

    private void UrunGuncelle(int position,@NonNull @NotNull OneriAdapter.CardHolder holder){
        Oneriler oneriler = oneriList.get(position);

        firebaseFirestore.collection("Oneriler").whereEqualTo("key",oneriler.getKey()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        DocumentReference washingtonRef = firebaseFirestore.collection("Oneriler").document(document.getId());

                        washingtonRef.update("okundu", "1").addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {

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

    @Override
    public int getItemCount() {
        return oneriList.size();
    }

    public class CardHolder extends RecyclerView.ViewHolder{

        public ImageView okundu, sil;
        public TextView tarih, baslik, aciklama;
        private ConstraintLayout item_linear;

        public CardHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            tarih = itemView.findViewById(R.id.txttarih);
            baslik = itemView.findViewById(R.id.txtoneri_baslik);
            aciklama = itemView.findViewById(R.id.txtoneri_aciklama);
            okundu = itemView.findViewById(R.id.okundu);
            sil = itemView.findViewById(R.id.sil);
            item_linear = itemView.findViewById(R.id.item_c);

        }
    }
}
