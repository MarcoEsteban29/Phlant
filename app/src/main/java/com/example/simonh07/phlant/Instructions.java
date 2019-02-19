package com.example.simonh07.phlant;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.simonh07.phlant.activities.MainActivity;
import com.squareup.picasso.Picasso;

public class Instructions extends AppCompatActivity {
    TextView textView; ImageView imageView;
    boolean finish =false;
    int count=0;
    String text[] = {"Facebook Account is needed for access in the application PHLANT."
            ,"Enter your Username and Password to proceed."
            ,"Tap Continue to confirm your access in the application."
            ,"You can see the weather forecast for today, tomorrow, and later. On top menu, you can see the Refresh, Search for Location and Settings. The Refresh will re update your current weather forecast. You can set your own location by searching it. And it has some other feature. Press back to go to Lobby."
            ,"You can detect your location for its to adjust the weather forecast in your area. Theme can be edited and refresh interval can be adjusted in Settings."
            ,"The Lobby displays the appropriate indoor plant for your current location's weather."
            ,"On pressing the Navigation button on left side of the screen, it contains the Home, About Us, ChatBot, Weather, Instruction, and Log Out. When Home is pressed, you will go back to the Lobby. When About Us is pressed, it will show the mission, vision, and the developers. The ChatBot assists you on your inquiries about the application PHLANT. When Weather is pressed, the weather forecast on your current location or area. if you do not understand how to use the application, just click the Instruction button to redo the steps on how to use the application. The Log Out button will log off your Facebook Account on the application."
            ,"For your questions about the application, the ChatBot will try its best to answer your questions. That is all for this Application. Start Now. Enjoy!"};
    int icons[]= {R.drawable.login,R.drawable.facebooklogin,R.drawable.confirmation,R.drawable.weather,R.drawable.weatherbutton,
            R.drawable.lobby,R.drawable.navigationdraweer,R.drawable.chatbot};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructions);
        textView = findViewById(R.id.explain);
        imageView = findViewById(R.id.instructs);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(icons.length==count && !finish){
                    Intent next = new Intent(getApplicationContext(), LoginActivity.class);
                    next.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(next);
                    finish();
                    finish = true;
                }
                else if(icons.length==count && finish){
                    Intent next = new Intent(getApplicationContext(), MainActivity.class);
                    next.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(next);
                    finish();
                }else{
                    Picasso.get().load(icons[count]).into(imageView);
                    textView.setText(text[count]);
                    count++;
                }

            }
        });


    }
}
