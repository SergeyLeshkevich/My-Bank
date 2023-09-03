package by.leshkevich.controllers.userControllers;

import by.leshkevich.model.User;
import by.leshkevich.utils.constants.AppConstant;
import by.leshkevich.utils.exceptions.UserException;
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
        try {
            if (!userService.checkUniqueLogin(login)) throw new UserException("login busy");

            User user = User.builder()
                    .login(login)
                    .firstname(lastname)
                    .lastname(firstname).build();

            if (userService.registration(user, password)) {
                out.println("status 200");
            } else {
                out.println("status 500");
            }

        } catch (UserException e) {
            throw new RuntimeException(e);
        }


    }

}

