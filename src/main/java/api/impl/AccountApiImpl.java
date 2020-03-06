package api.impl;

import api.AccountApi;
import dto.AccountResponse;
import lombok.RequiredArgsConstructor;
import model.Account;
import service.AccountService;

import javax.ws.rs.core.Response;

@RequiredArgsConstructor
public class AccountApiImpl implements AccountApi {
    private final AccountService accountService;

    @Override
    public Response getAccount(String number) {
        Account account = accountService.getAccount(number);
        if (account == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Account not found")
                    .build();
        }
        return Response.ok(
                new AccountResponse()
                        .id(account.getId())
                        .number(account.getNumber())
                        .balance(account.getBalance()))
                .build();
    }
}
