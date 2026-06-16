package lt.kuzya.game2048;

public enum Tile {
	EMPTY,
	T2,
	T4,
	T8,
	T16,
	T32,
	T64,
	T128,
	T256,
	T512,
	T1024,
	T2048;
	
	public static int tileToInt(Tile tile) {
		switch(tile) {
		case T2:
			return 2;
		case T4:
			return 4;
		case T8:
			return 8;
		case T16:
			return 16;
		case T32:
			return 32;
		case T64:
			return 64;
		case T128:
			return 128;
		case T256:
			return 256;
		case T512:
			return 512;
		case T1024:
			return 1024;
		case T2048:
			return 2048;
		default:
			return 0;
		}
	}
	public static Tile fromValue(int value) {
		switch(value) {
		case 2:
			return T2;
		case 4:
			return T4;
		case 8:
			return T8;
		case 16:
			return T16;
		case 32:
			return T32;
		case 64:
			return T64;
		case 128:
			return T128;
		case 256:
			return T256;
		case 512:
			return T512;
		case 1024:
			return T1024;
		case 2048:
			return T2048;
		default:
			return EMPTY;
		}
	}
}
