package com.openapi.nasa.repository;

import com.openapi.nasa.daorepo.NasaRepository;
import com.openapi.nasa.entity.NasaApod;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.NONE)
public class NasaRepositoryUnitTests extends AbstractionBaseContainerTest {
    // injecting repository layer //
    private final NasaRepository nasaRepository;

    @Autowired
    public NasaRepositoryUnitTests(NasaRepository nasaRepository) {
        this.nasaRepository = nasaRepository;
    }

    // setup //
    @BeforeEach
    public void setup()
    {
        nasaRepository.deleteAll();
    }

    @Test
    @DisplayName("JUnit Test For Save Nasa Apod Operation")
    public void givenNasaApodObject_whenSaveNasaApod_thenReturnSavedApod()
    {
        // given-> precondition or setup //
        NasaApod theNasaApod=NasaApod.builder()
                .copyright("Varun G Ravi")
                .date("12-12-1997")
                .explanation("This Is Just A Random Sentence")
                .hdurl("https://www.nasa.gov/image-detail/simsbury-oli2-2022258-lrg/")
                .title("Demo Title")
                .url("https://www.nasa.gov/image-detail/simsbury-oli2-2022258-lrg/")
                .build();

        // when-> task or action to  be performed //
        NasaApod savedApod=nasaRepository.save(theNasaApod);

        // verify-> results or output //
       assertThat(savedApod).isNotNull();
       assertThat(savedApod.getCopyright()).isEqualTo("Varun G Ravi");
    }

    @Test
    @DisplayName("JUnit Test For Delete Nasa Apod Operation")
    public void givenNasaApodObject_whenDeleteNasaApod_thenReturnNothing()
    {
        // given-> precondition or setup //
        // given-> precondition or setup //
        NasaApod theNasaApod=NasaApod.builder()
                .copyright("Varun G Ravi")
                .date("12-12-1997")
                .explanation("This Is Just A Random Sentence")
                .hdurl("https://www.nasa.gov/image-detail/simsbury-oli2-2022258-lrg/")
                .title("Demo Title")
                .url("https://www.nasa.gov/image-detail/simsbury-oli2-2022258-lrg/")
                .build();

        // when-> task or action to  be performed //
        nasaRepository.delete(theNasaApod);
        Optional<NasaApod> deletedApod=nasaRepository.findById(theNasaApod.getId());

        // verify-> results or output //
        assertThat(deletedApod).isEmpty();
    }

    @Test
    @DisplayName("JUnit Test For FindById Apod Operation")
    public void givenNasaApod_whenFindById_thenReturnFoundNasaApod()
    {
        // given-> precondition or setup //
        // given-> precondition or setup //
        NasaApod theNasaApod=NasaApod.builder()
                .copyright("Varun G Ravi")
                .date("12-12-1997")
                .explanation("This Is Just A Random Sentence")
                .hdurl("https://www.nasa.gov/image-detail/simsbury-oli2-2022258-lrg/")
                .title("Demo Title")
                .url("https://www.nasa.gov/image-detail/simsbury-oli2-2022258-lrg/")
                .build();

        // when-> task or action to  be performed //
        nasaRepository.save(theNasaApod);
        Optional<NasaApod> foundNasaApod=nasaRepository.findById(theNasaApod.getId());

        // verify-> results or output //
        assertThat(foundNasaApod).isNotEmpty();
        assertThat(foundNasaApod.get().getCopyright()).isEqualTo("Varun G Ravi");
    }

    @Test
    @DisplayName("JUnit Test For FindAll Operation")
    public void givenNasaApod_whenFindAll_thenReturnListOfApods()
    {
        // given-> precondition or setup//
        // adding multiple nasa apods in the directory //
        // given-> precondition or setup //
        NasaApod theNasaApod=NasaApod.builder()
                .copyright("Varun G Ravi")
                .date("12-12-1997")
                .explanation("This Is Just A Random Sentence")
                .hdurl("https://www.nasa.gov/image-detail/simsbury-oli2-2022258-lrg/")
                .title("Demo Title")
                .url("https://www.nasa.gov/image-detail/simsbury-oli2-2022258-lrg/")
                .build();

        NasaApod theNasaApod2=NasaApod.builder()
                .copyright("Gagan G Ravi")
                .date("07-25-1994")
                .explanation("This Is Just Another Random Sentence")
                .hdurl("https://www.nasa.gov/image-detail/simsbury-oli2-2022258-lrg/")
                .title("Demo Title")
                .url("https://www.nasa.gov/image-detail/simsbury-oli2-2022258-lrg/")
                .build();
        // when-> task or action performed //
        nasaRepository.save(theNasaApod);
        nasaRepository.save(theNasaApod2);
        List<NasaApod> nasaApods=nasaRepository.findAll();

        // then-> verify results or actions //
        assertThat(nasaApods.size()).isEqualTo(2);
        assertThat(nasaApods).isNotEmpty();
    }

    @Test
    @DisplayName("JUnit Test For Update NasaApod Operation")
    public void givenNasaApodObject_whenUpdateApod_thenReturnUpdatedApod()
    {
        // given-> precondition or setup//
        NasaApod theNasaApod=NasaApod.builder()
                .copyright("Varun G Ravi")
                .date("12-12-1997")
                .explanation("This Is Just A Random Sentence")
                .hdurl("https://www.nasa.gov/image-detail/simsbury-oli2-2022258-lrg/")
                .title("Demo Title")
                .url("https://www.nasa.gov/image-detail/simsbury-oli2-2022258-lrg/")
                .build();
        nasaRepository.save(theNasaApod);

        // when-> action or task performed //
        theNasaApod.setCopyright("Hailyes Comet");
        theNasaApod.setDate("09-09-1443");
        nasaRepository.save(theNasaApod);

        // then->verify results or output //
        assertThat(theNasaApod.getCopyright()).isEqualTo("Hailyes Comet");
        assertThat(theNasaApod.getDate()).isEqualTo("09-09-1443");

    }

    @Test
    @DisplayName("JUnit Test For FindByDateNamedParametersJPQL Operation")
    public void givenNasaApodObjects_whenFindByDate_thenReturnListOfNasaApod()
    {
        NasaApod theNasaApod=NasaApod.builder()
                .copyright("Varun G Ravi")
                .date("12-12-1997")
                .explanation("This Is Just A Random Sentence")
                .hdurl("https://www.nasa.gov/image-detail/simsbury-oli2-2022258-lrg/")
                .title("Demo Title")
                .url("https://www.nasa.gov/image-detail/simsbury-oli2-2022258-lrg/")
                .build();

        NasaApod theNasaApod2=NasaApod.builder()
                .copyright("Gagan G Ravi")
                .date("12-12-1997")
                .explanation("This Is Just Another Random Sentence")
                .hdurl("https://www.nasa.gov/image-detail/simsbury-oli2-2022258-lrg/")
                .title("Demo Title")
                .url("https://www.nasa.gov/image-detail/simsbury-oli2-2022258-lrg/")
                .build();
        // when-> task or action performed //
        nasaRepository.save(theNasaApod);
        nasaRepository.save(theNasaApod2);
        List<NasaApod> foundApodsByDate=nasaRepository.findByDate("12-12-1997");

        // then->verify results or output //
        assertThat(foundApodsByDate.size()).isEqualTo(2);
        assertThat(foundApodsByDate).isNotEmpty();
    }

    @Test
    @DisplayName("JUnit Test For FindByCopyrightNamedParams Operation")
    public void givenNasaApodObject_whenFindByCopyright_thenReturnFoundObject()
    {
        NasaApod theNasaApod=NasaApod.builder()
                .copyright("Varun G Ravi")
                .date("12-12-1997")
                .explanation("This Is Just A Random Sentence")
                .hdurl("https://www.nasa.gov/image-detail/simsbury-oli2-2022258-lrg/")
                .title("Demo Title")
                .url("https://www.nasa.gov/image-detail/simsbury-oli2-2022258-lrg/")
                .build();

        // when-> task or action performed //
        nasaRepository.save(theNasaApod);
        NasaApod foundApod=nasaRepository.findApodByCopyright("Varun G Ravi");

        // verify->results or outputs //
        assertThat(foundApod.getCopyright()).isEqualTo("Varun G Ravi");
    }
}
