package com.marlonncarvalhosa.covidmap.view

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityOptionsCompat
import androidx.core.content.ContextCompat
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.TileOverlayOptions
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.maps.android.heatmaps.Gradient
import com.google.maps.android.heatmaps.HeatmapTileProvider
import com.google.maps.android.heatmaps.WeightedLatLng
import com.marlonncarvalhosa.covidmap.R
import com.marlonncarvalhosa.covidmap.databinding.ActivityMapsBinding
import com.marlonncarvalhosa.covidmap.dialog.DialogLogin
import com.marlonncarvalhosa.covidmap.utils.FirebaseRepo
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.activity_maps.*
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlin.random.Random

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private val dataStore: DataStore<Preferences> by preferencesDataStore("location")
    private var TAG = "MAPSACTIVITY"
    private lateinit var firebaseAuth: FirebaseAuth
    private val LOCATION_PERMISSION_REQUEST = 1
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: LocationCallback
    private var googleSignClient: GoogleSignInClient? = null
    private var dataMap = ArrayList<WeightedLatLng>()
    val firestore = FirebaseFirestore.getInstance()
    companion object{
        val LAT_KEY = stringPreferencesKey("LATITUDE")
        val LONG_KEY = stringPreferencesKey("LOGITUDE")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapsBinding.inflate(layoutInflater).apply {
            setContentView(root)
        }
        getSignInGoogle()
        firebaseAuth = FirebaseAuth.getInstance()

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        fb_profile.setOnClickListener {
            openProfile()
        }

        openQuiz()

        val fab = binding.fbStats
        fab.setOnClickListener {
            val intent = Intent(this, BrasilStatusCovidActivity::class.java)
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                this, fab, fab.transitionName)

            startActivity(intent, options.toBundle())
        }

        ib_my_location.setOnClickListener {
            getLocationAccess()
            onMapReady(mMap)
        }
    }

    private suspend fun saveData(lat: String, long: String) {
        dataStore.edit { settings ->
            settings[LAT_KEY] = lat
            settings[LONG_KEY] = long
            Log.d("LOCATION", "Latitude ${settings[LAT_KEY].toString()}, Longitude ${settings[LONG_KEY].toString()}")
        }
    }

    suspend fun readData(key: String): String? {
        val prefsKey = stringPreferencesKey(key)
        val prefs = dataStore.data.first()
        return prefs[prefsKey]
    }

    private fun openProfile() {
        if (FirebaseAuth.getInstance().currentUser != null) {
            val builder = AlertDialog.Builder(this)
            val view = View.inflate(this, R.layout.dialog_profile, null)
            val messageView = view.findViewById<TextView>(R.id.tv_name_profile)
            val civ_profile = view.findViewById<CircleImageView>(R.id.civ_profile)

            builder.setView(view)

            val dialog = builder.create()

            dialog.window?.attributes?.windowAnimations = R.style.SlidingDialogAnimation
            dialog.show()
            dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

            val nome = FirebaseAuth.getInstance().currentUser?.displayName
            val photo = FirebaseAuth.getInstance().currentUser?.photoUrl

            messageView.text = nome
            Picasso.get().load(photo).fit().centerCrop()
                .placeholder(R.drawable.ic_person).into(civ_profile)

            view.findViewById<ImageButton>(R.id.iv_close_dialog).setOnClickListener {
                dialog.dismiss()
            }
            view.findViewById<ImageButton>(R.id.iv_exit_app).setOnClickListener {
                messageView.text = R.string.nome_do_usuario.toString()
                Picasso.get().load(R.drawable.ic_person).fit().centerCrop()
                    .into(civ_profile)

                signOutGoogle()
                dialog.dismiss()
            }

        } else {
            authenticator()
        }
    }

    private fun openQuiz() {
        if (firebaseAuth.currentUser != null) {
            val fabQuiz = binding.fbQuiz
            fabQuiz.setOnClickListener {
                val intent = Intent(this, QuizActivity::class.java)
                val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    this, fabQuiz, fabQuiz.transitionName)

                val fb = FirebaseRepo()
                fusedLocationProviderClient.lastLocation.addOnSuccessListener { location ->
                    if (location != null) {
                        lifecycleScope.launch {
                            val lat = location.latitude.toString()
                            val long = location.longitude.toString()
                            saveData(lat, long)
                        }

                        val arr: Array<Double> = arrayOf(200.0, 150.0, 100.0, 50.0)
                        val nextValues = Random.nextInt(1, 4)
                        fb.postLocation(
                            arr[nextValues],
                            location.latitude.toString(),
                            location.longitude.toString()
                        )
                    }
                }

                startActivity(intent, options.toBundle())
            }

            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return
            }
        } else {
            authenticator()
        }
    }

    fun getSignInGoogle() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignClient = GoogleSignIn.getClient(this, gso)
    }

    private fun signOutGoogle() {
        googleSignClient?.signOut()?.addOnCompleteListener(this, OnCompleteListener<Void?> {
            FirebaseAuth.getInstance().signOut()
            Toast.makeText(this, "Sign Out realizado!", Toast.LENGTH_SHORT).show()
        })
    }

    private fun authenticator() {
        val dialog = DialogLogin()
        dialog.show(supportFragmentManager, "DialogLogin")
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_PERMISSION_REQUEST) {
            if (grantResults.contains(PackageManager.PERMISSION_GRANTED)) {
                if (ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    return
                }
                mMap.isMyLocationEnabled = true
                fusedLocationProviderClient.lastLocation.addOnSuccessListener { location ->
                    if (location != null) {
                        val currentLatLong = LatLng(location.latitude, location.longitude)
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLong, 15f))
                    }
                }
            } else {
                Toast.makeText(
                    this,
                    "O usuário não concedeu permissão de acesso ao local",
                    Toast.LENGTH_LONG
                ).show()
                finish()
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {

        firestore.collection("locations")
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {

                    val lat = document.data["lat"].toString().toDouble()
                    val lon = document.data["lon"].toString().toDouble()
                    val density = document.data["intensity"].toString().toDouble()

                    // optional: remove edge cases like 0 population density values
                    if (density != 0.0) {
                        val weightedLatLng = WeightedLatLng(LatLng(lat, lon), density)
                        dataMap.add(weightedLatLng)
                    }
                    Log.d(TAG, "${document.id} => ${document.data["id_user"]}")
                }

                mMap = googleMap
                mMap.setMaxZoomPreference(15f)
                mMap.uiSettings.isMyLocationButtonEnabled = false

                Log.d(TAG, "${dataMap.toArray()}")

                if (dataMap.isEmpty()) {
                    getLocationAccess()
                } else {
                    val colors = intArrayOf(
                        Color.GREEN,  // green(0-50)
                        Color.YELLOW,  // yellow(51-100)
                        Color.rgb(255, 165, 0),  //Orange(101-150)
                        Color.RED,  //red(151-200)
                    )
                    val startPoints = floatArrayOf(
                        0.25f, 0.5f, 0.75f, 1.0f
                    )

                    val gradient = Gradient(colors, startPoints)

                    val heatMapProvider = HeatmapTileProvider.Builder()
                        .weightedData(dataMap) // load our weighted data
                        .radius(50) // optional, in pixels, can be anything between 20 and 50
                        .gradient(gradient)
                        .build()

                    mMap.addTileOverlay(TileOverlayOptions().tileProvider(heatMapProvider))

                    getLocationAccess()
                }
            }.addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents: ", exception)
            }
    }

    private fun getLocationAccess() {
        if (ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            mMap.isMyLocationEnabled = true
            getLocationUpdates()
            startLocationUpdates()
        } else
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST
            )
    }

    private fun getLocationUpdates() {
        locationRequest = LocationRequest()
        locationRequest.interval = 30000
        locationRequest.fastestInterval = 20000
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                if (locationResult.locations.isNotEmpty()) {
                    val location = locationResult.lastLocation
                    val latLng = LatLng(location.latitude, location.longitude)
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))
                }
            }
        }
    }

    private fun startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        fusedLocationProviderClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            null
        )
    }
}