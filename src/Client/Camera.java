package Client;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;

import static java.lang.Math.max;
import static java.lang.Math.min;
import static java.lang.Math.pow;

public class Camera {
    private static Logger log = LogManager.getLogger(Camera.class);

    public static final float MAX_ZOOM = 5.0f;
    public static final float MIN_ZOOM = -3.0f;

    private float center_x;
    private float center_y;

    private int window_width;
    private int window_height;

    private float zoom;
    private float zoomRatio;

    public Camera(float center_x, float center_y, int window_width, int window_height) {
        this.center_x = center_x;
        this.center_y = center_y;
        this.window_width = window_width;
        this.window_height = window_height;
        this.zoom = 1;
    }

    /**
     * Get the translation value to render this camera
     * @return
     */
    public float x() {
        return (center_x - (window_width / 2) / zoom) * zoom;
    }

    /**
     * get the translation value to render this camera
     * @return
     */
    public float y() {
        return (center_y - (window_height / 2) / zoom) * zoom;
    }

    public void setupGraphics(GameContainer gc, Graphics g) {
        this.window_height = gc.getHeight();
        this.window_width = gc.getWidth();
        g.drawString("Center: (" + center_x + ", " + center_y + ")", 300, 50);
        g.drawString("Window: (" + window_width + ", " + window_height + ")", 300, 70);
        g.translate(-x(), -y());
        g.scale(zoom, zoom);
    }

    public void setCenter(float x, float y) {
        this.center_x = x;
        this.center_y = y;
    }

    public void pan_x(float val) {
        this.center_x += val;
    }

    public void pan_y(float val) {
        this.center_y += val;
    }

    public Vector2f toWorldCoordinates(float x, float y) {
        return new Vector2f((x + x())/zoom, (y + y())/zoom);
    }

    public void zoom_in() {
        zoomRatio = min(MAX_ZOOM, zoomRatio + 1);
        zoom = (float) pow(1.5, zoomRatio);
        log.debug("Zoom: " + zoom);
    }

    public void zoom_out() {
        zoomRatio = max(MIN_ZOOM, zoomRatio - 1);
        zoom = (float) pow(1.5, zoomRatio);
    }
}
