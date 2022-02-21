package com.project.arengeridonusum.Users;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.project.arengeridonusum.R;

import java.util.HashMap;
import java.util.UUID;

public class UserRegister extends AppCompatActivity {

    EditText name, surname, username, password, r_password, about, email;
    Button btn_register;
    ImageView img_profile;
    Uri image_data;

    EditText ad, soyad, tel, setemail, setsifre, setsifretekrar;

    ActivityResultLauncher<Intent> activityResultLauncher;
    ActivityResultLauncher<String> permissionLauncher;

    private FirebaseAuth auth;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;

    //private ActivityRegisterBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_register);

        ad = findViewById(R.id.name);
        soyad = findViewById(R.id.surname);
        tel = findViewById(R.id.tel);
        setemail = findViewById(R.id.email);
        setsifre = findViewById(R.id.password);
        setsifretekrar = findViewById(R.id.repeat_password);

        auth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();

        //RegisterLauncher();


    }

//    public void ImageAdd(View view){
//        if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
//            if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.READ_EXTERNAL_STORAGE)){
//                Snackbar.make(view,"Permission needed for gallery", Snackbar.LENGTH_INDEFINITE).setAction("Give permission", new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        //izin istenecek
//                        permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE);
//
//                    }
//                }).show();
//            }else{
//                //izin istenecek
//                permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE);
//            }
//        }
//        else{
//            //izin verildiyse
//            Intent intentGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//            activityResultLauncher.launch(intentGallery);
//
//        }
//    }
//
//    public void RegisterLauncher(){
//        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
//            @Override
//            public void onActivityResult(ActivityResult result) {
//                if(result.getResultCode() == RESULT_OK){
//                    Intent intentFromResult = result.getData();
//                    if(intentFromResult != null){
//                        image_data = intentFromResult.getData();
//                        binding.profileIcon.setImageURI(image_data);
//                    }
//                    else{
//                        image_data = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE+
//                                "://"+getResources().getResourcePackageName(R.drawable.profile_create)+
//                                '/'+getResources().getResourceTypeName(R.drawable.profile_create)+'/'+
//                                getResources().getResourceEntryName(R.drawable.profile_create));
//                    }
//                }
//            }
//        });
//
//        permissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), new ActivityResultCallback<Boolean>() {
//            @Override
//            public void onActivityResult(Boolean result) {
//                if(result){
//                    Intent intentGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                    activityResultLauncher.launch(intentGallery);
//                }else{
//                    Toast.makeText(getApplicationContext(),"Permission needed!",Toast.LENGTH_LONG).show();
//
//                }
//            }
//        });
//
//    }

    public void UserRegister(View view){

        String email = setemail.getText().toString();
        String password = setsifre.getText().toString();

        int bosmu = 0;
//        if(ad.length()==0)
//        {
//            bosmu++;
//            ad.requestFocus();
//            ad.setError("Bu alan boş geçilemez");
//        }
//
//        if(soyad.length()==0)
//        {
//            bosmu++;
//            soyad.requestFocus();
//            soyad.setError("Bu alan boş geçilemez");
//        }
//        if(tel.length()==0)
//        {
//            bosmu++;
//            tel.requestFocus();
//            tel.setError("Bu alan boş geçilemez");
//        }
//
//        if(email.equals(""))
//        {
//            bosmu++;
//            setemail.requestFocus();
//            setemail.setError("Bu alan boş geçilemez");
//        }
//        if(password.equals(""))
//        {
//            bosmu++;
//            setsifre.requestFocus();
//            setsifre.setError("Bu alan boş geçilemez");
//        }
//
//        if(setsifretekrar.equals(""))
//        {
//            bosmu++;
//            setsifretekrar.requestFocus();
//            setsifretekrar.setError("Bu alan boş geçilemez");
//        }
        if(bosmu<1){

            auth.createUserWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    auth.signOut();
                    UserSave();
                    Intent intent = new Intent(getApplicationContext(),UserLoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(),e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
                }
            });
//            if(image_data !=null){
//                UserSave(image_data);
//            }
//            else{
//
//                image_data = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE +
//                        "://" + getResources().getResourcePackageName(R.drawable.profile_n)
//                        + '/' + getResources().getResourceTypeName(R.drawable.profile_n) + '/' + getResources().getResourceEntryName(R.drawable.profile_n) );
//                //VerileriEkle(imageData);
//                UserSave(image_data);
//                //SavePersonDb(image_data);
//            }

        }
//
//        SavePersonDb();

    }

    public void UserSave(){
        FirebaseUser user = auth.getCurrentUser();

        HashMap<String,Object> kullanicibilgileri = new HashMap<>();
        kullanicibilgileri.put("ad",ad.getText().toString());
        kullanicibilgileri.put("soyad",soyad.getText().toString());
        kullanicibilgileri.put("tel",tel.getText().toString());
        kullanicibilgileri.put("email",setemail.getText().toString());
        kullanicibilgileri.put("sifre",setsifre.getText().toString());


        firebaseFirestore.collection("Users").add(kullanicibilgileri).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(UserRegister.this,"Successfully saved",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext(), UserLoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(UserRegister.this,e.getLocalizedMessage(),Toast.LENGTH_LONG).show();

            }
        });
}}