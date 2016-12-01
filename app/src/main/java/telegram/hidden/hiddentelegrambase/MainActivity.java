package telegram.hidden.hiddentelegrambase;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.AssetManager;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

public class MainActivity extends Activity {


    ImageView img ;
    Button btn ;
    EditText edtext ;
    int page = 1;
    SharedPreferences sp;
    SharedPreferences.Editor ed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        ed = sp.edit();


        img = (ImageView) findViewById(R.id.imageView);
        btn = (Button) findViewById(R.id.button);
        edtext = (EditText) findViewById(R.id.editText);

        img.setBackgroundResource(R.mipmap.hiddentelegram1);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(page==1){
                    page =2;
                    img.setBackgroundResource(R.mipmap.hiddentelegram2);
                }else if(page==2){
                    page =3;
                    img.setBackgroundResource(R.mipmap.hiddentelegram3);
                }else if(page==3){
                    page =4;
                    img.setVisibility(View.INVISIBLE);
                }else if(page==5){
                    installfromassets("hidden.telegram.apk");
                }
            }
        });




        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtext.getText().toString().equals("")){
                    toast("لطفا یک رمز وارد کنید");
                }else {
                    page = 5;
                    img.setVisibility(View.VISIBLE);
                    img.setBackgroundResource(R.mipmap.hiddentelegram4);
                    ed.putString("password", ""+edtext.getText().toString());
                    ed.commit();
                }

            }
        });









    }






    public static boolean isIntentAvailable(final Context context, final Intent intent) {

        final PackageManager packageManager = context.getPackageManager();

        List<ResolveInfo> list =

                packageManager.queryIntentActivities(intent,

                        PackageManager.MATCH_DEFAULT_ONLY);

        return list.size() > 0;

    }









    public void installfromassets(String appfilename){


        AssetManager assetManager = getAssets();

        InputStream in = null;
        OutputStream out = null;

        try {
            in = assetManager.open(appfilename);
            out = new FileOutputStream("/sdcard/app.apk");

            byte[] buffer = new byte[1024];

            int read;
            while((read = in.read(buffer)) != -1) {

                out.write(buffer, 0, read);

            }

            in.close();
            in = null;

            out.flush();
            out.close();
            out = null;

            Intent intent = new Intent(Intent.ACTION_VIEW);

            intent.setDataAndType(Uri.fromFile(new File("/sdcard/app.apk")),
                    "application/vnd.android.package-archive");

            startActivity(intent);

        } catch(Exception e) { }





    }






    public void toast(String string){

        Toast toast = Toast.makeText(getApplicationContext(), string, Toast.LENGTH_SHORT);
        toast.show();

    }



}
