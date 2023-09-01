package by.leshkevich.controllers.transactionControllers;


import by.leshkevich.model.Account;
import by.leshkevich.model.Transaction;
import by.leshkevich.utils.constants.AppConstant;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "GetTransactionController", value = AppConstant.GET_TRANSACTION_CONTROLLER)
public class GetTransactionController extends TransactionAbstractController {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int number = Integer.parseInt(request.getParameter(AppConstant.NUMBER_PARAMETER));
        Transaction transaction=transactionService.getTransaction(number);
        String json = GSON.toJson(transaction);
        PrintWriter out = response.getWriter();
        out.println(json);
    }


}