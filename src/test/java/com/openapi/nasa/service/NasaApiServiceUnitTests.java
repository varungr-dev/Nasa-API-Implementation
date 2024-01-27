package com.openapi.nasa.service;

import com.openapi.nasa.daorepo.NasaRepository;
import com.openapi.nasa.entity.NasaApod;
import static org.assertj.core.api.Assertions.assertThat;

import com.openapi.nasa.exceptionHandler.NasaNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class NasaApiServiceUnitTests {
    // mocking dependency for Nasa Repository //
    @Mock
    private NasaRepository nasaRepository;

    // mocking dependency for Nasa Api Service //
    @InjectMocks
    private NasaApiServiceImpl nasaApiServiceImpl;

    private NasaApod theNasaApod;
    @BeforeEach
    public void setup()
    {
        nasaRepository.deleteAll();

                 theNasaApod=NasaApod.builder()
                .copyright("Varun G Ravi")
                .date("12-12-1997")
                .explanation("This Is Just A Random Sentence")
                .hdurl("https://www.nasa.gov/image-detail/simsbury-oli2-2022258-lrg/")
                .title("Demo Title")
                .url("https://www.nasa.gov/image-detail/simsbury-oli2-2022258-lrg/")
                .build();
    }

    @Test
    @DisplayName("JUnit Test For SaveApodService Service Operation")
    public void givenNasaApodObject_whenSaveNasaApod_thenReturnSavedNasaApod()
    {
        // given-> pre-condition or setup //

        // stubbing methods //
        given(nasaRepository.save(theNasaApod)).willReturn(theNasaApod);

        // when->action or task to be performed //
        nasaApiServiceImpl.save(theNasaApod);

        // then-> verify results or actions //
        assertThat(theNasaApod).isNotNull();
        assertThat(theNasaApod.getCopyright()).isEqualTo("Varun G Ravi");
    }

    @Test
    @DisplayName(("JUnit Test For FetchAllApods Service Operation Positive Scenario"))
    public void givenNasaApodObjects_whenFetchAllApods_thenReturnListOfApods()
    {
        // given-> pre-condition or setup //
        // adding another apod object //

        NasaApod theNasaApod2= theNasaApod=NasaApod.builder()
                .copyright("Gagan G Ravi")
                .date("12-12-1998")
                .explanation("This Is Just A New Sentence")
                .hdurl("https://www.nasa.gov/image-detail/simsbury-oli2-2022258-lrg/")
                .title("Demo Title")
                .url("https://www.nasa.gov/image-detail/simsbury-oli2-2022258-lrg/")
                .build();

        // stubbing methods //
        given(nasaRepository.findAll()).willReturn(List.of(theNasaApod,theNasaApod2));
        // when-> task or action to be performed //
        List<NasaApod> foundApods=nasaApiServiceImpl.fetchAllApods();

        // then->verify results or actions //
        assertThat(foundApods.size()).isEqualTo(2);
        assertThat(foundApods).isNotEmpty();
    }

    @Test
    @DisplayName("JUnit Test For FetchAllApods Negative Scenario")
    public void givenEmptyApodObjects_whenFetchAllApods_throwException()
    {
        // given-> pre-condition or setup //

        // stubbing methods //
        // creating empty list //
        List<NasaApod> apods=new ArrayList<>();
        given(nasaRepository.findAll()).willReturn(apods);
        // when-> task or action to be performed //

        org.junit.jupiter.api.Assertions.assertThrows(NasaNotFoundException.class,()->
        {
            nasaApiServiceImpl.fetchAllApods();
        });

        // verify results or output //
        assertThat(apods).isEmpty();
    }

    @Test
    @DisplayName("JUnit Test For FindNasaApodById Service Operation Positive Scenario")
    public void givenNasaObject_whenFindNasaApodById_thenReturnFoundNasaApod()
    {
        // given-> precondition or setup already provided //
        // stubbing methods //
        given(nasaRepository.findById(theNasaApod.getId())).willReturn(Optional.of(theNasaApod));

        // when-> action or task to be performed //
        NasaApod foundNasaApod=nasaApiServiceImpl.findNasaApodById(theNasaApod.getId());

        // then-> verify results or output //
        assertThat(foundNasaApod).isNotNull();
        assertThat(foundNasaApod.getCopyright()).isEqualTo("Varun G Ravi");
    }
    @Test
    @DisplayName("JUnit Test For FindNasaApodById Service Operation Negative Scenario")
    public void givenEmptyNasaObject_whenFindByNasaApodById_thenThrowException()
    {
        // stubbing methods //
        given(nasaRepository.findById(420)).willReturn(Optional.empty());

        // when-> task or action to be performed //
        org.junit.jupiter.api.Assertions.assertThrows(NasaNotFoundException.class,()->
        {
           nasaApiServiceImpl.findNasaApodById(420);
        });

        // verify results or outputs //
        verify(nasaRepository,never()).save(any(NasaApod.class));
    }

    @Test
    @DisplayName("JUnit Test For DeleteNasaApodById Positive Scenario")
    public void givenNasaApodId_whenDeleteNasaApodById_thenReturnNothing()
    {
        // given-> precondition or setup//
        int id=theNasaApod.getId();
        // stubbing methods//
       given(nasaRepository.findById(id)).willReturn(Optional.of(theNasaApod));

        // when-> task or action to be performed //
        nasaApiServiceImpl.deleteNasaApodById(id);

        // then->verify results or actions //
        verify(nasaRepository, times(1)).deleteById(id);
    }

    @Test
    @DisplayName("JUnit Test For DeleteNasaApodById Service Operation Negative Scenario")
    public void givenEmptyNasaApodObject_whenDeleteById_thenThrowException() {
        // given-> precondition or setup //
        int id = theNasaApod.getId();

        // stubbing methods //
        given(nasaRepository.findById(id)).willReturn(Optional.empty());

        // when-> task or action to be performed //
        org.junit.jupiter.api.Assertions.assertThrows(NasaNotFoundException.class, () -> {
            nasaApiServiceImpl.deleteNasaApodById(id);
        });

        // verify results or output //
        verify(nasaRepository,never()).save(any(NasaApod.class));
    }
}
