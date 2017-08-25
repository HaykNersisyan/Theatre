package diploma.gyumri.theatre.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v4.content.res.ResourcesCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ScaleGestureDetector;
import android.view.View;

import java.util.List;

import diploma.gyumri.theatre.R;
import diploma.gyumri.theatre.model.Ticket;

/**
 * Created by Hayk on 15.08.2017.
 */


public class HallView extends View {
    private ScaleGestureDetector scaleDetector;
    private float scaleFactor = 1f;
    private boolean zoomed = false;
    private float previousX;
    private float previousY;

    //        Paint p;
    private Paint paint;
    private float radius;
    private float x, X, Y;
    private float y;
    private float a;
    private List<Ticket>[] tickets;

    @Override
    public float getX() {
        return X;
    }

    @Override
    public float getY() {
        return Y;
    }

    @Override
    public void setX(float x) {
        X += x;
        if (X >= 0){
            X = 0;
        }
    }

    @Override
    public void setY(float y) {
//        previousY = Y;
        Y += y;
    }

    public List<Ticket>[] getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket>[] tickets) {
        this.tickets = tickets;
    }

    private float temp = 1f;

    public void zoomIn() {
        if (scaleFactor == 3f) {
            return;
        }
        if (scaleFactor == 2) {
            temp = 2.0f;
        }
        zoomed = true;
        scaleFactor += 1.0f;
        invalidate();
    }

    public void zoomOut() {
        if (scaleFactor == 3f) {
            scaleFactor -= 1.0f;
            temp = 1.0f;
            invalidate();
            return;
        }
        if (scaleFactor == 2) {
            scaleFactor -= 1.0f;
            zoomed = false;
            invalidate();
            return;
        }
        if (scaleFactor == 1) {
            zoomed = false;
            return;

        }
    }




    public HallView(Context c, AttributeSet attrs) {
        super(c, attrs);
        paint = new Paint();
    }

    public Ticket select(float x, float y) {
        float maxX, minX, maxY, minY;
        Ticket ticket;
        for (int j = tickets.length - 1; j >= 0; j--) {
            for (int i = 0; i < tickets[j].size(); i++) {
                ticket = tickets[j].get(i);

                if (zoomed) {
                    maxX = (ticket.getcX() + ticket.getcX() * temp) + (ticket.getRadius() + ticket.getRadius() * temp) * 1.6f;
                    minX = (ticket.getcX() + ticket.getcX() * temp) - (ticket.getRadius() + ticket.getRadius() * temp) * 1.6f;
                    maxY = (ticket.getcY() + ticket.getcY() * temp) + (ticket.getRadius() + ticket.getRadius() * temp) * 1.6f;
                    minY = (ticket.getcY() + ticket.getcY() * temp) - (ticket.getRadius() + ticket.getRadius() * temp) * 1.6f;
                } else {
                    maxX = ticket.getcX() + ticket.getRadius() * 1.6f;
                    minX = ticket.getcX() - ticket.getRadius() * 1.6f;
                    maxY = ticket.getcY() + ticket.getRadius() * 1.6f;
                    minY = ticket.getcY() - ticket.getRadius() * 1.6f;
                }
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
        canvas.save();
        canvas.scale(scaleFactor, scaleFactor);
        if (tickets == null) {
            return;
        }
        Log.i("DRAW", "onDraw: " + tickets.length);
        a = getWidth() / 11;
//        a = 50;
        radius = 0.4f * a;
        for (int j = tickets.length - 1; j >= 0; j--) {
            for (int i = 0; i < tickets[j].size(); i++) {
                y = ((7 - j) * a + 0.5f * a) + a / 2 + Y;
                x = ((i) * a + 0.5f * a) + a / 2 + X;
//                if (i < 17) {
//                    x = (2 + i) * a + 0.5f * a;
//                } else {
////                        tickets[j].get(i).state = Ticket.State.SOLD;
//                    x = (6 + i) * a + 0.5f * a;
//                }
                tickets[j].get(i).draw(canvas, x, y, radius);
            }
        }
        paint.setColor(ResourcesCompat.getColor(getResources(), R.color.colorPrimary, null));
        canvas.drawRect(1f * a + (a / 2), 8.3f * a + (a / 2), 9f * a + (a / 2), 9.5f * a + a, paint);
        paint.setColor(ResourcesCompat.getColor(getResources(), R.color.textColor, null));
        paint.setTextSize(1f * a);
        canvas.drawText("ԲԵՄ", 4f * a + (a / 2), 9.5f * a + (a / 2), paint);
        try {
            canvas.restore();
        } catch (IllegalStateException e) {
            Log.i("Tag", "onDraw: " + e);
        }
    }
}
