package com.project.arengeridonusum.Yonetim.UrunEkleme;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.project.arengeridonusum.MainPage.Fiyat.Urunler;
import com.project.arengeridonusum.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import static android.app.Activity.RESULT_OK;


public class UrunFragment extends Fragment {

    ImageView imageadd;
    MultiAutoCompleteTextView urun_isim, urun_fiyat;
    TextView addButton;

    Uri imageData;

    ActivityResultLauncher<Intent> activityResultLauncher;
    ActivityResultLauncher<String> permissionLauncher;

    private FirebaseAuth auth;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable  Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.y_urun_fragment,container,false);


        addButton = view.findViewById(R.id.btnekle);
        imageadd = view.findViewById(R.id.imageadd);
        urun_isim = view.findViewById(R.id.urun_isim);
        urun_fiyat = view.findViewById(R.id.urun_fiyat);

        registerLauncher();

        auth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();


        //

        imageadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageAdd(v);
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Save(v);

            }

        });


        return view;
    }

    public void Save(View v){

        int isvoid = 0;

        if(urun_isim.equals(""))
        {
            isvoid++;
            urun_isim.requestFocus();
            urun_isim.setError("Ürün ismi girin!");
        }
        if(urun_fiyat.equals(""))
        {
            isvoid++;
            urun_fiyat.requestFocus();
            urun_fiyat.setError("Ürün fiyatı girin");
        }


        if(isvoid<1){

            BaslikKontrol(urun_isim.getText().toString());
//            if(this.imageData != null){
//                DataAdd(imageData);
//            }
//            else{
//                this.imageData = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE +
//                        "://" + getResources().getResourcePackageName(R.drawable.arenlogo)
//                        + '/' + getResources().getResourceTypeName(R.drawable.arenlogo) + '/' + getResources().getResourceEntryName(R.drawable.arenlogo) );
//                //VerileriEkle(imageData);
//                DataAdd("default");
//            }
        }
    }

    public void ImageAdd(View view){
        if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),Manifest.permission.READ_EXTERNAL_STORAGE)){
                Snackbar.make(view,"Permission needed for gallery", Snackbar.LENGTH_INDEFINITE).setAction("Give permission", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //izin istenecek
                        permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE);

                    }
                }).show();
            }else{
                //izin istenecek
                permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE);
            }
        }
        else{
            //izin verildiyse
            Intent intentGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            activityResultLauncher.launch(intentGallery);

        }
    }

    private void registerLauncher(){
        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if(result.getResultCode() == RESULT_OK){
                    Intent intentFromResult = result.getData();
                    if(intentFromResult != null){
                        imageData = intentFromResult.getData();
                        imageadd.setImageURI(imageData);

                    }
                }
            }
        });

        permissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), new ActivityResultCallback<Boolean>() {
            @Override
            public void onActivityResult(Boolean result) {
                if(result){
                    Intent intentGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    activityResultLauncher.launch(intentGallery);
                }else{
                    Toast.makeText(getActivity(),"Permission needed!",Toast.LENGTH_LONG).show();

                }
            }
        });
    }

    public void DataAdd(String imageData){
        String downloadUrl = "default";

        String isim = urun_isim.getText().toString();
        String fiyat = urun_fiyat.getText().toString();

        FirebaseUser user = auth.getCurrentUser();
        String email = user.getEmail();

        HashMap<String,Object> subjectData = new HashMap<>();
        subjectData.put("urun_adi",isim);
        subjectData.put("fiyat",fiyat);
        subjectData.put("resim",downloadUrl);


        firebaseFirestore.collection("Urunler").add(subjectData).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(getActivity(),"Ürün eklendi",Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                Toast.makeText(getActivity(),"The word could not be registered",Toast.LENGTH_LONG).show();

            }
        });
    }

    public void DataAdd(Uri imageData){
        UUID uuid = UUID.randomUUID();
        String imageName = "images/"+uuid+".jpg";

        storageReference.child(imageName).putFile(imageData).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                StorageReference newRefrence = firebaseStorage.getReference(imageName);
                newRefrence.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        String downloadUrl = uri.toString();

                        String isim = urun_isim.getText().toString();
                        String fiyat = urun_fiyat.getText().toString();

                        FirebaseUser user = auth.getCurrentUser();
                        String email = user.getEmail();

                        HashMap<String,Object> subjectData = new HashMap<>();
                        subjectData.put("urun_adi",isim);
                        subjectData.put("fiyat",fiyat);
                        subjectData.put("resim",downloadUrl);

                        firebaseFirestore.collection("Urunler").add(subjectData).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Toast.makeText(getActivity(),"Ürün eklendi",Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull @NotNull Exception e) {
                                Toast.makeText(getActivity(),"The word could not be registered!",Toast.LENGTH_SHORT).show();

                            }
                        });
                    }
                });

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                Toast.makeText(getActivity(),e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
            }
        });

    }

    public void BaslikKontrol(String urun_adi){

        firebaseFirestore.collection("Urunler").whereEqualTo("urun_adi",urun_adi).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    String documentt = null;
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        ArrayList<Urunler> deneme = new ArrayList<>();
                        DocumentReference docRef = firebaseFirestore.collection("Urunler").document(document.getId());
                        documentt = docRef.toString();
                        Toast.makeText(getActivity(), "Bu ürün daha önce eklendi.",Toast.LENGTH_LONG).show();
                        return;


                    }

                    if(documentt == null){
//
                        if(imageData != null){
                            DataAdd(imageData);
                        }
                        else {
                            imageData = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE +
                                    "://" + getResources().getResourcePackageName(R.drawable.arenlogo)
                                    + '/' + getResources().getResourceTypeName(R.drawable.arenlogo) + '/' + getResources().getResourceEntryName(R.drawable.arenlogo) );
                            DataAdd("default");
                        }
                    }

                }
            }
        });
    }
}