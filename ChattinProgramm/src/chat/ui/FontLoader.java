package chat.ui;

import java.awt.Font;
import java.io.*;

public class FontLoader {
	
	public static Font loadFont(float size) {
		Font font;
		try {
			System.out.println("폰트경로 : "  + new File("resource/font/PretendardVariable.ttf").getAbsolutePath());
			InputStream is = new FileInputStream("resource/font/PretendardVariable.ttf");
			font = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(size);
		} catch (Exception e) {
			e.printStackTrace();
			return new Font("SansSerif", Font.PLAIN, 14);
		}
		return font;
	}
}
