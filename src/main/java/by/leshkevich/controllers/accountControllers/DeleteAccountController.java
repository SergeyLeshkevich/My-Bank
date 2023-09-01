package by.leshkevich.controllers.accountControllers;

import by.leshkevich.utils.constants.AppConstant;
import by.leshkevich.utils.exceptions.DAOException;

import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "DelAccountController", value = AppConstant.DELETE_ACCOUNT_CONTROLLER)
public class DeleteAccountController extends AccountAbstractController{

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter(AppConstant.ID_PARAMETER));
        PrintWriter out = response.getWriter();
        try {
            out = response.getWriter();
            accountService.delete(id);
            out.println("status 200");
        } catch (DAOException e) {
            out.println("status 500");
        }

    }

}
