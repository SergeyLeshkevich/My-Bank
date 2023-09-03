package by.leshkevich.controllers.accountControllers.accountStatement;

import by.leshkevich.controllers.accountControllers.AccountAbstractController;
import by.leshkevich.services.TransactionService;
import by.leshkevich.services.UserService;
import jakarta.servlet.annotation.WebServlet;
/**
 * @author S.Leshkevich
 * @version 1.0
 */
@WebServlet(name = "AbstractExtractYearController")
public abstract class AbstractExtractYearController extends AccountAbstractController {
    /**
     * UserService userService
     */
    protected UserService userService;
    /**
     * TransactionService transactionService;
     */
    protected TransactionService transactionService;

    /**
     * constructor AbstractMoneyStatementController
     */
    public AbstractExtractYearController() {
        this.userService = new UserService();
        this.transactionService = new TransactionService();
    }
}
