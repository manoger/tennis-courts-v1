package com.tenniscourts.tenniscourts;


import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TennisCourtControllerTest {

    private MockMvc mockMvc;
    @Mock
    TennisCourtService tennisCourtService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final var tennisCourtController = new TennisCourtController(tennisCourtService);
        Mockito.when(tennisCourtService.addTennisCourt(ArgumentMatchers.any())).thenReturn(new TennisCourtDTO());
        mockMvc = MockMvcBuilders.standaloneSetup(tennisCourtController).build();
    }


    @Test
    public void givenValidId_WhenGettingTennisCourtById_then200IsReturned() throws Exception {
        mockMvc.perform(get("/tennis-court/1"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    //TODO: givenInvalidId_WhenGettingTennisCourtById_then4XXIsReturned

    //TODO: givenNoId_WhenGettingTennisCourtWithSchedulesById_then4XXIsReturned

    //TODO: givenInvalidId_WhenGettingTennisCourtWithSchedulesById_then4XXIsReturned

    @Test
    public void givenValidId_WhenGettingTennisCourtWithSchedulesById_then200IsReturned() throws Exception {
        mockMvc.perform(get("/tennis-court/schedule/1"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void givenPayload_WhenInsertingGuest_then201IsReturned() throws Exception {
        var json = new JSONObject("{\n" +
                "  \"id\": 0,\n" +
                "  \"name\": \"string\",\n" +
                "  \"tennisCourtSchedules\": [\n" +
                "    {\n" +
                "      \"endDateTime\": \"2021-09-12T23:50\",\n" +
                "      \"id\": 0,\n" +
                "      \"startDateTime\": \"2021-09-12T23:50\",\n" +
                "      \"tennisCourtId\": 1\n" +
                "    }\n" +
                "  ]\n" +
                "}");
        mockMvc.perform(post("/tennis-court").contentType(MediaType.APPLICATION_JSON).content(json.toString()))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    public void givenNoPayload_WhenInsertingGuest_then4XXIsReturned() throws Exception {
        mockMvc.perform(post("/tennis-court"))
                .andExpect(status().is4xxClientError())
                .andDo(print());
    }
}
