package api;

import dto.AccountRequest;
import dto.AccountTransferRequest;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("v1/accounts")
@Produces(MediaType.APPLICATION_JSON)
public interface AccountApi {
    @GET
    @Path("{number}")
    Response getAccount(@PathParam("number") String number);

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    Response addAccount(AccountRequest request);

    @POST
    @Path("transfer")
    @Consumes(MediaType.APPLICATION_JSON)
    Response transfer(AccountTransferRequest request);
}
