package ui;

public class TokenHostPort {
    private String token;
    private String host;
    private int port;

    public TokenHostPort(String token, String host, int port) {
        this.token = token;
        this.host = host;
        this.port = port;
    }

    public String getToken() {
        return token;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }
}
