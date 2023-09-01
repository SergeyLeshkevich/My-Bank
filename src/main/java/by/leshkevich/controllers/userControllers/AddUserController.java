package by.leshkevich.controllers.userControllers;


import by.leshkevich.model.Account;
import by.leshkevich.model.Bank;
import by.leshkevich.model.Transaction;
import by.leshkevich.model.User;
import by.leshkevich.utils.DateManager;
import by.leshkevich.utils.constants.AppConstant;
import by.leshkevich.utils.enums.Status;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "AddUserController", value = AppConstant.ADD_USER_CONTROLLER)
public class AddUserController extends UserAbstractController {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String login = request.getParameter(AppConstant.LOGIN_PARAMETER);
        String lastname = request.getParameter(AppConstant.LASTNAME_PARAMETER);
        String firstname = request.getParameter(AppConstant.FIRSTNAME_OPERATION);
        String password = request.getParameter(AppConstant.PASSWORD_OPERATION);

        PrintWriter out = response.getWriter();
        User user = User.builder()
                .login(login)
                .firstname(lastname)
                .lastname(firstname).build();

        if (userService.save(user,password) >0) {
            out.println("status 200");
        } else {
            out.println("status 500");
        }

    }

}

