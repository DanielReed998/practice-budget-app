package application;

import javafx.beans.property.SimpleStringProperty;

public class DataPiece {
	private final SimpleStringProperty category;
	private final SimpleStringProperty amount;
	
	public DataPiece(String category, String amount) {
		super();
		this.category = new SimpleStringProperty(category);
		this.amount = new SimpleStringProperty(amount);
	}

	public String getCategory() {
		return category.get();
	}

	public String getAmount() {
		return amount.get();
	}
	
}
