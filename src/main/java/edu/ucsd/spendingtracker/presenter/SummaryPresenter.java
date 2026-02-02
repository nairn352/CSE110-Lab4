package edu.ucsd.spendingtracker.presenter;

import edu.ucsd.spendingtracker.model.Model;
import edu.ucsd.spendingtracker.view.SummaryView;

import java.util.List;
import edu.ucsd.spendingtracker.view.charts.IChartProvider;

import edu.ucsd.spendingtracker.model.Category;
import javafx.scene.Node;
import java.util.Map;

import edu.ucsd.spendingtracker.model.Expense;
import java.util.TreeMap;

public class SummaryPresenter extends AbstractPresenter<SummaryView> {
    private Runnable onBack;
    private final List<IChartProvider> chartProviders;

    public SummaryPresenter(Model model, SummaryView view, List<IChartProvider> chartProviders) {
        super(model, view);
        this.chartProviders = chartProviders;
        this.view.getChartSelector().getItems().addAll(this.chartProviders);

        if (!this.chartProviders.isEmpty()) {
            this.view.getChartSelector().setValue(this.chartProviders.get(0));
        }

        this.view.getBackButton().setOnAction(e -> {
            if (onBack != null)
                onBack.run();
        });

        this.view.getChartSelector().setOnAction(e -> refresh());
    }

    public void refresh() {
        List<Expense> expenses = model.getExpenses();
        Map<Category, Double> totals = new TreeMap<>();

        for(Category cat : Category.values()) {
            totals.put(cat, 0.0);
        }

        for(Expense expense: expenses) {
            Category category = expense.getCategory();
            double amount = expense.getAmount();

            totals.put(category, totals.get(category) + amount);
        }

        view.setTotal(model.getTotalSpending());

        IChartProvider selectedProvider = view.getChartSelector().getValue();

        if (selectedProvider != null) {
            Node chartNode = selectedProvider.createChart(totals);
            view.setChartDisplay(chartNode);
        }
    }

    public void setOnBack(Runnable action) {
        this.onBack = action;
    }

    @Override
    public void updateView() {
        refresh(); //we don't know why
        view.setTotal(model.getTotalSpending());
    }

    @Override
    public String getViewTitle() {
        return "Summary";
    }
}
