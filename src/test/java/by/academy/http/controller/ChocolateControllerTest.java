package by.academy.http.controller;

import by.academy.entity.Role;
import by.academy.service.dto.ChocolateDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static by.academy.util.Constants.*;
import static by.academy.util.MockUtil.createChocolateDTO;
import static by.academy.util.TestConstants.USER;
import static by.academy.util.TestConstants.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
@WithMockUser(username = TEST_USERNAME, password = PASSWORD, authorities = {USER, ADMIN})
class ChocolateControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void contextLoads() {
        assertNotNull(mockMvc);
    }

    @Test
    void findAll() throws Exception {
        mockMvc.perform(get(API_CHOCOLATES)
                        .with(user(TEST_USERNAME).authorities(Role.ADMIN)))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name(CHOCOLATE_CHOCOLATES))
                .andExpect(model().attributeExists(CHOCOLATES))
                .andExpect(model().attributeExists(PAGE))
                .andExpect(model().attributeExists(SIZE));
    }

    @Test
    void create() throws Exception {
        ChocolateDTO chocolateDTO = createChocolateDTO();
        mockMvc.perform(post(API_CHOCOLATES + ADD_CHOCOLATE)
                        .param(PAGE, TEST_PAGE_VALUE)
                        .param(SIZE, TEST_SIZE_VALUE)
                        .flashAttr(CHOCOLATE_DTO, chocolateDTO)
                        .with(user(TEST_USERNAME).authorities(Role.ADMIN)))
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrlPattern(API_CHOCOLATES + PATTERN_PATH)
                );
    }

    @Test
    void update() throws Exception {
        Long chocolateId = 1L;
        ChocolateDTO chocolateDTO = createChocolateDTO();

        mockMvc.perform(post(API_CHOCOLATES + ID_UPDATE, chocolateId)
                        .param(PAGE, TEST_PAGE_VALUE)
                        .param(SIZE, TEST_SIZE_VALUE)
                        .flashAttr(CHOCOLATE_DTO, chocolateDTO)
                        .with(user(TEST_USERNAME).authorities(Role.ADMIN)))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern(API_CHOCOLATES + PATTERN_PATH));
    }

    @Test
    void delete() throws Exception {
        Long chocolateId = 1L;
        String referer = API_CHOCOLATES;
        mockMvc.perform(post(API_CHOCOLATES + ID_DELETE, chocolateId)
                        .header(REFERER, referer)
                        .with(user(TEST_USERNAME).authorities(Role.ADMIN)))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(referer));
    }

    @Test
    void goToEditPage() throws Exception {
        Long chocolateId = 1L;

        mockMvc.perform(get(API_CHOCOLATES + EDIT_CHOCOLATE_ID, chocolateId)
                        .param(PAGE, TEST_PAGE_VALUE)
                        .param(SIZE, TEST_SIZE_VALUE)
                        .param(SEARCH, SEARCH_TEST_VALUE)
                        .with(user(TEST_USERNAME).authorities(Role.ADMIN)))
                .andExpect(status().isOk())
                .andExpect(view().name(CHOCOLATE_EDIT_CHOCOLATE))
                .andExpect(model().attribute(PAGE, 0))
                .andExpect(model().attribute(SIZE, 10))
                .andExpect(model().attribute(SEARCH, SEARCH_TEST_VALUE));
    }

    @Test
    void goToAddPage() throws Exception {
        mockMvc.perform(get(API_CHOCOLATES + ADD_CHOCOLATE)
                        .param(PAGE, TEST_PAGE_VALUE)
                        .param(SIZE, TEST_SIZE_VALUE)
                        .param(SEARCH, SEARCH_TEST_VALUE)
                        .with(user(TEST_USERNAME).authorities(Role.ADMIN)))
                .andExpect(status().isOk())
                .andExpect(view().name(CHOCOLATE_ADD_CHOCOLATE))
                .andExpect(model().attribute(PAGE, 0))
                .andExpect(model().attribute(SIZE, 10))
                .andExpect(model().attribute(SEARCH, SEARCH_TEST_VALUE));
    }
}