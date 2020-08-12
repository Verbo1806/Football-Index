package bg.verbo.footind.web.error;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResponseErrorHandler;

public class RestTemplateResponseErrorHandler implements ResponseErrorHandler {

  @Override
  public boolean hasError(ClientHttpResponse httpResponse) throws IOException {
	  HttpStatus status = httpResponse.getStatusCode();
      return status.is4xxClientError() || status.is5xxServerError();
  }

  @Override
  public void handleError(ClientHttpResponse httpResponse) throws IOException {

      if (httpResponse.getStatusCode()
        .series() == HttpStatus.Series.CLIENT_ERROR) {

    	  if (httpResponse.getRawStatusCode() == 401) {
    		 throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED, "Bad credentials!");
    	  } else {
    		  throw new HttpClientErrorException(httpResponse.getStatusCode(), "Client error!");
    	  }
    	  
      } else if (httpResponse.getStatusCode()
        .series() == HttpStatus.Series.SERVER_ERROR) {
    	  throw new HttpClientErrorException(httpResponse.getStatusCode(), "Server error!");
      }
  }
}
