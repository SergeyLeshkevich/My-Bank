package by.leshkevich.controllers.userControllers;

import by.leshkevich.controllers.transactionControllers.TransactionAbstractController;
import by.leshkevich.utils.constants.AppConstant;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "DeleteUserController", value = AppConstant.DELETE_USER_CONTROLLER)
public class DeleteUserController extends UserAbstractController {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter(AppConstant.ID_PARAMETER));
        PrintWriter out = response.getWriter();

        if (userService.delete(id)) {
            out.println("status 200");
        } else {
            out.println("status 500");
        }


    }

}
