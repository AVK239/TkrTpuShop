package com.tkr.tkrtpushop;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tkr.tkrtpushop.Admin.AdminCategoryActivity;
import com.tkr.tkrtpushop.Model.Users;
import com.tkr.tkrtpushop.Users.HomeActivity;
import com.tkr.tkrtpushop.ViewHolder.ProductAdapter;

import org.mindrot.jbcrypt.BCrypt;

public class LoginActivity extends AppCompatActivity
{
    private Button loginBtn;
    private EditText usernameInput, phoneInput, passwordInput;
    private ProgressDialog loadingBar;
    private TextView AdminLink, NotAdminLink;
    private String parentDbName = "Users";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginBtn = (Button) findViewById(R.id.login_btn);
        phoneInput = (EditText) findViewById(R.id.Login_phone_input);
        passwordInput = (EditText) findViewById(R.id.Login_password_input);
        loadingBar = new ProgressDialog(this);

        AdminLink = (TextView)findViewById(R.id.admin_panel_link);
        NotAdminLink = (TextView)findViewById(R.id.not_admin_panel_link);

        AdminLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AdminLink.setVisibility(View.INVISIBLE);
                NotAdminLink.setVisibility(View.VISIBLE);
                loginBtn.setText("Вход для админа");
                parentDbName = "Admins";
            }

        });

        NotAdminLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AdminLink.setVisibility(View.VISIBLE);
                NotAdminLink.setVisibility(View.INVISIBLE);
                loginBtn.setText("Вход");
                parentDbName = "Users";
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser();
            }

        });
    }

    private void loginUser() {
        String phone = phoneInput.getText().toString();
        String password = passwordInput.getText().toString();

        if(TextUtils.isEmpty(phone))
        {
            Toast.makeText(this, "Введите телефон", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(password))
        {
            Toast.makeText(this, "Введите пароль", Toast.LENGTH_SHORT).show();
        }
        else {
            loadingBar.setTitle("Вход в приложение");
            loadingBar.setMessage("Пожалуйста, подождите...");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            ValidateUser( phone, password);
        }
    }
    private void ValidateUser(String phone, String password ){
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.child(parentDbName).child(phone).exists())
                {   String storedPassword = snapshot.child(parentDbName).child(phone).child("password").getValue(String.class);
                    Users usersData = snapshot.child(parentDbName).child(phone).getValue(Users.class);

                    if(usersData.getPhone().equals(phone)) {
                        try {
                            if (BCrypt.checkpw(password, storedPassword)) {
                                if (parentDbName.equals("Users")) {
                                    loadingBar.dismiss();

                                    String uid = BCrypt.hashpw(phone, BCrypt.gensalt());

                                    Toast.makeText(LoginActivity.this, "Успешный вход!", Toast.LENGTH_SHORT).show();

                                    Intent homeIntent = new Intent(LoginActivity.this, HomeActivity.class);



                                    homeIntent.putExtra("uid", uid); // userUid - UID пользователя

                                    startActivity(homeIntent);

                                } else if (parentDbName.equals("Admins")) {
                                    loadingBar.dismiss();
                                    Toast.makeText(LoginActivity.this, "Успешный вход!", Toast.LENGTH_SHORT).show();

                                    Intent homeIntent = new Intent(LoginActivity.this, AdminCategoryActivity.class);
                                    startActivity(homeIntent);
                                }
                            } else {
                                loadingBar.dismiss();
                                Toast.makeText(LoginActivity.this, "Неверный пароль", Toast.LENGTH_SHORT).show();
                            }

                        }
                        catch (IllegalArgumentException exc) {
                            // Ошибка при проверке пароля
                            loadingBar.dismiss();
                            Toast.makeText(LoginActivity.this, "Ошибка при проверке пароля", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                else {
                    loadingBar.dismiss();
                    Toast.makeText(LoginActivity.this,"Аккаунт с номером" +" "+ phone + "не существует", Toast.LENGTH_SHORT).show();

                    Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(mainIntent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}
