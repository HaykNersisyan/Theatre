package diploma.gyumri.theatre.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ScaleGestureDetector;
import android.view.View;

import java.util.ArrayList;

import diploma.gyumri.theatre.model.Ticket;

/**
 * Created by Hayk on 15.08.2017.
 */

public class HallView extends View {
    ScaleGestureDetector scaleDetector;
    private float scaleFactor = 1.f;

    //        Paint p;
    Canvas canvas;
    Paint paint;
    float radius;
    float x;
    float y;
    float a;


    public HallView(Context c, AttributeSet attrs) {
        super(c, attrs);
        paint = new Paint();
        scaleDetector = new ScaleGestureDetector(c, new ScaleListener());


    }

    public ArrayList<Ticket>[] tickets = new ArrayList[8];

    private void iii() {
        for (int i = 0; i < tickets.length; i++) {
            tickets[i] = new ArrayList<>(10);
            for (int j = 0; j < 10; j++) {
                tickets[i].add(new Ticket(i + 1, j + 1, 4, 1500, Color.RED));
            }
        }
    }

    public Ticket select(float x, float y) {
        float maxX, minX, maxY, minY;
        Ticket ticket;
        for (int j = tickets.length - 1; j >= 0; j--) {
            for (int i = 0; i < tickets[j].size(); i++) {
                ticket = tickets[j].get(i);
                maxX = ticket.getcX() + ticket.getRadius() * 1.6f;
                minX = ticket.getcX() - ticket.getRadius() * 1.6f;
                maxY = ticket.getcY() + ticket.getRadius() * 1.6f;
                minY = ticket.getcY() - ticket.getRadius() * 1.6f;
                if (x > minX && x < maxX && y > minY && y < maxY) {
                    return ticket;
                }
            }
        }
        return null;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.canvas = canvas;
        canvas.save();
        canvas.scale(scaleFactor, scaleFactor);

        if (tickets[0] == null) {
            Log.i("tag", "iii()");
            iii();
        }
        paint.setColor(Color.YELLOW);
        a = getWidth() / 11;
//        a = 50;
        radius = 0.4f * a;
        for (int j = tickets.length - 1; j >= 0; j--) {
            for (int i = 0; i < tickets[j].size(); i++) {
                y = (7 - j) * a + 0.5f * a;
                x = (i) * a + 0.5f * a;
//                if (i < 17) {
//                    x = (2 + i) * a + 0.5f * a;
//                } else {
////                        tickets[j].get(i).state = Ticket.State.SOLD;
//                    x = (6 + i) * a + 0.5f * a;
//                }
                tickets[j].get(i).draw(canvas, x, y, radius);
            }
        }

        canvas.drawRect(1f * a, 8.3f * a, 9f * a, 9.5f * a, paint);
        paint.setColor(Color.RED);
        paint.setTextSize(1f * a);

        canvas.drawText("ԲԵՄ", 4f * a, 9.3f * a, paint);
        try {
            canvas.restore();
        } catch (IllegalStateException e) {
            Log.i("Tag", "onDraw: " + e);
        }

    }

    public ScaleGestureDetector detector;

    private class ScaleListener extends
            ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            HallView.this.detector = detector;
            scaleFactor *= detector.getScaleFactor();
            scaleFactor = Math.max(0.1f, Math.min(scaleFactor, 15.0f));
            invalidate();
            return true;
        }
    }
}




