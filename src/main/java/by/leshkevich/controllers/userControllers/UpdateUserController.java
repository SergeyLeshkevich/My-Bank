package by.leshkevich.controllers.userControllers;

import by.leshkevich.model.Transaction;
import by.leshkevich.model.User;
import by.leshkevich.utils.DateManager;
import by.leshkevich.utils.constants.AppConstant;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;


@WebServlet(name = "UpdateUserController", value = AppConstant.UPDATE_USER_CONTROLLER)
public class UpdateUserController extends UserAbstractController {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String login = request.getParameter(AppConstant.LOGIN_PARAMETER);
        String lastname = request.getParameter(AppConstant.LASTNAME_PARAMETER);
        PrintWriter out = response.getWriter();
        User user= User.builder()
                .lastname(lastname)
                .login(login)
                .build();
        if(userService.updateLastname(user)){
            out.println("status 200");
        }else {
            out.println("status 500");
        }


    }

}