package guru.mrtu;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.MoreObjects;
import guru.mrtu.exceptions.InsufficientFundsException;
import guru.mrtu.exceptions.InvalidTransactionRequestException;
import guru.mrtu.exceptions.NonexistentAccountException;
import guru.mrtu.routes.AccountsController.AccountCheckRoute;
import guru.mrtu.routes.AccountsController.AccountCreationRoute;
import guru.mrtu.routes.TransactionController.TransactionCreationRoute;
import spark.Response;

import javax.xml.ws.http.HTTPException;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.google.common.base.MoreObjects.firstNonNull;
import static java.util.logging.Level.INFO;
import static org.eclipse.jetty.http.HttpStatus.BAD_REQUEST_400;
import static org.eclipse.jetty.http.HttpStatus.FORBIDDEN_403;
import static spark.Spark.*;

public class Application {

    private final static Logger LOGGER = Logger.getLogger(Application.class.getName());

    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static void main(String[] args) {
        post("/transaction", "application/json", new TransactionCreationRoute(), MAPPER::writeValueAsString);
        post("/account", "application/json", new AccountCreationRoute(), MAPPER::writeValueAsString);
        get("/account", "application/json", new AccountCheckRoute(), MAPPER::writeValueAsString);

        exception(InsufficientFundsException.class,
                (exception, request, response) -> setHandler(exception, response, FORBIDDEN_403, INFO));
        exception(NonexistentAccountException.class,
                (exception, request, response) -> setHandler(exception, response, BAD_REQUEST_400, INFO));
        exception(InvalidTransactionRequestException.class,
                (exception, request, response) -> setHandler(exception, response, BAD_REQUEST_400, INFO));
        exception(HTTPException.class,
                (exception, request, response) -> setHandler(exception, response, exception.getStatusCode(), INFO));

    }

    private static void setHandler(Exception exception, Response response, int statusCode, Level level) {
        LOGGER.log(level, exception::getMessage);
        response.status(statusCode);
        response.body(firstNonNull(exception.getMessage(), "N/A"));
    }

}
