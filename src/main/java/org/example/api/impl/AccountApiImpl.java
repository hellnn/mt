package api.impl;

import api.AccountApi;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.common.BadRequestException;
import org.example.dto.AccountRequest;
import org.example.dto.AccountResponse;
import org.example.dto.AccountTransferRequest;
import org.example.model.Account;
import org.example.service.AccountService;

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
        if (request.getNumber() == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Account number must be given")
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
    public Response transfer(AccountTransferRequest request) {
        log.debug("transfer: {}", request);
        if (request == null || request.getSrcNumber() == null || request.getDestNumber() == null || request.getSum() == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Transfer details must be given")
                    .build();
        }
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
