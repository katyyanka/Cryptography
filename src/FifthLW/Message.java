package FifthLW;

import java.math.BigInteger;

public class Message {
    private String message;
    private BigInteger key;

    public Message(String message, BigInteger key) {
        this.message = message;
        this.key = key;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public BigInteger getKey() {
        return key;
    }

    public void setKey(BigInteger key) {
        this.key = key;
    }
}
