package lt.kuzya.game2048;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.json.JSONArray;
import org.json.JSONObject;

public class OnlineScoresFrame extends JFrame{
	
	DefaultListModel<String> listModel = new DefaultListModel<>();
    JList<String> scoreList = new JList<>(listModel);

    int page = 0;
	
	public OnlineScoresFrame() {
		Font font = new Font("Verdana", Font.PLAIN, 24);
		setTitle("Online Scores");
		setSize(new Dimension(300, 500));
		setResizable(false);
		
		scoreList.setFont(font);
		
		JScrollPane pane = new JScrollPane(scoreList);
		add(pane, BorderLayout.CENTER);
        add(createBottomPanel(), BorderLayout.SOUTH);

        fetchScores(page);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		setVisible(true);
	}
	
	JPanel createBottomPanel() {
	        JPanel panel = new JPanel();

	        JButton prev = new JButton("<");
	        JButton next = new JButton(">");

	        prev.addActionListener(e -> {
	            if (page > 0) {
	                page--;
	                fetchScores(page);
	            }
	        });

	        next.addActionListener(e -> {
	            page++;
	            fetchScores(page);
	        });

	        panel.add(prev);
	        panel.add(next);

	        return panel;
	    }
	
	void fetchScores(int page) {
	    try {
	        HttpClient client = HttpClient.newHttpClient();

	        HttpRequest request = HttpRequest.newBuilder()
	                .uri(URI.create("http://kuzya.lt/backend/2048/getScores.php?page=" + page))
	                .GET()
	                .build();

	        HttpResponse<String> response = client.send(
	                request,
	                HttpResponse.BodyHandlers.ofString()
	        );

	        JSONArray array = new JSONArray(response.body());

	        listModel.clear();

	        for (int i = 0; i < array.length(); i++) {
	            JSONObject obj = array.getJSONObject(i);

	            String name = obj.getString("name");
	            int score = obj.getInt("score");

	            listModel.addElement(name + " - " + score);
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	        listModel.clear();
	        listModel.addElement("Failed to load scores");
	    }
	}
}
