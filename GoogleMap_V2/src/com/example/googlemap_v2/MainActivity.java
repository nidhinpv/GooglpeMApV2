package com.example.googlemap_v2;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.app.Activity;
import android.app.Dialog;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.widget.Toast;

public class MainActivity extends FragmentActivity {

	static String pwd;
	GoogleMap map;
	LatLng currentLocation;
	double latitude, longitude;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Getting status
		int status = GooglePlayServicesUtil
				.isGooglePlayServicesAvailable(getBaseContext());
		System.out.println("am hereeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");

		LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

		 if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
			// Toast.makeText(this, "GPS is Enabled in your device",
			// Toast.LENGTH_SHORT).show();
			Criteria criteria = new Criteria();// set criteria for the kind of
												// provider you want
			// criteria.setAltitudeRequired(false);
			// criteria.setBearingRequired(false);
			// criteria.setAccuracy(Criteria.ACCURACY_COARSE);
			// criteria.setCostAllowed(true);
			String provider = locationManager.getBestProvider(criteria, false);
			Location location = locationManager.getLastKnownLocation(provider);
			if (location != null) {
				latitude = location.getLatitude();
				longitude = location.getLongitude();
				currentLocation = new LatLng(latitude, longitude);

				System.out.println(latitude + "latt," + longitude + "longg");

				// updateLocation(location);

				// Showing status
				if (status == ConnectionResult.SUCCESS) {
					SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager()
							.findFragmentById(R.id.map);

					map = supportMapFragment.getMap();

					map.setMapType(GoogleMap.MAP_TYPE_NORMAL);

				} else {

					int requestCode = 10;
					Dialog dialog = GooglePlayServicesUtil.getErrorDialog(
							status, this, requestCode);
					dialog.show();
				}
				Marker hamburg = map.addMarker(new MarkerOptions().position(
						currentLocation).title(latitude + "," + longitude));
				// Move the camera instantly to hamburg with a zoom of 15.
				map.moveCamera(CameraUpdateFactory.newLatLng(currentLocation));

				// Zoom in, animating the camera.
				map.animateCamera(CameraUpdateFactory.zoomTo(7), 2000, null);
			}
		} else {
			Toast.makeText(MainActivity.this, "Location Not Available",
					Toast.LENGTH_LONG).show();
			System.out.println("location not available");
		}

	}

}