package com.openapi.nasa.integration;

import com.openapi.nasa.daorepo.NasaRepository;
import com.openapi.nasa.entity.NasaApod;
import com.openapi.nasa.repository.AbstractionBaseContainerTest;
import com.openapi.nasa.service.NasaApiService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class NasaApiControllerIT extends AbstractionBaseContainerTest{

    // injecting mockMvc,ObjectMapper,NasaApiRepository //
    private final MockMvc mockMvc;
    private final NasaRepository nasaRepository;
    private final NasaApiService nasaApiService;

    @Autowired
    public NasaApiControllerIT(MockMvc mockMvc,NasaApiService nasaApiService,NasaRepository nasaRepository) {
        this.mockMvc = mockMvc;
        this.nasaApiService=nasaApiService;
        this.nasaRepository = nasaRepository;
    }

    private NasaApod theNasaApod;
    @BeforeEach
    public void setup()
    {
        nasaRepository.deleteAll();
        theNasaApod=nasaApiService.getAstronomyPictureOfTheDay();
    }

    @Test
    @DisplayName("JUnit Test For /save-apod Controller Operation")
    @WithMockUser(username = "tester", authorities = {"ROLE_ADMIN", "ROLE_MANAGER", "ROLE_EMPLOYEE"})
    public void givenNasaApodObject_whenSave_thenReturnSavedApod() throws Exception
    {
        // given-> pre condition or setup //


        // when->action or task performed //
        ResultActions response=mockMvc.perform(MockMvcRequestBuilders.get("/api/save-apod")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(theNasaApod)));

        // then-> verify results or output //
        response.andDo(MockMvcResultHandlers.print());
        response.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("JUnit Test For /api/apod Controller")
    @WithMockUser(username = "tester", authorities = {"ROLE_ADMIN", "ROLE_MANAGER", "ROLE_EMPLOYEE"})
    public void givenNasaApodObject_whenGetAstronomyPictureOfTheDay_thenReturnApod() throws Exception
    {
        // given-> pre condition or setup //

        // when->action or task performed //
        ResultActions response=mockMvc.perform(MockMvcRequestBuilders.get("/api/apod")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(theNasaApod)));

        // then-> verify results or output //
        response.andDo(MockMvcResultHandlers.print());
        response.andExpect(MockMvcResultMatchers.status().isOk());
        response.andExpect(MockMvcResultMatchers.jsonPath("$.copyright", CoreMatchers.is(theNasaApod.getCopyright())));
        response.andExpect(MockMvcResultMatchers.jsonPath("$.date",CoreMatchers.is(theNasaApod.getDate())));
        response.andExpect(MockMvcResultMatchers.jsonPath("$.title",CoreMatchers.is(theNasaApod.getTitle())));
    }

    @Test
    @DisplayName("JUnit Test For /api/apods Controller Operation")
    @WithMockUser(username = "tester", authorities = {"ROLE_ADMIN", "ROLE_MANAGER", "ROLE_EMPLOYEE"})
    public void givenListOfNasaApods_whenGetAllApods_thenReturnListOfApods() throws Exception
    {
        // given-> precondition or setup //
        List<NasaApod> apods=new ArrayList<>();
        NasaApod theNasaApod2=NasaApod.builder().date("2024-12-12").copyright("Gagan").explanation("Some Random Sentence By Gagan GR").hdurl("https://www.nasa.gov/image-detail/simsbury-oli2-2022258-lrg/").title("Narendra Modi :)").url("https://www.nasa.gov/image-detail/simsbury-oli2-2022258-lrg/").build();
        apods.add(theNasaApod);
        apods.add(theNasaApod2);
        nasaRepository.saveAll(apods);

        // when-> task or action performed //
        ResultActions response=mockMvc.perform(MockMvcRequestBuilders.get("/api/apods")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(theNasaApod)));

        // then-> verify results or output //
        response.andDo(MockMvcResultHandlers.print());
        response.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("JUnit Test For api/apod/{apodId} GetApodById Controller Operation")
    @WithMockUser(username = "tester", authorities = {"ROLE_ADMIN", "ROLE_MANAGER", "ROLE_EMPLOYEE"})
    public void givenNasaApodObject_whenGetApodById_thenReturnFoundApod() throws Exception
    {
        //given-> precondition or setup //
        List<NasaApod> apods=new ArrayList<>();
        NasaApod theNasaApod2=NasaApod.builder().date("2024-12-12").copyright("Gagan").explanation("Some Random Sentence By Gagan GR").hdurl("https://www.nasa.gov/image-detail/simsbury-oli2-2022258-lrg/").title("Narendra Modi :)").url("https://www.nasa.gov/image-detail/simsbury-oli2-2022258-lrg/").build();
        apods.add(theNasaApod);
        apods.add(theNasaApod2);
        nasaRepository.saveAll(apods);
        // when-> task or action to be performed //
        // when-> task or action performed //
        ResultActions response=mockMvc.perform(MockMvcRequestBuilders.get("/api/apod/{apodId}",theNasaApod.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(theNasaApod)));

        // then-> verify results or output //
        response
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.copyright",CoreMatchers.is(theNasaApod.getCopyright())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title",CoreMatchers.is(theNasaApod.getTitle())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.date",CoreMatchers.is(theNasaApod.getDate())));
    }

    @Test
    @DisplayName("JUnit Test For UpdateApod Controller Operation")
    @WithMockUser(username = "tester", authorities = {"ROLE_ADMIN", "ROLE_MANAGER", "ROLE_EMPLOYEE"})
    public void givenNasaApodObject_whenupdateApodById_thenReturnUpdatedNasaApod() throws Exception
    {
        //given-> precondition or setup //
        List<NasaApod> apods=new ArrayList<>();
        NasaApod theNasaApod2=NasaApod.builder().date("2024-12-12").copyright("Gagan").explanation("Some Random Sentence By Gagan GR").hdurl("https://www.nasa.gov/image-detail/simsbury-oli2-2022258-lrg/").title("Narendra Modi :)").url("https://www.nasa.gov/image-detail/simsbury-oli2-2022258-lrg/").build();
        apods.add(theNasaApod);
        apods.add(theNasaApod2);

        theNasaApod.setCopyright("ISRO");
        theNasaApod.setTitle("Meteor Showers");
        theNasaApod.setDate("12-12-2054");

        nasaRepository.saveAll(apods);

        // when-> task or action to be performed //
        ResultActions response=mockMvc.perform(MockMvcRequestBuilders.put("/api/apod/{apodId}",theNasaApod.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(theNasaApod)));
        String expectedOutPut="Updated Nasa Apod Id: "+theNasaApod.getId();

        // then->verify results or output //
        response
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());

    }
    @Test
    @DisplayName("JUnit Test For /api/apod/{apodId} DeleteApodById Controller Operation")
    @WithMockUser(username = "tester", authorities = {"ROLE_ADMIN", "ROLE_MANAGER", "ROLE_EMPLOYEE"})
    public void givenNasaApodObject_whenDeleteApodById_thenReturn200Ok()throws Exception
    {
        // given-> precondition or setup //
        //given-> precondition or setup //
        List<NasaApod> apods=new ArrayList<>();
        NasaApod theNasaApod2=NasaApod.builder().date("2024-12-12").copyright("Gagan").explanation("Some Random Sentence By Gagan GR").hdurl("https://www.nasa.gov/image-detail/simsbury-oli2-2022258-lrg/").title("Narendra Modi :)").url("https://www.nasa.gov/image-detail/simsbury-oli2-2022258-lrg/").build();
        apods.add(theNasaApod);
        apods.add(theNasaApod2);
        nasaRepository.saveAll(apods);
        // when-> task or action to be performed //
        ResultActions response=mockMvc.perform(MockMvcRequestBuilders.delete("/api/apod/{apodId}",theNasaApod2.getId()));

        // verify results or output //
        response
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
