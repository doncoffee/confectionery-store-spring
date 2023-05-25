package by.academy.util;

import by.academy.entity.Role;
import by.academy.service.dto.*;

import java.time.LocalDate;

import static by.academy.util.TestConstants.*;

public class MockUtil {

    public static AddressDTO createAddressDTO() {
        return AddressDTO.builder()
                .name(TEST_ADDRESS_NAME)
                .build();
    }

    public static BrandDTO createBrandDTO() {
        return BrandDTO.builder()
                .name(TEST_BRAND_NAME)
                .build();
    }

    public static ChocolateDTO createChocolateDTO() {
        return ChocolateDTO.builder()
                .price(CHOCOLATE_PRICE)
                .type(CHOCOLATE_TYPE)
                .weight(CHOCOLATE_WEIGHT)
                .composition(CHOCOLATE_COMPOSITION)
                .brandId(1L)
                .storeId(1L)
                .supplierId(1L)
                .build();
    }
    public static CookieDTO createCookieDTO() {
        return CookieDTO.builder()
                .price(COOKIE_PRICE)
                .type(COOKIE_TYPE)
                .weight(COOKIE_WEIGHT)
                .composition(COOKIE_COMPOSITION)
                .brandId(1L)
                .storeId(1L)
                .supplierId(1L)
                .build();
    }
    public static SweetsDTO createSweetsDTO() {
        return SweetsDTO.builder()
                .price(SWEETS_PRICE)
                .type(SWEETS_TYPE)
                .weight(SWEETS_WEIGHT)
                .composition(SWEETS_COMPOSITION)
                .brandId(1L)
                .storeId(1L)
                .supplierId(1L)
                .build();
    }
    public static PhoneNumberDTO createPhoneNumberDTO() {
        return PhoneNumberDTO.builder()
                .number(NUMBER)
                .build();
    }
    public static StoreDTO createStoreDTO() {
        return StoreDTO.builder()
                .addressId(1L)
                .phoneNumberId(1L)
                .build();
    }
    public static SupplierDTO createSupplierDTO() {
        return SupplierDTO.builder()
                .name(SUPPLIER_TEST_NAME)
                .contactPerson(CONTACT_PERSON)
                .addressId(1L)
                .phoneNumberId(1L)
                .build();
    }

    public static UserDTO createUserDTO() {
        return UserDTO.builder()
                .username(USERNAME)
                .rawPassword(RAW_PASSWORD)
                .role(Role.USER)
                .firstname(FIRSTNAME)
                .lastname(LASTNAME)
                .birthDate(LocalDate.of(2003, 2, 14))
                .build();
    }
}
