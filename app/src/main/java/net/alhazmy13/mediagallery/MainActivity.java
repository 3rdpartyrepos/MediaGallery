package net.alhazmy13.mediagallery;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;

import net.alhazmy13.mediagallery.library.activity.MediaGallery;
import net.alhazmy13.mediagallery.library.views.MediaGalleryView;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements MediaGalleryView.OnImageClicked {
    ArrayList<String> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        list = getFakeList();

        MediaGalleryView view = findViewById(R.id.gallery);
        view.setImages(list);
        view.setOnImageClickListener(this);
        view.setPlaceHolder(R.drawable.media_gallery_placeholder);
        view.setOrientation(MediaGalleryView.HORIZONTAL);
//        view.setImageSize(500,MediaGalleryView.DEFAULT);
        view.notifyDataSetChanged();

    }

    private ArrayList getFakeList() {
        ArrayList<String> imagesList = new ArrayList<>();
        Bitmap image = Bitmap.createBitmap(500, 500, Bitmap.Config.ARGB_8888);
        image.eraseColor(android.graphics.Color.CYAN);
        imagesList.add(bitMapToString(image));
        imagesList.addAll(Arrays.asList(
                "https://i.ibb.co/74VcyM3/00mainview.jpg",
                "https://i.ibb.co/HG8Fz6r/00mainviewgrid.jpg",
                "https://i.ibb.co/tDDGPZH/001quicksearch.jpg",
                "https://i.ibb.co/nDDWbKL/002xreserver.jpg",
                "https://i.ibb.co/58qddy6/003advancedsort.jpg",
                "https://i.ibb.co/wJDhJzj/004compress.jpg",
                "https://i.ibb.co/566G6H4/005compressbrowse.jpg",
                "https://i.ibb.co/sHYmZjx/006contsel.jpg",
                "https://i.ibb.co/XjFMPtv/007contselgrid.jpg",
                "https://i.ibb.co/6RX3Zvq/008advancedsearch.jpg",
                "https://i.ibb.co/JHLMnKS/009checksum.jpg",
                "https://i.ibb.co/7YrwsnB/010godir.jpg"
        ));
        return imagesList;
    }


    @Override
    public void onImageClicked(int pos) {
        MediaGallery.Builder(this,list)
                .title("Media Gallery")
                .backgroundColor(R.color.white)
                .placeHolder(R.drawable.media_gallery_placeholder)
                .selectedImagePosition(pos)
                .show();
    }

    public String bitMapToString(Bitmap bitmap){
        ByteArrayOutputStream baos=new  ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100, baos);
        byte [] b=baos.toByteArray();
        return Base64.encodeToString(b, Base64.DEFAULT);
    }
}
