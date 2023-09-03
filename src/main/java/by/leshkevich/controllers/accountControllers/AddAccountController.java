package by.leshkevich.controllers.accountControllers;


import by.leshkevich.model.Account;
import by.leshkevich.model.Bank;
import by.leshkevich.model.User;

import by.leshkevich.utils.DateManager;
import by.leshkevich.utils.constants.AppConstant;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "AddAccountController", value = AppConstant.ADD_ACCOUNT_CONTROLLER)
public class AddAccountController extends AccountAbstractController {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String number = request.getParameter(AppConstant.NUMBER_PARAMETER);
        int idUser = Integer.parseInt(request.getParameter(AppConstant.ID_USER_PARAMETER));
        int idBank = Integer.parseInt(request.getParameter(AppConstant.ID_BANK_PARAMETER));
        double balance = Double.parseDouble(request.getParameter(AppConstant.BALANCE_PARAMETER));
        PrintWriter out = response.getWriter();
        Account account = Account.builder()
                .number(number)
                .Bank(Bank.builder().id(idBank).build())
                .User(User.builder().id(idUser).build())
                .balance(balance)
                .openDate(new DateManager()).build();

        if (accountService.save(account) != null) {
            out.println("status 200");
        } else {
            out.println("status 500");
        }

    }

}

