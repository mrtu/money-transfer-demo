package guru.mrtu.routes;

import com.fasterxml.jackson.databind.ObjectMapper;
import guru.mrtu.AccountsRepository;
import guru.mrtu.model.AccountCreationRequest;
import spark.Request;
import spark.Response;
import spark.Route;

import javax.xml.ws.http.HTTPException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.util.Objects.isNull;
import static org.eclipse.jetty.http.HttpStatus.BAD_REQUEST_400;
import static org.eclipse.jetty.http.HttpStatus.CREATED_201;

public final class AccountsController {

    private final static Logger LOGGER = Logger.getLogger(AccountsController.class.getName());

    public static class AccountCreationRoute implements Route {

        private static final ObjectMapper MAPPER = new ObjectMapper();

        @Override
        public Object handle(Request request, Response response) throws HTTPException {
            AccountCreationRequest accountCreationRequest = getValidated(request.bodyAsBytes());
            AccountsRepository.instance().createAccount(accountCreationRequest);
            response.status(CREATED_201);
            return accountCreationRequest.getIban();
        }

        private AccountCreationRequest getValidated(byte[] content) {
            try {
                AccountCreationRequest accountCreationRequest =
                        MAPPER.readValue(content, AccountCreationRequest.class);
                if (isNull(accountCreationRequest.getInitialBalance())
                        || isNull(accountCreationRequest.getIban())
                        || accountCreationRequest.getInitialBalance() < 0) {
                    LOGGER.log(Level.INFO, "Bad account creation request");
                    throw new HTTPException(BAD_REQUEST_400);
                } else {
                    return accountCreationRequest;
                }
            } catch (IOException e) {
                LOGGER.log(Level.INFO, "Deserialization fault", e);
                throw new HTTPException(BAD_REQUEST_400);
            }
        }

    }

    public static class AccountCheckRoute implements Route {

        @Override
        public Object handle(Request request, Response response) throws HTTPException {
            String iban = request.queryParams("iban");
            if (iban == null) {
                response.status(BAD_REQUEST_400);
                return "No iban provided";
            }
            response.type("application/json");
            return AccountsRepository.instance().getAccount(iban);
        }

    }

}
