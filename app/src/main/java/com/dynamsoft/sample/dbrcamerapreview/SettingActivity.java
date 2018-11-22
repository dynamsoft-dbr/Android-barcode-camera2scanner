package com.dynamsoft.sample.dbrcamerapreview;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.dynamsoft.sample.dbrcamerapreview.util.DBRCache;

public class SettingActivity extends
		Activity implements CompoundButton.OnCheckedChangeListener {

	CheckBox mLinear;
	CheckBox mQRCode;
	CheckBox mPDF417;
	CheckBox mDataMatrix;
	CheckBox mDataAztec;
	private int mBarcodeFormat;
	private DBRCache mCache;


	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_barcode_type);
		mLinear = findViewById(R.id.ckbLinear);
		mQRCode= findViewById(R.id.ckbQR);
		mPDF417= findViewById(R.id.ckbPDF417);
		mDataMatrix= findViewById(R.id.ckbDataMatrix);
		mDataAztec= findViewById(R.id.ckbAztec);

		Toolbar toolbar = (Toolbar) findViewById(R.id.settoolbar);
		toolbar.setTitle("Types Setting");
		toolbar.setNavigationOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onBackPressed();
			}
		});
		mLinear.setOnCheckedChangeListener(this);
		mQRCode.setOnCheckedChangeListener(this);
		mPDF417.setOnCheckedChangeListener(this);
		mDataMatrix.setOnCheckedChangeListener(this);
		mDataAztec.setOnCheckedChangeListener(this);

		mCache = DBRCache.get(this);
		if ("1".equals(mCache.getAsString("linear"))) {
			mLinear.setChecked(true);
		}
		if ("1".equals(mCache.getAsString("qrcode"))) {
			mQRCode.setChecked(true);
		}
		if ("1".equals(mCache.getAsString("pdf417"))) {
			mPDF417.setChecked(true);
		}
		if ("1".equals(mCache.getAsString("matrix"))) {
			mDataMatrix.setChecked(true);
		}
		if ("1".equals(mCache.getAsString("aztec"))) {
			mDataAztec.setChecked(true);
		}
		updateFormatCheckboxsState();

	}

	private void updateFormatCheckboxsState(){
		int nState = 0;
		CheckBox enabledCheckBox = null;
		if(mLinear.isChecked()) {
			nState++;
			enabledCheckBox = mLinear;
		}
		if(mQRCode.isChecked()) {
			nState++;
			enabledCheckBox = mQRCode;
		}
		if(mPDF417.isChecked()) {
			nState++;
			enabledCheckBox = mPDF417;
		}
		if(mDataMatrix.isChecked()){
			nState++;
			enabledCheckBox = mDataMatrix;
		}
		if(mDataAztec.isChecked()) {
			nState++;
			enabledCheckBox = mDataAztec;
		}

		if(nState ==1){
			enabledCheckBox.setEnabled(false);
		}else{
			mLinear.setEnabled(true);
			mQRCode.setEnabled(true);
			mPDF417.setEnabled(true);
			mDataMatrix.setEnabled(true);
			mDataAztec.setEnabled(true);
		}
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		if (mLinear.isChecked()) {
			mCache.put("linear", "1");
		} else {
			mCache.put("linear", "0");
		}
		if (mQRCode.isChecked()) {
			mCache.put("qrcode", "1");
		} else {
			mCache.put("qrcode", "0");
		}
		if (mPDF417.isChecked()) {
			mCache.put("pdf417", "1");
		} else {
			mCache.put("pdf417", "0");
		}
		if (mDataMatrix.isChecked()) {
			mCache.put("matrix", "1");
		} else {
			mCache.put("matrix", "0");
		}
		if (mDataAztec.isChecked()) {
			mCache.put("aztec", "1");
		} else {
			mCache.put("aztec", "0");
		}
		setResult(0);
	}

	@Override
	public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
		updateFormatCheckboxsState();
	}
}
