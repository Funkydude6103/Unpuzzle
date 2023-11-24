package Client.Model;

import java.io.Serializable;

public class Left extends Tile implements Serializable {
    public final String imagePath="Resources/Images/left.png";

    public Left(String name) {
        super(name);
    }
}
