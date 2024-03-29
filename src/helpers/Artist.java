package helpers;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import java.io.IOException;
import java.io.InputStream;

import static org.lwjgl.opengl.GL11.*;

public class Artist {
    public static final int WIDTH = 300, HEIGHT = 300;
    public static final int TILE_SIZE = 64;


    public static void BeginSession(){

        Display.setTitle("TTTLWJGL");
        try {
            Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
            Display.create();
        } catch (LWJGLException e) {
            throw new RuntimeException(e);
        }


        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(0, WIDTH, HEIGHT, 0, 1, -1);
        glMatrixMode(GL_MODELVIEW);
        glEnable(GL_TEXTURE_2D);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

    }

    public static boolean CheckCollision(float x1, float y1, float width1, float height1,
                                         float x2, float y2, float width2, float height2){
        if(x1 + width1 > x2 && x1 < x2 + width2 && y1 + height1 > y2 && y1 < y2 + height2)
            return true;
        return false;
    }

    public static void DrawQuad(float x, float y, float width, float height){

        glBegin(GL_QUADS);
        glVertex2f(x, y);   // top l
        glVertex2f(x + width, y);   // top r
        glVertex2f(x + width, y + height);   // bottom r
        glVertex2f(x, y + height);   // bottom l
        glEnd();
    }

    public static void DrawLine(int x1, int y1, int x2, int y2){
        glBegin(GL_LINES);
        glColor3d(255, 255, 255);
        glVertex2i(x1, y1);
        glVertex2i(x2, y2);
        glEnd();
    }

    public static void ClearArea(){
        glClear(1);
    }

    public static void DrawQuadTex(Texture tex, float x, float y, float width, float height){
        tex.bind();
        glTranslatef(x, y, 0);
        glBegin(GL_QUADS);
        glTexCoord2f(0,0);
        glVertex2f(0, 0);
        glTexCoord2f(1,0);
        glVertex2f(width, 0);
        glTexCoord2f(1,1);
        glVertex2f(width, height);
        glTexCoord2f(0,1);
        glVertex2f(0, height);
        glEnd();
        glLoadIdentity();
    }

    public static void DrawQuadTexRot(Texture tex, float x, float y, float width, float height, float angle){
        tex.bind();
        glTranslatef(x + width / 2, y + height / 2, 0);
        // angle - 90 if start texture facing down
        glRotatef(angle - 90, 0, 0, 1);
        glTranslatef(- width / 2, - height / 2, 0);
        glBegin(GL_QUADS);
        glTexCoord2f(0,0);
        glVertex2f(0, 0);
        glTexCoord2f(1,0);
        glVertex2f(width, 0);
        glTexCoord2f(1,1);
        glVertex2f(width, height);
        glTexCoord2f(0,1);
        glVertex2f(0, height);
        glEnd();
        glLoadIdentity();
    }

    public static Texture LoadTexture(String path, String fileType){

        Texture tex = null;

        InputStream in = ResourceLoader.getResourceAsStream(path);
        try {
            tex = TextureLoader.getTexture(fileType, in);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return tex;
    }

    public static Texture QuickLoad(String name){
        Texture tex = null;

        tex = LoadTexture("src/res/" + name + ".png", "PNG");

        return tex;
    }

}
