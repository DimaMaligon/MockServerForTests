package utils;

import okhttp3.mockwebserver.Dispatcher;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.RecordedRequest;

import java.util.Objects;

public class SimpleDispatcher extends Dispatcher {
    @Override
    public MockResponse dispatch(RecordedRequest request) {
        try {
            if (request.getPath().contains("get")) {
                System.out.println("GET Response");
                return new MockResponse().setResponseCode(200).setBody("{ \"message\": \"It was a GET request\" }");
            }

            else if (request.getMethod().equals("POST")) {
                System.out.println("POST Response");
                return new MockResponse().setResponseCode(200).setBody("{ \"message\": \"It was a POST request\" }");
            }
            return new MockResponse().setResponseCode(404);
        } catch (Exception e) {
            throw new NullPointerException("The path or method are empty");
        }
    }
}
