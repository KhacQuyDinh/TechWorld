package anhnguyen.com.vn.starstudy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Thread bamgio=new Thread(){
            public void run()
            {
                try {
                    sleep(3000);
                } catch (Exception e) {

                }
                finally
                {
                    Intent i =new Intent(MainActivity.this,Main2Activity.class);
                    startActivity(i);
                }
            }
        };
        bamgio.start();
    }

    protected void onPause(){
        super.onPause();
        finish();
    }

}
