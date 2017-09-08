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
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import diploma.gyumri.theatre.R;
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

    public void initData(Event event, Context context) {
//        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Point size = new Point();
//        Display display = wm.getDefaultDisplay();
//        display.getSize(size);
        ((Activity)context).getWindowManager().getDefaultDisplay().getSize(size);

        int h = (size.x * 62) / 100;
        eventTitle.setText(event.getName());
        eventDate.setText(event.getDate());
        Picasso.with(context).load(event.getImgUrl()).resize(image.getWidth(), h).into(image);
    }


}
