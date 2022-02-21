package com.project.arengeridonusum.Users;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.project.arengeridonusum.MainPage.MainPageAvtivity;
import com.project.arengeridonusum.R;
import com.project.arengeridonusum.Yonetim.YonetimMainPage;

public class UserLoginActivity extends AppCompatActivity{


    private FirebaseAuth auth;
    private static final int RC_SIGN_IN = 9001;
    private SignInButton signInButton;

    private GoogleApiClient mGoogleApiClient;

    EditText setemail, setpassword;
    //private ActivityUserLoginBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_login);

        setemail = findViewById(R.id.email);
        setpassword = findViewById(R.id.password);

        auth = FirebaseAuth.getInstance();


        FirebaseUser user2 = auth.getCurrentUser();

        if(user2 != null){
            String email = user2.getEmail();
            if(email.equals("aren.geri.donusum.resmi@gmail.com")){
                Intent intent = new Intent(getApplicationContext(), YonetimMainPage.class);
                startActivity(intent);
                finish();
            }
            else {
                Intent intent = new Intent(getApplicationContext(), MainPageAvtivity.class);
                startActivity(intent);
                finish();
            }

        }
    }




    public void Login(View view){

        String email = setemail.getText().toString();
        String password = setpassword.getText().toString();

        int bosmu = 0;

        if(email.equals(""))
        {
            bosmu++;
            setemail.requestFocus();
            setemail.setError("Bu alan boş geçilemez");
        }
        if(password.equals(""))
        {
            bosmu++;
            setpassword.requestFocus();
            setpassword.setError("Bu alan boş geçilemez");
        }

        if(bosmu<1){
            auth.signInWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    if(email.equals("aren.geri.donusum.resmi@gmail.com")){
                        Intent intent = new Intent(getApplicationContext(), YonetimMainPage.class);
                        startActivity(intent);
                        finish();
                    }
                    else {
                        Intent intent = new Intent(getApplicationContext(), MainPageAvtivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(),e.getLocalizedMessage(),Toast.LENGTH_LONG).show();

                }
            });

//            if(image_data !=null){
//                SavePersonDb(image_data);
//            }
//            else{
//
//                image_data = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE +
//                        "://" + getResources().getResourcePackageName(R.drawable.profile)
//                        + '/' + getResources().getResourceTypeName(R.drawable.profile) + '/' + getResources().getResourceEntryName(R.drawable.profile) );
//                //VerileriEkle(imageData);
//                SavePersonDb(image_data);
//            }

        }

    }

    public void Register(View view){
        Intent intent = new Intent(getApplicationContext(),UserRegister.class);
        startActivity(intent);

    }
}