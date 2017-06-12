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
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterUser extends AppCompatActivity {

    private static final String TAG = "event";
    EditText eemail;
    EditText epass;
    EditText ename;
    EditText emob;
    EditText eaddr;
    Button btnsubmit;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        eemail =  (EditText) findViewById(R.id.editemail);
        epass =  (EditText) findViewById(R.id.editpass);
        ename =  (EditText) findViewById(R.id.editnam);
        emob =  (EditText) findViewById(R.id.editmob);
        eaddr =  (EditText) findViewById(R.id.editaddr);
        btnsubmit = (Button)findViewById(R.id.btnregister);

        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = eemail.getText().toString();
                String pass = epass.getText().toString();
                String name= ename.getText().toString();
                String mob = emob.getText().toString();
                String addr = eaddr.getText().toString();

                if(email.equals("")||pass.equals("")||name.equals("")||mob.equals("")||addr.equals(""))
                {
                    Toast.makeText(getApplicationContext(),"Please Fill All The Field",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    volleyCall(email,pass,name,mob,addr);
                }
            }
        });

    }

    private void volleyCall(String email,String password, String name, String mobile, String address)
    {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://192.168.42.22:8010/Registration/rest/login/goregistration";
        Map<String,String> jsonparams = new HashMap<String,String>();
        jsonparams.put("email",email);
        jsonparams.put("password",password);
        jsonparams.put("name",name);
        jsonparams.put("mobile",mobile);
        jsonparams.put("address",address);
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

                if (msg.equals("user Registration Successfull")) {
                    Toast.makeText(RegisterUser.this, "Your data is Submitted", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegisterUser.this, MainActivity.class);
                    startActivity(intent);
                } else
                    Toast.makeText(RegisterUser.this, "user already exist", Toast.LENGTH_SHORT).show();
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

