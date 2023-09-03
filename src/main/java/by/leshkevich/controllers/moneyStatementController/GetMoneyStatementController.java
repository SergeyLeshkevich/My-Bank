package by.leshkevich.controllers.moneyStatementController;

import by.leshkevich.utils.DateManager;
import by.leshkevich.utils.MoneyStatement;
import by.leshkevich.utils.constants.AppConstant;
import by.leshkevich.utils.file.FileHandler;
import by.leshkevich.utils.file.StringsForFile;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

@WebServlet(name = "GetMoneyStatementController", value = AppConstant.GET_MONEY_STATEMENT_CONTROLLER)
public class GetMoneyStatementController extends AbstractMoneyStatementController {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        String number = request.getParameter(AppConstant.NUMBER_PARAMETER);
        LocalDateTime dateTimeFor = DateManager.getDateFor(request.getParameter(AppConstant.DATE_FOR_PARAMETER));
        LocalDateTime dateTimeBefore = DateManager.getDateBefore(request.getParameter(AppConstant.DATE_BEFORE_PARAMETER));
        MoneyStatement moneyStatement = moneyStatementService.getMoneyStatement(number, dateTimeFor, dateTimeBefore);


        if (moneyStatement != null) {

            String stringMoneyStatement = StringsForFile.StringForMoneyStatement(moneyStatement);
            String statementMoneyName = moneyStatement.getNumberAccount() + "-" + moneyStatement.getDateFor().dateFormat() + "-"
                    + moneyStatement.getDateBefore().dateFormat() + ".pdf";
            FileHandler.writeFilePDF(AppConstant.PATH_STATEMENT_MONEY + statementMoneyName, stringMoneyStatement);

            out.println(StringsForFile.StringForMoneyStatement(moneyStatement));
        } else {
            out.println("not found");
        }
    }
}
