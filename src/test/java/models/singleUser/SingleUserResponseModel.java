package models.singleUser;

import lombok.Data;
import com.fasterxml.jackson.annotation.JsonProperty;

@Data
public class SingleUserResponseModel {

    private Data data;
    private Support support;

    @lombok.Data
    public static class Data {
        private Integer id;
        private String email;

        @JsonProperty("first_name")
        private String firstName;

        @JsonProperty("last_name")
        private String lastName;

        private String avatar;
    }

    @lombok.Data
    public static class Support {
        private String url;
        private String text;
    }
}