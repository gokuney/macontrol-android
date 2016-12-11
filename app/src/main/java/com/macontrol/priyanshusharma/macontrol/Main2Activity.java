package com.macontrol.priyanshusharma.macontrol;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeScanner;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


public class Main2Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private final static int REQUEST_ENABLE_BT = 1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {









        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);




//BT

        // Create a BroadcastReceiver for ACTION_FOUND
         final BroadcastReceiver mReceiver = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                // When discovery finds a device

                if (BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(action)) {
                    //discovery starts, we can show progress dialog or perform other tasks
                    Toast.makeText(context, "Discovering..", Toast.LENGTH_SHORT).show();
                    ProgressBar pg = (ProgressBar) findViewById(R.id.searching);
                    pg.setVisibility(View.VISIBLE);
                } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                    //discovery finishes, dismis progress dialog
                    Toast.makeText(context, "Done Searching!", Toast.LENGTH_SHORT).show();

                    ProgressBar pg = (ProgressBar) findViewById(R.id.searching);
                    pg.setVisibility(View.INVISIBLE);


                } else if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                    //bluetooth device found
                    BluetoothDevice device = (BluetoothDevice) intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                    Toast.makeText(context, "!! FOUND SOMETHING !!", Toast.LENGTH_SHORT).show();
                    //showToast("Found device " + device.getName());
                    //Toast.makeText(context, device.getName(), Toast.LENGTH_SHORT).show();

                    ProgressBar pg = (ProgressBar) findViewById(R.id.searching);
                    pg.setVisibility(View.INVISIBLE);
                }

            }
        };


// Register the BroadcastReceiver
        IntentFilter filter = new IntentFilter();
        filter.addAction(BluetoothDevice.ACTION_NAME_CHANGED);
        filter.addAction(BluetoothDevice.ACTION_FOUND);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);

        registerReceiver(mReceiver, filter); // Don't forget to unregister during onDestroy



    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main2, menu);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            Toast.makeText(this, "Clicked on camera", Toast.LENGTH_SHORT).show();
            //LinearLayout testLayout = (LinearLayout) findViewById( R.id.main_container);
            RelativeLayout testLayout = (RelativeLayout)  findViewById( R.id.main_container);
            LayoutInflater inflator = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout  = inflator.inflate(R.layout.test , null);

            testLayout.removeAllViews();
            testLayout.addView(layout);

            Button search = (Button) findViewById(R.id.search);
            search.setOnClickListener( mOnClickListener );


            // Handle the camera action
        } else if (id == R.id.nav_gallery) {
            Toast.makeText(this, "Clicked on gallery", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



    public void list_paired_devices(BluetoothAdapter mBluetoothAdapter){
        //Check for paired devices
        //set adapter
        final ListView list = (ListView) findViewById(R.id.list_devices);
        final List<String> devices_list = new ArrayList<String>();
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>( Main2Activity.this , android.R.layout.simple_list_item_1 , devices_list);
        list.setAdapter(arrayAdapter);
        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
        // If there are paired devices
        if (pairedDevices.size() > 0) {
            // Loop through paired devices
            for (BluetoothDevice device : pairedDevices) {
                // Add the name and address to an array adapter to show in a ListView
                arrayAdapter.add(device.getName() + "\n" + device.getAddress());
            }
            arrayAdapter.notifyDataSetChanged();

        }
    }

    public void list_new_devices( BluetoothAdapter mBluetoothAdapter ){
        //Check for paired devices
        Toast.makeText(this, "Searching for new devices", Toast.LENGTH_SHORT).show();
        //set adapter
        final ListView list = (ListView) findViewById(R.id.new_devices);
        final List<String> devices_list = new ArrayList<String>();
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>( Main2Activity.this , android.R.layout.simple_list_item_1 , devices_list);
        list.setAdapter(arrayAdapter);
        ProgressBar pg = (ProgressBar) findViewById(R.id.searching);
        pg.setVisibility(View.VISIBLE);

        //mBluetoothAdapter.startDiscovery();
        mBluetoothAdapter.enable();
        mBluetoothAdapter.startDiscovery();
        //BluetoothLeScanner scanner = mBluetoothAdapter.getBluetoothLeScanner();



        }

    //Onclick add devices button

    public View.OnClickListener   mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            //Check if the bluetooth is working

            BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            //BluetoothManager mBluetoothManager= (BluetoothManager) getSystemService(BLUETOOTH_SERVICE);


           //BluetoothAdapter mBluetoothAdapter = mBluetoothManager.getAdapter();

            if (mBluetoothAdapter == null) {
                // Device does not support Bluetooth
                Toast.makeText(Main2Activity.this, "Device does not support BT", Toast.LENGTH_SHORT).show();
            }

            //Check if BT is enabled
            if (!mBluetoothAdapter.isEnabled()) {
                Toast.makeText(Main2Activity.this, "Please enable BT", Toast.LENGTH_SHORT).show();
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);

            }
            //Make it discoverable
//            Intent discoverableIntent = new
//                    Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
//            discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
//            startActivity(discoverableIntent);

            list_paired_devices(mBluetoothAdapter);
            list_new_devices( mBluetoothAdapter );

        }
    };






}


