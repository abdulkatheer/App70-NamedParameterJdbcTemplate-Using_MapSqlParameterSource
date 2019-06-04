package org.katheer.mapper;

import org.katheer.dto.Account;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountRowMapper implements RowMapper<Account> {
    @Override
    public Account mapRow(ResultSet rs, int rowNum) throws SQLException {
        Account account = new Account();
        account.setAccNo(rs.getInt("accNo"));
        account.setName(rs.getString("name"));
        account.setMobile(rs.getString("mobile"));
        account.setEmail(rs.getString("email"));
        account.setBalance(rs.getDouble("balance"));
        account.setBranch(rs.getString("branch"));

        return account;
    }
}
