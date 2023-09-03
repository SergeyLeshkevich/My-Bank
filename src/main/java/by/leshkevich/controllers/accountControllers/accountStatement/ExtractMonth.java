package by.leshkevich.controllers.accountControllers.accountStatement;

import by.leshkevich.model.Account;
import by.leshkevich.model.Transaction;
import by.leshkevich.model.User;
import by.leshkevich.utils.DateManager;
import by.leshkevich.utils.constants.AppConstant;
import by.leshkevich.utils.exceptions.AccountException;
import by.leshkevich.utils.exceptions.AuthorisationException;
import by.leshkevich.utils.exceptions.TransactionException;
import by.leshkevich.utils.exceptions.UserException;
import by.leshkevich.utils.file.FileHandler;
import by.leshkevich.utils.file.StringsForFile;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
/**
 * @author S.Leshkevich
 * @version 1.0
 */
@WebServlet(name = "ExtractMonth", value = AppConstant.EXTRACT_MONTH_CONTROLLER)
public class ExtractMonth extends AbstractExtractYearController {
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
        String number = request.getParameter(AppConstant.NUMBER_PARAMETER);
        String login = request.getParameter(AppConstant.LOGIN_PARAMETER);
        try {
            User user = userService.getUser(login);
            if (user == null) throw new UserException("User not found");

            Account account = accountService.getAccountByNumber(number);
            if (account == null) throw new AccountException("Account not found");

            if ((accountService.getListByIdUser(user.getId()).
                    stream().filter(a -> a.getNumber().equals(number))
                    .collect(Collectors.toList())).isEmpty()) throw new AuthorisationException("Access denied");

            LocalDateTime dateTimeFor = LocalDateTime.now().minusMonths(1);
            LocalDateTime dateTimeBefore = LocalDateTime.now();

            List<Transaction> transactionList = transactionService.getAllTransactionsListForPeriod(account.getNumber(), dateTimeFor, dateTimeBefore);
            if (transactionList.isEmpty()) throw new TransactionException("Transaction not found");
            DateManager dateManager = new DateManager();

            String stringForExtract = StringsForFile.StringForExtract(user, account.getBank().getName(), transactionList, account, dateTimeFor, dateTimeBefore);

            String statementAccountName = "accountStatement-" + dateManager.dateFormat() + "-" + number + "-month" + ".pdf";
            FileHandler.writeFilePDF(AppConstant.PATH_STATEMENT_MONEY + statementAccountName, stringForExtract);

            out.println(stringForExtract);

        } catch (UserException | AccountException | TransactionException | AuthorisationException e) {
            out.println(e.getMessage());
        }
    }
}
