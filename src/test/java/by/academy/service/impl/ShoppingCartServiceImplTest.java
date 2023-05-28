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

        assertEquals(1, shoppingCart.getItems().size());
        assertEquals(3, shoppingCart.getItems().get(0).getQuantity());
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

        assertEquals(1, shoppingCart.getItems().size());
        assertEquals(1, shoppingCart.getItems().get(0).getQuantity());
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

        assertEquals(1, shoppingCart.getItems().size());
        assertEquals(5, shoppingCart.getItems().get(0).getQuantity());
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

        assertEquals(1, shoppingCart.getItems().get(0).getQuantity());

        shoppingCartService.updateItemQuantity(shoppingCart.getId(), shoppingCart.getItems().get(0).getId(), 3);

        assertEquals(3, shoppingCart.getItems().get(0).getQuantity());
    }

    @Test
    void removeItemFromCart() {
        CookieDTO cookieDTO = createCookieDTO();
        CookieDTO createdCookieDTO = cookieService.createCookie(cookieDTO);
        ShoppingCart shoppingCart = shoppingCartService.getOrCreateCart(SESSION_ID);

        shoppingCartService.addCookieToCart(shoppingCart.getId(), createdCookieDTO.getId(), 1);

        assertEquals(1, shoppingCart.getItems().size());

        shoppingCartService.removeItemFromCart(shoppingCart.getId(), shoppingCart.getItems().get(0).getId());

        assertEquals(0, shoppingCart.getItems().size());
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

        assertEquals(2, shoppingCart.getItems().size());

        shoppingCartService.clearCart(shoppingCart.getId());

        assertEquals(0, shoppingCart.getItems().size());
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
        assertEquals( createdChocolateDTO.getPrice() +
                createdCookieDTO.getPrice() +
                createdSweetsDTO.getPrice(), totalPrice);
    }
}