package lt.kuzya.game2048;

import java.awt.Color;

public abstract class ColorSheme {
	public static Color getColor(Tile tile) {
		switch(tile) {
		case EMPTY:
			return Color.decode("#ffffff");
		case T2:
			return Color.decode("#eee4da");
		case T4:
			return Color.decode("#ede0c8");
		case T8:
			return Color.decode("#f2b179");
		case T16:
			return Color.decode("#f59563");
		case T32:
			return Color.decode("#f67c60");
		case T64:
			return Color.decode("#f65e3b");
		case T128:
			return Color.decode("#edcf73");
		case T256:
			return Color.decode("#edcc62");
		case T512:
			return Color.decode("#edc850");
		case T1024:
			return Color.decode("#edc53f");
		case T2048:
			return Color.decode("#edc22d");
		default:
			return Color.black;
		}
	}
	public static Color getTextColor(Tile tile) {
		int tileInt = Tile.tileToInt(tile);
		if (tileInt > 4) {
			return Color.white;
		} else {
			return Color.black;
		}
	}
}
