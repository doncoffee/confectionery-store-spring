package by.academy.http.controller;

import by.academy.entity.Role;
import by.academy.service.dto.SweetsDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static by.academy.util.Constants.*;
import static by.academy.util.Constants.SEARCH;
import static by.academy.util.MockUtil.createSweetsDTO;
import static by.academy.util.TestConstants.*;
import static by.academy.util.TestConstants.SEARCH_TEST_VALUE;
import static by.academy.util.TestConstants.USER;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
@WithMockUser(username = TEST_USERNAME, password = PASSWORD, authorities = {USER, ADMIN})
class SweetsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void contextLoads() {
        assertNotNull(mockMvc);
    }

    @Test
    void findAll() throws Exception {
        mockMvc.perform(get(API_SWEETS)
                        .with(user(TEST_USERNAME).authorities(Role.ADMIN)))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name(SWEETS_SWEETS))
                .andExpect(model().attributeExists(SWEETS))
                .andExpect(model().attributeExists(PAGE))
                .andExpect(model().attributeExists(SIZE));
    }

    @Test
    void create() throws Exception {
        SweetsDTO sweetsDTO = createSweetsDTO();
        mockMvc.perform(post(API_SWEETS + ADD_SWEETS)
                        .param(PAGE, TEST_PAGE_VALUE)
                        .param(SIZE, TEST_SIZE_VALUE)
                        .flashAttr(SWEETS_DTO, sweetsDTO)
                        .with(user(TEST_USERNAME).authorities(Role.ADMIN)))
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrlPattern(API_SWEETS + PATTERN_PATH)
                );
    }

    @Test
    void update() throws Exception {
        Long sweetsId = 1L;
        SweetsDTO sweetsDTO = createSweetsDTO();

        mockMvc.perform(post(API_SWEETS + ID_UPDATE, sweetsId)
                        .param(PAGE, TEST_PAGE_VALUE)
                        .param(SIZE, TEST_SIZE_VALUE)
                        .flashAttr(SWEETS_DTO, sweetsDTO)
                        .with(user(TEST_USERNAME).authorities(Role.ADMIN)))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern(API_SWEETS + PATTERN_PATH));
    }

    @Test
    void delete() throws Exception {
        Long sweetsId = 1L;
        String referer = API_SWEETS;
        mockMvc.perform(post(API_SWEETS + ID_DELETE, sweetsId)
                        .header(REFERER, referer)
                        .with(user(TEST_USERNAME).authorities(Role.ADMIN)))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(referer));
    }

    @Test
    void goToEditPage() throws Exception {
        Long sweetsId = 1L;

        mockMvc.perform(get(API_SWEETS + EDIT_SWEETS_ID, sweetsId)
                        .param(PAGE, TEST_PAGE_VALUE)
                        .param(SIZE, TEST_SIZE_VALUE)
                        .param(SEARCH, SEARCH_TEST_VALUE)
                        .with(user(TEST_USERNAME).authorities(Role.ADMIN)))
                .andExpect(status().isOk())
                .andExpect(view().name(SWEETS_EDIT_SWEETS))
                .andExpect(model().attribute(PAGE, 0))
                .andExpect(model().attribute(SIZE, 10))
                .andExpect(model().attribute(SEARCH, SEARCH_TEST_VALUE));
    }

    @Test
    void goToAddPage() throws Exception {
        mockMvc.perform(get(API_SWEETS + ADD_SWEETS)
                        .param(PAGE, TEST_PAGE_VALUE)
                        .param(SIZE, TEST_SIZE_VALUE)
                        .param(SEARCH, SEARCH_TEST_VALUE)
                        .with(user(TEST_USERNAME).authorities(Role.ADMIN)))
                .andExpect(status().isOk())
                .andExpect(view().name(SWEETS_ADD_SWEETS))
                .andExpect(model().attribute(PAGE, 0))
                .andExpect(model().attribute(SIZE, 10))
                .andExpect(model().attribute(SEARCH, SEARCH_TEST_VALUE));
    }
}