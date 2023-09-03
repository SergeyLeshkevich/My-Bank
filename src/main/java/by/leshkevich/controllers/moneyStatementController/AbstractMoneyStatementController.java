package by.leshkevich.controllers.moneyStatementController;

import by.leshkevich.controllers.AbstractController;
import by.leshkevich.services.MoneyStatementService;
import jakarta.servlet.annotation.WebServlet;

@WebServlet(name = "AbstractMoneyStatementController")
public abstract class AbstractMoneyStatementController extends AbstractController {
    protected MoneyStatementService moneyStatementService;
    public AbstractMoneyStatementController() {
        this.moneyStatementService = new MoneyStatementService();
    }
}
