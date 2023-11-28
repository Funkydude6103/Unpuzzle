package Client.Model;

import java.io.Serializable;

public class Rotatory extends Tile implements Serializable {
    public final String imagePath="Resources/Images/rotate.png";

    public Rotatory(String name) {
        super(name);
    }
}
