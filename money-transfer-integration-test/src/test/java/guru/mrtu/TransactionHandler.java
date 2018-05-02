package guru.mrtu;

import com.fasterxml.jackson.databind.ObjectMapper;
import guru.mrtu.model.TransactionCreationRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.fluent.Request;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import static org.apache.http.entity.ContentType.APPLICATION_JSON;

class TransactionHandler {

    private final ObjectMapper MAPPER = new ObjectMapper();

    private final URI uriResource;

    TransactionHandler(String host, int port) {
        try {
            this.uriResource = new URI("http", null, host, port, "/transaction", null, null);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    boolean applyTransaction(float amount, String from, String to) throws IOException {
        HttpResponse response =
                Request.Post(uriResource)
                        .bodyString(MAPPER.writeValueAsString(new TransactionCreationRequest(from, to, amount)), APPLICATION_JSON)
                        .execute()
                        .returnResponse();
        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode == HttpStatus.SC_CREATED) {
            return true;
        } else if (statusCode == HttpStatus.SC_FORBIDDEN) {
            return false;
        } else {
            throw new TestFailedException("Unable to perform transaction", statusCode);
        }
    }


}
