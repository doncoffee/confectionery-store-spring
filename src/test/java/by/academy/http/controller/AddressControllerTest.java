package by.academy.http.controller;

import by.academy.entity.Role;
import by.academy.service.dto.AddressDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static by.academy.util.Constants.*;
import static by.academy.util.MockUtil.createAddressDTO;
import static by.academy.util.TestConstants.*;
import static by.academy.util.TestConstants.USER;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
@WithMockUser(username = TEST_USERNAME, password = PASSWORD, authorities = {USER, ADMIN})
class AddressControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void contextLoads() {
        assertNotNull(mockMvc);
    }

    @Test
    void findAll() throws Exception {
        mockMvc.perform(get(API_ADDRESSES)
                        .with(user(TEST_USERNAME).authorities(Role.ADMIN)))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name(ADDRESS_ADDRESSES))
                .andExpect(model().attributeExists(ADDRESSES))
                .andExpect(model().attributeExists(PAGE))
                .andExpect(model().attributeExists(SIZE));
    }

    @Test
    void create() throws Exception {
        AddressDTO addressDTO = createAddressDTO();
        mockMvc.perform(post(API_ADDRESSES + ADD_ADDRESS)
                        .param(PAGE, TEST_PAGE_VALUE)
                        .param(SIZE, TEST_SIZE_VALUE)
                        .flashAttr(ADDRESS_DTO, addressDTO)
                        .with(user(TEST_USERNAME).authorities(Role.ADMIN)))
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrlPattern(API_ADDRESSES + PATTERN_PATH)
                );
    }

    @Test
    void update() throws Exception {
        Long addressId = 1L;
        AddressDTO addressDTO = createAddressDTO();

        mockMvc.perform(post(API_ADDRESSES + ID_UPDATE, addressId)
                        .param(PAGE, TEST_PAGE_VALUE)
                        .param(SIZE, TEST_SIZE_VALUE)
                        .flashAttr(ADDRESS_DTO, addressDTO)
                        .with(user(TEST_USERNAME).authorities(Role.ADMIN)))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern(API_ADDRESSES + PATTERN_PATH));
    }

    @Test
    void delete() throws Exception {
        Long addressId = 1L;
        String referer = API_ADDRESSES;
        mockMvc.perform(post(API_ADDRESSES + ID_DELETE, addressId)
                        .header(REFERER, referer)
                        .with(user(TEST_USERNAME).authorities(Role.ADMIN)))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(referer));
    }

    @Test
    void goToEditPage() throws Exception {
        Long addressId = 1L;

        mockMvc.perform(get(API_ADDRESSES + EDIT_ADDRESS_ID, addressId)
                        .param(PAGE, TEST_PAGE_VALUE)
                        .param(SIZE, TEST_SIZE_VALUE)
                        .param(SEARCH, SEARCH_TEST_VALUE)
                        .with(user(TEST_USERNAME).authorities(Role.ADMIN)))
                .andExpect(status().isOk())
                .andExpect(view().name(ADDRESS_EDIT_ADDRESS))
                .andExpect(model().attribute(PAGE, 0))
                .andExpect(model().attribute(SIZE, 10))
                .andExpect(model().attribute(SEARCH, SEARCH_TEST_VALUE));
    }

    @Test
    void goToAddPage() throws Exception {
        mockMvc.perform(get(API_ADDRESSES + ADD_ADDRESS)
                        .param(PAGE, TEST_PAGE_VALUE)
                        .param(SIZE, TEST_SIZE_VALUE)
                        .param(SEARCH, SEARCH_TEST_VALUE)
                        .with(user(TEST_USERNAME).authorities(Role.ADMIN)))
                .andExpect(status().isOk())
                .andExpect(view().name(ADDRESS_ADD_ADDRESS))
                .andExpect(model().attribute(PAGE, 0))
                .andExpect(model().attribute(SIZE, 10))
                .andExpect(model().attribute(SEARCH, SEARCH_TEST_VALUE));
    }
}