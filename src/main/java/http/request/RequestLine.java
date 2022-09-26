package http.request;

public class RequestLine {

    /**
     * The request line has the following syntax:
     * request-method-name request-URI HTTP-version
     * ex) GET /test.html HTTP/1.1
     *
     * GET 요청 메세지에 query string
     */

    private HttpMethod method;
    private String path;
    private String queryString;

    public RequestLine(String requestLine) {
        String[] tokens = requestLine.split(" ");
        this.method = HttpMethod.valueOf(tokens[0]);

        String[] url = tokens[1].split("\\?");
        this.path = url[0];

        // query string
        if (url.length == 2) {
            this.queryString = url[1];
        }
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public String getQueryString() {
        return queryString;
    }
}
