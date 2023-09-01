package by.leshkevich.controllers.transactionControllers;

import by.leshkevich.utils.constants.AppConstant;
import by.leshkevich.utils.exceptions.DAOException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "DeleteTransactionController", value = AppConstant.DELETE_TRANSACTION_CONTROLLER)
public class DeleteTransactionController extends TransactionAbstractController {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter(AppConstant.ID_PARAMETER));
        PrintWriter out = response.getWriter();

        if (transactionService.delete(id)) {
            out.println("status 200");
        } else {
            out.println("status 500");
        }


    }

}
