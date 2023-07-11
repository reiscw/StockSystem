import java.io.*;
import java.net.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;

public class Position {

	private String symbol;
	private double proportion;
	private double price;
	private double shares;
	
	public static HashMap<String, Double> priceDictionary = new HashMap<String, Double>();
	
	public Position(String symbol, double proportion, double shares) throws IOException {
		this.symbol = symbol;
		this.shares = shares;
		this.proportion = proportion;
		this.price = getPrice();
	}
	
	public double getPrice() throws IOException {
		if (priceDictionary.containsKey(symbol)) {
			return priceDictionary.get(symbol);
		} else {
			updatePrice();
			return priceDictionary.get(symbol);
		}	
	}

	// If the API to read stock quotes breaks, you can replace updatePrice
	// with the method below to allow stock prices to be entered manually.
	//
	// Either way, the program only updates the stock quotes one time at
	// the beginning of execution.
	
	private void updateMFPrice()  {
		JTextField priceField = new JTextField();
		Object[] message = {"Enter current price for " + symbol + ": ", priceField};
		int result = JOptionPane.showConfirmDialog(null,  message, "Enter price information", JOptionPane.OK_CANCEL_OPTION);
		if (result == JOptionPane.OK_OPTION) {
			priceDictionary.put(symbol, Double.parseDouble(priceField.getText()));
		}
	}

	private void updatePrice() throws IOException {
		if (symbol.length() >= 5) {
			updateMFPrice();
		} else {
			String URL1 = "https://financialmodelingprep.com/api/v3/quote/";
			String URL2 = symbol;
			String URL3 = "?apikey=";
			Scanner apiScan = new Scanner(new File("API_KEY.txt"));
			final String API_KEY = apiScan.nextLine();        
			URL url = new URL(URL1 + URL2 + URL3 + API_KEY);
			URLConnection positionData = url.openConnection();
			Scanner input = new Scanner(positionData.getInputStream());
			String content = "";
			while (input.hasNextLine()) {
				content = content + input.nextLine();
			}
			int index1 = content.indexOf("\"price\"");
			int index2 = content.indexOf(":", index1);
			int index3 = content.indexOf(",", index1);
			double price = Double.parseDouble(content.substring(index2+1, index3));
			priceDictionary.put(symbol, price);
		}
	}

	public String getSymbol() {
		return symbol;
	}

	public double getShares() {
		return shares;
	}

	public double getProportion() {
		return proportion;
	}
	
	public void setShares(double shares) {
		this.shares = shares;
	}	
}
