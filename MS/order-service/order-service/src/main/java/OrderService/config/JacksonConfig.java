package OrderService.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JacksonConfig {
  /*  @Bean
    public Jackson2ObjectMapperBuilderCustomizer addCustomDateTimeSerialization() {
        return builder -> {
            // Configura el formato de fecha y hora
            builder.simpleDateFormat("yyyy-MM-dd HH:mm:ss");
            // Agrega el módulo JavaTime para manejar LocalDateTime
            builder.modules(new JavaTimeModule());
            // Desactiva la escritura de fechas como timestamps
            builder.featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        };
    }*/

}
