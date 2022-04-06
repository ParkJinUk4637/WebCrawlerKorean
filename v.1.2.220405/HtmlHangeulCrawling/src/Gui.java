import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class Gui extends JFrame {
    private Container c = getContentPane();
    private JLabel statusLabel = new JLabel("Waiting");

    /*------NorthPanel Attribute Area------*/
    private JPanel northPanel = new JPanel();
    private JTextField northTextField = new JTextField(70);
    private JButton northExtractButton = new JButton("Extract");

    /*------CenterPanel Attribute Area------*/
    private JPanel centerPanel = new JPanel();
    private JTextArea centerTextArea = new JTextArea(35,70);
    private JScrollPane centerScrollPane = new JScrollPane(centerTextArea);

    /*------SouthPanel Attribute Area------*/
    private JPanel southPanel = new JPanel();
    private JButton southInitButton = new JButton("Init");
    private JButton southSaveButton = new JButton("Save");
    private JButton southCloseButton = new JButton("Close");

    public Gui() {
        setTitle("AutoCrawling V. 1.2.220405"); //버전명 yyyymmdd...해야함
        c.setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1280, 720);

        setAllPanel();

        c.add(Box.createHorizontalStrut(50),BorderLayout.EAST);
        c.add(Box.createHorizontalStrut(50),BorderLayout.WEST);

        setResizable(false);
        setVisible(true);
    }

    void initAll() {
        northTextField.setText("");
        centerTextArea.setText("");
        statusLabel.setText("Waiting");
    }

    void setAllPanel() {
        setNorthPanel();
        setCenterPanel();
        setSouthPanel();
    }

    void setNorthPanel() {
        northExtractButton.addActionListener(new MyActionListener());
        northPanel.add(new JLabel("URL"));
        northPanel.add(northTextField);
        northPanel.add(northExtractButton);

        c.add(northPanel, BorderLayout.NORTH);
    }

    void setCenterPanel() {
        centerPanel.setLayout(new BoxLayout(centerPanel,BoxLayout.Y_AXIS));
        centerTextArea.setEditable(false);
        centerPanel.add(centerScrollPane);
        centerPanel.add(statusLabel);
        statusLabel.setAlignmentX(CENTER_ALIGNMENT);

        c.add(centerPanel, BorderLayout.CENTER);
    }

    void setSouthPanel() {
        southInitButton.addActionListener(new MyActionListener());
        southCloseButton.addActionListener(new MyActionListener());
        southSaveButton.addActionListener(new MyActionListener());
        southPanel.add(southInitButton);
        southPanel.add(southSaveButton);
        southPanel.add(southCloseButton);

        c.add(southPanel, BorderLayout.SOUTH);
    }

    class MyActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == southCloseButton) {
                System.exit(0);
            } else if (e.getSource() == southSaveButton) {
                if(centerTextArea.getText().isEmpty()==false) {
                    CsvWritter csv = new CsvWritter();
                    csv.saveStringToCsv(northTextField.getText());
                    statusLabel.setText("Csv file saved \'" + csv.getFileName() + "\'");
                }
                else{
                    statusLabel.setText("TextArea is blank");
                }
            } else if (e.getSource() == northExtractButton) {
                try {
                    centerTextArea.setText(new WebCrawler().getHtmlKoreanString(northTextField.getText()));
                    statusLabel.setText("Successfully extracted");
                } catch (IOException ex) {
                    if(northTextField.getText().isEmpty()){
                        statusLabel.setText("URL TextField is blank");
                    }
                    else{
                        statusLabel.setText("URL is invalid.");
                    }
                }
            } else if (e.getSource() == southInitButton) {
                initAll();
                statusLabel.setText("Init all");
            }
        }
    }
}
