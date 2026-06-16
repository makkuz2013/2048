package lt.kuzya.game2048;

import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Color;
import java.awt.Graphics;

public class GamePanel extends JPanel{
	private int tileSize;
    
	private float tileSpacing = 10;

	private GameLogic gameLogic;
	
	private Font font;
	
	Input input;
	
    public GamePanel() {
    	setBackground(Color.lightGray);
    	
    	gameLogic = new GameLogic();
    	
    	font = new Font("Arial", Font.PLAIN, 36);
    	
    	input = new Input(this);
    }

    private int[] getTextMetrics(Graphics g, String text) {
    	g.setFont(font);

        FontMetrics fm = g.getFontMetrics();

        int width = fm.stringWidth(text);
        int height = fm.getHeight();
        return new int[] {width, height};
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        tileSize = getSize().width / 4;
        
        float tileSpacingPerTile = tileSpacing / 4;
        
        float yOffset = getHeight() - tileSize*4;
        
        String score = Integer.toString(gameLogic.score);
        g.setColor(Color.black);
        g.setFont(font);
        
        int[] metrics = getTextMetrics(g, score);
        
        g.drawString(score, (int)(getWidth()/2-metrics[0]/2), (int)(yOffset/2+metrics[1]/2));
        
        for (int i = 0; i<4; i++) {
        	for (int j = 0; j<4; j++) {
        		int x = (int) (i*tileSize + tileSpacingPerTile);
        		int y = (int) (yOffset + j*tileSize + tileSpacingPerTile);
        		int size = (int) (tileSize - tileSpacingPerTile);
        		Tile tile = gameLogic.grid[i][j];
        		g.setColor(ColorSheme.getColor(tile));
        		g.fillRect(x, y, size, size);
        		g.setColor(ColorSheme.getTextColor(tile));
        		if (Tile.tileToInt(tile) > 0) {
        			String text = Integer.toString(Tile.tileToInt(tile));
        			int[] metrics2 = getTextMetrics(g, text);
        			g.setFont(font);
        			g.drawString(text, (x + size / 2) - metrics2[0]/2, (y + size / 2) + metrics2[1]/4);
        		}
        	}
        };
        
        if (gameLogic.gameOver) {
	        g.setColor(Color.RED);
	        g.setFont(font);
	        
	        String text = "Game Over!";
	        int[] metrics3 = getTextMetrics(g, text);
	        g.drawString(text, getSize().width/2 - metrics3[0]/2, getSize().height/2 + metrics3[1]/2);
        }
    }
    
    public void onInput(int scanCode) {
    	gameLogic.onInput(scanCode);
    	repaint();
    }
}