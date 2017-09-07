package diploma.gyumri.theatre.view.fragments;


import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import diploma.gyumri.theatre.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AnnalsFragment extends Fragment {


    public AnnalsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_annals, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ImageView histIcon = (ImageView) view.findViewById(R.id.history_icon);
        Bitmap image = BitmapFactory.decodeResource(getResources(), R.drawable.history);
        Point size = new Point();
        (getActivity()).getWindowManager().getDefaultDisplay().getSize(size);
        int procent = image.getHeight() / (image.getWidth() / 100);
        int h = (size.x * procent) / 100;
        Picasso.with(getContext()).load(R.drawable.history).resize(size.x, h).into(histIcon);
    }
}
