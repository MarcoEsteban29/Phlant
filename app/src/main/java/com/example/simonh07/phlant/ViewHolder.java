package com.example.simonh07.phlant;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

public class ViewHolder extends RecyclerView.ViewHolder {
    View mView;
    public ViewHolder(View itemView) {
        super(itemView);
        mView = itemView;

    }
    public void setDetails (Context ctx, String title, String description,String image,String URL){
        TextView mTitleTv = mView.findViewById(R.id.rTitleTv);
        TextView mDetailTv = mView.findViewById(R.id.rDescriptionTv);
        ImageView mImageIv = mView.findViewById(R.id.rImageView);
        TextView mURLTv = mView.findViewById(R.id.rURLTv);

        mTitleTv.setText(title);
        mDetailTv.setText(description);
        mURLTv.setText(URL);
        Picasso.get().load(image).into(mImageIv);

    }
}
