import java.io.*;
import java.net.*;
import java.util.*;

public class Position {

	private String symbol;
	private double proportion;
	private double price;
	private double shares;
	
	public Position(String symbol, double proportion, double shares) throws IOException {
		this.symbol = symbol;
		this.shares = shares;
		this.proportion = proportion;
		updatePrice();
	}
	
	public double getPrice() throws IOException {
		updatePrice();
		return price;
	}
	
	private void updatePrice() throws IOException {
		String url = "https://query1.finance.yahoo.com/v6/finance/quote?symbols=" + symbol;
		URL yf = new URL(url);
        URLConnection positionData = yf.openConnection();
        Scanner input = new Scanner(positionData.getInputStream());
        String result = input.nextLine();
        int index1 = result.indexOf("regularMarketPrice\":");
        int index2 = result.indexOf(":", index1);
        int index3 = result.indexOf(",", index1);
        price = Double.parseDouble(result.substring(index2+1, index3));
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
