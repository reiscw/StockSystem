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
	
	public Position(String symbol, double proportion, double shares) {
		this.symbol = symbol;
		this.shares = shares;
		this.proportion = proportion;
		this.price = getPrice();
	}
	
	public double getPrice()  {
		if (priceDictionary.containsKey(symbol)) {
			return priceDictionary.get(symbol);
		} else {
			updatePrice();
			return priceDictionary.get(symbol);
		}	
	}
	
	private void updatePrice()  {
		JTextField priceField = new JTextField();
		Object[] message = {"Enter current price for " + symbol + ": ", priceField};
		int result = JOptionPane.showConfirmDialog(null,  message, "Enter price information", JOptionPane.OK_CANCEL_OPTION);
		if (result == JOptionPane.OK_OPTION) {
			priceDictionary.put(symbol, Double.parseDouble(priceField.getText()));
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
