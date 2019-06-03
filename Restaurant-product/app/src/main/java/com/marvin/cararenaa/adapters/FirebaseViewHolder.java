package com.marvin.cararenaa.adapters;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.marvin.cararenaa.R;
import com.marvin.cararenaa.models.Carzarena;
import com.marvin.cararenaa.util.ItemTouchHelperViewHolder;

public class FirebaseViewHolder extends RecyclerView.ViewHolder implements ItemTouchHelperViewHolder {
    View mView;
    Context mContext;
    public ImageView mImageLabe;



    public FirebaseViewHolder(View itemView){
        super(itemView);
        mView = itemView;
        mContext = itemView.getContext();
    }



    public void bindRestaurant(Carzarena mCarzarena) {
        mImageLabe = mView.findViewById(R.id.carrImageView);


        TextView nameTextView = mView.findViewById(R.id.carNameTextView);
        TextView categoryTextView = mView.findViewById(R.id.categoryTextView);
        TextView ratingTextView = mView.findViewById(R.id.priceTextView);
//
//        Picasso.get().load(mCarzarena.getPhoto_links()).into(mImageLabel);
//        Picasso.get().load(mCarzarena.getPhoto_links()).into(mImageLabe);

        nameTextView.setText(mCarzarena.getMake());
//        categoryTextView.setText(mCarzarena.getCategories().get(0));
        ratingTextView.setText("Year made: " + mCarzarena.getPrice());
    }

    @Override
    public void onItemSelected() {
        AnimatorSet set = (AnimatorSet) AnimatorInflater.loadAnimator(mContext,
                R.animator.drag_scale_on);
        set.setTarget(itemView);
        set.start();
    }

    @Override
    public void onItemClear() {
        AnimatorSet set = (AnimatorSet) AnimatorInflater.loadAnimator(mContext,
                R.animator.drag_scale_off);
        set.setTarget(itemView);
        set.start();
    }
}