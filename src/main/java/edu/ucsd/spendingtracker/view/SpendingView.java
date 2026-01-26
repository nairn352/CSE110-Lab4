package edu.ucsd.spendingtracker.view;

import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.util.function.Consumer;
import edu.ucsd.spendingtracker.model.Expense;
import edu.ucsd.spendingtracker.model.Category; 

public class SpendingView extends BorderPane {
    private VBox listContainer;
    private Button summaryButton;
    private Button openAddModalBtn;
    private Consumer<Integer> onDeleteHandler;

    public SpendingView() {
        VBox headerBox = new VBox(15);
        headerBox.setAlignment(Pos.CENTER);
        headerBox.setPadding(new Insets(20));


        Text title = new Text("Spending Tracker");
        title.setStyle("-fx-font-weight: bold; -fx-font-size: 26;");


        HBox navBox = new HBox(10);
        navBox.setAlignment(Pos.CENTER);


        summaryButton = new Button("View Summary");
        openAddModalBtn = new Button("Add New Expense +");
        openAddModalBtn.setStyle("-fx-background-color: #2E7D32; -fx-text-fill: white; -fx-font-weight: bold;");


        navBox.getChildren().addAll(openAddModalBtn, summaryButton);
        headerBox.getChildren().addAll(title, navBox);


        listContainer = new VBox(5);
        ScrollPane scroller = new ScrollPane(listContainer);
        scroller.setFitToWidth(true);
        scroller.setStyle("-fx-background-color: transparent; -fx-background: #FFFFFF;");


        this.setTop(headerBox);
        this.setCenter(scroller);
        this.setPadding(new Insets(0, 25, 20, 25));
        this.setStyle("-fx-background-color: #FFFFFF;");
    }


    public void addExpenseRow(int id, String name, String catName, String color, double amount) {
        HBox row = new HBox(10);
        row.setPrefSize(450, 40);
        row.setPadding(new Insets(5, 10, 5, 10));
        row.setAlignment(Pos.CENTER_LEFT);
        row.setStyle("-fx-background-color: " + color + "; -fx-border-color: #D3D3D3; " +
                "-fx-border-width: 0 0 1 0; -fx-font-weight: bold; " +
                "-fx-border-radius: 5px; -fx-background-radius: 5px;");


        Label nameL = new Label(name);
        nameL.setPrefWidth(200);
        Label catL = new Label("[" + catName + "]");
        catL.setPrefWidth(100);
        catL.setStyle("-fx-font-size: 10px; -fx-font-weight: normal;");
        Label amtL = new Label("$" + String.format("%.2f", amount));
        amtL.setPrefWidth(80);
        amtL.setAlignment(Pos.CENTER_RIGHT);
        Button deleteBtn = new Button("X");
        deleteBtn.setStyle("-fx-background-color: #ff6961; -fx-text-fill: white;");
        deleteBtn.setOnAction(e -> {
            if (onDeleteHandler != null)
                onDeleteHandler.accept(id);
        });


        row.getChildren().addAll(nameL, catL, amtL, deleteBtn);
        listContainer.getChildren().add(row);
    }

    public void showAddExpenseModal(Consumer<Expense> onSave) {
        Stage modal = new Stage();
        modal.initModality(Modality.APPLICATION_MODAL); // Blocks the main window
        modal.setTitle("Add New Expense");


        VBox layout = new VBox(15);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);


        TextField nameField = new TextField();
        nameField.setPromptText("Expense Name");
        TextField amountField = new TextField();
        amountField.setPromptText("Amount");
        ComboBox<Category> categoryBox = new ComboBox<>();
        categoryBox.getItems().setAll(Category.values());


        Button saveBtn = new Button("Save Expense");
        saveBtn.setOnAction(e -> {
            try {
                double amount = Double.parseDouble(amountField.getText());
                onSave.accept(new Expense(nameField.getText(), categoryBox.getValue(), amount));
                modal.close();
            } catch (NumberFormatException ex) {
                amountField.setStyle("-fx-border-color: red;");
            }
        });


        layout.getChildren().addAll(new Label("Expense Details"), nameField, amountField, categoryBox, saveBtn);
        modal.setScene(new Scene(layout, 300, 250));
        modal.showAndWait();
    }

    public Button getSummaryButton() {
        return summaryButton;
    }

    public void clearList() {
        listContainer.getChildren().clear();
    }


    public void setOnDelete(Consumer<Integer> handler) {
        this.onDeleteHandler = handler;
    }


    public Button getOpenModalButton() {
        return openAddModalBtn;
    }

}
