package guru.mrtu.routes;

import com.fasterxml.jackson.databind.ObjectMapper;
import guru.mrtu.ConcurrentTransactionManager;
import guru.mrtu.exceptions.InvalidTransactionRequestException;
import guru.mrtu.model.TransactionRequest;
import spark.Request;
import spark.Response;
import spark.Route;

import java.io.IOException;

import static java.util.Objects.isNull;
import static org.eclipse.jetty.http.HttpStatus.CREATED_201;

public class TransactionController {

    public static class TransactionCreationRoute implements Route {

        private static final ObjectMapper MAPPER = new ObjectMapper();

        private static final ConcurrentTransactionManager transactionManager = ConcurrentTransactionManager.instance();

        @Override
        public Object handle(Request request, Response response) {
            TransactionRequest transactionRequest = getValidated(request.bodyAsBytes());
            transactionManager.transfer(
                    transactionRequest.getIbanOrigin(),
                    transactionRequest.getIbanDestiny(),
                    transactionRequest.getAmount());
            response.status(CREATED_201);
            return "Transaction completed successfully";
        }

        private TransactionRequest getValidated(byte[] content) {
            try {
                TransactionRequest transactionRequest =
                        MAPPER.readValue(content, TransactionRequest.class);
                if (isNull(transactionRequest.getIbanOrigin())
                        || isNull(transactionRequest.getIbanDestiny())
                        || isNull(transactionRequest.getAmount())
                        || transactionRequest.getAmount() <= 0
                        || transactionRequest.getIbanOrigin().equalsIgnoreCase(transactionRequest.getIbanDestiny())) {
                    throw new InvalidTransactionRequestException(transactionRequest);
                } else {
                    return transactionRequest;
                }
            } catch (IOException e) {
                throw new InvalidTransactionRequestException();
            }
        }

    }

}
