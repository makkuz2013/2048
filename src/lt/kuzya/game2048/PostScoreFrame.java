package lt.kuzya.game2048;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class PostScoreFrame extends JFrame{
	int score;
	
	public PostScoreFrame(int score) {
		this.score = score;
		Font font = new Font("Verdana", Font.PLAIN, 24);
		setTitle("Post score");
		setSize(new Dimension(300, 300));
		setLayout(new GridLayout(4, 1));
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		JLabel scoreLabel = new JLabel("Your score is " + Integer.toString(score));
		scoreLabel.setFont(font);
		add(scoreLabel);
		JLabel nameLabel = new JLabel("Name");
		nameLabel.setFont(font);
		add(nameLabel);
		JTextField nameField = new JTextField();
		nameField.setFont(font);
		add(nameField);
		JButton postButton = new JButton("Post");
		postButton.setFont(font);
		
		postButton.addActionListener(e -> {
			uploadScoreToServer(nameField.getText());
			dispose();
		});
		
		add(postButton);
		
		setVisible(true);
	}
	
	void uploadScoreToServer(String name) {
		HttpClient client = HttpClient.newHttpClient();

        String form = "name=" + URLEncoder.encode(name, StandardCharsets.UTF_8)
                    + "&score=" + score;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://kuzya.lt/backend/2048/postScore.php"))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString(form))
                .build();

        client.sendAsync(request, HttpResponse.BodyHandlers.discarding());
	}
}
