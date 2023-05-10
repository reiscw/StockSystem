import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Portfolio {

	private double cashBalance;
	private ArrayList<Position> positions;
	private String filename;
	
	public Portfolio(double cashBalance, ArrayList<Position> positions, String filename) {
		this.cashBalance = cashBalance;
		this.positions = positions;
		this.filename = filename;
	}
	
	public void addPosition(Position position) {
		positions.add(position);
	}
	
	public boolean removePosition(String symbol) {
		for (int i = 0; i < positions.size(); i++) {
			if (positions.get(i).getSymbol().contentEquals(symbol)) {
				positions.remove(positions.get(i));
				return true;
			}
		}
		return false;
	}
	
	public ArrayList<Position> getPositions() {
		return positions;
	}

	public double getCashBalance() {
		return cashBalance;
	}

	public void setCashBalance(double cashBalance) {
		this.cashBalance = cashBalance;
	}

	public String getFilename() {
		return filename;
	}

	public void writeFile() throws IOException {
		FileWriter myFileWriter = new FileWriter((filename + ".sms"));
		myFileWriter.write(("" + cashBalance + "\n"));
		for (Position position : positions) {
			myFileWriter.write((position.getSymbol() + "\n"));
			myFileWriter.write(("" + position.getProportion() + "\n"));
			myFileWriter.write(("" + position.getShares() + "\n"));
		}
		myFileWriter.close();
	}

	public static Portfolio fileToPortfolio(File file) throws IOException {
		Scanner input = new Scanner(file);
		double cashBalance = Double.parseDouble(input.next());
		ArrayList<Position> positions = new ArrayList<Position>();
		while (input.hasNext()) {
			String symbol = input.next();
			double proportion = Double.parseDouble(input.next());
			double shares = Double.parseDouble(input.next());
			positions.add(new Position(symbol, proportion, shares));
		}
		input.close();
		return new Portfolio(cashBalance, positions, file.getName().substring(0,file.getName().length()-4));
	}
}
