package by.leshkevich.controllers.transactionControllers;


import by.leshkevich.model.Account;
import by.leshkevich.model.Bank;
import by.leshkevich.model.Transaction;
import by.leshkevich.utils.DateManager;
import by.leshkevich.utils.constants.AppConstant;
import by.leshkevich.utils.enums.Status;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "AddTransactionController", value = AppConstant.ADD_TRANSACTION_CONTROLLER)
public class AddTransactionController extends TransactionAbstractController {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String numberSA = request.getParameter(AppConstant.NUMBER_ACCOUNT_SENDER_BANK_PARAMETER);
        String numberDA = request.getParameter(AppConstant.NUMBER_ACCOUNT_BENEFICIARY_BANK_PARAMETER);
        String typeOperation = request.getParameter(AppConstant.TYPE_OPERATION);
        double amount = Double.parseDouble(request.getParameter(AppConstant.AMOUNT_PARAMETER));
        int idBankS = Integer.parseInt(request.getParameter(AppConstant.ID_SENDER_BANK_PARAMETER));
        int idBankD = Integer.parseInt(request.getParameter(AppConstant.ID_BENEFICIARY_BANK_PARAMETER));
        String status = Status.REJECTED.getMessage();

        PrintWriter out = response.getWriter();
        Transaction transaction= Transaction.builder()
                .senderAccount(Account.builder().number(numberSA).build())
                .beneficiaryAccount(Account.builder().number(numberDA).build())
                .typeOperation(typeOperation)
                .amount(amount)
                .senderBank(Bank.builder().id(idBankS).build())
                .beneficiaryBank(Bank.builder().id(idBankD).build())
                .status(Status.IN_PROCESSING.getMessage())
                .dataOperation(new DateManager()).build();

        if (transactionService.saveTransaction(transaction) != null) {
            out.println("status 200");
        } else {
            out.println("status 500");
        }

    }

}

