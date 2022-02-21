package com.project.arengeridonusum.MainPage.Fiyat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.project.arengeridonusum.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class UrunAdapter extends RecyclerView.Adapter<UrunAdapter.CardHolder> {

    private ArrayList<Urunler> urunlerList;
    private Context mContext;

    private FirebaseFirestore firebaseFirestore;
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;
    private FirebaseAuth auth;

    public UrunAdapter(ArrayList<Urunler> urunler, Context mContext){
        this.urunlerList = urunler;
        this.mContext = mContext;
    }

    @NonNull
    @NotNull
    @Override
    public CardHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.fiyat_liste_item,parent,false);
        return new UrunAdapter.CardHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull UrunAdapter.CardHolder holder, int position) {
        auth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();

        Urunler urunler = urunlerList.get(position);
        holder.urun_basligiitxt.setText(urunler.getBaslik());
        holder.urun_fiyatitxt.setText(urunler.getFiyat()+" ₺");

        if(!urunler.getUrunImage().equals("default")){
            Glide.with(mContext).load(urunler.getUrunImage()).into(holder.urun_resimimg);

        }
        else {
            holder.urun_resimimg.setImageResource(R.drawable.arenlogo);

        }

//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(holder.itemView.getContext(), MessagePage.class);
//
//                intent.putExtra("email_share",messagesArraylist.get(position).getBuyer_username());
//                holder.itemView.getContext().startActivity(intent);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return urunlerList.size();
    }

    public class CardHolder extends RecyclerView.ViewHolder{

        public ImageView urun_resimimg;
        public TextView urun_basligiitxt, urun_fiyatitxt;

        public CardHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            urun_resimimg = itemView.findViewById(R.id.urun_resimimg);
            urun_basligiitxt = itemView.findViewById(R.id.urun_basligiitxt);
            urun_fiyatitxt = itemView.findViewById(R.id.urun_fiyatitxt);

        }
    }
}
