package by.leshkevich.controllers.bankControllers;


import by.leshkevich.model.Bank;
import by.leshkevich.utils.constants.AppConstant;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "AddBankController", value = AppConstant.ADD_BANK_CONTROLLER)
public class AddBankController extends BankAbstractController {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String name = request.getParameter(AppConstant.NAME_PARAMETER);

        PrintWriter out = response.getWriter();
        Bank bank = Bank.builder().name(name).build();

        if (bankService.save(bank) != null) {
            out.println("status 200");
        } else {
            out.println("status 500");
        }

    }

}

