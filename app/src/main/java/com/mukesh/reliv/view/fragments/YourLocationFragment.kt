package com.mukesh.reliv.view.fragments

import android.Manifest
import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.location.Address
import android.location.Geocoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.*
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.mukesh.reliv.R
import com.mukesh.reliv.common.CustomAlertDialog
import com.mukesh.reliv.common.LocationProvider
import com.mukesh.reliv.common.LocationProvider.LocationCallback
import com.mukesh.reliv.common.Preferences
import com.mukesh.reliv.databinding.FragmentYourLocationBinding
import com.mukesh.reliv.model.SignUpDO
import com.mukesh.reliv.view.activities.MainActivity
import com.mukesh.reliv.view.activities.SignUpActivity
import java.util.*


class YourLocationFragment : Fragment(), OnMapReadyCallback {

    private lateinit var fragBinding: FragmentYourLocationBinding
    private lateinit var mMap: GoogleMap
    private var lat: Double = 0.0
    private var lng: Double = 0.0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window: Window = (activity as SignUpActivity).window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = resources.getColor(R.color.white)
        }

        fragBinding = FragmentYourLocationBinding.inflate(layoutInflater)

        val signUpDOTemp = arguments?.getSerializable("SignUpDO") as SignUpDO

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        fragBinding.tvChange.setOnClickListener {
            val signUpDO = SignUpDO(
                mobileNo = signUpDOTemp.mobileNo,
                firstName = signUpDOTemp.firstName,
                lastName = signUpDOTemp.lastName,
                profileImagePath = signUpDOTemp.profileImagePath,
                gender = signUpDOTemp.gender,
                height = signUpDOTemp.height,
                weight = signUpDOTemp.weight,
                dateOfBirth = signUpDOTemp.dateOfBirth,
            )

            val bundle = bundleOf(
                "SignUpDO" to signUpDO
            )
            findNavController().navigate(R.id.action_yourLocation_to_changeLocation, bundle)
        }

        fragBinding.btnNext.setOnClickListener {

            if (fragBinding.tvLocation.text.toString().isEmpty()) {
                CustomAlertDialog.showDialog(
                    requireActivity(),
                    getString(R.string.alert),
                    getString(R.string.please_enter_your_address),
                    getString(R.string.ok),
                    "",
                    "",
                    true
                )
            } else {
                val signUpDO = SignUpDO(
                    mobileNo = signUpDOTemp.mobileNo,
                    firstName = signUpDOTemp.firstName,
                    lastName = signUpDOTemp.lastName,
                    profileImagePath = signUpDOTemp.profileImagePath,
                    gender = signUpDOTemp.gender,
                    height = signUpDOTemp.height,
                    weight = signUpDOTemp.weight,
                    dateOfBirth = signUpDOTemp.dateOfBirth,
                    address = fragBinding.tvLocation.text.toString()
                )

                var hashMap: HashMap<String, SignUpDO>? = Preferences.getUserHashMap()
                if (hashMap == null)
                    hashMap = HashMap()
                hashMap[signUpDO.mobileNo] = signUpDO
                Preferences.saveUserInHashMap(hashMap)

                val intent = Intent(activity, MainActivity::class.java)
                intent.putExtra("SignUpDO", signUpDO)
                startActivity(intent)
                activity?.finish()
            }
        }

        fragBinding.ivBack.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        return fragBinding.root
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        if (googleMap != null) {
            mMap = googleMap
            mMap.uiSettings.setAllGesturesEnabled(false)
            Dexter.withContext(requireActivity()).withPermissions(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ).withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                    report?.let {
                        if (report.areAllPermissionsGranted()) {
                            LocationProvider.requestSingleUpdate(requireActivity(),
                                object : LocationCallback {
                                    override fun onNewLocationAvailable(location: LocationProvider.GPSCoordinates?) {
                                        lat = (location?.latitude ?: 0.0).toDouble()
                                        lng = (location?.longitude ?: 0.0).toDouble()
                                        val currentLocation = LatLng(lat, lng)
                                        if (isAdded) {
                                            mMap.addMarker(
                                                MarkerOptions()
                                                    .position(currentLocation)
                                                    .title("Current Location")
                                                    .icon(
                                                        bitmapDescriptorFromVector(
                                                            requireActivity(),
                                                            R.drawable.ic_location_marker
                                                        )
                                                    )
                                            )
                                            mMap.animateCamera(
                                                CameraUpdateFactory.newLatLngZoom(
                                                    currentLocation,
                                                    18.0f
                                                )
                                            )
                                            if (lat != 0.0 || lng != 0.0)
                                                fragBinding.tvLocation.text =
                                                    getAddressFromGeoCoder(lat, lng)
                                        }
                                    }
                                })
                        }
                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    permissions: MutableList<PermissionRequest>?,
                    token: PermissionToken?
                ) {
                    showRationalDialogForPermissions()
                }
            }).onSameThread().check()
        }
    }

    fun getAddressFromGeoCoder(latitude: Double, longitude: Double): String {
        return try {
            val geoCoder = Geocoder(requireActivity(), Locale.getDefault())
            val addresses: List<Address> = geoCoder.getFromLocation(latitude, longitude, 1)
            addresses[0].getAddressLine(0)
        } catch (e: Exception) {
            e.printStackTrace()
            ""
        }
    }

    private fun showRationalDialogForPermissions() {
        AlertDialog.Builder(requireContext()).setMessage(
            "It looks like you have turn off permissions " +
                    "required for this feature. It can be enabled under Application Settings"
        )
            .setPositiveButton("GO TO SETTINGS") { _, _ ->
                try {
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    val uri = Uri.fromParts("package", requireActivity().packageName, null)
                    intent.data = uri
                    startActivity(intent)
                } catch (e: ActivityNotFoundException) {
                    e.printStackTrace()
                }
            }
            .setNegativeButton("CANCEL") { dialog, _ ->
                dialog.dismiss()

            }.show()
    }

    private fun bitmapDescriptorFromVector(
        context: Context,
        @DrawableRes vectorDrawableResourceId: Int
    ): BitmapDescriptor? {
        val vectorDrawable = ContextCompat.getDrawable(context, vectorDrawableResourceId)
        vectorDrawable!!.setBounds(
            40,
            20,
            vectorDrawable.intrinsicWidth + 40,
            vectorDrawable.intrinsicHeight + 20
        )
        val bitmap = Bitmap.createBitmap(
            vectorDrawable.intrinsicWidth + 50,
            vectorDrawable.intrinsicHeight + 20,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        vectorDrawable.draw(canvas)
        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }
}