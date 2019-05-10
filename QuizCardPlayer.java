import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class QuizCardPlayer {

    JFrame frame;
    JPanel mainPanel;
    JButton button;
    List<QuizCard> cardList;
    JTextArea display;
    private QuizCard currentCard;
    private int currentIndex;
    private boolean isShowAnswer;




    public static void main(String[] args) {
        QuizCardPlayer quizCardPlayer = new QuizCardPlayer();
        quizCardPlayer.go();
    }

    public void go() {
        frame = new JFrame("Quiz Player");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mainPanel = new JPanel();


        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("File");
        JMenuItem Load = new JMenuItem("Open...");
        Load.addActionListener(new LoadAction());


        menu.add(Load);
        menuBar.add(menu);

        display = new JTextArea(6,20);
        Font font = new Font("sanserif", Font.ITALIC,24);
        display.setFont(font);

        button = new JButton("Show Question");

        button.addActionListener(new ButtonAction());

        frame.setJMenuBar(menuBar);
        mainPanel.add(display);
        mainPanel.add(button);


        frame.getContentPane().add(BorderLayout.CENTER,mainPanel);

        frame.setSize(500,600);
        frame.setVisible(true);
    }

        class LoadAction implements ActionListener {

            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileOpen = new JFileChooser();
                fileOpen.showOpenDialog(frame);
                loadFile(fileOpen.getSelectedFile());
            }
        }

        class ButtonAction implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isShowAnswer) {
                    button.setText("Next Question");
                    display.setText(currentCard.getAnswer());
                    isShowAnswer=false;
                } else {
                    if(currentIndex<cardList.size()) {
                        showNextCard();
                    } else {
                        display.setText("Вопросов не осталось");
                        button.setEnabled(false);
                    }

                }
            }
        }

        public void loadFile(File file) {
        cardList = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(file)))  {

               String line= null;
               while ((line = br.readLine()) !=null) {
                   makeCard(line);
               }



           } catch (Exception e) {
               System.out.println("не могу прочитать файл...");
               e.printStackTrace();
           }

            showNextCard();
        }

        public void makeCard (String s) {
            String [] j = s.split("/");
            cardList.add(new QuizCard(j[0], j[1]));
            System.out.println("Создана карточка");
        }

        public void showNextCard() {
            currentCard = cardList.get(currentIndex);
            currentIndex++;
            display.setText(currentCard.getQuestion());
            button.setText("Show Answer");
            isShowAnswer = true;
        }
}
