import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class KalkulatorGUI extends JFrame {

    private JTextField display;
    private double angkaPertama = 0;
    private String operator = "";
    private boolean operatorBaru = true;

    public KalkulatorGUI() {
        setTitle("Kalkulator Putri Ayu");
        setSize(300, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        
        JPanel panelUtama = new JPanel(new BorderLayout(5, 5));
        panelUtama.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelUtama.setBackground(Color.BLACK);

        display = new JTextField("0");
        display.setFont(new Font("Timesnewroman", Font.BOLD, 24));
        display.setBackground(new Color(0, 153, 76));  
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setEditable(false);
        display.setForeground(Color.WHITE);            
        display.setCaretColor(Color.WHITE);
        panelUtama.add(display, BorderLayout.NORTH);

       
        JPanel panelTombol = new JPanel(new GridLayout(4, 4, 5, 5));
        panelTombol.setBackground(Color.BLACK);

        String[] label = {
            "7", "8", "9", "/",
            "4", "5", "6", "*",
            "1", "2", "3", "-",
            "0", "C", "=", "+"
        };

       
        TombolHandler handler = new TombolHandler(this);

        for (String teks : label) {
            
            JButton tombol = new JButton(teks);
            tombol.setFont(new Font("SansSerif", Font.BOLD, 18));
            tombol.addActionListener(handler);
            panelTombol.add(tombol);
        }

        panelUtama.add(panelTombol, BorderLayout.CENTER);
        add(panelUtama);

        
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    prosesInput("C");
                }
            }
        });
        setFocusable(true);
    }


    void prosesInput(String input) {
        if (input.matches("[0-9]")) {
            if (operatorBaru || display.getText().equals("0")) {
                display.setText(input);
                operatorBaru = false;
            } else {
                display.setText(display.getText() + input);
            }
        } else if (input.equals("C")) {
            display.setText("0");
            angkaPertama = 0;
            operator = "";
            operatorBaru = true;
        } else if (input.equals("=")) {
            hitung();
            operator = "";
        } else {
            
            if (!operator.isEmpty()) {
                hitung();
            } else {
                angkaPertama = Double.parseDouble(display.getText());
            }
            operator = input;
            operatorBaru = true;
        }
    }

    private void hitung() {
        double angkaKedua = Double.parseDouble(display.getText());
        double hasil = angkaPertama;

        switch (operator) {
            case "+": hasil = angkaPertama + angkaKedua; break;
            case "-": hasil = angkaPertama - angkaKedua; break;
            case "*": hasil = angkaPertama * angkaKedua; break;
            case "/":
                if (angkaKedua == 0) {
                    display.setText("Error");
                    operatorBaru = true;
                    return;
                }
                hasil = angkaPertama / angkaKedua;
                break;
        }

        if (hasil == Math.floor(hasil)) {
            display.setText(String.valueOf((long) hasil));
        } else {
            display.setText(String.valueOf(hasil));
        }
        angkaPertama = hasil;
        operatorBaru = true;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new KalkulatorGUI().setVisible(true));
    }
}
class TombolHandler implements ActionListener {

    private KalkulatorGUI kalkulator;

    TombolHandler(KalkulatorGUI kalkulator) {
        this.kalkulator = kalkulator;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        kalkulator.prosesInput(e.getActionCommand());
    }
}
