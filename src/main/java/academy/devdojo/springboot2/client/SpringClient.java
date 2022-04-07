package academy.devdojo.springboot2.client;

import academy.devdojo.springboot2.domain.Anime;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Log4j2
public class SpringClient {
    public static void main(String[] args) {
        ResponseEntity<Anime> entity = new RestTemplate().getForEntity("http://localhost:8080/animes/{id}",
                Anime.class, 11);
        log.info(entity);

        Anime object = new RestTemplate().getForObject("http://localhost:8080/animes/{id}", Anime.class,
                11);
        log.info(object);

        Anime[] animes = new RestTemplate().getForObject("http://localhost:8080/animes/all", Anime[].class);
        log.info(Arrays.toString(animes));

        ResponseEntity<List<Anime>> exchange = new RestTemplate().exchange("http://localhost:8080/animes/all",
                HttpMethod.GET, null, new ParameterizedTypeReference<>() {
                });
        log.info(exchange.getBody());

//        Anime cachorroQuente = Anime.builder().name("Cachorro quente").build();
//        Anime cachorroQuenteSaved = new RestTemplate().postForObject("http://localhost:8080/animes/", cachorroQuente,
//        Anime.class);
//        log.info("Saved Anime {}", cachorroQuenteSaved);

        Anime samuraiJack = Anime.builder().name("Samurai Jack").build();
        ResponseEntity<Anime> samuraiJackSaved = new RestTemplate().exchange("http://localhost:8080/animes/", HttpMethod.POST, new HttpEntity<>(samuraiJack, createJsonHeader()),
                Anime.class);
        log.info("Saved Anime {}", samuraiJackSaved);


        Anime animeToBeUpdated = samuraiJackSaved.getBody();
        animeToBeUpdated.setName("Samurai Jack 2");
        ResponseEntity<Void> samuraiJackUpdated = new RestTemplate().exchange("http://localhost:8080/animes/", HttpMethod.PUT, new HttpEntity<>(animeToBeUpdated, createJsonHeader()),
                Void.class);
        log.info(samuraiJackUpdated);


        ResponseEntity<Void> samuraiJackToBeDeleted = new RestTemplate().exchange("http://localhost:8080/animes/{id}", HttpMethod.DELETE, null,
                Void.class, animeToBeUpdated.getId());
        log.info(samuraiJackToBeDeleted);

    }

    private static HttpHeaders createJsonHeader() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return httpHeaders;


    }
}
