package charity.marani.mydoctor;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class LoginActivity extends AppCompatActivity {
    //views and widget fields
    ProgressDialog progressDialog;
    ImageView buttonLogin;
    EditText editTextLoginEmail, editTextLoginPassword;
    TextView textViewSignUp;
    //firebase authentication fields
    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListener;

    //strings
    String emailString, passwordString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //assignID
        progressDialog = new ProgressDialog(this);
        buttonLogin = (ImageView) findViewById(R.id.buttonLogin);
        editTextLoginEmail = (EditText) findViewById(R.id.editTextLoginEmail);
        editTextLoginPassword = (EditText) findViewById(R.id.editTextLoginPassword);
        textViewSignUp = (TextView) findViewById(R.id.textViewSignUp);
        //assign instances
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    startActivity(new Intent(LoginActivity.this, ChatActivity.class));
                } else {

                }
            }
        };
        //onclick listener
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //perform login operation

                emailString = editTextLoginEmail.getText().toString().trim();
                passwordString = editTextLoginPassword.getText().toString().trim();
                if (!TextUtils.isEmpty(emailString) && !TextUtils.isEmpty(passwordString)) {
                    progressDialog.setMessage("Logging in...");
                    progressDialog.show();
                    mAuth.signInWithEmailAndPassword(emailString, passwordString).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                startActivity(new Intent(LoginActivity.this, ChatActivity.class));
                            } else {
                                progressDialog.hide();
                                Toast.makeText(LoginActivity.this, "Login Failed..Please Try  Again", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
        textViewSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,MainActivity.class));
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(mAuthListener);
    }
}

