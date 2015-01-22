package com.example.valeriaserber.trainingapp.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.valeriaserber.trainingapp.R;
import com.example.valeriaserber.trainingapp.model.SessionObject;
import com.example.valeriaserber.trainingapp.utilities.CircleTransformation;
import com.squareup.picasso.Picasso;

public class ProfileFragment extends Fragment {

    private static final String SESSION_OBJ = "SESSION_OBJ";

    private TextView mName;
    private TextView mLocation;
    private TextView mDescription;
    private ImageView mPicture;
    private ImageView mCover;
    private SessionObject mUser;
    private OnPictureSelectedListener mCallback;

    public static ProfileFragment newInstance(SessionObject user) {
        ProfileFragment f = new ProfileFragment();
        Bundle args = new Bundle();
        args.putSerializable(SESSION_OBJ, user);
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() == null || !getArguments().containsKey(SESSION_OBJ)) return;
        mUser = (SessionObject) getArguments().getSerializable(SESSION_OBJ);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        setUi(rootView);
        setListeners();
        init();
        return rootView;
    }

    private void setListeners() {
        mPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.onPictureSelected(mUser.getPicture());
            }
        });
        mCover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.onPictureSelected(mUser.getCover());
            }
        });
    }

    private void setUi(View rootView) {
        mName = (TextView) rootView.findViewById(R.id.fragment_profile_name_text_view);
        mLocation = (TextView) rootView.findViewById(R.id.fragment_profile_location_text_view);
        mDescription = (TextView) rootView.findViewById(R.id.fragment_profile_description_text_view);
        mPicture = (ImageView) rootView.findViewById(R.id.fragment_profile_picture_image_view);
        mCover = (ImageView) rootView.findViewById(R.id.fragment_profile_cover_image_view);
    }

    private void init() {
        if (mUser.getName() != null) { mName.setText(mUser.getName()); }
        if (mUser.getLocation() != null) { mLocation.setText(mUser.getLocation()); }
        if (mUser.getDescription() != null) { mDescription.setText(mUser.getDescription()); }
        if (mUser.getPicture() != null) {
            Picasso.with(getActivity().getApplicationContext())
                    .load(mUser.getPicture())
                    .transform(new CircleTransformation())
                    .placeholder(R.drawable.profile_image_view_empty)
                    .error(R.drawable.profile_image_view_error)
                    .into(mPicture);
        }
        if (mUser.getCover() != null) {
            Picasso.with(getActivity().getApplicationContext())
                    .load(mUser.getCover())
                    .into(mCover);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mCallback = (OnPictureSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }

    public interface OnPictureSelectedListener {
        public void onPictureSelected(String picture);
    }
}