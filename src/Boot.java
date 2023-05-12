import helpers.Clock;
import org.lwjgl.opengl.Display;

import static helpers.Artist.BeginSession;

public class Boot {

    public Boot() throws InterruptedException {
        // Call Static method in Artist class to initialize OpenGL calls
        //TestGit
        BeginSession();
        Runner r = new Runner();

        // Main game loop
        while(!Display.isCloseRequested()){
            Clock.update();
            r.updater();
            Display.update();
            Display.sync(60);
        }

        Display.destroy();

    }

    public static void main(String[] args) throws InterruptedException {
        new Boot();
    }
}
