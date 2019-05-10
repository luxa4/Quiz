import javax.swing.*;
import java.awt.*;
import java.util.List;

public class QuizCardPlayer {

    JFrame frame;
    JPanel mainPanel;
    JButton button;
    List<QuizCard> cardList;
    JTextArea display;




    public static void main(String[] args) {
        QuizCardPlayer quizCardPlayer = new QuizCardPlayer();
        quizCardPlayer.go();
    }

    public void go() {
        frame = new JFrame("Quiz Player");


        mainPanel = new JPanel();


        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("File");
        JMenuItem Load = new JMenuItem("Open...");

        menu.add(Load);
        menuBar.add(menu);

        display = new JTextArea(6,20);
        Font font = new Font("sanserif", Font.ITALIC,24);
        display.setFont(font);

        button = new JButton("Show Question");

        mainPanel.add(menuBar);
        mainPanel.add(display);
        mainPanel.add(button);


        frame.getContentPane().add(BorderLayout.CENTER,mainPanel);

        frame.setSize(500,600);
        frame.setVisible(true);
    }
}
