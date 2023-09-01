package by.leshkevich.controllers.userControllers;


import by.leshkevich.controllers.transactionControllers.TransactionAbstractController;
import by.leshkevich.model.Transaction;
import by.leshkevich.model.User;
import by.leshkevich.utils.constants.AppConstant;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "GetUserController", value = AppConstant.GET_USER_CONTROLLER)
public class GetUserController extends UserAbstractController {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String login = request.getParameter(AppConstant.LOGIN_PARAMETER);
        User user=userService.getUser(login);
        String json = GSON.toJson(user);
        PrintWriter out = response.getWriter();
        out.println(json);
    }


}