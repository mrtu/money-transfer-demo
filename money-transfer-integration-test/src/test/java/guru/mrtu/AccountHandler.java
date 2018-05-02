package guru.mrtu;

import com.fasterxml.jackson.databind.ObjectMapper;
import guru.mrtu.model.AccountCreationRequest;
import guru.mrtu.model.AccountInfo;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.utils.URIBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import static org.apache.http.entity.ContentType.APPLICATION_JSON;

public class AccountHandler {

    private final ObjectMapper MAPPER = new ObjectMapper();

    private final URI uriResource;

    AccountHandler(String host, int port) {
        try {
            this.uriResource = new URI("http", null, host, port, "/account", null, null);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    String createAccount(float amount) throws IOException {
        HttpResponse response =
                Request.Post(uriResource)
                        .bodyString(MAPPER.writeValueAsString(new AccountCreationRequest(amount)), APPLICATION_JSON)
                        .execute()
                        .returnResponse();
        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode != HttpStatus.SC_CREATED) {
            throw new TestFailedException("Unable to create account", statusCode);
        }
        return MAPPER.readValue(response.getEntity().getContent(), String.class);
    }

    AccountInfo getAccount(String iban) throws IOException, URISyntaxException {
        HttpResponse response =
                Request.Get(new URIBuilder(uriResource).setParameter("iban", iban).build())
                        .execute()
                        .returnResponse();
        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode != HttpStatus.SC_OK) {
            throw new TestFailedException("Unable to get account info", statusCode);
        }
        return MAPPER.readValue(response.getEntity().getContent(), AccountInfo.class);
    }

}
