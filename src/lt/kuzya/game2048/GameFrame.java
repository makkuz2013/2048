package lt.kuzya.game2048;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class GameFrame extends JFrame{
	public GameFrame() {
		GamePanel gp = new GamePanel();
		setTitle("2048");
		setSize(new Dimension(600, 700));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		add(gp);
		addKeyListener(gp.input);
		setFocusable(true);
		setJMenuBar(createMenuBar());
		setVisible(true);
	}
	
	JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        JMenu scoresMenu = new JMenu("Scores");

        JMenuItem onlineItem = new JMenuItem("Online Scores");
        onlineItem.addActionListener(e -> {
            new OnlineScoresFrame();
        });

        scoresMenu.add(onlineItem);

        menuBar.add(scoresMenu);

        return menuBar;
    }
}
