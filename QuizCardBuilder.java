import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class QuizCardBuilder {

    JFrame frame;
    JTextArea question;
    JTextArea answer;
    JButton nextCard;
    List<QuizCard> cardList;



    public static void main(String[] args) {
        QuizCardBuilder quizCardBuilder = new QuizCardBuilder();
        quizCardBuilder.go();
    }

    public void go () {

        frame = new JFrame("Quiz Card Builder");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel mainPanel = new JPanel();
        Font bigFont = new Font("sanserif",Font.BOLD,24);

        question = new JTextArea(6,20);
        question.setLineWrap(true); // разрешен перенос слов
        question.setWrapStyleWord(true); //перенос слов на новую строку целиком
        question.setFont(bigFont);

        JScrollPane qScroll = new JScrollPane(question);
        qScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        qScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        answer = new JTextArea(6,20);
        answer.setLineWrap(true);
        answer.setWrapStyleWord(true);
        answer.setFont(bigFont);

        JScrollPane aScroll = new JScrollPane(answer);
        aScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        aScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);


        JLabel qLabel = new JLabel("Question");
        JLabel aLabel = new JLabel("Answer");

        nextCard = new JButton("Next Card");
        nextCard.addActionListener(new NextCardListener());

        mainPanel.add(qLabel);
        mainPanel.add(question);
        mainPanel.add(aScroll);
        mainPanel.add(qScroll);
        mainPanel.add(aLabel);
        mainPanel.add(answer);
        mainPanel.add(nextCard);




        cardList = new ArrayList<>();

        JMenuBar menuBar = new JMenuBar();
        JMenu jmenu = new JMenu("File");
        JMenuItem jMenuItemCr = new JMenuItem("Created");
        jMenuItemCr.addActionListener(new CreatedList());

        JMenuItem jMenuItemSv = new JMenuItem("Save as ...");
        jMenuItemSv.addActionListener(new SaveList());

        jmenu.add(jMenuItemCr);
        jmenu.add(jMenuItemSv);
        menuBar.add(jmenu);

        frame.getContentPane().add(BorderLayout.CENTER,mainPanel);
        frame.setJMenuBar(menuBar);
        frame.setSize(500,600);
        frame.setVisible(true);


    }

    class NextCardListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            QuizCard qc = new QuizCard(question.getText(), answer.getText());
            cardList.add(qc);
            clearCard();
        }
    }

    class CreatedList implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            cardList.clear();
            clearCard();
        }
    }

    class SaveList implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            QuizCard qc = new QuizCard(question.getText(), answer.getText());
            cardList.add(qc);

            JFileChooser fileSave = new JFileChooser();
            fileSave.showSaveDialog(frame);
            saveFile(fileSave.getSelectedFile());

        }
    }

    public void clearCard () {
        answer.setText(" ");
        question.setText(" ");
        question.requestFocus();
    }

    public void saveFile (File file) {

        try (BufferedWriter fileWriter = new BufferedWriter(new FileWriter(file));) {


            for(QuizCard q : cardList){
                fileWriter.write(q.getQuestion()+"/");
                fileWriter.write(q.getAnswer()+"\r\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
