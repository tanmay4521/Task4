import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class BankAccount {
    private double balance;

    public BankAccount(double initialBalance) {
        balance = initialBalance;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        balance += amount;
    }

    public boolean withdraw(double amount) {
        if (balance >= amount) {
            balance -= amount;
            return true;
        }
        return false;
    }
}

class ATMFrame extends JFrame {
    private BankAccount account;

    private JLabel balanceLabel;
    private JTextField amountField;
    private JTextArea resultArea;

    public ATMFrame(BankAccount account) {
        this.account = account;

        setTitle("ATM Interface");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initComponents();
    }

    private void initComponents() {
        balanceLabel = new JLabel("Current Balance: $" + account.getBalance());
        JLabel amountLabel = new JLabel("Enter Amount:");
        amountField = new JTextField(10);

        JButton withdrawButton = new JButton("Withdraw");
        JButton depositButton = new JButton("Deposit");

        resultArea = new JTextArea(5, 20);
        resultArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultArea);

        withdrawButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performWithdrawal();
            }
        });

        depositButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performDeposit();
            }
        });

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(4, 1));
        mainPanel.add(balanceLabel);
        mainPanel.add(amountLabel);
        mainPanel.add(amountField);
        mainPanel.add(withdrawButton);
        mainPanel.add(depositButton);

        Container contentPane = getContentPane();
        contentPane.add(mainPanel, BorderLayout.NORTH);
        contentPane.add(scrollPane, BorderLayout.CENTER);
    }

    private void performWithdrawal() {
        try {
            double amount = Double.parseDouble(amountField.getText());
            if (account.withdraw(amount)) {
                resultArea.setText("Withdrawal successful. New balance: $" + account.getBalance());
                balanceLabel.setText("Current Balance: $" + account.getBalance());
            } else {
                resultArea.setText("Insufficient balance. Withdrawal failed.");
            }
        } catch (NumberFormatException e) {
            resultArea.setText("Invalid input. Please enter a valid amount.");
        }
    }

    private void performDeposit() {
        try {
            double amount = Double.parseDouble(amountField.getText());
            account.deposit(amount);
            resultArea.setText("Deposit successful. New balance: $" + account.getBalance());
            balanceLabel.setText("Current Balance: $" + account.getBalance());
        } catch (NumberFormatException e) {
            resultArea.setText("Invalid input. Please enter a valid amount.");
        }
    }
}

public class Main {
    public static void main(String[] args) {
        BankAccount userAccount = new BankAccount(1000.0); // Initialize with an initial balance
        SwingUtilities.invokeLater(() -> {
            ATMFrame atmFrame = new ATMFrame(userAccount);
            atmFrame.setVisible(true);
        });
    }
}
