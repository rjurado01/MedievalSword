import com.badlogic.gdx.tools.imagepacker.TexturePacker;
import com.badlogic.gdx.tools.imagepacker.TexturePacker.Settings;

public class Packer
{
	public static void main(String[] args) 
	{
		Settings settings = new Settings();
		settings.padding = 2;
		settings.maxWidth = 1024;
		settings.maxHeight = 1024;
		settings.incremental = true;
		TexturePacker.process(settings, "images", "data");
	}

}