package lt.kuzya.game2048;

import java.awt.event.KeyEvent;

public enum ShiftDirection {
	UP,
	DOWN,
	LEFT,
	RIGHT,
	OTHER;
	
	public static ShiftDirection scanCodeToShiftDirection(int scanCode) {
		switch(scanCode) {
		case KeyEvent.VK_UP:
			return UP;
		case KeyEvent.VK_DOWN:
			return DOWN;
		case KeyEvent.VK_LEFT:
			return LEFT;
		case KeyEvent.VK_RIGHT:
			return RIGHT;
		default:
			return OTHER;
		}
	}
}
