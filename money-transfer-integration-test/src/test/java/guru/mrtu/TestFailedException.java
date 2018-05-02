package guru.mrtu;

class TestFailedException extends RuntimeException {

    private final int status;

    TestFailedException(String reason, int status) {
        super(reason);
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}
