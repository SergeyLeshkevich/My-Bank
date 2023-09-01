package by.leshkevich.controllers.accountControllers;


import by.leshkevich.model.Account;
import by.leshkevich.utils.constants.AppConstant;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet(name = "GetAccountController", value = AppConstant.GET_ACCOUNT_CONTROLLER)
public class GetAccountController extends AccountAbstractController {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String number = request.getParameter(AppConstant.NUMBER_PARAMETER);
        Account account = accountService.getAccountByNumber(number);
        String json = GSON.toJson(account);
        PrintWriter out = response.getWriter();
        out.println(json);
    }


}