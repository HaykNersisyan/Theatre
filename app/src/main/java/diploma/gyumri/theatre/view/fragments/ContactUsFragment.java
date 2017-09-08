package diploma.gyumri.theatre.view.fragments;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.tuanchauict.intentchooser.SharePlainTextChooserMaker;
import com.tuanchauict.intentchooser.sharetext.EmailChooser;
import com.tuanchauict.intentchooser.sharetext.FacebookChooser;
import com.tuanchauict.intentchooser.sharetext.FacebookMessengerChooser;
import com.tuanchauict.intentchooser.sharetext.GooglePlusChooser;
import com.tuanchauict.intentchooser.sharetext.SMSChooser;
import com.tuanchauict.intentchooser.sharetext.TwitterChooser;
import com.tuanchauict.intentchooser.sharetext.UniversalChooser;
import com.tuanchauict.intentchooser.sharetext.ViberChooser;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import diploma.gyumri.theatre.R;
import diploma.gyumri.theatre.constants.Constants;
import diploma.gyumri.theatre.view.activities.MainActivity;
import diploma.gyumri.theatre.view.activities.WebActivity;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ContactUsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class ContactUsFragment extends Fragment implements OnMapReadyCallback {
    private GoogleMap map;
    private OnFragmentInteractionListener mListener;
    private Unbinder unbinder;
    private AlertDialog.Builder alertDialog;
    @BindView(R.id.call)
    LinearLayout call;
    @BindView(R.id.site)
    LinearLayout site;
    @BindView(R.id.email)
    LinearLayout email;
    @BindView(R.id.phoneNumber)
    TextView phoneNumber;
    @BindView(R.id.emailAdress)
    TextView emailAdress;
    @BindView(R.id.siteURL)
    TextView siteURL;

    public ContactUsFragment() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contact_us, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        alertDialog = new AlertDialog.Builder(getContext());

        alertDialog.setTitle(phoneNumber.getText().toString().trim());
        alertDialog.setCancelable(true);
        if (map == null) {
            ((SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map)).getMapAsync(this);
        }

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng theatreCoordinate = new LatLng(40.790665, 43.844927);
        map = googleMap;
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(theatreCoordinate, 16.5f));
        BitmapDescriptor marker = BitmapDescriptorFactory.fromResource(R.mipmap.map_marker);
        map.addMarker(new MarkerOptions().position(theatreCoordinate).icon(marker));
    }

    @OnClick({R.id.fb, R.id.site, R.id.call, R.id.email})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.fb:
                Intent facebookIntent = new Intent(Intent.ACTION_VIEW);
                try {
                    String facebookUrl = getFacebookPageURL(getActivity());
                    facebookIntent.setData(Uri.parse(facebookUrl));
                    startActivity(facebookIntent);
                } catch (PackageManager.NameNotFoundException | ActivityNotFoundException e) {
                    Intent intent = new Intent(getActivity(), WebActivity.class);
                    intent.putExtra("url", Constants.FACEBOOK_URL);
                    startActivity(intent);
                }
                break;
            case R.id.call:
                call_action();
                break;
            case R.id.site:
                Intent intent = new Intent(getActivity(), WebActivity.class);
                intent.putExtra("url", siteURL.getText().toString());
                startActivity(intent);
                break;
            case R.id.email:
                email();
                break;
        }
    }

    public String getFacebookPageURL(Context context) throws PackageManager.NameNotFoundException {
        PackageManager packageManager = context.getPackageManager();
        try {
            int versionCode = packageManager.getPackageInfo("com.facebook.katana", 0).versionCode;
            Log.i("111", "getFacebookPageURL: 2");
            return "fb://page/" + Constants.FACEBOOK_PAGE_ID;

        } catch (PackageManager.NameNotFoundException e) {
            return Constants.FACEBOOK_URL;
        }
    }

    public boolean isPermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (getActivity().checkSelfPermission(android.Manifest.permission.CALL_PHONE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v("TAG", "Permission is granted");
                return true;
            } else {

                Log.v("TAG", "Permission is revoked");
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CALL_PHONE}, 1);
                return false;
            }
        } else {
            Log.v("TAG", "Permission is granted");
            return true;
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getActivity(), "Permission granted", Toast.LENGTH_SHORT).show();
                    call_action();
                } else {
                    Toast.makeText(getActivity(), "Permission denied", Toast.LENGTH_SHORT).show();
                }
                return;
            }

        }
    }

    public void call_action() {
        String phnum = phoneNumber.getText().toString().trim();
        Intent i = new Intent(Intent.ACTION_DIAL);
        i.setData(Uri.parse("tel:" + phnum));
        startActivity(Intent.createChooser(i, "Զանգահարել"));
    }


    private void email() {
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("plain/text");
        i.putExtra(Intent.EXTRA_EMAIL, new String[]{emailAdress.getText().toString()});
        try {
            startActivity(Intent.createChooser(i, "Ուղղարկել նամակ"));
        } catch (android.content.ActivityNotFoundException ex) {

        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
