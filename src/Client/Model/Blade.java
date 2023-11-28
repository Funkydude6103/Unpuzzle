package Client.Model;

import java.io.Serializable;

public class Blade extends Tile implements Serializable {
    public final String imagePath="Resources/Images/blade.png";

    public Blade(String name) {
        super(name);
    }
}
