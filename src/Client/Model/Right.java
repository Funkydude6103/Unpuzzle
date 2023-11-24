package Client.Model;

import java.io.Serializable;

public class Right extends Tile implements Serializable {
    public final String imagePath="Resources/Images/right.png";

    public Right(String name) {
        super(name);
    }
}
