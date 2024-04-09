package com.example.spacegym;

import static com.example.spacegym.Constants.BUSINESS_SHORT_CODE;
import static com.example.spacegym.Constants.CALLBACKURL;
import static com.example.spacegym.Constants.PARTYB;
import static com.example.spacegym.Constants.PASSKEY;
import static com.example.spacegym.Constants.TRANSACTION_TYPE;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.annotation.NonNull;
import android.view.View;
import android.widget.Button;

import com.example.spacegym.Model.AccessToken;
import com.example.spacegym.Model.STKPush;
import com.example.spacegym.Services.DarajaApiClient;
import com.example.spacegym.Services.Utils;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class PaymentActivity extends AppCompatActivity implements View.OnClickListener {
    // declare views
    TextInputEditText phone, amount;
    Button sendPayment;

    private DarajaApiClient mApiClient;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        getSupportActionBar().setTitle("M-PESA Payment");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.red_700)));

        // bind views
        phone = findViewById(R.id.phone);
        amount = findViewById(R.id.amount);
        sendPayment = findViewById(R.id.sendPayment);

        mProgressDialog = new ProgressDialog(this);
        mApiClient = new DarajaApiClient();
        mApiClient.setIsDebug(true);

        getAccessToken();

        sendPayment.setOnClickListener(this);
    }

    public void getAccessToken() {
        mApiClient.setGetAccessToken(true);
        mApiClient.mpesaService().getAccessToken().enqueue(new Callback<AccessToken>() {
            @Override
            public void onResponse(Call<AccessToken> call, Response<AccessToken> response) {
                if (response.isSuccessful()) {
                    mApiClient.setAuthToken(response.body().accessToken);
                }
            }

            @Override
            public void onFailure(Call<AccessToken> call, Throwable t) {

            }
        });
    }

        @Override
        public void onClick(View view){
            if (view == sendPayment) {
                String phoneNo = phone.getText().toString();
                String amountSent = amount.getText().toString();
                performSTKPush(phoneNo, amountSent);
            }
        }

    public void performSTKPush(String phoneNo, String amountSent) {
        mProgressDialog.setMessage("Processing your request");
        mProgressDialog.setTitle("Please Wait...");
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.show();
        String timestamp = Utils.getTimestamp();
        STKPush stkPush = new STKPush(
                BUSINESS_SHORT_CODE,
                Utils.getPassword(BUSINESS_SHORT_CODE, PASSKEY, timestamp),
                timestamp,
                TRANSACTION_TYPE,
                String.valueOf(amountSent),
                Utils.sanitizePhoneNumber(phoneNo),
                PARTYB,
                Utils.sanitizePhoneNumber(phoneNo),
                CALLBACKURL,
                "Space Gym MPESA test", //Account reference
                "Testing"  //Transaction description
        );

        mApiClient.setGetAccessToken(false);

        //Sending the data to the Mpesa API, remember to remove the logging when in production.
        mApiClient.mpesaService().sendPush(stkPush).enqueue(new Callback<STKPush>() {
            @Override
            public void onResponse(Call<STKPush> call, Response<STKPush> response) {
                mProgressDialog.dismiss();
                try {
                    if (response.isSuccessful()){
                        Timber.d("post submitted to API. %s", response.body());
                        Intent goToLogin = new Intent(getApplicationContext(), login.class);
                        startActivity(goToLogin);
                    } else {
                        Timber.e("Response %s", response.errorBody().string());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<STKPush> call, Throwable t) {
                mProgressDialog.dismiss();
                Timber.e(t);
            }
        });
    }
    @Override
    public void onPointerCaptureChanged(boolean hasCapture){

    }
}