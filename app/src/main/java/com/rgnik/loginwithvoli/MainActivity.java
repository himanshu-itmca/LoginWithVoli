package com.rgnik.loginwithvoli;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "event";
    EditText eemail,epass;
    Button btnsubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        eemail =  (EditText) findViewById(R.id.editemail);
        epass =  (EditText) findViewById(R.id.editpass);
        btnsubmit = (Button)findViewById(R.id.btnlogin);

        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = eemail.getText().toString();
                String pass = epass.getText().toString();

                if(email.equals("")||pass.equals(""))
                {
                    Toast.makeText(getApplicationContext(),"Please Fill All The Field",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    volleyCall(email,pass);
                }
            }
        });

    }

    public void register(View v)
    {
        Intent intent = new Intent(getApplicationContext(),RegisterUser.class);
        startActivity(intent);
    }

    private void volleyCall(final String email, String password)
    {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://192.168.42.22:8010/Registration/rest/login/gologin";
        Map<String,String> jsonparams = new HashMap<String,String>();
        jsonparams.put("email",email);
        jsonparams.put("password",password);
        Log.d(TAG,"json: "+ new JSONObject(jsonparams));
        final JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(jsonparams),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        String msg = null;
                        try {
                            msg = (String) response.get("message");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        if (msg.equals("login Success")) {
                            Toast.makeText(MainActivity.this, "Login Successfully....", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(MainActivity.this, Welcome.class);
                            intent.putExtra("temail",email);
                            startActivity(intent);
                        } else
                            Toast.makeText(MainActivity.this, "Incorrect Email/Password", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "Json : " + response);
                    }
                },new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Log.d(TAG,"Error : "+error +"\n Message "+error.getMessage());
            }
        });
        queue.add(postRequest);
    }

}
