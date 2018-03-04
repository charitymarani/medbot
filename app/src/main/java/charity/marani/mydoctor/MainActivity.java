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

public class MainActivity extends AppCompatActivity {
    //views and widget fields
    ProgressDialog progressDialog;
    ImageView buttonRegister;
     EditText editTextName,editTextEmail,editTextPassword;
     TextView textViewLogin;
    //firebase authentication fields
    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //assignID
        progressDialog = new ProgressDialog(this);
        buttonRegister =(ImageView) findViewById(R.id.buttonRegister);
        editTextName =(EditText) findViewById(R.id.editTextName);
        editTextEmail =(EditText) findViewById(R.id.editTextEmail);
        editTextPassword =(EditText) findViewById(R.id.editTextPassword);
        textViewLogin =(TextView) findViewById(R.id.textViewLogin);
        //Assign instances
        mAuth=FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null){
                    startActivity(new Intent(MainActivity.this,ChatActivity.class));
                }else{

                }
            }
        };
        //onclick listener
       buttonRegister.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               String name = editTextName.getText().toString().trim();
               String email = editTextEmail.getText().toString().trim();
               String password = editTextPassword.getText().toString().trim();
               if (!TextUtils.isEmpty(name) &&!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
                  progressDialog.setMessage("Registering User...");
                   progressDialog.show();
                   mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                       @Override
                       public void onComplete(@NonNull Task<AuthResult> task) {
                           if (task.isSuccessful()) {
                               //user successfully registered and logged in
                               //will start the welcome activity here
                               //for now we're toasting
                               Toast.makeText(MainActivity.this, "User Account Created", Toast.LENGTH_SHORT).show();
                               startActivity(new Intent(MainActivity.this, ChatActivity.class));
                           } else {
                               progressDialog.hide();
                               Toast.makeText(MainActivity.this, "Could not register..Please Try  Again", Toast.LENGTH_SHORT).show();
                           }
                       }


                   });

               }
           }
       });

      // }

textViewLogin.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
                   startActivity(new Intent(MainActivity.this,LoginActivity.class));
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
