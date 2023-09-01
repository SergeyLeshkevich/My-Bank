package by.leshkevich.utils.file;

import by.leshkevich.model.Account;
import by.leshkevich.model.Transaction;
import by.leshkevich.model.User;
import by.leshkevich.utils.DateManager;

import java.time.LocalDateTime;
import java.util.List;

public class StringsForFile {
    public static String StringForCheck(Transaction transaction) {
        StringBuilder sb = new StringBuilder();
        sb.append(" _________________________________________\n");
        sb.append("|                 Bank Check              |\n");
        sb.append("| " + String.format("%-18s%22d", "Check:",
                transaction.getId()) + "|\n");
        sb.append("| " + String.format("%-18s%24s",
                transaction.getDataOperation().dateFormat(),
                transaction.getDataOperation().timeFormat() + "|\n"));
        sb.append("| " + String.format("%-18s%22s", "Transaction type:",
                transaction.getTypeOperation()) + "|\n");
        sb.append("| " + String.format("%-18s%24s", "Sender's bank:",
                transaction.getSenderBank().getName() + "|\n"));
        sb.append("| " + String.format("%-18s%24s", "Payee's bank:",
                transaction.getBeneficiaryBank().getName() + "|\n"));
        sb.append("| " + String.format("%-18s%24s", "Sender's account:",
                transaction.getSenderAccount().getNumber() + "|\n"));
        sb.append("| " + String.format("%-18s%20s", "Beneficiary's account:",
                transaction.getBeneficiaryAccount().getNumber() + "|\n"));
        sb.append("| " + String.format("%-18s%24s", "Sum:",
                String.format("%.2f", transaction.getAmount()) + "|\n"));
        sb.append("|_________________________________________|\n");

        return sb.toString();
    }

    public static String StringForExtract(User user, String bankName, List<Transaction> list,
                                          Account account, LocalDateTime dateFor,LocalDateTime dateBefore) {
        DateManager dataManager = new DateManager();
        StringBuilder sb = new StringBuilder();
        sb.append("                        Extract         \n");
        sb.append(String.format("%33s",
                bankName) + "\n");
        sb.append(String.format("%-30s%2s%-24s", "Client", "| ",
                user.getFirstname() + " " + user.getLastname()) + "\n");
        sb.append(String.format("%-30s%2s%-24s", "Check", "| ",
                account.getNumber()) + "\n");
        sb.append(String.format("%-30s%2s%-24s", "Currency", "| ",
                "BYN") + "\n");
        sb.append(String.format("%-30s%2s%-24s", "Opening date", "| ",
                account.getOpenData().dateFormat()) + "\n");

        sb.append(String.format("%-30s%2s%-24s","Period","| ",
                dateFor.format(DateManager.FORMATTER_DATE)+" - "+dateBefore.format(DateManager.FORMATTER_DATE))+"\n");

        sb.append(String.format("%-30s%2s%-24s", "Date and time of formation", "| ",
                dataManager.dateFormat() + ", " + dataManager.timeFormat()) + "\n");

        sb.append(String.format("%-30s%2s%-24s", "Remainder", "| ",
                String.format("%.2f", account.getBalance()) + " BYN") + "\n");

        sb.append(String.format("%-10s%1s%-30s%1s-10s","    Date","|",
                "        Notice   ","|"," Sum")+"\n");

        sb.append("_____________________________________________________\n");

        for (Transaction transaction:list) {
            sb.append(String.format("%-10s%1s%-30s%1s-10s",transaction.getDataOperation().dateFormat(),"|",
                    transaction.getTypeOperation(),"|",String.format("%.2f", account.getBalance()))+"\n");
        }

        return sb.toString();
    }

}
