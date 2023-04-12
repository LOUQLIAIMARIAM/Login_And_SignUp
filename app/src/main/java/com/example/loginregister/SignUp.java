package com.example.loginregister;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class SignUp extends AppCompatActivity {

    TextInputEditText textInputEditTextfullname, textInputEditTextusername, textInputEditTextemail, textInputEditTextpassword;
    Button buttonSignUp;
    TextView textViewLogin;
    ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        textInputEditTextusername = findViewById(R.id.username);
        textInputEditTextfullname = findViewById(R.id.fullname);
        textInputEditTextpassword = findViewById(R.id.password);
        textInputEditTextemail = findViewById(R.id.email);
        buttonSignUp = findViewById(R.id.buttonSignUp);
        textViewLogin = findViewById(R.id.loginText);
        progress = findViewById(R.id.progress);

        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String fullname, username, password, email;
                fullname = String.valueOf(textInputEditTextfullname.getText());
                username = String.valueOf(textInputEditTextusername.getText());
                password = String.valueOf(textInputEditTextpassword.getText());
                email = String.valueOf(textInputEditTextemail.getText());

                if (!fullname.equals("") && !username.equals("") && !password.equals("") && !email.equals("")) {

                    progress.setVisibility(View.VISIBLE);

                    //Start ProgressBar first (Set visibility VISIBLE)
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            //Starting Write and Read data with URL
                            //Creating array for parameters
                            String[] field = new String[4];
                            field[0] = "fullname";
                            field[1] = "username";
                            field[2] = "password";
                            field[3] = "email";
                            //Creating array for data
                            String[] data = new String[4];
                            data[0] = fullname;
                            data[1] = username;
                            data[2] = password;
                            data[3] = email;
                            PutData putData = new PutData("http://192.168.1.105/loginregister/signup.php", "POST", field, data);
                            if (putData.startPut()) {
                                if (putData.onComplete()) {
                                    progress.setVisibility(View.GONE);
                                   String result = putData.getResult();
                                   if(result.equals("Sign Up Success")){
                                       Toast.makeText(getApplicationContext(),result,Toast.LENGTH_SHORT).show();
                                       Intent intent = new Intent(getApplicationContext(),Login.class);
                                       startActivity(intent);
                                       finish();
                                   }else{
                                       Toast.makeText(getApplicationContext(),result,Toast.LENGTH_SHORT).show();
                                   }
                                }
                            }
                            //End Write and Read data with URL
                        }
                    });
                }else{
                    Toast.makeText(getApplicationContext(),"All fields required",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
