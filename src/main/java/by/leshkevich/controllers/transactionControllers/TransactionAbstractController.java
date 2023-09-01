package by.leshkevich.controllers.transactionControllers;

import by.leshkevich.controllers.AbstractController;
import by.leshkevich.services.TransactionService;


import jakarta.servlet.annotation.WebServlet;

@WebServlet(name = "TransactionAbstractController")
public abstract class TransactionAbstractController extends AbstractController {
    protected TransactionService transactionService;

    public TransactionAbstractController() {
        this.transactionService = new TransactionService();
    }
}
