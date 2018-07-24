package Client;

import org.newdawn.slick.Image;

import static java.lang.Math.*;

public class Entity {
    private Image sprite;
    private float x;
    private float y;
    private float dest_x;
    private float dest_y;
    private float speed;

    private void update_position( ){
        // move towards the destination
        float speed = 150.0f;

        float dx = x - dest_x;
        float dy = y - dest_y;
        float hyp = (float) sqrt(dx * dx + dy * dy);

        if (abs(hyp) > 1) {
//            log.info("differences: x: " + dx + ", dy: " + dy + " hyp: " + hyp);

            float xratio = dx/hyp;
            float yratio = dy/hyp;

            float angle = (float) Math.atan2(dy, dx);

//            view_x -= speed * cos(angle) / TICKS_PER_SECOND;
//            view_y -= speed * sin(angle) / TICKS_PER_SECOND;
//            x -= speed * xratio / TICKS_PER_SECOND;
//            y -= speed * yratio / TICKS_PER_SECOND;
        }

    }

}
