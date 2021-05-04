package api.model;

import com.fasterxml.jackson.core.JsonParseException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.net.http.HttpResponse;
import java.util.function.Supplier;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonBodyHandler<T> implements HttpResponse.BodyHandler<Supplier<T>> {

	
  //an object of class objectMapper	
  private static final ObjectMapper om = new ObjectMapper();
  
 
  //variables
  private final Class<T> targetClass;

  
  //constractor
  public JsonBodyHandler(Class<T> targetClass) {
      this.targetClass = targetClass;
  }

  
  @Override
  public HttpResponse.BodySubscriber<Supplier<T>> apply(HttpResponse.ResponseInfo responseInfo) {
      return asJSON(this.targetClass);
  }


  public static <W> HttpResponse.BodySubscriber<Supplier<W>> asJSON(Class<W> targetType) {
      HttpResponse.BodySubscriber<InputStream> upstream = HttpResponse.BodySubscribers.ofInputStream();
      return HttpResponse.BodySubscribers.mapping(upstream, inputStream -> toSupplierOfType(inputStream, targetType));
  }


  public static <W> Supplier<W> toSupplierOfType(InputStream inputStream, Class<W> targetType) {
      return () -> {
          try (InputStream stream = inputStream){ObjectMapper objectMapper = new ObjectMapper();
              return objectMapper.readValue(stream, targetType);
          }catch (IOException e){throw new UncheckedIOException(e);}
      };
  }
  
}//finClasse 
