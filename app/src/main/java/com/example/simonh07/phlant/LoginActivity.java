package com.example.simonh07.phlant;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.simonh07.phlant.activities.MainActivity;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.share.Share;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;

public class LoginActivity extends AppCompatActivity {
    private CallbackManager callbackManager;
    private TextView txtEmail,txtBirthday;
    private ProgressDialog mDialog;
    private ImageView imgAvatar;
    private LoginButton loginButton;
    private AccessTokenTracker accessTokenTracker;
    private ProfileTracker profileTracker;
    DatabaseReference ref;
    SharedPreferences prefss,prefs1;
    ImageView imageView;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode,resultCode,data);

    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ref = FirebaseDatabase.getInstance().getReference();
        callbackManager = CallbackManager.Factory.create();
         txtBirthday = (TextView) findViewById(R.id.TvBirthday);
         txtEmail = (TextView)findViewById(R.id.TvEmail);
        imgAvatar = (ImageView) findViewById(R.id.IvAvatar);
        prefss = getSharedPreferences("Information",MODE_PRIVATE);
        prefs1 = getSharedPreferences("Temperature",MODE_PRIVATE);
        imageView = findViewById(R.id.backtolobby);

        loginButton = (LoginButton) findViewById(R.id.FbLogin);


        loginButton.setReadPermissions(Arrays.asList("public_profile","email"));
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                @Override
            public void onSuccess(LoginResult loginResult) {
                mDialog = new ProgressDialog(LoginActivity.this);
                mDialog.setMessage("Retrieving data...");
                mDialog.show();

                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                finish();
                startActivity(intent);


               final GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        mDialog.dismiss();
                        Log.d("response", response.toString());
                        getData(object);
                        accessTokenTracker.startTracking();
                        profileTracker.startTracking();
                    }
                });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,email,first_name,last_name");
                request.setParameters(parameters);
                request.executeAsync();


            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });

        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                if(currentAccessToken == null)
                {
                    new GraphRequest(currentAccessToken, "me/permissions/", null, HttpMethod.DELETE, new GraphRequest.Callback() {
                        @Override
                        public void onCompleted(GraphResponse response) {
                           prefss.edit().clear().apply();


                            LoginManager.getInstance().logOut();
                            Intent intent = new Intent(LoginActivity.this,LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            finish();


                        }
                    }).executeAsync();

                }


            }
        };
        profileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {

            }
        };


    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent (this,LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }


    private void getData(JSONObject object) {
        try{

            URL profile_Picture = new URL("https://graph.facebook.com/"+object.getString("id")+"/picture?width=250&height=250");
            Picasso.get().load(profile_Picture.toString()).into(imgAvatar);
            txtEmail.setText(object.getString("email")+"\n"+object.getString("first_name")+" " +object.getString("last_name"));
            Log.d("name",object.getString("first_name")+ " "+object.getString("last_name"));
            Log.d("email",object.getString("email"));
            Log.d("photo",profile_Picture.toString());
            SharedPreferences.Editor editor = getSharedPreferences("Information", MODE_PRIVATE).edit();
            editor.putString("name", object.getString("first_name")+ " "+object.getString("last_name"));
            editor.putString("email",object.getString("email"));
            editor.putString("photo",profile_Picture.toString() );
            editor.apply();


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


//    private void printKeyHash() {
//        try{
//            PackageInfo info = getPackageManager().getPackageInfo("com.example.simonh07.phlant", PackageManager.GET_SIGNATURES);
//
//            for (Signature signature:info.signatures){
//                MessageDigest md = MessageDigest.getInstance("SHA");
//                md.update(signature.toByteArray());
//               Log.d("KeyHash",  Base64.encodeToString(md.digest(),Base64.DEFAULT));
//            }
//
//        } catch (PackageManager.NameNotFoundException e) {
//            e.printStackTrace();
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        }
//    }


}
