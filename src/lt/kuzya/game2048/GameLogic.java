package lt.kuzya.game2048;

import java.awt.event.KeyEvent;
import java.util.Random;

public class GameLogic {
	public Tile[][] grid;
	
	private Random rng;
	
	public boolean gameOver = false;
	
	private Tile[][] tempGrid;
	
	public int score;
	
	private void emptyGrid() {
		for (int i = 0; i < 4; i++) {
		    for (int j = 0; j < 4; j++) {
		        grid[i][j] = Tile.EMPTY;
		    }
		}
	}
	
	public GameLogic() {
		rng = new Random();
		
		resetGame();
	}
	
	public void resetGame() {
		score = 0;
		tempGrid = new Tile[4][4];
		grid = new Tile[4][4];
		emptyGrid();
		spawnRandomTile();
	}
	
	private Tile[] compressAndMerge(Tile[] line) {
	    Tile[] compressed = new Tile[4];
	    int index = 0;

	    for (int i = 0; i < 4; i++) {
	        if (line[i] != Tile.EMPTY) {
	            compressed[index++] = line[i];
	        }
	    }

	    while (index < 4) {
	        compressed[index++] = Tile.EMPTY;
	    }

	    for (int i = 0; i < 3; i++) {
	        if (compressed[i] != Tile.EMPTY &&
	            compressed[i] == compressed[i + 1]) {

	            compressed[i] =
	                Tile.fromValue(Tile.tileToInt(compressed[i]) * 2);

	            score += Tile.tileToInt(compressed[i]);
	            
	            compressed[i + 1] = Tile.EMPTY;
	            i++;
	        }
	    }

	    Tile[] result = new Tile[4];
	    index = 0;

	    for (int i = 0; i < 4; i++) {
	        if (compressed[i] != Tile.EMPTY) {
	            result[index++] = compressed[i];
	        }
	    }

	    while (index < 4) {
	        result[index++] = Tile.EMPTY;
	    }

	    return result;
	}
	
	private void shiftLeft() {
	    for (int i = 0; i < 4; i++) {
	        Tile[] row = new Tile[4];

	        for (int j = 0; j < 4; j++) {
	            row[j] = tempGrid[j][i];
	        }

	        row = compressAndMerge(row);

	        for (int j = 0; j < 4; j++) {
	            tempGrid[j][i] = row[j];
	        }
	    }
	}
	
	private void shiftRight() {
	    for (int i = 0; i < 4; i++) {
	        Tile[] row = new Tile[4];

	        for (int j = 0; j < 4; j++) {
	            row[3 - j] = tempGrid[j][i];
	        }

	        row = compressAndMerge(row);

	        for (int j = 0; j < 4; j++) {
	            tempGrid[j][i] = row[3 - j];
	        }
	    }
	}
	
	private void shiftUp() {
	    for (int i = 0; i < 4; i++) {
	        Tile[] col = new Tile[4];

	        for (int j = 0; j < 4; j++) {
	            col[j] = tempGrid[i][j];
	        }

	        col = compressAndMerge(col);

	        for (int j = 0; j < 4; j++) {
	        	tempGrid[i][j] = col[j];
	        }
	    }
	}
	
	private void shiftDown() {
	    for (int i = 0; i < 4; i++) {
	        Tile[] col = new Tile[4];

	        for (int j = 0; j < 4; j++) {
	            col[3 - j] = tempGrid[i][j];
	        }

	        col = compressAndMerge(col);

	        for (int j = 0; j < 4; j++) {
	        	tempGrid[i][j] = col[3 - j];
	        }
	    }
	}
	
	public void shiftGrid(ShiftDirection dir) {
		switch(dir) {
		case UP:
			shiftUp();
			break;
		case DOWN:
			shiftDown();
			break;
		case LEFT:
			shiftLeft();
			break;
		case RIGHT:
			shiftRight();
			break;
		}
	}
	
	private int countSpawnable(int[] spawnable) {
	    int count = 0;

	    for (int i = 0; i<4; i++) {
	        for (int j = 0; j<4; j++) {
	            if (grid[i][j] == Tile.EMPTY) {
	                spawnable[count] = i + j * 4;
	                count++;
	            }
	        }
	    }

	    return count;
	}
	
	private boolean gridsEqual(Tile[][] a, Tile[][] b) {
	    for (int i = 0; i < 4; i++) {
	        for (int j = 0; j < 4; j++) {
	            if (a[i][j] != b[i][j]) return false;
	        }
	    }
	    return true;
	}
	
	public State spawnRandomTile() {
		int randomNum = rng.nextInt(10);
		
		Tile newTile;
		if (randomNum == 0) {
			newTile = Tile.T4;
		} else {
			newTile = Tile.T2;
		}
		
		int[] spawnable = new int[16];
		int spawnableAmount = countSpawnable(spawnable);
		
		if (spawnableAmount == 0) {
			boolean[] matches = new boolean[4];
			for (int i = 0; i<4; i++) {
				ShiftDirection dir;
				switch(i) {
				case 0:
					dir = ShiftDirection.UP;
					break;
				case 1:
					dir = ShiftDirection.DOWN;
					break;
				case 2:
					dir = ShiftDirection.LEFT;
					break;
				case 3:
					dir = ShiftDirection.RIGHT;
					break;
				default:
					dir = ShiftDirection.OTHER;
					break;
				};
				pullGrid();
				
				Tile[][] before = new Tile[4][4];
				
				for (int j = 0; j<4; j++) {
					for (int k = 0; k<4; k++) {
						before[j][k] = tempGrid[j][k];
					}
				}
				
				shiftGrid(dir);
				matches[i] = gridsEqual(before, tempGrid);
			};
			
			int impossibleMoves = 0;
			for (int i = 0; i<4; i++) {
				if (matches[i]) {
					impossibleMoves++;
				}
			}
			
			if (impossibleMoves == 4) {
				return State.GAMEOVER;
			}
		}
		
		if (spawnableAmount > 0) {
			int spawn = rng.nextInt(0, spawnableAmount);
		
			int index = spawnable[spawn];
			
			int row = index / 4;
			int col = index % 4;
			
			grid[col][row] = newTile;
		}
		
		// ShezdesyatSem
		
		return State.OK;
	}
	
	private void pullGrid() {
		for (int i = 0; i<4; i++) {
			for (int j = 0; j<4; j++) {
				tempGrid[i][j] = grid[i][j];
			}
		}
	}
	
	private void commitGrid() {
		for (int i = 0; i<4; i++) {
			for (int j = 0; j<4; j++) {
				grid[i][j] = tempGrid[i][j];
			}
		}
	}
	
	public void onInput(int scanCode) {
		if (scanCode == KeyEvent.VK_R) {
			resetGame();
			gameOver = false;
			return;
		}
		
		if (gameOver) return;
		
		pullGrid();
		shiftGrid(ShiftDirection.scanCodeToShiftDirection(scanCode));
		commitGrid();
		
		if (spawnRandomTile() == State.GAMEOVER) {
			gameOver = true;
			new PostScoreFrame(score);
		}
	};
}
