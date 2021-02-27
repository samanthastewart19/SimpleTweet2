package com.codepath.apps.restclienttemplate;

import androidx.appcompat.app.AppCompatActivity;
import okhttp3.Headers;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.codepath.apps.restclienttemplate.models.Tweet;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;

import org.json.JSONException;
import org.parceler.Parcels;

public class Compose extends AppCompatActivity {
    
    Button tweetButton;
    EditText compose;
    
    TwitterClient client;
  
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);
        
        tweetButton = findViewById(R.id.buttontween);
        compose =findViewById(R.id.editTextcompse);
        
        client = TwitterApp.getRestClient(this);
        
        tweetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
                String tweetContent = compose.getText().toString();
               
                if(tweetContent.isEmpty())
                {
                    Toast.makeText(Compose.this,"Tweet is empty",Toast.LENGTH_SHORT).show();
                }
                if(tweetContent.length() >140)
                {
                    Toast.makeText(Compose.this,"Tweet is too long",Toast.LENGTH_SHORT).show();
                }
                
    
                client.publishTweet(tweetContent, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Headers headers, JSON json) {
    
                        try {
                            
                            Tweet tweet = Tweet.fromJson(json.jsonObject);
                            Intent i = new Intent();
                            i.putExtra("tweet", Parcels.wrap(tweet));
                            setResult(RESULT_OK,i);
                            finish();
                            
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        
                        
                    }
    
                    @Override
                    public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                    }
                });
            }
        });
    }
}


//TODO: Clean up toasts and log messages