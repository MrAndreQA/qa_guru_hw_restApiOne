package configs;

import lombok.Getter;
import org.aeonbits.owner.ConfigFactory;

public class ApiProvider {
    @Getter
    private static final ApiConfig apiConfig =
            ConfigFactory.create(
                    ApiConfig.class,
                    System.getProperties()
            );
}