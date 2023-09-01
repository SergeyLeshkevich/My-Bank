package by.leshkevich.controllers;

import java.io.*;

//import by.leshkevich.Main;
import by.leshkevich.utils.constants.AppConstant;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "RunAppController", value = AppConstant.RUN_APP)
public class RunAppController extends HttpServlet {
    public void init() { }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

//        Main.main(new String[0]);
    }

    public void destroy() {
    }
}
