package Client.Model;

import java.io.Serializable;

public class Wall extends Tile implements Serializable {
    public final String imagePath="Resources/Images/wall.png";

    public Wall(String name) {
        super(name);
    }
}
