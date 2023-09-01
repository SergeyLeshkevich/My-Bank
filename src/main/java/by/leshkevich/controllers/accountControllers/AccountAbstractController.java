package by.leshkevich.controllers.accountControllers;

import by.leshkevich.controllers.AbstractController;
import by.leshkevich.services.AccountService;
import jakarta.servlet.annotation.WebServlet;
@WebServlet(name = "AccountAbstractController")
public abstract class AccountAbstractController extends AbstractController {
    protected AccountService accountService;

    public AccountAbstractController() {
        this.accountService = new AccountService();
    }
}
