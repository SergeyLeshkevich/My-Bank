package by.leshkevich.controllers.accountControllers;

import by.leshkevich.utils.constants.AppConstant;

import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.io.PrintWriter;


@WebServlet(name = "UpdateAccountController", value = AppConstant.UPDATE_ACCOUNT_CONTROLLER)
public class UpdateAccountController extends AccountAbstractController {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        double balance = Double.parseDouble(request.getParameter(AppConstant.BALANCE_PARAMETER));
        String number = request.getParameter(AppConstant.NUMBER_PARAMETER);
        PrintWriter out = response.getWriter();
        if(accountService.updateAccount(number,balance)){
            out.println("status 200");
        }else {
            out.println("status 500");
        }


    }

}