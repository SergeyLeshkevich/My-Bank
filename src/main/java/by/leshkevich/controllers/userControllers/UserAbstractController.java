package by.leshkevich.controllers.userControllers;

import by.leshkevich.controllers.AbstractController;
import by.leshkevich.services.UserService;
import jakarta.servlet.annotation.WebServlet;

@WebServlet(name = "UserAbstractController")
public abstract class UserAbstractController extends AbstractController {
    protected UserService userService;

    public UserAbstractController() {
        this.userService = new UserService();
    }
}