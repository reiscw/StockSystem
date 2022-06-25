import java.io.IOException;
import org.apache.log4j.BasicConfigurator;
import yahoofinance.YahooFinance;

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
		BasicConfigurator.configure();
		price = YahooFinance.get(symbol).getQuote(true).getPrice().doubleValue();
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
