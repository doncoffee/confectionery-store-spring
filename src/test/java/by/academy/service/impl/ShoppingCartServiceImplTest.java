package by.academy.service.impl;

import by.academy.entity.ShoppingCart;
import by.academy.service.ChocolateService;
import by.academy.service.CookieService;
import by.academy.service.ShoppingCartService;
import by.academy.service.SweetsService;
import by.academy.service.dto.ChocolateDTO;
import by.academy.service.dto.CookieDTO;
import by.academy.service.dto.SweetsDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static by.academy.util.MockUtil.*;
import static by.academy.util.TestConstants.SESSION_ID;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ShoppingCartServiceImplTest {

    private final ShoppingCartService shoppingCartService;
    private final ChocolateService chocolateService;
    private final CookieService cookieService;
    private final SweetsService sweetsService;

    @Autowired
    public ShoppingCartServiceImplTest(ShoppingCartService shoppingCartService,
                                       ChocolateService chocolateService, CookieService cookieService, SweetsService sweetsService) {
        this.shoppingCartService = shoppingCartService;
        this.chocolateService = chocolateService;
        this.cookieService = cookieService;
        this.sweetsService = sweetsService;
    }

    @Test
    void getOrCreateCart() {
        ShoppingCart result = shoppingCartService.getOrCreateCart(SESSION_ID);

        assertNotNull(result);
        assertNotNull(result.getId()); // Assuming the ID is generated during creation
    }

    @Test
    void addChocolateToCart() {
        ChocolateDTO chocolateDTO = createChocolateDTO();
        ChocolateDTO createdChocolateDTO = chocolateService.createChocolate(chocolateDTO);
        ShoppingCart shoppingCart = shoppingCartService.getOrCreateCart(SESSION_ID);

        shoppingCartService.addChocolateToCart(shoppingCart.getId(), createdChocolateDTO.getId(), 3);

        assertEquals(shoppingCart.getItems().size(), 1);
        assertEquals(shoppingCart.getItems().get(0).getQuantity(), 3);
        assertNull(shoppingCart.getItems().get(0).getCookie());
        assertNull(shoppingCart.getItems().get(0).getSweets());
        assertEquals(shoppingCart.getItems().get(0).getChocolate().getPrice(),
                createdChocolateDTO.getPrice());
    }

    @Test
    void addCookieToCart() {
        CookieDTO cookieDTO = createCookieDTO();
        CookieDTO createdCookieDTO = cookieService.createCookie(cookieDTO);
        ShoppingCart shoppingCart = shoppingCartService.getOrCreateCart(SESSION_ID);

        shoppingCartService.addCookieToCart(shoppingCart.getId(), createdCookieDTO.getId(), 1);

        assertEquals(shoppingCart.getItems().size(), 1);
        assertEquals(shoppingCart.getItems().get(0).getQuantity(), 1);
        assertNull(shoppingCart.getItems().get(0).getChocolate());
        assertNull(shoppingCart.getItems().get(0).getSweets());
        assertEquals(shoppingCart.getItems().get(0).getCookie().getPrice(),
                createdCookieDTO.getPrice());
    }

    @Test
    void addSweetsToCart() {
        SweetsDTO sweetsDTO = createSweetsDTO();
        SweetsDTO createdSweetsDTO = sweetsService.createSweets(sweetsDTO);
        ShoppingCart shoppingCart = shoppingCartService.getOrCreateCart(SESSION_ID);

        shoppingCartService.addSweetsToCart(shoppingCart.getId(), createdSweetsDTO.getId(), 5);

        assertEquals(shoppingCart.getItems().size(), 1);
        assertEquals(shoppingCart.getItems().get(0).getQuantity(), 5);
        assertNull(shoppingCart.getItems().get(0).getChocolate());
        assertNull(shoppingCart.getItems().get(0).getCookie());
        assertEquals(shoppingCart.getItems().get(0).getSweets().getPrice(),
                createdSweetsDTO.getPrice());
    }

    @Test
    void updateItemQuantity() {
        CookieDTO cookieDTO = createCookieDTO();
        CookieDTO createdCookieDTO = cookieService.createCookie(cookieDTO);
        ShoppingCart shoppingCart = shoppingCartService.getOrCreateCart(SESSION_ID);

        shoppingCartService.addCookieToCart(shoppingCart.getId(), createdCookieDTO.getId(), 1);

        assertEquals(shoppingCart.getItems().get(0).getQuantity(), 1);

        shoppingCartService.updateItemQuantity(shoppingCart.getId(), shoppingCart.getItems().get(0).getId(), 3);

        assertEquals(shoppingCart.getItems().get(0).getQuantity(), 3);
    }

    @Test
    void removeItemFromCart() {
        CookieDTO cookieDTO = createCookieDTO();
        CookieDTO createdCookieDTO = cookieService.createCookie(cookieDTO);
        ShoppingCart shoppingCart = shoppingCartService.getOrCreateCart(SESSION_ID);

        shoppingCartService.addCookieToCart(shoppingCart.getId(), createdCookieDTO.getId(), 1);

        assertEquals(shoppingCart.getItems().size(), 1);

        shoppingCartService.removeItemFromCart(shoppingCart.getId(), shoppingCart.getItems().get(0).getId());

        assertEquals(shoppingCart.getItems().size(), 0);
    }

    @Test
    void clearCart() {
        CookieDTO cookieDTO = createCookieDTO();
        CookieDTO createdCookieDTO = cookieService.createCookie(cookieDTO);
        SweetsDTO sweetsDTO = createSweetsDTO();
        SweetsDTO createdSweetsDTO = sweetsService.createSweets(sweetsDTO);
        ShoppingCart shoppingCart = shoppingCartService.getOrCreateCart(SESSION_ID);

        shoppingCartService.addCookieToCart(shoppingCart.getId(), createdCookieDTO.getId(), 1);
        shoppingCartService.addSweetsToCart(shoppingCart.getId(), createdSweetsDTO.getId(), 5);

        assertEquals(shoppingCart.getItems().size(), 2);

        shoppingCartService.clearCart(shoppingCart.getId());

        assertEquals(shoppingCart.getItems().size(), 0);
    }

    @Test
    void countTotalPrice() {
        ChocolateDTO chocolateDTO = createChocolateDTO();
        ChocolateDTO createdChocolateDTO = chocolateService.createChocolate(chocolateDTO);
        CookieDTO cookieDTO = createCookieDTO();
        CookieDTO createdCookieDTO = cookieService.createCookie(cookieDTO);
        SweetsDTO sweetsDTO = createSweetsDTO();
        SweetsDTO createdSweetsDTO = sweetsService.createSweets(sweetsDTO);
        ShoppingCart shoppingCart = shoppingCartService.getOrCreateCart(SESSION_ID);

        shoppingCartService.addChocolateToCart(shoppingCart.getId(), createdChocolateDTO.getId(), 1);
        shoppingCartService.addCookieToCart(shoppingCart.getId(), createdCookieDTO.getId(), 1);
        shoppingCartService.addSweetsToCart(shoppingCart.getId(), createdSweetsDTO.getId(), 1);
        Double totalPrice = shoppingCartService.countTotalPrice(shoppingCart.getItems());

        assertNotNull(totalPrice);
        assertEquals(totalPrice, createdChocolateDTO.getPrice() +
                createdCookieDTO.getPrice() +
                createdSweetsDTO.getPrice());
    }
}