package configs;

import org.aeonbits.owner.Config;

@Config.Sources("classpath:config.properties")
public interface ApiConfig extends Config {

    @Key("baseUri")
    String baseApiUri();

    @Key("basePath")
    String basePath();

    @Key("apiKeyParam")
    String apiKeyParam();

    @Key("apiKeyValue")
    String apiKeyValue();

    @Key("registerEndpoint")
    String registerEndpoint();

    @Key("usersEndpoint")
    String usersEndpoint();

    @Key("userEmail")
    @DefaultValue("${userEmail}")
    String userEmail();

    @Key("userPassword")
    @DefaultValue("${userPassword}")
    String userPassword();
}