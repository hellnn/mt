package api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("v1/account")
@Produces(MediaType.APPLICATION_JSON)
public interface AccountApi {

    @GET
    @Path("{number}")
    Response getAccount(@PathParam("number") String number);

}
