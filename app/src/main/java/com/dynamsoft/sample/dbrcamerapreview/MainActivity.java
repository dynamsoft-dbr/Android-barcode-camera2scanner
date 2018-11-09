package com.dynamsoft.sample.dbrcamerapreview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;

import com.dynamsoft.barcode.BarcodeReader;
import com.dynamsoft.barcode.EnumBarcodeFormat;
import com.dynamsoft.barcode.PublicRuntimeSettings;

public class MainActivity extends AppCompatActivity {
    private BarcodeReader mbarcodeReader;
    private DBRCache mCache;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            mbarcodeReader = new BarcodeReader("t0068MgAAACm/O50JQCeJC5TJTNpXrUs4Do3MPzQxK0CvQvCGslylduMz/icYA3lAmVbE7NYhTFM60BRpW3QUav1sP6MWdZo=");
        } catch (Exception e) {
            e.printStackTrace();
        }
        mCache = DBRCache.get(this);
        mCache.put("linear", "1");
        mCache.put("qrcode", "1");
        mCache.put("pdf417", "1");
        mCache.put("matrix", "1");
        mCache.put("aztec", "0");

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);



        if (null == savedInstanceState) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, Camera2BasicFragment.newInstance())
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(MainActivity.this, SettingActivity.class);
           // intent.putExtra("type", barcodeType);
            startActivityForResult(intent, 0);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            int nBarcodeFormat =0;
            if (mCache.getAsString("linear").equals("1")) {
                nBarcodeFormat = nBarcodeFormat| EnumBarcodeFormat.BF_OneD;
            }
            if (mCache.getAsString("qrcode").equals("1")) {
                nBarcodeFormat = nBarcodeFormat|EnumBarcodeFormat.BF_QR_CODE;
            }
            if (mCache.getAsString("pdf417").equals("1")) {
                nBarcodeFormat = nBarcodeFormat|EnumBarcodeFormat.BF_PDF417;
            }
            if (mCache.getAsString("matrix").equals("1")) {
                nBarcodeFormat = nBarcodeFormat|EnumBarcodeFormat.BF_DATAMATRIX;
            }
            if (mCache.getAsString("aztec").equals("1")) {
                nBarcodeFormat = nBarcodeFormat|EnumBarcodeFormat.BF_AZTEC;
            }

            PublicRuntimeSettings runtimeSettings =  mbarcodeReader.getRuntimeSettings();
            runtimeSettings.mBarcodeFormatIds = nBarcodeFormat;
            mbarcodeReader.updateRuntimeSettings(runtimeSettings);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public BarcodeReader getMainBarcdoeReader(){
        return mbarcodeReader;
    }
}
