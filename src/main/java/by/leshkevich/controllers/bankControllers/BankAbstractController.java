package by.leshkevich.controllers.bankControllers;

import by.leshkevich.controllers.AbstractController;
import by.leshkevich.services.BankService;

import jakarta.servlet.annotation.WebServlet;
@WebServlet(name = "BankAbstractController")
public abstract class BankAbstractController extends AbstractController {
    protected BankService bankService;

    public BankAbstractController() {
        this.bankService = new BankService();
    }
}
