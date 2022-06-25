import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.*;

@SuppressWarnings("serial")
public class StockSystem extends JPanel {

	private JLabel prompt;						// functions correctly
	private JComboBox<String> choices;			// functions correctly
	private JButton newFileButton;				// functions correctly
    private JButton updateButton;				// functions correctly
    private JButton quitButton;					// functions correctly
    private JButton updateCashButton;			// functions correctly
	private JButton updatePositionButton;		// functions correctly
	private JButton rebalanceButton;			// functions correctly
	private JButton rebalanceMFButton;			// functions correctly
    private JButton withdrawalButton;			// functions correctly
    private JButton addPositionButton;			// functions correctly
    private JButton removePositionButton;		// functions correctly
    private JButton removePortfolioButton;		// functions correctly
    private JTextArea snapshotLabel;			// functions correctly
    
    private ArrayList<Portfolio> portfolios;
    
    public StockSystem() throws IOException {
    	
        //configure drop down menu
    	portfolios = getPortfolios();
    	String[] fileChoices = new String[portfolios.size()];
    	for (int i = 0; i < fileChoices.length; i++) {
    		fileChoices[i] = portfolios.get(i).getFilename();
    	}

        //construct components
    	prompt = new JLabel ("Current file:");
    	choices = new JComboBox<String> (fileChoices);
    	newFileButton = new JButton("New File");
        updateButton = new JButton("Update");
        quitButton = new JButton("Quit");
        updateCashButton = new JButton("Update Cash");
        updatePositionButton = new JButton("Update Positions");
        rebalanceButton = new JButton("Rebalance");
        rebalanceMFButton = new JButton("Rebalance MF");
        withdrawalButton = new JButton("Withdrawal");
        addPositionButton = new JButton("Add Position");
        removePositionButton = new JButton("Remove Position");
        removePortfolioButton = new JButton("Remove File");
        snapshotLabel = new JTextArea(16, 80);
        
        //adjust size and set layout
        setPreferredSize (new Dimension (944, 570));
        setLayout (null);

        //add components
        add(prompt);
        add(choices);
        add(newFileButton);
        add(updateButton);
        add(quitButton);
        add(updateCashButton);
        add(updatePositionButton);
        add(rebalanceButton);
        add(rebalanceMFButton);
        add(withdrawalButton);
        add(addPositionButton);
        add(removePositionButton);
        add(removePortfolioButton);
        add(snapshotLabel);

        //set component bounds (only needed by Absolute Positioning)
        prompt.setBounds(30, 30, 100, 25);
        choices.setBounds(135, 30, 295, 25);
        newFileButton.setBounds(560, 30, 100, 25);
        updateButton.setBounds(445, 30, 100, 25);
        quitButton.setBounds(675, 30, 100, 25);
        updateCashButton.setBounds(25, 90, 175, 25);
        updatePositionButton.setBounds(25, 130, 175, 25);
        rebalanceButton.setBounds(25, 170, 175, 25);
        rebalanceMFButton.setBounds(25, 210, 175, 25);
        withdrawalButton.setBounds(25, 250, 175, 25);
        addPositionButton.setBounds(25, 290, 175, 25);
        removePositionButton.setBounds(25, 330, 175, 25);
        removePortfolioButton.setBounds(25, 370, 175, 25);
        snapshotLabel.setBounds(240, 90, 560, 390);
        
        // button behaviors
        
        choices.addActionListener(e -> {
			try {
				updateLabel();
			} catch (IOException e2) {
				e2.printStackTrace();
			}
		});

        updateButton.addActionListener(e -> {
			try {
				updateLabel();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		});

		newFileButton.addActionListener(e -> {
			try {
				newFile();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		});
		
		quitButton.addActionListener(e -> System.exit(0));
        
        updateCashButton.addActionListener(e -> {
			try {
				updateCash();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		});
        
        updatePositionButton.addActionListener(e -> {
			try {
				updatePosition();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		});
       
        rebalanceButton.addActionListener(e -> {
			try {
				rebalance();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		});
        
        rebalanceMFButton.addActionListener(e -> {
			try {
				rebalanceMF();
			} catch (IOException e2) {
				e2.printStackTrace();
			}
		});
        
        withdrawalButton.addActionListener(e -> {
			try {
				withdrawal();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		});
        
        addPositionButton.addActionListener(e -> {
			try {
				addPosition();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		});
        
        removePositionButton.addActionListener(e -> {
			try {
				removePosition();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		});
                
        removePortfolioButton.addActionListener(e -> {
			try {
				removePortfolio();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		});   
    }

	private void newFile() throws IOException {
		JTextField balance = new JTextField();
		JTextField num = new JTextField();
		JTextField name = new JTextField();
		Object[] message = {"Cash balance: ", balance, "Number of positions", num, "Keyword (used as filename): ", name};
		int result = JOptionPane.showConfirmDialog(null,  message, "Initial File Setup", JOptionPane.OK_CANCEL_OPTION);
		if (result != JOptionPane.OK_CANCEL_OPTION) {
			double cashBalance = Double.parseDouble(balance.getText());
			int numPositions = Integer.parseInt(num.getText());
			String filename = name.getText();
			ArrayList<Position> positions = new ArrayList<Position>();
			JTextField sym = new JTextField();
			JTextField prop = new JTextField();
			JTextField share = new JTextField();
			Object[] positionMessage = {"Symbol: ", sym, "Percent of portfolio: ", prop, "Current shares: ", share};
			for (int i = 0; i < numPositions; i++) {
				result = JOptionPane.showConfirmDialog(null, positionMessage, "Enter a Position", JOptionPane.OK_CANCEL_OPTION);
				String symbol = sym.getText();
				double proportion = Double.parseDouble(prop.getText());
				double shares = Double.parseDouble(share.getText());
				positions.add(new Position(symbol, proportion, shares));
				sym.setText("");
				prop.setText("");
				share.setText("");
			}
			Portfolio portfolio = new Portfolio(cashBalance, positions, filename);
			portfolio.writeFile();
	    	portfolios = getPortfolios();
	    	String[] newFileChoices = new String[portfolios.size()];
	    	for (int i = 0; i < newFileChoices.length; i++) {
	    		newFileChoices[i] = portfolios.get(i).getFilename();
	    	}
	    	DefaultComboBoxModel<String> model = new DefaultComboBoxModel<String>(newFileChoices);
	    	choices.setModel(model);
	    	choices.revalidate();
		}
	}
    
	private void updateLabel() throws IOException {
    	String choice = (String) choices.getSelectedItem();
    	Portfolio target = portfolioWithFilename(choice);
		getPortfolioLabel(snapshotLabel, target);
	}
	
    private void updateCash() throws IOException {
    	String choice = (String) choices.getSelectedItem();
    	Portfolio target = portfolioWithFilename(choice);
    	JTextField cash = new JTextField();
    	Object[] message = {"Enter new cash balance: ", cash};
    	int result = JOptionPane.showConfirmDialog(null,  message, "Updating Cash Balance", JOptionPane.OK_CANCEL_OPTION);
    	if (result != JOptionPane.OK_CANCEL_OPTION) {
    		double newCash = Double.parseDouble(cash.getText());
    		target.setCashBalance(newCash);
    		target.writeFile();
    		getPortfolioLabel(snapshotLabel, target);
    	}
    }
    
    private void updatePosition() throws IOException {
    	String choice = (String) choices.getSelectedItem();
    	Portfolio target = portfolioWithFilename(choice);
        
    	//configure drop down menu
    	ArrayList<Position> positions = target.getPositions();
    	String[] positionChoices = new String[positions.size()];
    	for (int i = 0; i < positionChoices.length; i++) {
    		positionChoices[i] = positions.get(i).getSymbol();
    	}
        String symbolToBeUpdated = (String) JOptionPane.showInputDialog(null, "Choose a position to update: ", "Update Position", JOptionPane.QUESTION_MESSAGE, null, positionChoices, positionChoices[0]);
        if (symbolToBeUpdated != null) {
        	JTextField shares = new JTextField();
        	Object[] message = {"Enter new shares: ", shares};
        	int result = JOptionPane.showConfirmDialog(null,  message, "Updating Shares", JOptionPane.OK_CANCEL_OPTION);
        	if (result != JOptionPane.OK_CANCEL_OPTION) {
        		double newShares = Double.parseDouble(shares.getText());
        		for (Position position : target.getPositions()) {
        			if (position.getSymbol().contentEquals(symbolToBeUpdated)) {
        				position.setShares(newShares);
        				target.writeFile();
                		getPortfolioLabel(snapshotLabel, target);
        			}
        		}
        	}
        }
    }
    
	private void rebalance() throws IOException {
    	String choice = (String) choices.getSelectedItem();
    	Portfolio target = portfolioWithFilename(choice);
    	ArrayList<Position> positions = target.getPositions();
    	String[] symbols = new String[positions.size()];
    	double[] prices = new double[positions.size()];
    	double[] proportions = new double[positions.size()];
    	double[] shares = new double[positions.size()];
    	double[] newShares = new double[positions.size()];
    	double totalAccountValue = target.getCashBalance();
    	for (int i = 0; i < positions.size(); i++) {
    		symbols[i] = positions.get(i).getSymbol();
    		prices[i] = positions.get(i).getPrice();
    		proportions[i] = positions.get(i).getProportion();
    		shares[i] = positions.get(i).getShares();
    		totalAccountValue += prices[i] * shares[i];
    	}
    	double newCashBalance = totalAccountValue;
    	for (int i = 0; i < positions.size(); i++) {
    		newShares[i] = (int)(proportions[i]/100*totalAccountValue/prices[i]);
    		newCashBalance -= newShares[i]*prices[i];
    	}
    	Object[] message = new Object[positions.size()+2];
    	for (int i = 0; i < positions.size(); i++) {
    		if (newShares[i] < shares[i]) {
    			message[i] = "You should sell " + (shares[i] - newShares[i]) + " shares of " + symbols[i];
    		} else {
    			message[i] = "You should buy " + (newShares[i] - shares[i]) + " shares of " + symbols[i];
    		}
    	}
    	message[positions.size()] = "Your new cash balance will be $" + newCashBalance;
    	message[positions.size()+1] = "Click OK to accept these transactions and make the trades, otherwise cancel";
    	int result = JOptionPane.showConfirmDialog(null, message, "Proposed transactions: ", JOptionPane.OK_CANCEL_OPTION);
    	if (result != JOptionPane.OK_CANCEL_OPTION) {
    		for (int i = 0; i < positions.size(); i++) {
    			positions.get(i).setShares(newShares[i]);
    		}
    		target.setCashBalance(newCashBalance);
    		target.writeFile();
    		getPortfolioLabel(snapshotLabel, target);
    	}
    }
    
	private void rebalanceMF() throws IOException {
		String choice = (String) choices.getSelectedItem();
    	Portfolio target = portfolioWithFilename(choice);
    	ArrayList<Position> positions = target.getPositions();
    	String[] symbols = new String[positions.size()];
    	double[] prices = new double[positions.size()];
    	double[] proportions = new double[positions.size()];
    	double[] shares = new double[positions.size()];
    	double[] newShares = new double[positions.size()];
    	double totalAccountValue = target.getCashBalance();
    	for (int i = 0; i < positions.size(); i++) {
    		symbols[i] = positions.get(i).getSymbol();
    		prices[i] = positions.get(i).getPrice();
    		proportions[i] = positions.get(i).getProportion();
    		shares[i] = positions.get(i).getShares();
    		totalAccountValue += prices[i] * shares[i];
    	}
    	double newCashBalance = totalAccountValue;
    	for (int i = 0; i < positions.size(); i++) {
    		newShares[i] = proportions[i]/100*totalAccountValue/prices[i];
    		newCashBalance -= newShares[i]*prices[i];
    	}
    	Object[] message = new Object[positions.size()+2];
    	for (int i = 0; i < positions.size(); i++) {
    		if (newShares[i] < shares[i]) {
    		message[i] = "You should sell " + (shares[i] - newShares[i]) + " shares of " + symbols[i] + " = $" + ((shares[i]-newShares[i])*prices[i]);
    		} else {
    			message[i] = "You should buy " + (newShares[i] - shares[i]) + " shares of " + symbols[i] + " = $" + ((newShares[i]-shares[i])*prices[i]);
    		}
    	}
    	message[positions.size()] = "Your new cash balance will be $" + newCashBalance;
    	message[positions.size()+1] = "Please note that for mutual fund transactions, you must update positions manually.";
    	JOptionPane.showConfirmDialog(null, message, "Proposed transactions: ", JOptionPane.OK_CANCEL_OPTION);
	}
	
	private void withdrawal() throws IOException {
		JTextField cash = new JTextField();
    	Object[] withdrawalMessage = {"Enter cash desired for withdrawal: ", cash};
    	int result = JOptionPane.showConfirmDialog(null,  withdrawalMessage, "Withdrawal", JOptionPane.OK_CANCEL_OPTION);
    	if (result != JOptionPane.OK_CANCEL_OPTION) {
    		double cashDesired = Double.parseDouble(cash.getText());
    		String choice = (String) choices.getSelectedItem();
	    	Portfolio target = portfolioWithFilename(choice);
	    	ArrayList<Position> positions = target.getPositions();
	    	String[] symbols = new String[positions.size()];
	    	double[] prices = new double[positions.size()];
	    	double[] proportions = new double[positions.size()];
	    	double[] shares = new double[positions.size()];
	    	double[] newShares = new double[positions.size()];
	    	double totalAccountValue = target.getCashBalance();
	    	for (int i = 0; i < positions.size(); i++) {
	    		symbols[i] = positions.get(i).getSymbol();
	    		prices[i] = positions.get(i).getPrice();
	    		proportions[i] = positions.get(i).getProportion();
	    		shares[i] = positions.get(i).getShares();
	    		totalAccountValue += prices[i] * shares[i];
	    	}
	    	double newCashBalance = totalAccountValue;
	    	for (int i = 0; i < positions.size(); i++) {
	    		newShares[i] = (int)(proportions[i]/100*(totalAccountValue-cashDesired)/prices[i]);
	    		newCashBalance -= newShares[i]*prices[i];
	    	}
	    	Object[] message = new Object[positions.size()+2];
	    	for (int i = 0; i < positions.size(); i++) {
	    		if (newShares[i] < shares[i]) {
	    			message[i] = "You should sell " + (shares[i] - newShares[i]) + " shares of " + symbols[i];
	    		} else {
	    			message[i] = "You should buy " + (newShares[i] - shares[i]) + " shares of " + symbols[i];
	    		}
	    	}
	    	message[positions.size()] = "Your new cash balance will be $" + newCashBalance;
	    	message[positions.size()+1] = "Click OK to accept these transactions and make the trades, otherwise cancel";
	    	result = JOptionPane.showConfirmDialog(null, message, "Proposed transactions: ", JOptionPane.OK_CANCEL_OPTION);
	    	if (result != JOptionPane.OK_CANCEL_OPTION) {
	    		for (int i = 0; i < positions.size(); i++) {
	    			positions.get(i).setShares(newShares[i]);
	    		}
	    		target.setCashBalance(newCashBalance);
	    		target.writeFile();
	    		getPortfolioLabel(snapshotLabel, target);
	    	}
    	}
	}
	
    private void addPosition() throws IOException {
    	String choice = (String) choices.getSelectedItem();
    	Portfolio target = portfolioWithFilename(choice);
    	JTextField sym = new JTextField();
		JTextField prop = new JTextField();
		JTextField share = new JTextField();
		Object[] positionMessage = {"Symbol: ", sym, "Percent of portfolio: ", prop, "Current shares: ", share};
		int result = JOptionPane.showConfirmDialog(null, positionMessage, "Enter a Position", JOptionPane.OK_CANCEL_OPTION);
    	if (result != JOptionPane.OK_CANCEL_OPTION) {
    		String symbol = sym.getText();
    		double proportion = Double.parseDouble(prop.getText());
    		double shares = Double.parseDouble(share.getText());
    		target.addPosition(new Position(symbol, proportion, shares));
    		target.writeFile();
    		getPortfolioLabel(snapshotLabel, target);
    	} 
    }
    
    private void removePosition() throws IOException {
    	String choice = (String) choices.getSelectedItem();
    	Portfolio target = portfolioWithFilename(choice);
        
    	//configure drop down menu
    	ArrayList<Position> positions = target.getPositions();
    	String[] positionChoices = new String[positions.size()];
    	for (int i = 0; i < positionChoices.length; i++) {
    		positionChoices[i] = positions.get(i).getSymbol();
    	}
        String symbolToBeRemoved = (String) JOptionPane.showInputDialog(null, "Choose a position to remove: ", "Remove Position", JOptionPane.QUESTION_MESSAGE, null, positionChoices, positionChoices[0]);
        if (symbolToBeRemoved != null) {
        	target.removePosition(symbolToBeRemoved);
        	target.writeFile();
        	getPortfolioLabel(snapshotLabel, target);
        }
    }

	private void removePortfolio() throws IOException {
		//configure drop down menu
    	ArrayList<Portfolio> portfolios = getPortfolios();
    	String[] portfolioChoices = new String[portfolios.size()];
    	for (int i = 0; i < portfolioChoices.length; i++) {
    		portfolioChoices[i] = portfolios.get(i).getFilename();
    	}
        String portfolioToBeRemoved = (String) JOptionPane.showInputDialog(null, "Choose a portfolio to remove: ", "Remove Portfolio", JOptionPane.QUESTION_MESSAGE, null, portfolioChoices, portfolioChoices[0]);
        new File((portfolioToBeRemoved + ".sms")).delete();
        portfolios = getPortfolios();
    	String[] newFileChoices = new String[portfolios.size()];
    	for (int i = 0; i < newFileChoices.length; i++) {
    		newFileChoices[i] = portfolios.get(i).getFilename();
    	}
    	DefaultComboBoxModel<String> model = new DefaultComboBoxModel<String>(newFileChoices);
    	choices.setModel(model);
    	choices.revalidate();
	}
    
	private ArrayList<Portfolio> getPortfolios() throws IOException {
		ArrayList<Portfolio> result = new ArrayList<Portfolio>();
		File currentFolder = new File(".");
		File[] files = currentFolder.listFiles();
		for (File file : files) {
			if (file.getName().endsWith(".sms")) {
				result.add(Portfolio.fileToPortfolio(file));
			}
		}
		return result;
	}

	private void getPortfolioLabel(JTextArea label, Portfolio portfolio) throws IOException {
		double balance = portfolio.getCashBalance();
		label.setFont(new Font("Courier", Font.BOLD, 15));
		String text = "Filename: " + portfolio.getFilename() + "\n\n";
		text = text + "Cash balance: " + portfolio.getCashBalance() + "\n";
		text = text + "Positions:" + "\n\n";
		text = text + String.format("%-10s %-10s %-10s %-10s %-10s\n", "Symbol", "Percent", "Shares", "Price", "Value");
		for (Position position : portfolio.getPositions()) {
			double price = position.getPrice();
			text = text + String.format("%-10s %-10.2f %-10.2f %-10.2f %-10.2f\n", position.getSymbol(), position.getProportion(), position.getShares(), price, position.getShares()*price);
			balance = balance + position.getShares()*price;
		}
		text = text + String.format("\nTotal balance: $%-10.2f", balance);
		label.setText(text);
	}
	
	private Portfolio portfolioWithFilename(String filename) {
		Portfolio result = null;
		String choice = (String) choices.getSelectedItem();
		for (Portfolio portfolio : portfolios) {
			if (portfolio.getFilename().contentEquals(choice)) {
				result = portfolio;
				break;
			}
		}
		return result;
	}
	
	public static void main (String[] args) throws IOException {
        JFrame frame = new JFrame("Stock Management System");
        frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new StockSystem());
        frame.pack();
        frame.setVisible(true);
    }

}