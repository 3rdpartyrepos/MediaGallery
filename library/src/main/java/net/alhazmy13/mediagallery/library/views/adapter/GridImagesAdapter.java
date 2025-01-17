package net.alhazmy13.mediagallery.library.views.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import net.alhazmy13.mediagallery.library.R;
import net.alhazmy13.mediagallery.library.Utility;
import net.alhazmy13.mediagallery.library.views.MediaGalleryView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;


public class GridImagesAdapter extends RecyclerView.Adapter<GridImagesAdapter.ViewHolder> {
    private ArrayList<String> mDataset;
    private Context mContext;
    private Drawable imgPlaceHolderResId;
    private MediaGalleryView.OnImageClicked mClickListener;
    private int mHeight;
    private int mWidth;

    public GridImagesAdapter(Context activity, ArrayList<String> imageURLs, Drawable imgPlaceHolderResId) {
        this.mDataset = imageURLs;
        this.mContext = activity;
        this.imgPlaceHolderResId = imgPlaceHolderResId;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image, null));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        boolean isImageValid;

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mClickListener == null) return;
                mClickListener.onImageClicked(holder.getAdapterPosition());
            }
        });

        ViewGroup.LayoutParams params = holder.image.getLayoutParams();

        if (mHeight != -1 && mHeight != MediaGalleryView.DEFAULT)
            params.height = mHeight;

        if (mWidth != -1 && mWidth != MediaGalleryView.DEFAULT)
            params.width = mWidth;

        holder.image.setLayoutParams(params);

        String o = mDataset.get(holder.getAdapterPosition());
        if (Utility.isValidURL(o) || new File(o).exists()) {
            Glide.with(mContext)
                    .load(o)
                    .placeholder(imgPlaceHolderResId)
                    .into(holder.image);
            isImageValid = true;
        } else {
            ByteArrayOutputStream stream = Utility.toByteArrayOutputStream(o);
            if (stream != null) {
                Glide.with(mContext)
                        .load(stream.toByteArray())
                        .asBitmap()
                        .placeholder(imgPlaceHolderResId)
                        .into(holder.image);
                isImageValid = true;

            } else {
                throw new RuntimeException("Image at position: " + position + " it's not valid image");
            }

        }
        if(!isImageValid) {
            throw new RuntimeException("Value at position: " + position + " Should be as url string or bitmap object");
        }

    }


    public void setImgPlaceHolder(Drawable imgPlaceHolderResId) {
        this.imgPlaceHolderResId = imgPlaceHolderResId;
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public void setOnImageClickListener(MediaGalleryView.OnImageClicked onImageClickListener) {
        this.mClickListener = onImageClickListener;
    }

    public void setImageSize(int width, int height) {
        this.mWidth = width;
        this.mHeight = height;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;

        ViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.imageView);
        }
    }


}
