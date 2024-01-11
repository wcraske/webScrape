package leagueStats;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class Main {

    private JTextField usernameField;
    private JTextField hashtagField;
    private JTextArea outputArea;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Main().createAndShowGUI();
        });
    }

    private void createAndShowGUI() {
        JFrame frame = new JFrame("Web Scraper GUI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        JPanel mainPanel = new JPanel(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(3, 2));
        inputPanel.add(new JLabel("Username:"));
        usernameField = new JTextField();
        inputPanel.add(usernameField);

        inputPanel.add(new JLabel("Hashtag:"));
        hashtagField = new JTextField();
        inputPanel.add(hashtagField);

        JButton scrapeButton = new JButton("Scrape");
        inputPanel.add(scrapeButton);

        mainPanel.add(inputPanel, BorderLayout.NORTH);

        outputArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(outputArea);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        scrapeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    scrapeAndDisplayMostPlayedChampion();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        frame.getContentPane().add(mainPanel);
        frame.setVisible(true);
    }

    private void scrapeAndDisplayMostPlayedChampion() throws IOException {
        String baseUrl = "https://www.op.gg/summoners/na/";
        String username = usernameField.getText();
        String hashtag = hashtagField.getText();
        String url = baseUrl + username + "-" + hashtag;
        Document doc = Jsoup.connect(url).get();
        Elements championBoxes = doc.select(".champion-box");

        for (Element championBox : championBoxes) {
            String championName = championBox.text();
            outputArea.append("Champion Name: " + championName + "\n");
        }
    }
}
