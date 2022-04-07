package academy.devdojo.springboot2.repository;

import academy.devdojo.springboot2.controller.AnimeController;
import academy.devdojo.springboot2.domain.Anime;
import academy.devdojo.springboot2.util.AnimeCreator;
import lombok.extern.log4j.Log4j2;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;


import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Optional;

import static academy.devdojo.springboot2.util.AnimeCreator.createAnimeToBeSaved;
import static org.assertj.core.api.Assertions.*;


@DataJpaTest
@Log4j2
@DisplayName("Tests for Anime Repository")
class AnimeRepositoryTest {
    @Autowired
    private AnimeRepository animeRepository;

    @Test
    @DisplayName("Save persists anime when Successful")
    void save_PersistAnime_WhenSuccessful() {
        Anime animeToBeSaved = AnimeCreator.createAnimeToBeSaved();
        Anime animeSaved = this.animeRepository.save(animeToBeSaved);
        //Assertion é verificação
        Assertions.assertThat(animeSaved).isNotNull();
        Assertions.assertThat(animeSaved.getId()).isNotNull();
        Assertions.assertThat(animeSaved.getName()).isEqualTo(animeToBeSaved.getName());

    }

    @Test
    @DisplayName("Save updates anime when Successful)")
    void save_UpdatesAnime_WhenSuccessful() {
        Anime animeToBeSaved = AnimeCreator.createAnimeToBeSaved();
        Anime animeSaved = this.animeRepository.save(animeToBeSaved);
        log.info(animeSaved.getName());

        animeSaved.setName("Anime atualizado");
        Anime animeUpdated = this.animeRepository.save(animeSaved);
        log.info(animeUpdated.getName());

        Assertions.assertThat(animeUpdated).isNotNull();
        Assertions.assertThat(animeUpdated.getName()).isNotEmpty();
        Assertions.assertThat(animeUpdated.getId()).isNotNull();
        Assertions.assertThat(animeUpdated.getName()).isEqualTo(animeUpdated.getName());

    }

    @Test
    @DisplayName("Delete removes anime when Successful)")
    void delete_RemovesAnime_WhenSuccessful() {
        Anime animeToBeSaved = AnimeCreator.createAnimeToBeSaved(); //Cria um construtor de Anime
        Anime animeSaved = this.animeRepository.save(animeToBeSaved);

        this.animeRepository.delete(animeSaved);

        Optional<Anime> animeOptional = this.animeRepository.findById(animeSaved.getId());

        Assertions.assertThat(animeOptional).isEmpty();
        log.info(animeSaved);

    }

    @Test
    @DisplayName("Find by name returns anime when Successful)")
    void findByName_ReturnsListOfAnime_WhenSuccessful() {
        Anime animeToBeSaved = AnimeCreator.createAnimeToBeSaved(); //Cria um construtor de Anime
        Anime animeSaved = this.animeRepository.save(animeToBeSaved);

        //String name = animeSaved.getName();

        List<Anime> animes = this.animeRepository.findByName(animeSaved.getName());

        Assertions.assertThat(animes).isNotEmpty().contains(animeSaved);

        Assertions.assertThat(animes).contains(animeSaved);
    }

    @Test
    @DisplayName("Find by name returns empty list when no anime is found)")
    void findByName_ReturnsEmptyListOfAnime_WhenNoAnimeIsNotFound() {
        List<Anime> animes = this.animeRepository.findByName("Teste 1");

        Assertions.assertThat(animes).isEmpty();
    }



//    @Test
//    @DisplayName("Save throw ConstraintViolationException when name is empty")
//    void save_ThrowsConstraintViolationException_WhenNameIsEmpty() {
//        Anime anime = new Anime();
//        assertThatThrownBy(() -> this.animeRepository.save(anime))
//                .isInstanceOf(ConstraintViolationException.class);
//
//    }


}