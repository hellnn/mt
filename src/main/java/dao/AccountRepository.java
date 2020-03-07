package dao;

import model.Account;

import java.sql.ResultSet;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class AccountRepository extends AbstractRepository<Account> {
    public static final String FIND_ACCOUNT_SQL_PREFIX = "select * from account where number in (";
    public static final String INSERT_ACCOUNT_SQL = "insert into account (number, balance) values (?, ?)";
    public static final String UPDATE_ACCOUNT_SQL = "update account set number = ?, balance = ? where id = ?";

    public Account findByNumber(String number) {
        return findByNumbers(Collections.singletonList(number), false)
                .stream()
                .findFirst()
                .orElse(null);
    }

    public List<Account> findByNumbers(List<String> numbers, boolean lock) {
        String sql = FIND_ACCOUNT_SQL_PREFIX + numbers.stream().map(s -> "?").collect(Collectors.joining(",")) + ")";
        return executeQuery(sql, numbers.toArray(), rs ->
                new Account(rs.getLong(1), rs.getString(2), rs.getBigDecimal(3)), lock);
    }

    public Account save(Account account) {
        return saveAll(Collections.singletonList(account))
                .stream()
                .findFirst()
                .orElse(null);
    }

    public List<Account> saveAll(List<Account> accounts) {
        accounts.forEach(a -> {
            if (a.getId() == null) {
                executeUpdate(INSERT_ACCOUNT_SQL, ps -> {
                    ps.setString(1, a.getNumber());
                    ps.setBigDecimal(2, a.getBalance());
                    try (ResultSet rs = ps.getGeneratedKeys()) {
                        if (rs.next()) {
                            a.setId(rs.getLong(1));
                        }
                    }
                });
            } else {
                executeUpdate(UPDATE_ACCOUNT_SQL, ps -> {
                    ps.setString(1, a.getNumber());
                    ps.setBigDecimal(2, a.getBalance());
                    ps.setLong(3, a.getId());
                });
            }
        });
        return accounts;
    }
}
