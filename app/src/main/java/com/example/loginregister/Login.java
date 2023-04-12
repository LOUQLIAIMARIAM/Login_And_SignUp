package com.example.loginregister;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class Login extends AppCompatActivity {


    TextInputEditText textInputEditTextusername,textInputEditTextpassword;
    Button buttonLogin;
    TextView textViewLogin;
    ProgressBar progress;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        textInputEditTextusername = findViewById(R.id.userName);
        textInputEditTextpassword = findViewById(R.id.password);
        buttonLogin = findViewById(R.id.btnlogin);
        textViewLogin = findViewById(R.id.loginText);
        progress = findViewById(R.id.progress);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String fullname, username, password, email;
                username = String.valueOf(textInputEditTextusername.getText());
                password = String.valueOf(textInputEditTextpassword.getText());


                if (!username.equals("") && !password.equals("")) {

                    progress.setVisibility(View.VISIBLE);

                    //Start ProgressBar first (Set visibility VISIBLE)
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            //Starting Write and Read data with URL
                            //Creating array for parameters
                            String[] field = new String[2];
                            field[0] = "username";
                            field[1] = "password";
                            //Creating array for data
                            String[] data = new String[4];
                            data[0] = username;
                            data[1] = password;
                            PutData putData = new PutData("http://192.168.1.105/loginregister/login.php", "POST", field, data);
                            if (putData.startPut()) {
                                if (putData.onComplete()) {
                                    progress.setVisibility(View.GONE);
                                    String result = putData.getResult();
                                    if(result.equals("Login Up Success")){
                                        Toast.makeText(getApplicationContext(),result,Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
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