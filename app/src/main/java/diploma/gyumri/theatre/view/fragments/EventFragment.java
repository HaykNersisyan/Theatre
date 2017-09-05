package diploma.gyumri.theatre.view.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayer.OnInitializedListener;
import com.google.android.youtube.player.YouTubePlayer.Provider;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTouch;
import butterknife.Unbinder;
import diploma.gyumri.theatre.R;
import diploma.gyumri.theatre.constants.Constants;
import diploma.gyumri.theatre.model.Event;
import diploma.gyumri.theatre.view.presenter.listeners.YouTubePlayerStateChangeListener;

public class EventFragment extends Fragment {
    private YouTubePlayer YPlayer;
    private Event mEvent;
    private YouTubePlayerSupportFragment youTubePlayerFragment;
    private boolean notVideo;
    private Unbinder unbinder;
    private boolean expandable = true;
    @BindView(R.id.playerContainer)
    FrameLayout playerContainer;
    @BindView(R.id.imgEventFragment)
    ImageView imgEventFragment;
    @BindView(R.id.extDescription)
    TextView description;
    @BindView(R.id.expandableImg)
    ImageView expandableImg;
    @BindView(R.id.txtTitle)
    TextView txtTitle;
    @BindView(R.id.descriptionLayout)
    LinearLayout descriptionLayout;
    @BindView(R.id.buyTicket)
    Button buyTicketBtn;
    @BindView(R.id.eventDate)
    TextView eventDate;

    public EventFragment(Event event) {
        mEvent = event;
        this.notVideo = event.getVideoUrl() == null;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        description.setText(mEvent.getDesc());
//        expandableDescription.setVisibility(View.INVISIBLE);
//        descriptionLayout.setVisibility(View.INVISIBLE);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_event, container, false);

        unbinder = ButterKnife.bind(this, rootView);
        eventDate.setText(mEvent.getDate().toString());

        playerContainer.setVisibility(View.VISIBLE);
        youTubePlayerFragment = YouTubePlayerSupportFragment.newInstance();
        txtTitle.setText(mEvent.getName());

        if (notVideo) {
            playerContainer.setVisibility(View.GONE);
            Picasso.with(getContext()).load(mEvent.getImgUrl()).into(imgEventFragment);

        } else {
            getChildFragmentManager().beginTransaction()
                    .add(R.id.playerContainer, youTubePlayerFragment).commit();
            youTubePlayerFragment.initialize(Constants.YoutubeDeveloperKey, new OnInitializedListener() {

                @Override
                public void onInitializationSuccess(Provider arg0, YouTubePlayer youTubePlayer, boolean b) {
                    if (!b) {
                        YPlayer = youTubePlayer;
                        YPlayer.setFullscreen(false);
                        YPlayer.cueVideo(mEvent.getVideoUrl());
                        YPlayer.setShowFullscreenButton(false);
                        YPlayer.setPlayerStateChangeListener(
                                new YouTubePlayerStateChangeListener(getContext()
                                        , mEvent, imgEventFragment, playerContainer));
                        imgEventFragment.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onInitializationFailure(Provider arg0, YouTubeInitializationResult arg1) {

                }
            });
        }

        return rootView;
    }


    @OnClick({R.id.eventTitle, R.id.buyTicket})
    void expandableBtnOnClick(View view) {
        switch (view.getId()) {
//        expandableDescription.expandableTextListener();
            case R.id.eventTitle:
                if (expandable) {
                    description.setVisibility(View.GONE);
                    descriptionLayout.setVisibility(View.GONE);
                    expandableImg.setImageDrawable(getResources().getDrawable(R.drawable.expand_button));

                } else {

                    description.setVisibility(View.VISIBLE);
                    descriptionLayout.setVisibility(View.VISIBLE);
                    expandableImg.setImageDrawable(getResources().getDrawable(R.drawable.up_arrow_key));
                }
                expandable = !expandable;
                break;
            case R.id.buyTicket:
                getFragmentManager().beginTransaction().
                        replace(R.id.container, new ToBuyFragment(mEvent)).addToBackStack(null)
                        .commit();
                break;
        }
    }

    @OnTouch(R.id.buyTicket)
    boolean onTouch(Button button, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                button.setTextColor(ResourcesCompat.getColor(getResources(), R.color.buttonColor, null));
                button.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.button_pressed, null));
                return false;
            case MotionEvent.ACTION_CANCEL:
                ResourcesCompat.getDrawable(getResources(), R.drawable.button, null);
                button.setTextColor(ResourcesCompat.getColor(getResources(), R.color.textColor, null));
                button.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.button, null));
                return false;
            case MotionEvent.ACTION_UP:
                ResourcesCompat.getDrawable(getResources(), R.drawable.button, null);
                button.setTextColor(ResourcesCompat.getColor(getResources(), R.color.textColor, null));
                button.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.button, null));
                return false;
        }
        return false;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}