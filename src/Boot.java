import helpers.Clock;
import org.lwjgl.opengl.Display;

import static helpers.Artist.BeginSession;

public class Boot {

    public Boot(){
        // Call Static method in Artist class to initialize OpenGL calls
        //TestGit
        BeginSession();

        // Main game loop
        while(!Display.isCloseRequested()){
            Clock.update();

            Display.update();
            Display.sync(60);
        }

        Display.destroy();

    }

    public static void main(String[] args){
        new Boot();
    }
}
