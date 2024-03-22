package fpoly.thuyltph35992.lab1_2_ph35992;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import fpoly.thuyltph35992.lab1_2_ph35992.SignInEmail.Login_Email;
import fpoly.thuyltph35992.lab1_2_ph35992.SignInPhoneNumber.Login_OTP;


public class SignInOption extends AppCompatActivity {
    Button btnNumberPhone,btnEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_in_option);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        btnEmail = findViewById(R.id.btnEmail);
        btnNumberPhone = findViewById(R.id.btnNumberPhone);

        btnEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Login_Email.class);
                startActivity(intent);
            }
        });

        btnNumberPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Login_OTP.class);
                startActivity(intent);
            }
        });


    }
}