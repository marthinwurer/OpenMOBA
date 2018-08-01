package Client;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;
import org.newdawn.slick.*;
import org.apache.logging.log4j.Logger;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.tiled.TiledMap;

import static java.lang.Math.*;

public class Client extends BasicGame
{
    private static Logger log = LogManager.getLogger(Client.class);

    private static SpriteSheet spriteSheet;

    public static final int SPRITE_SIZE = 32;
    public static final int TICKS_PER_SECOND = 60;


    SpriteSheetWrapper sheetWrapper;

    AppGameContainer container;

    int base_width = 800;
    int base_height = 600;

    int max_width;
    int max_height;

    int camera_x = 0;
    int camera_y = 0;

    int imagex = 0;
    int imagey = 0;

    Vector2f mousePos = new Vector2f();
    Vector2f mouseWorldPosition = new Vector2f();

    Entity character;

    Camera camera;

    MyTiledMap map;

    public Client()
    {
        super("OpenMOBA");
    }

    @Override
    public void init(GameContainer gc) throws SlickException {
        log.info("in init");
        if (gc instanceof AppGameContainer) {
            AppGameContainer c = (AppGameContainer) gc;
            container = c;
            max_width = c.getScreenWidth();
            max_height = c.getScreenHeight();
            c.setDisplayMode(base_width, base_height, false);
//            c.setDisplayMode(max_width, max_height, true);
        }
        else {
            log.error("Container is not a AppGameContainer");
        }

        spriteSheet = new SpriteSheet("assets/ProjectUtumno_full.png", SPRITE_SIZE, SPRITE_SIZE);
        sheetWrapper = new SpriteSheetWrapper(spriteSheet);
//        map = new TiledMap("assets/3lanemap.tmx", "assets");
        map = new MyTiledMap("assets/3lanemap_out.csv");


        Input input = gc.getInput();
        input.enableKeyRepeat();

        camera = new Camera(0, 0, max_width, max_height);

        character  = new Entity("Archer",
                sheetWrapper.getSprite(SpriteID.ARCHER).getScaledCopy(3),
                200, 200, 200);


    }

    @Override
    public void update(GameContainer gc, int i) throws SlickException {

        character.update_position();

    }

    @Override
    public void render(GameContainer gc, Graphics g) throws SlickException
    {
        g.drawString("(" + gc.getWidth() + ", " + gc.getHeight() + ")", 50, 50);
        g.drawString("(" + camera.x() + ", " + camera.y() + ")", 50, 100);
        g.drawString("Mouse:(" + mousePos.x + ", " + mousePos.y + ")", 50, 120);
        g.drawString("World:(" + mouseWorldPosition.x + ", " + mouseWorldPosition.y + ")", 50, 140);
        camera.setupGraphics(gc, g);
        map.render(g, spriteSheet, 0, 0);
        character.getSprite().drawCentered(character.getX(), character.getY());
        g.drawImage(sheetWrapper.getSprite(SpriteID.TREE), 0, 0);
    }

    @Override
    public void keyPressed(int key, char c) {
        switch (key){
            case Input.KEY_W:
                camera.pan_y(SPRITE_SIZE);
                break;
            case Input.KEY_S:
                camera.pan_y(-SPRITE_SIZE);
                break;
            case Input.KEY_A:
                camera.pan_x(SPRITE_SIZE);
                break;
            case Input.KEY_D:
                camera.pan_x(-SPRITE_SIZE);
                break;
            case Input.KEY_UP:
                imagey = max(0, imagey - 1);
                imagey = min(spriteSheet.getVerticalCount() - 1, imagey);
                break;
            case Input.KEY_DOWN:
                imagey = max(0, imagey + 1);
                imagey = min(spriteSheet.getVerticalCount() - 1, imagey);
                break;
            case Input.KEY_LEFT:
                imagex = max(0, imagex - 1);
                imagex = min(spriteSheet.getHorizontalCount() - 1, imagex);
                break;
            case Input.KEY_RIGHT:
                imagex = max(0, imagex + 1);
                imagex = min(spriteSheet.getHorizontalCount() - 1, imagex);
                break;
            case Input.KEY_F2:
                if (container != null) {
                    try {
                        if (!container.isFullscreen()) {
                            container.setDisplayMode(max_width, max_height, true);
//                            container.setMouseGrabbed(true);
                            container.setDefaultMouseCursor();
                        } else {
                            container.setDisplayMode(base_width, base_height, false);
//                            container.setMouseGrabbed(false);
                        }
                    } catch (SlickException e) {
                        log.error(e);
                    }
                }
                break;
            case Input.KEY_ESCAPE:
                container.exit();
                break;
        }
    }

    @Override
    public void mouseReleased(int button, int x, int y) {
        Vector2f val = camera.toWorldCoordinates(x, y);
        character.setDest(val.x, val.y);
    }

    @Override
    public void mouseWheelMoved(int change) {
        log.info("Mouse Change: " + change);
        if ( change > 0) {
            camera.zoom_in();
        } else {
            camera.zoom_out();
        }
    }

    @Override
    public void mouseMoved(int oldx, int oldy, int newx, int newy) {
        mousePos.x = newx;
        mousePos.y = newy;
        mouseWorldPosition = camera.toWorldCoordinates(newx, newy);
    }

    public static void main(String[] args)
    {
        LoggerContext context = LoggerContext.getContext();
//        context.setConfigLocation();

        System.out.println(log.getLevel());
        try
        {
            AppGameContainer appgc;
            appgc = new AppGameContainer(new Client());
            appgc.setDisplayMode(1920, 1080, false);
            appgc.setTargetFrameRate(120);
            appgc.setVSync(true);
            appgc.start();
        }
        catch (SlickException ex)
        {
            log.error(ex);
        }
    }
}