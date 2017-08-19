package diploma.gyumri.theatre.model;


import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;

import lombok.Data;

/**
 * Created by Hayk on 07.08.2017.
 */

@Data
public class Ticket {
    State state = State.AVAILABLE;


    public enum State {
        AVAILABLE, SOLD, RESERVED, SELECTED
    }

    private int row;
    private int seat;
    private int id;
    private int price;
    private int color;
    private float cX, cY, radius;
    private Canvas canvas;
    private Paint p = new Paint();

    public Ticket(int row, int seat, int price) {
        this.row = row;
        this.seat = seat;
        this.price = price;
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public int getSeat() {
        return seat;
    }

    public State getState() {
        return state;
    }

    public int getRow() {
        return row;
    }

    public int getPrice() {
        return price;
    }

    public float getcX() {
        return cX;
    }

    public float getcY() {
        return cY;
    }

    public float getRadius() {
        return radius;
    }


    public void coo(float a) {
        this.cX *= a;
        this.cY *= a;
        this.radius *= a;
    }

    public Ticket(int row, int seat, int id, int price, int color) {
        this.row = row;
        this.seat = seat;
        this.id = id;
        this.price = price;
        this.color = color;
    }

    public void draw(Canvas canvas, float cX, float cY, float radius) {


        this.cX = cX;
        this.cY = cY;
        this.radius = radius;
        this.canvas = canvas;
        switch (state) {
            case AVAILABLE:
                p.setColor(Color.RED);
                break;
            case SOLD:
                p.setColor(Color.BLACK);
                break;
            case RESERVED:
                p.setColor(Color.YELLOW);
                break;
            case SELECTED:
                p.setColor(Color.BLUE);
        }
        canvas.drawCircle(cX, cY, radius, p);
    }

    public static boolean isSelected = false;


}
