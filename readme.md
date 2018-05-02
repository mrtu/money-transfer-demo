### Money tranfer demo
#### Project structure
Two modules:
* money-transfer-app, containing the app components
* money-transfer-integration-test, containing some tests for testing the overall functionality of the application

#### The App
The app provides two basic REST HTTP endpoints:
* `/account`, allowing the creation of new account and checking of the account balance and transactions
* `/transaction`, allowing to make transactions (referenced by an unique UUID) between two accounts identified by a IBAN (for the demo purpose is an UUID)

The app runs with in-memory datastructures and allow concurrent operations for both endpoints.

#### Running and Testing the app
The project is a maven project so if you execute `mvn clean package` on the root of the project you should see in the target folders the jars:
* `money-transfer-app-1.0-jar-with-dependencies.jar`, corresponding to the fat jar of the application, which you can
 execute with the command `java -jar money-transfer-app-1.0-jar-with-dependencies.jar [PORT]` (the default port is `4567`)
* `money-transfer-integration-test-1.0-jar-with-dependencies.jar`, corresponding to the fat jar of the integration tests, which you can
 execute with the command `java -jar money-transfer-integration-test-1.0-jar-with-dependencies.jar [HOST] [PORT]` (the default host is `localhost` and port is `4567`)

If you prefer to skip this part the jars themselves are also in the repository (`jars` dir)

#### The operations
The server supports:
* `POST /account`, the body should in JSON format following the structure
```json
{
  "balance": 100.0
}
```
in which `100.0` is the initial value you can specify, the return body is a quoted string with the account IBAN (JSON UUID).
* `GET /account?iban=[IBAN]`, allows to obtain the account details including balance and transaction history
* `POST /transaction`, allows the creation of a new transaction between two accounts, the body should be json following this structure:
```json
{
  "origin": "ORIGIN_IBAN",
  "destiny": "DESTINY_IBAN",
  "amount": AMOUNT_TO_TRANSFER
}
```