package com.tenniscourts.guests;


import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
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
public class GuestControllerTest {

    private MockMvc mockMvc;
    @Mock
    GuestService guestService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final var guestController = new GuestController(guestService);
        Mockito.when(guestService.createGuest(ArgumentMatchers.any())).thenReturn(new GuestDTO(0L,"MockedGuest"));
        mockMvc = MockMvcBuilders.standaloneSetup(guestController).build();
    }

    @Test
    public void whenListingGuest_then200IsReturned() throws Exception {
        mockMvc.perform(get("/guest"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andDo(print());
    }

    @Test
    public void givenValidId_WhenGettingGuestById_then200IsReturned() throws Exception {
        mockMvc.perform(get("/guest/1"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void givenInvalidId_WhenGettingGuestById_then4XXIsReturned() throws Exception {
        mockMvc.perform(get("/guest/test"))
                .andExpect(status().is4xxClientError())
                .andDo(print());
    }

    @Test
    public void givenName_WhenGettingGuestByName_then200IsReturned() throws Exception {
        mockMvc.perform(get("/guest?name=test"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void givenPayload_WhenInsertingGuest_then201IsReturned() throws Exception {
        var json = new JSONObject("{\n" +
                "                \"id\": 0,\n" +
                "                \"name\": \"guest\"\n" +
                "  }");
        mockMvc.perform(post("/guest").contentType(MediaType.APPLICATION_JSON).content(json.toString()))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    public void givenNoPayload_WhenInsertingGuest_then4XXIsReturned() throws Exception {
        mockMvc.perform(post("/guest"))
                .andExpect(status().is4xxClientError())
                .andDo(print());
    }
    @Test
    public void givenValidId_WhenDeletingGuestById_then204IsReturned() throws Exception {
        mockMvc.perform(delete("/guest/1"))
                .andExpect(status().isNoContent())
                .andDo(print());
    }

    @Test
    public void givenInvalidId_WhenDeletingGuestById_then4XXIsReturned() throws Exception {
        mockMvc.perform(delete("/guest/test"))
                .andExpect(status().is4xxClientError())
                .andDo(print());
    }
    @Test
    public void givenNoId_WhenDeletingGuestById_then4XXIsReturned() throws Exception {
        mockMvc.perform(delete("/guest"))
                .andExpect(status().is4xxClientError())
                .andDo(print());
    }
}
