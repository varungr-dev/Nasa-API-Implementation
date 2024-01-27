package com.openapi.nasa.controller;

import com.openapi.nasa.entity.NasaApod;
import com.openapi.nasa.rest.NasaApiController;
import com.openapi.nasa.service.NasaApiService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

@WebMvcTest(controllers = NasaApiController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
public class NasaApiControllerUnitTests {

    // injecting mockMvc and ObjectMapper for Deserialization //
    private MockMvc mockMvc;

    @Autowired
    public NasaApiControllerUnitTests(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }
    // mocking dependencies for employeeService //
    @MockBean
    private NasaApiService nasaApiService;

    private NasaApod theNasaApod;
    @BeforeEach
    public void setup()
    {
        nasaApiService.deleteAllApods();
        theNasaApod= NasaApod.builder()
                .copyright("Varun G Ravi")
                .date("12-12-1997")
                .explanation("This Is Just A Random Sentence")
                .hdurl("https://www.nasa.gov/image-detail/simsbury-oli2-2022258-lrg/")
                .title("Demo Title")
                .url("https://www.nasa.gov/image-detail/simsbury-oli2-2022258-lrg/")
                .build();
    }

    @Test
    @DisplayName("JUnit Test For /save-apod Controller Operation")
    public void givenNasaApodObject_whenSave_thenReturnSavedApod() throws Exception
    {
        // given-> pre condition or setup //
        // stubbing methods //
        given(nasaApiService.getAstronomyPictureOfTheDay()).willReturn(theNasaApod);

        // when->action or task performed //
        ResultActions response=mockMvc.perform(MockMvcRequestBuilders.get("/api/save-apod")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(theNasaApod)));

        // then-> verify results or output //
        String expectedString = "Successfully Saved\nTitle: " + theNasaApod.getTitle() + "\nDate: " + theNasaApod.getDate();
        response.andDo(MockMvcResultHandlers.print());
        response.andExpect(MockMvcResultMatchers.status().isOk());
        response.andExpect(MockMvcResultMatchers.content().string(expectedString));

        // Verify that the getAstronomyPictureOfTheDay method is called
        Mockito.verify(nasaApiService, Mockito.times(1)).getAstronomyPictureOfTheDay();

        // Verify that the save method is called with the expectedNasaApod
        Mockito.verify(nasaApiService, Mockito.times(1)).save(theNasaApod);

    }

    @Test
    @DisplayName("JUnit Test For /api/apod Controller")
    public void givenNasaApodObject_whenGetAstronomyPictureOfTheDay_thenReturnApod() throws Exception
    {
        // given-> pre condition or setup //
        // stubbing methods //
        given(nasaApiService.getAstronomyPictureOfTheDay()).willReturn(theNasaApod);

        // when->action or task performed //
        ResultActions response=mockMvc.perform(MockMvcRequestBuilders.get("/api/apod")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(theNasaApod)));

        // then-> verify results or output //
        response.andDo(MockMvcResultHandlers.print());
        response.andExpect(MockMvcResultMatchers.status().isOk());
        response.andExpect(MockMvcResultMatchers.jsonPath("$.copyright",CoreMatchers.is(theNasaApod.getCopyright())));
        response.andExpect(MockMvcResultMatchers.jsonPath("$.date",CoreMatchers.is(theNasaApod.getDate())));
        response.andExpect(MockMvcResultMatchers.jsonPath("$.title",CoreMatchers.is(theNasaApod.getTitle())));

        // Verify that the getAstronomyPictureOfTheDay method is called
        Mockito.verify(nasaApiService, Mockito.times(1)).getAstronomyPictureOfTheDay();
    }

    @Test
    @DisplayName("JUnit Test For /api/apods Controller Operation")
    public void givenListOfNasaApods_whenGetAllApods_thenReturnListOfApods() throws Exception
    {
        // given-> precondition or setup //
        List<NasaApod> nasaApods=new ArrayList<>();
        nasaApods.add(theNasaApod);
        nasaApods.add(NasaApod.builder().copyright("Gagan").explanation("Some Random Sentence By Gagan GR").hdurl("https://www.nasa.gov/image-detail/simsbury-oli2-2022258-lrg/").title("Narendra Modi :)").url("https://www.nasa.gov/image-detail/simsbury-oli2-2022258-lrg/").build());
        // stubbing methods //
        given(nasaApiService.fetchAllApods()).willReturn(nasaApods);

        // when-> task or action performed //
        ResultActions response=mockMvc.perform(MockMvcRequestBuilders.get("/api/apods"));

        // then-> verify results or output //
        response.andDo(MockMvcResultHandlers.print());
        response.andExpect(MockMvcResultMatchers.status().isOk());
        response.andExpect(MockMvcResultMatchers.jsonPath("$.size()",CoreMatchers.is(nasaApods.size())));
    }

    @Test
    @DisplayName("JUnit Test For api/apod/{apodId} GetApodById Controller Operation")
    public void givenNasaApodObject_whenGetApodById_thenReturnFoundApod() throws Exception
    {
        //given-> precondition or setup //
        // stubbing methods //
        given(nasaApiService.findNasaApodById(theNasaApod.getId())).willReturn(theNasaApod);

        // when-> task or action to be performed //
        ResultActions response=mockMvc.perform(MockMvcRequestBuilders.get("/api/apod/{apodId}",theNasaApod.getId()));

        // then-> verify results or output //
        response
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.copyright",CoreMatchers.is(theNasaApod.getCopyright())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title",CoreMatchers.is(theNasaApod.getTitle())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.date",CoreMatchers.is(theNasaApod.getDate())));
    }

    @Test
    @DisplayName("JUnit Test For /api/apod/{apodId} DeleteApodById Controller Operation")
    public void givenNasaApodObject_whenDeleteApodById_thenReturn200Ok()throws Exception
    {
        // given-> precondition or setup //
        // stubbing methods //
        willDoNothing().given(nasaApiService).deleteNasaApodById(theNasaApod.getId());

        // when-> task or action to be performed //
        ResultActions response=mockMvc.perform(MockMvcRequestBuilders.delete("/api/apod/{apodId}",theNasaApod.getId()));

        // verify results or output //
        response
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("JUnit Test For UpdateApod Controller Operation")
    public void givenNasaApodObject_whenupdateApodById_thenReturnUpdatedNasaApod() throws Exception
    {
        // given-> pre condition or setup //

        // stubbing methods //
        given(nasaApiService.findNasaApodById(theNasaApod.getId())).willReturn(theNasaApod);
        theNasaApod.setCopyright("ISRO");
        theNasaApod.setTitle("Meteor Showers");
        theNasaApod.setDate("12-12-2054");

        willDoNothing().given(nasaApiService).save(theNasaApod);

        // when-> task or action to be performed //
        ResultActions response=mockMvc.perform(MockMvcRequestBuilders.put("/api/apod/{apodId}",theNasaApod.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(theNasaApod)));

        // then->verify results or output //
        response
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());

    }



}
