package academy.devdojo.springboot2.requests;

import lombok.Data;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class AnimePostRequestBody {
    @NotEmpty(message = "The name cannot be empty or null")
    private String name;
    @URL(message = "The URL is not valid")
    private String url;
}
