/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.bluetoothadvertisements;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Setup display fragments and ensure the device supports Bluetooth.
 */
public class MainActivity extends FragmentActivity {

    private BluetoothAdapter mBluetoothAdapter;
    private final int PERMISSIONS_REQUEST_CODE = 111;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(R.string.activity_main_title);

        // get location permission that BLE required.
        this.requestPremissions();

        if (savedInstanceState == null) {

            mBluetoothAdapter = ((BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE))
                    .getAdapter();

            // Is Bluetooth supported on this device?
            if (mBluetoothAdapter != null) {

                // Is Bluetooth turned on?
                if (mBluetoothAdapter.isEnabled()) {

                    // Are Bluetooth Advertisements supported on this device?
                    if (mBluetoothAdapter.isMultipleAdvertisementSupported()) {

                        // Everything is supported and enabled, load the fragments.
                        setupFragments();

                    } else {

                        // Bluetooth Advertisements are not supported.
                        showErrorText(R.string.bt_ads_not_supported);
                    }
                } else {

                    // Prompt user to turn on Bluetooth (logic continues in onActivityResult()).
                    Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(enableBtIntent, Constants.REQUEST_ENABLE_BT);
                }
            } else {

                // Bluetooth is not supported.
                showErrorText(R.string.bt_not_supported);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case Constants.REQUEST_ENABLE_BT:

                if (resultCode == RESULT_OK) {

                    // Bluetooth is now Enabled, are Bluetooth Advertisements supported on
                    // this device?
                    if (mBluetoothAdapter.isMultipleAdvertisementSupported()) {

                        // Everything is supported and enabled, load the fragments.
                        setupFragments();

                    } else {

                        // Bluetooth Advertisements are not supported.
                        showErrorText(R.string.bt_ads_not_supported);
                    }
                } else {

                    // User declined to enable Bluetooth, exit the app.
                    Toast.makeText(this, R.string.bt_not_enabled_leaving,
                            Toast.LENGTH_SHORT).show();
                    finish();
                }

            default:
                super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
        String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_REQUEST_CODE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    this.requestPremissions();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    private void setupFragments() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        ScannerFragment scannerFragment = new ScannerFragment();
        // Fragments can't access system services directly, so pass it the BluetoothAdapter
        scannerFragment.setBluetoothAdapter(mBluetoothAdapter);
        transaction.replace(R.id.scanner_fragment_container, scannerFragment);

        AdvertiserFragment advertiserFragment = new AdvertiserFragment();
        transaction.replace(R.id.advertiser_fragment_container, advertiserFragment);

        transaction.commit();
    }

    private void showErrorText(int messageId) {

        TextView view = (TextView) findViewById(R.id.error_textview);
        view.setText(getString(messageId));
    }

    private void requestPremissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {

                // Should we show an explanation?
                if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_CONTACTS)) {

                    // Show an expanation to the user *asynchronously* -- don't block
                    // this thread waiting for the user's response! After the user
                    // sees the explanation, try again to request the permission.

                } else {

                    // No explanation needed, we can request the permission.

                    ActivityCompat.requestPermissions(this,
                        new String[]{
                            Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION,
                        },
                        PERMISSIONS_REQUEST_CODE);

                    // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                    // app-defined int constant. The callback method gets the
                    // result of the request.
                }
            }
        }
    }
}