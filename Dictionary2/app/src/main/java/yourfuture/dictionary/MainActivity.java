package yourfuture.dictionary;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    //Ket thuc
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //thuc hien mot man hinh cho mau hong dam trong 500ms.
        Thread demtg = new Thread(){
            public void run(){
                try{
                    sleep(1000);
                }catch (Exception e){
                    e.printStackTrace();
                } finally {
                    Intent intent = new Intent(MainActivity.this,Main2Activity.class);
                    startActivity(intent);
                }
            }
        };
        demtg.start();
    }

    //Khi quay lai activity nay thi se goi phuong thuc onPause va ket thuc ung dung.
    protected void onPause(){
        finish();
        super.onPause();
    }
}
