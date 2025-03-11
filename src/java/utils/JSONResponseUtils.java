package utils;

import java.io.IOException;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletResponse;

public class JSONResponseUtils {
    private static class ResponseObject {
        @JsonProperty private String message;
        @JsonProperty private int status;
        @JsonProperty private Object data;

        public ResponseObject(String message, int status, Object data) {
            this.message = message;
            this.status = status;
            this.data = data;
        }
        
    }

    public static void sendJsonResponse(HttpServletResponse response, String message, int status, Object data) 
        throws IOException {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            ResponseObject resObj = new ResponseObject(message, status, data);

            ObjectMapper objectMapper = new ObjectMapper();
            String jsonResponse = objectMapper.writeValueAsString(resObj);

            response.getWriter().write(jsonResponse);
    }

}
