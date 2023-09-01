package by.leshkevich.controllers;


import by.leshkevich.utils.DateManager;
import com.google.gson.Gson;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;


@WebServlet(name = "AbstractController")
public abstract class AbstractController extends HttpServlet {
    protected static final Gson GSON = DateManager.buildGson();

    public void init() {
    }

    public void destroy() {
    }
}
