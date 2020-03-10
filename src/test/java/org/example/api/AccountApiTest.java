package api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.example.dto.AccountRequest;
import org.example.dto.AccountTransferRequest;
import org.example.model.Account;
import org.junit.Test;

import java.io.IOException;
import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

//Need run main application before tests
public class AccountApiTest {
    public static final String BASE_URL = "http://localhost:8080/v1/accounts";

    private CloseableHttpClient client = HttpClients.createDefault();
    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void addAccount() throws IOException {
        HttpPost post = new HttpPost(BASE_URL);
        post.setHeader("Content-Type", "application/json");
        post.setEntity(new StringEntity(objectMapper.writeValueAsString(new AccountRequest("111", new BigDecimal("20.00")))));
        HttpResponse response = client.execute(post);
        assertEquals(200, response.getStatusLine().getStatusCode());

        Account account = objectMapper.readValue(response.getEntity().getContent(), Account.class);
        assertNotNull(account.getId());
        assertEquals("111", account.getNumber());
        assertEquals(new BigDecimal("20.00"), account.getBalance());
    }

    @Test
    public void transfer() throws IOException {
        HttpPost post = new HttpPost(BASE_URL + "/transfer");
        post.setHeader("Content-Type", "application/json");
        post.setEntity(new StringEntity(objectMapper.writeValueAsString(new AccountTransferRequest("123", "321", new BigDecimal(10)))));
        HttpResponse response = client.execute(post);
        assertEquals(200, response.getStatusLine().getStatusCode());

        HttpGet get = new HttpGet(BASE_URL + "/123");
        response = client.execute(get);
        assertEquals(200, response.getStatusLine().getStatusCode());

        Account account = objectMapper.readValue(response.getEntity().getContent(), Account.class);
        assertNotNull(account.getId());
        assertEquals("123", account.getNumber());
        assertEquals(new BigDecimal("0.00"), account.getBalance());

        get = new HttpGet(BASE_URL + "/321");
        response = client.execute(get);
        assertEquals(200, response.getStatusLine().getStatusCode());

        account = objectMapper.readValue(response.getEntity().getContent(), Account.class);
        assertNotNull(account.getId());
        assertEquals("321", account.getNumber());
        assertEquals(new BigDecimal("10.00"), account.getBalance());

        post = new HttpPost(BASE_URL + "/transfer");
        post.setHeader("Content-Type", "application/json");
        post.setEntity(new StringEntity(objectMapper.writeValueAsString(new AccountTransferRequest("123", "321", new BigDecimal(10)))));
        response = client.execute(post);
        assertEquals(400, response.getStatusLine().getStatusCode());
    }

    @Test
    public void transferWithInvalidParams() throws IOException {
        HttpPost post = new HttpPost(BASE_URL + "/transfer");
        post.setHeader("Content-Type", "application/json");
        HttpResponse response = client.execute(post);
        assertEquals(400, response.getStatusLine().getStatusCode());
    }

}