package com.dynamsoft.sample.dbrcamerapreview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;

import com.dynamsoft.barcode.BarcodeReader;
import com.dynamsoft.barcode.DBRServerLicenseVerificationListener;
import com.dynamsoft.barcode.EnumBarcodeFormat;
import com.dynamsoft.barcode.EnumBarcodeFormat_2;
import com.dynamsoft.barcode.EnumIntermediateResultType;
import com.dynamsoft.barcode.PublicRuntimeSettings;
import com.dynamsoft.sample.dbrcamerapreview.util.DBRCache;

public class MainActivity extends AppCompatActivity {
    private BarcodeReader mbarcodeReader;
    private DBRCache mCache;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            mbarcodeReader = new BarcodeReader("");
            //mbarcodeReader = new BarcodeReader("license-key");
            //You can get trial license from "https://www.dynamsoft.com/CustomerPortal/Portal/Triallicense.aspx"
            PublicRuntimeSettings settings = mbarcodeReader.getRuntimeSettings();
            settings.intermediateResultTypes = EnumIntermediateResultType.IRT_TYPED_BARCODE_ZONE;
            settings.barcodeFormatIds = EnumBarcodeFormat.BF_ONED | EnumBarcodeFormat.BF_DATAMATRIX | EnumBarcodeFormat.BF_QR_CODE | EnumBarcodeFormat.BF_PDF417;
            settings.barcodeFormatIds_2 = 0;
            mbarcodeReader.updateRuntimeSettings(settings);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mCache = DBRCache.get(this);
        mCache.put("linear", "1");
        mCache.put("qrcode", "1");
        mCache.put("pdf417", "1");
        mCache.put("matrix", "1");
        mCache.put("aztec", "0");
        mCache.put("databar", "0");
        mCache.put("patchcode", "0");
        mCache.put("maxicode", "0");
        mCache.put("microqr", "0");
        mCache.put("micropdf417", "0");
        mCache.put("gs1compositecode", "0");
        mCache.put("postalcode", "0");
        mCache.put("dotcode", "0");

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
            int nBarcodeFormat_2 = 0;
            if (mCache.getAsString("linear").equals("1")) {
                nBarcodeFormat = nBarcodeFormat| EnumBarcodeFormat.BF_ONED;
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
            if (mCache.getAsString("databar").equals("1")) {
                nBarcodeFormat = nBarcodeFormat|EnumBarcodeFormat.BF_GS1_DATABAR;
            }
            if (mCache.getAsString("patchcode").equals("1")) {
                nBarcodeFormat = nBarcodeFormat|EnumBarcodeFormat.BF_PATCHCODE;
            }
            if (mCache.getAsString("maxicode").equals("1")) {
                nBarcodeFormat = nBarcodeFormat|EnumBarcodeFormat.BF_MAXICODE;
            }
            if (mCache.getAsString("microqr").equals("1")) {
                nBarcodeFormat = nBarcodeFormat|EnumBarcodeFormat.BF_MICRO_QR;
            }
            if (mCache.getAsString("micropdf417").equals("1")) {
                nBarcodeFormat = nBarcodeFormat|EnumBarcodeFormat.BF_MICRO_PDF417;
            }
            if (mCache.getAsString("gs1compositecode").equals("1")) {
                nBarcodeFormat = nBarcodeFormat|EnumBarcodeFormat.BF_GS1_COMPOSITE;
            }
            if (mCache.getAsString("postalcode").equals("1")) {
                nBarcodeFormat_2 = nBarcodeFormat_2 | EnumBarcodeFormat_2.BF2_POSTALCODE;
            }
            if (mCache.getAsString("dotcode").equals("1")) {
                nBarcodeFormat_2 = nBarcodeFormat_2 | EnumBarcodeFormat_2.BF2_DOTCODE;
            }

            PublicRuntimeSettings runtimeSettings =  mbarcodeReader.getRuntimeSettings();
            runtimeSettings.barcodeFormatIds = nBarcodeFormat;
            runtimeSettings.barcodeFormatIds_2 = nBarcodeFormat_2;
            mbarcodeReader.updateRuntimeSettings(runtimeSettings);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public BarcodeReader getMainBarcdoeReader(){
        return mbarcodeReader;
    }
}
