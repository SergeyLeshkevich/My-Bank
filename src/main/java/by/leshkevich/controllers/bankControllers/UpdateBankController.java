package by.leshkevich.controllers.bankControllers;

import by.leshkevich.model.Bank;
import by.leshkevich.utils.constants.AppConstant;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;


@WebServlet(name = "UpdateBankController", value = AppConstant.UPDATE_BANK_CONTROLLER)
public class UpdateBankController extends BankAbstractController {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter(AppConstant.ID_PARAMETER));
        String name = request.getParameter(AppConstant.NAME_PARAMETER);
        PrintWriter out = response.getWriter();
        if (bankService.update(Bank.builder()
                .id(id)
                .name(name).build())) {
            out.println("status 200");
        } else {
            out.println("status 500");
        }

    }

}