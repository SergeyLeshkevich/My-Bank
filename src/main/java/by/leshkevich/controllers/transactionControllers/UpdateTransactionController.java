package by.leshkevich.controllers.transactionControllers;

import by.leshkevich.model.Transaction;
import by.leshkevich.utils.DateManager;
import by.leshkevich.utils.constants.AppConstant;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;


@WebServlet(name = "UpdateTransactionController", value = AppConstant.UPDATE_TRANSACTION_CONTROLLER)
public class UpdateTransactionController extends TransactionAbstractController {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        int number = Integer.parseInt(request.getParameter(AppConstant.ID_PARAMETER));
        String status = request.getParameter(AppConstant.STATUS_PARAMETER);
        PrintWriter out = response.getWriter();
        Transaction transaction=Transaction.builder()
                .id(number)
                .dataOperation(new DateManager())
                .status(status).build();
        if(transactionService.updateStatus(transaction)){
            out.println("status 200");
        }else {
            out.println("status 500");
        }


    }

}