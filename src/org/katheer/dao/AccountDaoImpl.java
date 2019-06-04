package org.katheer.dao;

import org.katheer.dto.Account;
import org.katheer.mapper.AccountRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository("accountDao")
public class AccountDaoImpl implements AccountDao {
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;
    private Map<String, Object> params;
    private MapSqlParameterSource parameterSource;
    private String query;

    @Override
    public int createAccount(Account account) {
        try {
            query = "INSERT INTO account(name, mobile, email, branch, balance) VALUES(:name, :mobile, :email, :branch, :initialBalance)";

            parameterSource = new MapSqlParameterSource();
            parameterSource.addValue("name", account.getName());
            parameterSource.addValue("mobile", account.getMobile());
            parameterSource.addValue("email", account.getEmail());
            parameterSource.addValue("branch", account.getBranch());
            parameterSource.addValue("initialBalance", account.getBalance());

            jdbcTemplate.update(query, parameterSource);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }

        //returning account number to user
        query = "SELECT * FROM account WHERE name=:name AND mobile=:mobile " +
                "AND email=:email";

        parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("name", account.getName());
        parameterSource.addValue("mobile", account.getMobile());
        parameterSource.addValue("email", account.getEmail());
        return jdbcTemplate.query(query, parameterSource, new AccountRowMapper()).get(0).getAccNo();
    }

    @Override
    public Account getAccount(int accNo) {
        Account account = null;
        try {
            query = "SELECT * FROM account WHERE accNo=:accNo";

            parameterSource = new MapSqlParameterSource();
            parameterSource.addValue("accNo", accNo);

            List<Account> accounts = jdbcTemplate.query(query, parameterSource,
                    new AccountRowMapper());
            account = accounts.size() == 0 ? null : accounts.get(0);
        } catch (Exception e) {
            e.printStackTrace();
            return account;
        }
        return account;
    }

    @Override
    public double deposit(int accNo, double amount) {
        try {
            query = "UPDATE account SET balance=:newBalance WHERE accNo=:accNo";

            parameterSource = new MapSqlParameterSource();
            parameterSource.addValue("newBalance", amount);
            parameterSource.addValue("accNo", accNo);

            int rowCount = jdbcTemplate.update(query, parameterSource);

            if (rowCount == 1) {
                return amount;
            } else {
                return -1;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public double withdraw(int accNo, double amount) {
        try {
            query = "UPDATE account SET balance=:balance WHERE accNo=:accno";

            parameterSource = new MapSqlParameterSource();
            parameterSource.addValue("balance", amount);
            parameterSource.addValue("accno", accNo);

            int rowCount = jdbcTemplate.update(query, parameterSource);

            if (rowCount == 1) {
                return amount;
            } else {
                return -1;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
}
