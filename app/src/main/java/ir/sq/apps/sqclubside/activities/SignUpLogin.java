package ir.sq.apps.sqclubside.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.percent.PercentLayoutHelper;
import android.support.percent.PercentRelativeLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.BitmapRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.sq.apps.sqclubside.R;
import ir.sq.apps.sqclubside.controllers.UrlHandler;
import ir.sq.apps.sqclubside.controllers.UserHandler;
import ir.sq.apps.sqclubside.models.Club;
import ir.sq.apps.sqclubside.models.Plan;
import ir.sq.apps.sqclubside.models.Receipt;
import ir.sq.apps.sqclubside.models.User;
import ir.sq.apps.sqclubside.uiControllers.TypeFaceHandler;

public class SignUpLogin extends AppCompatActivity implements View.OnClickListener {
    private boolean isSigninScreen = true;
    private TextView tvSignupInvoker;
    private LinearLayout llSignup;
    private TextView tvSigninInvoker;
    private LinearLayout llSignin;
    private Button signUpbutton;
    private Button signInbutton;
    private TextInputEditText[] signUPviews;
    private TextInputEditText[] signInViews;

    @BindView(R.id.userName_signup)
    TextInputEditText userName_signup;
    @BindView(R.id.passWord_signup)
    TextInputEditText passWord_signup;
    @BindView(R.id.email_signup)
    TextInputEditText email_signup;
    @BindView(R.id.userName_signin)
    TextInputEditText userName_signin;
    @BindView(R.id.passWord_signin)
    TextInputEditText passWord_signin;
    @BindView(R.id.userName_signin_layout)
    TextInputLayout userName_signin_layout;
    @BindView(R.id.passWord_signin_layout)
    TextInputLayout passWord_signin_layout;
    @BindView(R.id.userName_signup_layout)
    TextInputLayout userName_signup_layout;
    @BindView(R.id.passWord_signup_layout)
    TextInputLayout passWord_signup_layout;
    @BindView(R.id.email_signup_layout)
    TextInputLayout email_signup_layout;


    LinearLayout llsignup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_login);

        ButterKnife.bind(this);

        llSignin = findViewById(R.id.llSignin);
        llSignin.setOnClickListener(this);
        llsignup = findViewById(R.id.llSignup);
        llsignup.setOnClickListener(this);
        tvSignupInvoker = findViewById(R.id.tvSignupInvoker);
        tvSigninInvoker = findViewById(R.id.tvSigninInvoker);

        signUpbutton = findViewById(R.id.signUP_button);
        signInbutton = findViewById(R.id.signIn_button);

        llSignup = findViewById(R.id.llSignup);
        llSignin = findViewById(R.id.llSignin);

        tvSignupInvoker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isSigninScreen = false;
                showSignupForm();
                emptyErrors();
            }
        });

        tvSigninInvoker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isSigninScreen = true;
                showSigninForm();
                emptyErrors();
            }
        });
        showSigninForm();
        setFonts();
        setviews();
    }

    private void emptyErrors() {
        userName_signup.setError(null);
        email_signup.setError(null);
        passWord_signup.setError(null);
        userName_signin.setError(null);
        passWord_signin.setError(null);
    }

    private void setviews() {
        signUPviews = new TextInputEditText[3];
        signUPviews[0] = userName_signup;
        signUPviews[1] = passWord_signup;
        signUPviews[2] = email_signup;

        signInViews = new TextInputEditText[2];
        signInViews[0] = userName_signin;
        signInViews[1] = passWord_signin;
    }

    private void setFonts() {
        tvSigninInvoker.setTypeface(TypeFaceHandler.getInstance(this).getFa_bold());
        tvSignupInvoker.setTypeface(TypeFaceHandler.getInstance(this).getFa_bold());
        signInbutton.setTypeface(TypeFaceHandler.getInstance(this).getFa_bold());
        signUpbutton.setTypeface(TypeFaceHandler.getInstance(this).getFa_bold());
        passWord_signin.setTypeface(TypeFaceHandler.getInstance(this).getFa_bold());
        passWord_signup.setTypeface(TypeFaceHandler.getInstance(this).getFa_bold());
        userName_signin.setTypeface(TypeFaceHandler.getInstance(this).getFa_bold());
        userName_signup.setTypeface(TypeFaceHandler.getInstance(this).getFa_bold());
        email_signup.setTypeface(TypeFaceHandler.getInstance(this).getFa_bold());
        userName_signin_layout.setTypeface(TypeFaceHandler.getInstance(this).getFa_bold());
        passWord_signin_layout.setTypeface(TypeFaceHandler.getInstance(this).getFa_bold());
        userName_signup_layout.setTypeface(TypeFaceHandler.getInstance(this).getFa_bold());
        passWord_signup_layout.setTypeface(TypeFaceHandler.getInstance(this).getFa_bold());
        email_signup_layout.setTypeface(TypeFaceHandler.getInstance(this).getFa_bold());

    }

    private void showSignupForm() {
        PercentRelativeLayout.LayoutParams paramsLogin = (PercentRelativeLayout.LayoutParams) llSignin.getLayoutParams();
        PercentLayoutHelper.PercentLayoutInfo infoLogin = paramsLogin.getPercentLayoutInfo();
        infoLogin.widthPercent = 0.15f;
        llSignin.requestLayout();


        PercentRelativeLayout.LayoutParams paramsSignup = (PercentRelativeLayout.LayoutParams) llSignup.getLayoutParams();
        PercentLayoutHelper.PercentLayoutInfo infoSignup = paramsSignup.getPercentLayoutInfo();
        infoSignup.widthPercent = 0.85f;
        llSignup.requestLayout();

        tvSignupInvoker.setVisibility(View.GONE);
        tvSigninInvoker.setVisibility(View.VISIBLE);
        Animation translate = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.translate_right_to_left);
        llSignup.startAnimation(translate);

        Animation clockwise = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_right_to_left);
        signUpbutton.startAnimation(clockwise);

    }

    private void showSigninForm() {
        PercentRelativeLayout.LayoutParams paramsLogin = (PercentRelativeLayout.LayoutParams) llSignin.getLayoutParams();
        PercentLayoutHelper.PercentLayoutInfo infoLogin = paramsLogin.getPercentLayoutInfo();
        infoLogin.widthPercent = 0.85f;
        llSignin.requestLayout();
        PercentRelativeLayout.LayoutParams paramsSignup = (PercentRelativeLayout.LayoutParams) llSignup.getLayoutParams();
        PercentLayoutHelper.PercentLayoutInfo infoSignup = paramsSignup.getPercentLayoutInfo();
        infoSignup.widthPercent = 0.15f;
        llSignup.requestLayout();

        Animation translate = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.translate_left_to_right);
        llSignin.startAnimation(translate);

        tvSignupInvoker.setVisibility(View.VISIBLE);
        tvSigninInvoker.setVisibility(View.GONE);
        Animation clockwise = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_left_to_right);
        signInbutton.startAnimation(clockwise);
    }

    private Boolean checkEmptyFieldsSignUp() {
        Boolean flag = true;
        for (EditText e : signUPviews) {
            if (e.getText().toString().length() == 0) {
                e.setError(getString(R.string.empty_field_error_meesage));
                flag = false;
            }
        }
        return flag;
    }

    private Boolean checkEmptyFieldsSignIn() {
        Boolean flag = true;
        for (EditText e : signInViews) {
            if (e.getText().toString().length() == 0) {
                e.setError(getString(R.string.empty_field_error_meesage));
                flag = false;
            }
        }
        return flag;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.signIn_button:
                if (checkEmptyFieldsSignIn()) {
                    signIn();
                }
                break;
            case R.id.signUP_button:
                Animation clockwise = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_right_to_left);
                if (isSigninScreen)
                    signUpbutton.startAnimation(clockwise);
                if (checkEmptyFieldsSignUp()) {
                    signUp();
                }
                break;
        }

    }

    private void signIn() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("userName", userName_signin.getText().toString());
            jsonObject.put("passWord", passWord_signin.getText().toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        AndroidNetworking.post(UrlHandler.signInUserURL.getUrl())
                .addJSONObjectBody(jsonObject)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("SIGNIN", response.toString());
                        try {

                            int status = response.getInt("status");
                            if (status == 1) {
                                Log.i("SIGNIN", "Done");
                                if (!createUser(response.getJSONObject("object"))) {
                                    finish();
                                    startActivity(new Intent(SignUpLogin.this, FormActivity.class));
                                } else {
                                    if (UserHandler.getInstance().getThisUser().isVerified()) {
                                        startActivity(new Intent(SignUpLogin.this, MainActivity.class));
                                    } else {
                                        finish();
                                        startActivity(new Intent(SignUpLogin.this, FormActivity.class));
                                    }
                                }

                            } else {
                                String errorMessage = getString(R.string.string_wrong_username_or_password);
                                Snackbar.make(signInbutton, errorMessage, Snackbar.LENGTH_SHORT).show();
                                emptyFields();
                                Log.e("SIGNIN", "Not Correct");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }


                    @Override
                    public void onError(ANError error) {
                        Log.e("SIGN IN", "ERROR: " + error.getErrorBody() + " code : " + error.getErrorCode());
                        if (error.getErrorCode() == 400) {
                            Snackbar.make(signUpbutton, getString(R.string.string_wrong_username_or_password), Snackbar.LENGTH_SHORT).show();
                        }

                    }
                });

    }

    private boolean createUser(JSONObject object) throws JSONException {
        String name = object.getString("name");
        String userName = object.getString("userName");
        String password = object.getString("passWord");
        String email = object.getString("email");
        int credit = object.getInt("credit");
        if (object.isNull("club")) {
            User user = new User(name, userName, email, password, false);
            user.setCredit(credit);
            UserHandler.getInstance().setThisUser(user);
            return false;
        }
        JSONObject jsonObjectClub = object.getJSONObject("club");
        String clubName = jsonObjectClub.getString("name");
        String clubTele = jsonObjectClub.getString("telePhoneNumber");
        String clubCell = jsonObjectClub.getString("cellPhoneNumber");
        String clubAddress = jsonObjectClub.getString("address");
        String clubOpeningTime = jsonObjectClub.getString("openingTime");
        String clubCloseTime = jsonObjectClub.getString("closingTime");
        double clubLat = jsonObjectClub.getDouble("latitude");
        double clubLong = jsonObjectClub.getDouble("longtitude");
        int clubType = jsonObjectClub.getInt("type");
        boolean isVerified = jsonObjectClub.getBoolean("verified");
        User user = new User(name, userName, email, password, isVerified);
        Club club = new Club(userName, clubName, name, clubTele, clubCell, clubAddress);
        club.setType(clubType);
        club.setLatitude(clubLat);
        club.setLongtitude(clubLong);
        setClubPlans(club, jsonObjectClub.getJSONArray("weeklyPlan"));
        JSONArray imagesJsonArray = jsonObjectClub.getJSONArray("images");
        JSONArray tagsJsonArray = jsonObjectClub.getJSONArray("tagList");
        JSONArray receiptsArray = object.getJSONArray("receipts");
        setReceipts(user, receiptsArray);
        for (int j = 0; j < tagsJsonArray.length(); j++) {
            club.addTags(tagsJsonArray.getJSONObject(j).getString("name"));
        }
        for (int j = 0; j < imagesJsonArray.length(); j++) {
            String img = ((JSONObject) imagesJsonArray.get(j)).getString("name");
            club.addNameImage(img);
            getImageFrom(club, String.valueOf(img));
        }
        UserHandler.getInstance().setThisUser(user);
        UserHandler.getInstance().setmClub(club);
        return true;
    }

    private void setClubPlans(Club club, JSONArray plansArray) throws JSONException {
        for (int j = 0; j < plansArray.length(); j++) {
            JSONObject receiptObject = plansArray.getJSONObject(j);
            int price = receiptObject.getInt("price");
            int status = receiptObject.getInt("status");
            int day = receiptObject.getInt("day");
            long id = receiptObject.getLong("id");
            String date = receiptObject.getString("date");
            String time = receiptObject.getString("time");
            Plan plan = new Plan(id, price, status, day, date, time);
            club.addPlan(plan);
            Log.i("PLAn", "ADDED" + plan.getId());
        }
    }

    private void setReceipts(User user, JSONArray receiptsArray) throws JSONException {
        for (int j = 0; j < receiptsArray.length(); j++) {
            JSONObject receiptObject = receiptsArray.getJSONObject(j);
            int price = receiptObject.getInt("price");
            String date = receiptObject.getString("date");
            String time = receiptObject.getString("time");
            Receipt receipt = new Receipt(price, date, time);
            user.addReceipt(receipt);
        }
    }

    private void getImageFrom(final Club club, final String imageUrl) {
        final String url = UrlHandler.getImageClubURL.getUrl() + imageUrl;
        AndroidNetworking.get(url)
                .setPriority(Priority.MEDIUM)
                .setBitmapMaxHeight(100)
                .setBitmapMaxWidth(100)
                .setBitmapConfig(Bitmap.Config.ARGB_8888)
                .build()
                .getAsBitmap(new BitmapRequestListener() {
                    @Override
                    public void onResponse(Bitmap bitmap) {
                        Log.i("IMAGE LOAD", "url: " + url);
                        club.addImage(bitmap);
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("IMAGE LOAD", "url : " + url + " error message: " + anError.getErrorBody() + " code : " + anError.getErrorBody());
                    }
                });
    }

    private void emptyFields() {
        userName_signup.setText("");
        passWord_signup.setText("");
        email_signup.setText("");
        userName_signin.setText("");
        passWord_signin.setText("");
    }

    private void signUp() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("userName", userName_signup.getText().toString());
            jsonObject.put("passWord", passWord_signup.getText().toString());
            jsonObject.put("email", email_signup.getText().toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        AndroidNetworking.post(UrlHandler.signUpUserURL.getUrl())

                .addJSONObjectBody(jsonObject)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            int status = response.getInt("status");
                            if (status == 1) {
                                Log.i("SIGNUP", "Done");
                                Toast.makeText(SignUpLogin.this, R.string.string_signup_message_done, Toast.LENGTH_SHORT).show();
                            } else
                                Log.e("SIGNUP", "Error In Response");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        Log.e("SIGNUP", "ERROR: " + error.getErrorBody() + " code : " + error.getErrorCode());
                    }
                });
    }


}

