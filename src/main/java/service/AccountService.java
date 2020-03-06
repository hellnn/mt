package service;

import dao.AccountRepository;
import lombok.RequiredArgsConstructor;
import model.Account;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
public class AccountService extends AbstractService {
    private final AccountRepository accountRepository;

    public Account getAccount(String number) {
        return accountRepository.findByNumber(number);
    }

    public void saveAccount(Account account) throws SQLException {
        accountRepository.save(account);
    }

    public void transferMoney(String srcNumber, String destNumber, BigDecimal sum) throws SQLException {
        doInTransaction(() -> {
            List<Account> accounts = accountRepository.findByNumbers(Arrays.asList(srcNumber, destNumber), true);
            accounts.forEach(a -> {
                if (a.getNumber().equals(srcNumber)) {
                    a.setBalance(a.getBalance().add(sum.negate()));
                } else {
                    a.setBalance(a.getBalance().add(sum));
                }
            });
            accountRepository.saveAll(accounts);
        });
    }
}
