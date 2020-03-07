package api.impl;

import api.AccountApi;
import common.BadRequestException;
import dto.AccountRequest;
import dto.AccountResponse;
import dto.AccountTransferRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import model.Account;
import service.AccountService;

import javax.ws.rs.core.Response;

@Slf4j
@RequiredArgsConstructor
public class AccountApiImpl implements AccountApi {
    private final AccountService accountService;

    @Override
    public Response getAccount(String number) {
        log.debug("getAccount: {}", number);
        Account account = accountService.get(number);
        if (account == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Account " + number + " not found")
                    .build();
        }
        return Response.ok(
                new AccountResponse()
                        .setId(account.getId())
                        .setNumber(account.getNumber())
                        .setBalance(account.getBalance()))
                .build();
    }

    @Override
    public Response addAccount(AccountRequest request) {
        log.debug("addAccount: {}", request);
        Account account = accountService.save(new Account(request.getNumber(), request.getBalance()));
        return Response.ok(
                new AccountResponse()
                        .setId(account.getId())
                        .setNumber(account.getNumber())
                        .setBalance(account.getBalance()))
                .build();
    }

    @Override
    public Response transfer(AccountTransferRequest request) {
        log.debug("transfer: {}", request);
        try {
            accountService.transfer(request.getSrcNumber(), request.getDestNumber(), request.getSum());
        } catch (BadRequestException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage())
                    .build();
        }
        return Response.ok("OK").build();
    }
}
