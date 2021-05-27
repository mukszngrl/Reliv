package com.mukesh.reliv.view.fragments

import android.Manifest
import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.mukesh.reliv.R
import com.mukesh.reliv.common.CustomAlertDialog
import com.mukesh.reliv.common.LocationProvider
import com.mukesh.reliv.common.Preferences
import com.mukesh.reliv.databinding.FragmentChangeLocationBinding
import com.mukesh.reliv.model.SignUpDO
import com.mukesh.reliv.view.activities.MainActivity
import java.util.*
import kotlin.collections.HashMap

class ChangeLocationFragment : Fragment(), OnMapReadyCallback {

    private lateinit var fragBinding: FragmentChangeLocationBinding
    private lateinit var mMap: GoogleMap
    private var lat: Double = 0.0
    private var lng: Double = 0.0
    private lateinit var country: String
    private lateinit var houseNo: String
    private lateinit var street: String
    private lateinit var city: String
    private lateinit var state: String
    private lateinit var zip: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragBinding = FragmentChangeLocationBinding.inflate(layoutInflater)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        fragBinding.ivBack.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        val signUpDOTemp = arguments?.getSerializable("SignUpDO") as SignUpDO

        fragBinding.btnNext.setOnClickListener {
            when {
                houseNo.isEmpty() ->
                    CustomAlertDialog.showDialog(
                        requireActivity(),
                        getString(R.string.alert),
                        getString(R.string.please_enter_your_house_no),
                        getString(R.string.ok),
                        "",
                        "",
                        true
                    )
                street.isEmpty() ->
                    CustomAlertDialog.showDialog(
                        requireActivity(),
                        getString(R.string.alert),
                        getString(R.string.please_enter_street),
                        getString(R.string.ok),
                        "",
                        "",
                        true
                    )
                city.isEmpty() ->
                    CustomAlertDialog.showDialog(
                        requireActivity(),
                        getString(R.string.alert),
                        getString(R.string.please_enter_city),
                        getString(R.string.ok),
                        "",
                        "",
                        true
                    )
                state.isEmpty() ->
                    CustomAlertDialog.showDialog(
                        requireActivity(),
                        getString(R.string.alert),
                        getString(R.string.please_enter_state),
                        getString(R.string.ok),
                        "",
                        "",
                        true
                    )
                zip.isEmpty() ->
                    CustomAlertDialog.showDialog(
                        requireActivity(),
                        getString(R.string.alert),
                        getString(R.string.please_enter_zip_code),
                        getString(R.string.ok),
                        "",
                        "",
                        true
                    )
                else -> {
                    val signUpDO = SignUpDO(
                        mobileNo = signUpDOTemp.mobileNo,
                        firstName = signUpDOTemp.firstName,
                        lastName = signUpDOTemp.lastName,
                        profileImagePath = signUpDOTemp.profileImagePath,
                        gender = signUpDOTemp.gender,
                        height = signUpDOTemp.height,
                        weight = signUpDOTemp.weight,
                        dateOfBirth = signUpDOTemp.dateOfBirth,
                        address = "${fragBinding.etHouse.text}, ${fragBinding.etStreet.text}, ${fragBinding.etCity.text}, ${fragBinding.etState.text}, ${fragBinding.etZip.text}, $country"
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
        }

        return fragBinding.root
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        Dexter.withContext(requireActivity()).withPermissions(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ).withListener(object : MultiplePermissionsListener {
            override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                report?.let {
                    if (report.areAllPermissionsGranted()) {
                        LocationProvider.requestSingleUpdate(requireActivity(),
                            object : LocationProvider.LocationCallback {
                                override fun onNewLocationAvailable(location: LocationProvider.GPSCoordinates?) {
                                    lat = (location?.latitude ?: 0.0).toDouble()
                                    lng = (location?.longitude ?: 0.0).toDouble()
                                    val currentLocation = LatLng(lat, lng)
                                    mMap.animateCamera(
                                        CameraUpdateFactory.newLatLngZoom(
                                            currentLocation,
                                            18.0f
                                        )
                                    )
                                    if (lat != 0.0 || lng != 0.0)
                                        getAddressFromGeoCoder(lat, lng)
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

        mMap.setOnCameraMoveListener {
            lat = mMap.cameraPosition.target.latitude
            lng = mMap.cameraPosition.target.longitude
            if (lat != 0.0 || lng != 0.0)
                getAddressFromGeoCoder(lat, lng)
        }
    }

    fun getAddressFromGeoCoder(latitude: Double, longitude: Double) {
        try {
            val geoCoder = Geocoder(requireActivity(), Locale.getDefault())
            val addresses: List<Address> = geoCoder.getFromLocation(latitude, longitude, 1)
            if (addresses.isNotEmpty()) {
                houseNo = addresses[0].featureName?.let { addresses[0].featureName } ?: ""
                street = addresses[0].thoroughfare?.let { addresses[0].thoroughfare } ?: ""
                city = addresses[0].locality?.let { addresses[0].locality } ?: ""
                state = addresses[0].adminArea?.let { addresses[0].adminArea } ?: ""
                zip = addresses[0].postalCode?.let { addresses[0].postalCode } ?: ""
                country = addresses[0].countryName?.let { addresses[0].countryName } ?: ""

                fragBinding.etHouse.setText(houseNo)
                fragBinding.etStreet.setText(street)
                fragBinding.etCity.setText(city)
                fragBinding.etState.setText(state)
                fragBinding.etZip.setText(zip)
            }
        } catch (e: Exception) {
            e.printStackTrace()
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
}