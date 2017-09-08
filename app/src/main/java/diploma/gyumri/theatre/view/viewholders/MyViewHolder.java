package diploma.gyumri.theatre.view.viewholders;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import diploma.gyumri.theatre.R;
import diploma.gyumri.theatre.constants.Constants;
import diploma.gyumri.theatre.model.Event;

/**
 * Created by sofi on 7/27/17.
 */

public class MyViewHolder extends RecyclerView.ViewHolder {
    private ImageView image;
    private TextView eventTitle;
    private TextView eventDate;

    public MyViewHolder(View itemView) {
        super(itemView);
        eventDate = (TextView) itemView.findViewById(R.id.date_event);
        eventTitle = (TextView) itemView.findViewById(R.id.title_event);
        image = (ImageView) itemView.findViewById(R.id.imgEvent);
    }

    public void initData(final Event event, final Context context) {
//        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        final Point size = new Point();
//        Display display = wm.getDefaultDisplay();
//        display.getSize(size);
        ((Activity) context).getWindowManager().getDefaultDisplay().getSize(size);

        int h = (size.x * 62) / 100;
        eventTitle.setText(event.getName());
        eventDate.setText(event.getDate());
        Picasso.with(context).load(Constants.BASE_URL + "images/"+event.getImgUrl()).resize(size.x, h).into(image);
//        Thread thread = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                Log.i("LIST", "run: " + event.getImgUrl());
//                final Bitmap[] bit = {getBitmapFromURL("https://theater-cs50artashes.cs50.io/images/" + event.getImgUrl())};
//                ((Activity) context).runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        int procent = bit[0].getHeight() / (bit[0].getWidth() / 100);
//                        int h = (size.x * procent) / 100;
//                        bit[0] = Bitmap.createScaledBitmap(bit[0], size.x, h, false);
//                        image.setImageBitmap(bit[0]);
////                        Picasso.with(context).load(event.getImgUrl()).resize(size.x, h).into(image);
//                    }
//                });
//
//            }
//        });
//        thread.start();
    }


    public Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            // Log exception
            return null;
        }
    }


}
