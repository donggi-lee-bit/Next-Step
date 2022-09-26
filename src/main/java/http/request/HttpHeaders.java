package http.request;

import java.util.HashMap;
import java.util.Map;

public class HttpHeaders {

    private static final String CONTENT_LENGTH = "Content-Length";

    private Map<String, String> headers = new HashMap<>();

    public void add(String header) {
        String[] splitHeaders = header.split(":");
        headers.put(splitHeaders[0], splitHeaders[1].trim());
    }

    public String getHeader(String name) {
        return headers.get(name);
    }

    public int getContentLength() {
        return getIntHeader(CONTENT_LENGTH);
    }

    private int getIntHeader(String name) {
        String header = getHeader(name);
        return header == null ? 0 : Integer.parseInt(header);
    }
}
