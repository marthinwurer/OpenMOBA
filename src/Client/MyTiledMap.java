package Client;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.util.pathfinding.PathFindingContext;
import org.newdawn.slick.util.pathfinding.TileBasedMap;
import java.io.*;
import java.util.Arrays;

public class MyTiledMap implements TileBasedMap {
    private static Logger log = LogManager.getLogger(MyTiledMap.class);

    int[][] data;

    int height;
    int width;

    public MyTiledMap(String filename) {

        File file = new File(filename);
        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new FileReader(file));
            String line = null;

            // read the size
            line = reader.readLine();
            if (line == null){
                throw new IllegalArgumentException("Empty file");
            }

            String[] segments = line.trim().split(",");
            log.debug(Arrays.toString(segments));
            log.debug(segments[0] + "," + segments.length);
            height = Integer.parseInt(segments[0]);
            width = Integer.parseInt(segments[1]);

            data = new int[height][width];

            for (int row = 0; row < height; row++) {
                line = reader.readLine();
                if (line == null){ throw new IllegalArgumentException("file not complete"); }
                segments = line.trim().split(",");
                for (int column = 0; column < width; column++) {
                    data[row][column] = Integer.parseInt(segments[column].trim());
                }
                log.debug(Arrays.toString(data[row]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
            }
        }


    }

    @Override
    public int getWidthInTiles() {
        return width;
    }

    @Override
    public int getHeightInTiles() {
        return height;
    }

    @Override
    public void pathFinderVisited(int i, int i1) {

    }

    @Override
    public boolean blocked(PathFindingContext pathFindingContext, int i, int i1) {
        return false;
    }

    @Override
    public float getCost(PathFindingContext pathFindingContext, int i, int i1) {
        return 0;
    }

    public void render(Graphics g, SpriteSheet sheet, int x, int y) {
        int sheetHeight = getHeightInTiles();
        int sheetWidth = getWidthInTiles();
        float scale = 2.0f;
        log.info(sheetWidth + ", " + sheetHeight);

        for (int row = 0; row < height; row++) {
            for (int column = 0; column < width; column++) {
                int imageID = data[row][column];
                int spritex = imageID % sheetWidth;
                int spritey = imageID / sheetHeight;
                log.info(imageID);
                Image image = sheet.getSprite(spritex, spritey);
                float imagex = x + column * Client.SPRITE_SIZE * scale;
                float imagey = y + row * Client.SPRITE_SIZE * scale;
                image.draw(imagex, imagey, scale);


            }
        }
    }
}
