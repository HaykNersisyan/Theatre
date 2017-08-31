package diploma.gyumri.theatre.view.viewholders;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import diploma.gyumri.theatre.R;
import diploma.gyumri.theatre.model.Event;

/**
 * Created by sofi on 7/27/17.
 */

public class MyViewHolder extends RecyclerView.ViewHolder {
    private ImageView image;

    public MyViewHolder(View itemView) {
        super(itemView);
        image = (ImageView) itemView.findViewById(R.id.imgEvent);
    }

    public void initData(Event event, Context context) {
//        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Point size = new Point();
//        Display display = wm.getDefaultDisplay();
//        display.getSize(size);
        ((Activity)context).getWindowManager().getDefaultDisplay().getSize(size);

        int h = (size.x * 62) / 100;

        Log.i("TAG", "initData: " + h);
        Picasso.with(context).load(event.getImgUrl()).resize(image.getWidth(), h).into(image);
    }


}
