package by.leshkevich.controllers.bankControllers;


import by.leshkevich.controllers.accountControllers.AccountAbstractController;
import by.leshkevich.model.Account;
import by.leshkevich.model.Bank;
import by.leshkevich.utils.constants.AppConstant;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "GetBankController", value = AppConstant.GET_BANK_CONTROLLER)
public class GetBankController extends BankAbstractController {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter(AppConstant.ID_PARAMETER));
        Bank bank = bankService.get(id);
        String json = GSON.toJson(bank);
        PrintWriter out = response.getWriter();
        out.println(json);
    }


}