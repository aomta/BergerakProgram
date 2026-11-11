package com.example.jarakkekampus

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.os.Looper
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority

class MainActivity : AppCompatActivity() {

    companion object {
        // Koordinat contoh (Monas, Jakarta). Ganti dengan koordinat kampus Anda.
        private const val KAMPUS_LAT = -6.1754
        private const val KAMPUS_LON = 106.8272
    }

    private lateinit var fusedClient: FusedLocationProviderClient
    private lateinit var tvInfo: TextView

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(result: LocationResult) {
            val lokasi = result.lastLocation ?: return
            tampilkan(lokasi)
        }
    }

    private val mintaIzin = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { hasil ->
        if (hasil.values.none { it }) {
            tvInfo.text = "Izin lokasi ditolak"
        }
        // Jika diberikan, mulaiLokasi() dipanggil dari onResume().
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvInfo = findViewById(R.id.tvInfo)
        fusedClient = LocationServices.getFusedLocationProviderClient(this)

        if (!izinDiberikan()) {
            mintaIzin.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        }
    }

    override fun onResume() {
        super.onResume()
        if (izinDiberikan()) mulaiLokasi()
    }

    override fun onPause() {
        super.onPause()
        // Hentikan pembaruan saat layar tidak terlihat, agar baterai hemat.
        fusedClient.removeLocationUpdates(locationCallback)
    }

    @SuppressLint("MissingPermission")
    private fun mulaiLokasi() {
        val request = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 5000L).build()
        fusedClient.requestLocationUpdates(request, locationCallback, Looper.getMainLooper())
    }

    private fun izinDiberikan(): Boolean {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED
    }

    private fun tampilkan(lokasi: Location) {
        val kampus = Location("kampus").apply {
            latitude = KAMPUS_LAT
            longitude = KAMPUS_LON
        }
        val jarak = lokasi.distanceTo(kampus) // dalam meter
        tvInfo.text = "Lat: %.6f\nLon: %.6f\nJarak ke kampus: %.0f m"
            .format(lokasi.latitude, lokasi.longitude, jarak)
    }
}