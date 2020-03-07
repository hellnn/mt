package service;

import common.BadRequestException;
import dao.AccountRepository;
import lombok.RequiredArgsConstructor;
import model.Account;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
public class AccountService extends AbstractService {
    private final AccountRepository accountRepository;

    public Account get(String number) {
        return accountRepository.findByNumber(number);
    }

    public Account save(Account account) {
        return accountRepository.save(account);
    }

    public void transfer(String srcNumber, String destNumber, BigDecimal sum) {
        doInTransaction(() -> {
            List<Account> accounts = accountRepository.findByNumbers(Arrays.asList(srcNumber, destNumber), true);
            if (accounts.size() != 2) {
                throw new BadRequestException("Accounts " + srcNumber + " or " + destNumber + " not found");
            }
            accounts.forEach(a -> {
                if (a.getNumber().equals(srcNumber)) {
                    if (a.getBalance().compareTo(sum) < 0) {
                        throw new BadRequestException("Not enough money in account " + srcNumber);
                    }
                    a.setBalance(a.getBalance().add(sum.negate()));
                } else {
                    a.setBalance(a.getBalance().add(sum));
                }
            });
            accountRepository.saveAll(accounts);
        });
    }
}
