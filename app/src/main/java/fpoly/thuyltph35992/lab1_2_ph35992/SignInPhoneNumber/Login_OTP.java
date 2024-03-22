package fpoly.thuyltph35992.lab1_2_ph35992.SignInPhoneNumber;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;


import java.util.concurrent.TimeUnit;

import fpoly.thuyltph35992.lab1_2_ph35992.MainActivity;
import fpoly.thuyltph35992.lab1_2_ph35992.R;

public class Login_OTP extends AppCompatActivity {
    TextInputEditText txtNumber, txtOTP;
    Button btndangnhap, btnGetOTP;
    private FirebaseAuth mAuth;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks callbacks;
    private String verificationID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login_otp);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        mAuth = FirebaseAuth.getInstance();
        anhxa();

        btnGetOTP.setOnClickListener(view -> {
            String phoneNumber = txtNumber.getText().toString();
            if (phoneNumber.isEmpty()) {
                Toast.makeText(Login_OTP.this, "Vui lòng nhập số điện thoại", Toast.LENGTH_SHORT).show();
                return;
            }

            getOTP(phoneNumber);
        });

        btndangnhap.setOnClickListener(view -> {
            String code = txtOTP.getText().toString();
            if (code.isEmpty()) {
                Toast.makeText(Login_OTP.this, "Vui lòng nhập mã OTP", Toast.LENGTH_SHORT).show();
                return;
            }
            verifyOTP(code);
        });

        callbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                txtOTP.setText(phoneAuthCredential.getSmsCode());
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                Toast.makeText(Login_OTP.this, "Lấy OTP thất bại", Toast.LENGTH_SHORT).show();
//                if (e instanceof FirebaseAuthTimeoutAutoRetrievalException) {
//                    Toast.makeText(Login_OTP.this, "Quá thời gian chờ, vui lòng thử lại", Toast.LENGTH_SHORT).show();
//                    // Cho phép người dùng yêu cầu gửi lại mã OTP
//                } else {
//                    Toast.makeText(Login_OTP.this, "Lấy OTP thất bại: " + e.getMessage(), Toast.LENGTH_SHORT).show();
//                }
            }

            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                verificationID = s;
            }
        };


    }

    private void anhxa() {
        txtNumber = findViewById(R.id.txtNumber);
        txtOTP = findViewById(R.id.txtOTP);
        btndangnhap = findViewById(R.id.btndangnhap);
        btnGetOTP = findViewById(R.id.btnGetOTP);
    }

    private void getOTP(String phoneNumber) {
        btnGetOTP.setEnabled(false);
        btnGetOTP.setText("Đang xử lý...");
        PhoneAuthOptions options = PhoneAuthOptions
                .newBuilder(mAuth)
                .setPhoneNumber("+84" + phoneNumber)
                .setActivity(this)
                .setCallbacks(callbacks)
                .setTimeout(100L, TimeUnit.SECONDS)
                .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
        btnGetOTP.setEnabled(true);
        btnGetOTP.setText("Get OTP");
    }

    private void verifyOTP(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationID, code);
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        btndangnhap.setEnabled(false);
        btndangnhap.setText("Đang xử lý...");
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(Login_OTP.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                            FirebaseUser user = task.getResult().getUser();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));

                        } else {
                            Toast.makeText(Login_OTP.this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                Toast.makeText(Login_OTP.this, "Sai mã OTP", Toast.LENGTH_SHORT).show();
                            }
                        }
                        btndangnhap.setEnabled(true);
                        btndangnhap.setText("Login");
                    }
                });
    }
}