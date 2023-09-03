package by.leshkevich.controllers;

import java.io.*;

import by.leshkevich.Main;
import by.leshkevich.utils.constants.AppConstant;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
/**
 * @author S.Leshkevich
 * @version 1.0
 * Main.class launch controller
 * */
@WebServlet(name = "RunAppController", value = AppConstant.RUN_APP)
public class RunAppController extends AbstractController {
    /**
     *
     * @param request an {@link HttpServletRequest} object that contains the request the client has made of the servlet
     *
     * @param response an {@link HttpServletResponse} object that contains the response the servlet sends to the client
     *
     * @throws IOException
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        out.println("App running");
        Main.main(new String[0]);
    }
}
