package com.example.sarthak.navigationdrawer.Backend.Backend;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sarthak.navigationdrawer.R;
import com.google.gson.Gson;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Register extends Fragment {
    EditText user_name, phone_number, email, password, confirm_password;
    Button register;
    String user_name_txt, email_txt, password_txt, confirm_password_txt, phone_number_txt;
    static List<NameValuePair> params;
    static List<NameValuePair> params_user;
    ArrayList<History> history = new ArrayList<>();
    public static final String INTENT_PHONENUMBER = "phonenumber";
    public static final String INTENT_COUNTRY_CODE = "code";
    SharedPreferences pref;
    ServerRequest sr;

    private void openActivity(String phoneNumber) {
        Intent verification = new Intent(getContext(), VerificationActivity.class);
        verification.putExtra(INTENT_PHONENUMBER, phoneNumber);
        verification.putExtra(INTENT_COUNTRY_CODE, "91");
        verification.putExtra("email", email_txt);
        verification.putExtra("user_name", user_name_txt);
        verification.putExtra("from", "register");
        startActivity(verification);
        Log.d("start", "Activity started");
    }

    private String getE164Number() {
        return phone_number_txt.replaceAll("\\D", "").trim();
        // return PhoneNumberUtils.formatNumberToE164(mPhoneNumber.getText().toString(), mCountryIso);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_register, container, false);

        user_name = (EditText) view.findViewById(R.id.register_user_name);
        phone_number = (EditText) view.findViewById(R.id.register_phone_number);
        email = (EditText) view.findViewById(R.id.register_email);
        password = (EditText) view.findViewById(R.id.register_password);
        confirm_password = (EditText) view.findViewById(R.id.register_confirm__password);
        register = (Button) view.findViewById(R.id.register_register);
        pref = this.getActivity().getSharedPreferences("AppPref", Context.MODE_PRIVATE);
        sr = new ServerRequest(getContext());

        register.setOnClickListener(new View.OnClickListener() {

                                        @Override
                                        public void onClick(View v) {
                                            user_name_txt = user_name.getText().toString();
                                            phone_number_txt = phone_number.getText().toString();
                                            email_txt = email.getText().toString();
                                            password_txt = password.getText().toString();
                                            confirm_password_txt = confirm_password.getText().toString();

                                            if (user_name_txt.isEmpty() || phone_number_txt.isEmpty() || email_txt.isEmpty() || password_txt.isEmpty() || confirm_password_txt.isEmpty()) {
                                                Toast.makeText(getContext(), "Fill all details", Toast.LENGTH_SHORT).show();
                                            } else {
                    /*params_user.add(new BasicNameValuePair(Config.ip,user_name_txt));
                    JSONObject j_user = sr.getJSON(Config.ip+"/checkUserExists",params_user);
                    if(){}else{}*/
                                                if (phone_number_txt == null || (phone_number_txt.length() != 10 && phone_number_txt.length() != 11 && phone_number_txt.length() != 13)) {
                                                    Toast.makeText(getContext(), "Please Enter a valid phone number" + phone_number_txt, Toast.LENGTH_SHORT).show();
                                                } else {

                                                    if (!(email_txt.indexOf("@") < 1 || email_txt.lastIndexOf(".") < email_txt.indexOf("@") + 2 || email_txt.lastIndexOf(".") + 2 >= email_txt.length())) {

                                                        if (password_txt.length() < 6) {
                                                            Toast.makeText(getContext(), "password length should equal or greater then 6", Toast.LENGTH_SHORT).show();
                                                        } else {
                                                            if (password_txt.equals(confirm_password_txt)) {

                                                                Log.d("here", "register is clicked");
                                                                //Toast.makeText(getContext(), "Register clicked !", Toast.LENGTH_LONG).show();

                                        /*Users user = new Users();
                                        History h = new History();


                                        h.date = "19/2/96";
                                        h.source = "Gandhinagar";
                                        h.destination = "Ahmedabad";
                                        history.add(h);
                                        history.add(h);

                                        user.name = user_name_txt;
                                        user.emailId = email_txt;
                                        user._id = phone_number_txt;
                                        user.password = password_txt;
                                        user.currLoc = "";
                                        user.history = history;

                                        String lat = "145";
                                        String long_ = "125";
                                        Gson gson = new Gson();
                                        String s = gson.toJson(history);*/

                                                                params = new ArrayList<NameValuePair>();
                                                                params.add(new BasicNameValuePair(Config.user_name, user_name_txt));
                                                                params.add(new BasicNameValuePair(Config.phone_number, phone_number_txt));
                                                                params.add(new BasicNameValuePair(Config.email, email_txt));
                                                                params.add(new BasicNameValuePair(Config.password, password_txt));
                                        /*params.add(new BasicNameValuePair(Config.history, s));
                                        params.add(new BasicNameValuePair(Config.latitude, lat));
                                        params.add(new BasicNameValuePair(Config.longitude, long_));*/

                                                                Log.d(Config.user_name, user_name_txt);
                                                                Log.d(Config.phone_number, phone_number_txt);
                                                                Log.d(Config.email, email_txt);
                                                                Log.d(Config.password, password_txt);

                                                                //Log.d("history", s);
                                                                Log.d("here", "params sent");

                                                                JSONObject json = sr.getJSON(Config.ip + "/ckeckExistNumber", params);
                                                                Log.d("here", "json received");
                                                                if (json != null) {
                                                                    try {
                                                                        String jsonstr = json.getString("response");
                                                                        if (jsonstr.equals("yes")) {
                                                                            openActivity(getE164Number());
                                                                        } else {
                                                                            Toast.makeText(getContext(), "Phone number already existed!", Toast.LENGTH_SHORT).show();
                                                                        }
                                                                        Log.d("Hello", jsonstr);
                                                                    } catch (JSONException e) {
                                                                        e.printStackTrace();
                                                                    }
                                                                }

                                                            } else {
                                                                Toast.makeText(getContext(), "Password does not match !", Toast.LENGTH_LONG).show();
                                                            }
                                                        }

                                                    } else {
                                                        Toast.makeText(getContext(), "Email id not valid !", Toast.LENGTH_LONG).show();
                                                    }
                                                }
                                            }
                                        }

                                    }

        );

        return view;
    }


}
