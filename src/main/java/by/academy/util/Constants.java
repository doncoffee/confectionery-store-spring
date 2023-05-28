package by.academy.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Constants {

    public static final String LOGIN = "/login";
    public static final String LOGOUT = "/logout";
    public static final String DEFAULT_SUCCESS_URL = "/";
    public static final String ADDRESS = "address";
    public static final String ADDRESS_ID = "address_id";
    public static final String BRAND_ID = "brand_id";
    public static final String BRAND = "brand";
    public static final String CHOCOLATE_ID = "chocolate_id";
    public static final String COOKIE_ID = "cookie_id";
    public static final String SWEETS_ID = "sweets_id";
    public static final String CART_ID = "cart_id";
    public static final String CART_ITEM = "cart_item";
    public static final String CHOCOLATE = "chocolate";
    public static final String STORE_ID = "store_id";
    public static final String SUPPLIER_ID = "supplier_id";
    public static final String COOKIE = "cookie";
    public static final String PHONE_NUMBER = "phone_number";
    public static final String PHONE_NUMBER_ID = "phone_number_id";
    public static final String PHONE_NUMBER1 = "phoneNumber";
    public static final String SESSION_ID = "session_id";
    public static final String CART = "cart";
    public static final String CART_ITEM_NOT_FOUND = "CartItem not found";
    public static final String SHOPPING_CART = "shopping_cart";
    public static final String STORE = "store";
    public static final String CONTACT_PERSON = "contact_person";
    public static final String SUPPLIER = "supplier";
    public static final String SWEETS = "sweets";
    public static final String BIRTH_DATE = "birth_date";
    public static final String USERS = "users";
    public static final String EMAIL = "email";
    public static final String API_ADDRESSES = "/api/addresses";
    public static final String SEARCH = "search";
    public static final String ADDRESS_ADDRESSES = "address/addresses";
    public static final String ADD_ADDRESS = "/add_address";
    public static final String PAGE = "page";
    public static final String SIZE = "size";
    public static final String PAGE_PARAMETER_MUST_NOT_BE_NULL = "Page parameter must not be null";
    public static final String SIZE_PARAMETER_MUST_NOT_BE_NULL = "Size parameter must not be null";
    public static final String ADDRESS_ADD_ADDRESS = "address/add-address";
    public static final String REDIRECT_API_ADDRESSES_PAGE = "redirect:/api/addresses?page=";
    public static final String SIZE_PATH = "&size=";
    public static final String SEARCH_PATH = "&search=";
    public static final String ID_UPDATE = "/{id}/update";
    public static final String ID = "id";
    public static final String ADDRESS_EDIT_ADDRESS = "address/edit-address";
    public static final String ADDRESSES = "addresses";
    public static final String ID_DELETE = "/{id}/delete";
    public static final String EDIT_ADDRESS_ID = "/edit_address/{id}";
    public static final String REDIRECT = "redirect:";
    public static final String REFERER = "Referer";
    public static final String ERRORS = "errors";
    public static final String API_BRANDS = "/api/brands";
    public static final String BRANDS = "brands";
    public static final String BRAND_BRANDS = "brand/brands";
    public static final String ADD_BRAND = "/add_brand";
    public static final String EDIT_BRAND_ID = "/edit_brand/{id}";
    public static final String BRAND_ADD_BRAND = "brand/add-brand";
    public static final String BRAND_EDIT_BRAND = "brand/edit-brand";
    public static final String REDIRECT_API_BRANDS_PAGE = "redirect:/api/brands?page=";
    public static final String API_CHOCOLATES = "/api/chocolates";
    public static final String CHOCOLATE_ADD_CHOCOLATE = "chocolate/add-chocolate";
    public static final String ADD_CHOCOLATE = "/add_chocolate";
    public static final String CHOCOLATE_EDIT_CHOCOLATE = "chocolate/edit-chocolate";
    public static final String REDIRECT_API_CHOCOLATES_PAGE = "redirect:/api/chocolates?page=";
    public static final String CHOCOLATES = "chocolates";
    public static final String STORES = "stores";
    public static final String SUPPLIERS = "suppliers";
    public static final String EDIT_CHOCOLATE_ID = "/edit_chocolate/{id}";
    public static final String CHOCOLATE_CHOCOLATES = "chocolate/chocolates";
    public static final String COOKIE_ADD_COOKIE = "cookie/add-cookie";
    public static final String COOKIE_EDIT_COOKIE = "cookie/edit-cookie";
    public static final String EDIT_COOKIE_ID = "/edit_cookie/{id}";
    public static final String ADD_COOKIE = "/add_cookie";
    public static final String REDIRECT_API_COOKIES_PAGE = "redirect:/api/cookies?page=";
    public static final String COOKIES = "cookies";
    public static final String COOKIE_COOKIES = "cookie/cookies";
    public static final String API_COOKIES = "/api/cookies";
    public static final String HOME = "home";
    public static final String LOGIN1 = "login";
    public static final String API_PHONE_NUMBERS = "/api/phone_numbers";
    public static final String CONTACTS_PHONE_NUMBERS = "contacts/phone-numbers";
    public static final String CONTACTS_ADD_PHONE_NUMBER = "contacts/add-phone-number";
    public static final String CONTACTS_EDIT_PHONE_NUMBER = "contacts/edit-phone-number";
    public static final String ADD_PHONE_NUMBER = "/add_phone_number";
    public static final String REDIRECT_API_PHONE_NUMBERS_PAGE = "redirect:/api/phone_numbers?page=";
    public static final String PHONE_NUMBERS = "phoneNumbers";
    public static final String EDIT_PHONE_NUMBER_ID = "/edit_phone_number/{id}";
    public static final String API_CART = "/api/cart";
    public static final String ANONYMOUS_USER = "anonymousUser";
    public static final String PURCHASE_SUCCESSFUL = "purchase-successful";
    public static final String PURCHASE_SUCCESSFUL1 = "/purchase_successful";
    public static final String CHOCOLATE_ID_ADD_CHOCOLATE_TO_CART = "/{chocolateId}/add_chocolate_to_cart";
    public static final String COOKIE_ID_ADD_COOKIE_TO_CART = "/{cookieId}/add_cookie_to_cart";
    public static final String SWEETS_ID_ADD_SWEETS_TO_CART = "/{sweetsId}/add_sweets_to_cart";
    public static final String CART_ID_REMOVE_CART_ITEM_ID = "/{cartId}/remove/{cartItemId}";
    public static final String CLEAR_CART = "/clear_cart";
    public static final String CART_ID_UPDATE_CART_ITEM_ID = "/{cartId}/update/{cartItemId}";
    public static final String QUANTITY = "quantity";
    public static final String TOTAL_PRICE = "totalPrice";
    public static final String API_STORES = "/api/stores";
    public static final String ADD_STORE = "/add_store";
    public static final String STORE_ADD_STORE = "store/add-store";
    public static final String STORE_EDIT_STORE = "store/edit-store";
    public static final String REDIRECT_API_STORES_PAGE = "redirect:/api/stores?page=";
    public static final String EDIT_STORE_ID = "/edit_store/{id}";
    public static final String STORE_STORES = "store/stores";
    public static final String SUPPLIER_ADD_SUPPLIER = "supplier/add-supplier";
    public static final String ADD_SUPPLIER = "/add_supplier";
    public static final String EDIT_SUPPLIER_ID = "/edit_supplier/{id}";
    public static final String SUPPLIER_EDIT_SUPPLIER = "supplier/edit-supplier";
    public static final String REDIRECT_API_SUPPLIERS_PAGE = "redirect:/api/suppliers?page=";
    public static final String SUPPLIER_SUPPLIERS = "supplier/suppliers";
    public static final String API_SUPPLIERS = "/api/suppliers";
    public static final String SWEETS_ADD_SWEETS = "sweets/add-sweets";
    public static final String ADD_SWEETS = "/add_sweets";
    public static final String SWEETS_EDIT_SWEETS = "sweets/edit-sweets";
    public static final String EDIT_SWEETS_ID = "/edit_sweets/{id}";
    public static final String REDIRECT_API_SWEETS_PAGE = "redirect:/api/sweets?page=";
    public static final String SWEETS_SWEETS = "sweets/sweets";
    public static final String API_SWEETS = "/api/sweets";
    public static final String USER_PATH = "/user";
    public static final String REGISTRATION = "/registration";
    public static final String REGISTRATION1 = "registration";
    public static final String CREATE_USER = "/create_user";
    public static final String USER = "user";
    public static final String ROLES = "roles";
    public static final String MAPPING_FROM_USER_DTO_TO_USER_IS_NOT_SUPPORTED = "Mapping from UserDTO to User is not supported.";
    public static final String NAME_MUST_NOT_BE_BLANK = "Name must not be blank";
    public static final String NAME_SIZE_MUST_BE_BETWEEN_MIN_AND_MAX = "Name size must be between {min} and {max}";
    public static final String PRICE_MUST_BE_GREATER_THAN_0 = "Price must be greater than 0";
    public static final String PRICE_MUST_NOT_BE_BLANK = "Price must not be blank";
    public static final String TYPE_MUST_NOT_BE_BLANK = "Type must not be blank";
    public static final String TYPE_SIZE_MUST_BE_BETWEEN_MIN_AND_MAX = "Type size must be between {min} and {max}";
    public static final String WEIGHT_MUST_BE_GREATER_THAN_0 = "Weight must be greater than 0";
    public static final String WEIGHT_MUST_NOT_BE_BLANK = "Weight must not be blank";
    public static final String COMPOSITION_MUST_NOT_BE_BLANK = "Composition must not be blank";
    public static final String COMPOSITION_SIZE_MUST_BE_BETWEEN_MIN_AND_MAX = "Composition size must be between {min} and {max}";
    public static final String DECIMAL_MIN = "0.01";
    public static final String NUMBER_MUST_NOT_BE_BLANK = "Number must not be blank";
    public static final String INVALID_PHONE_NUMBER = "Invalid phone number";
    public static final String CONTACT_PERSON_INPUT_FIELD_MUST_NOT_BE_BLANK = "Contact person input field must not be blank";
    public static final String CONTACT_PERSON_NAME_SIZE_MUST_BE_BETWEEN_MIN_AND_MAX = "Contact person name size must be between {min} and {max}";
    public static final String USERNAME_MUST_NOT_BE_BLANK = "Username must not be blank";
    public static final String USERNAME_SIZE_MUST_BE_BETWEEN_MIN_AND_MAX = "Username size must be between {min} and {max}";
    public static final String YYYY_MM_DD = "yyyy-MM-dd";
    public static final String FIRST_NAME_MUST_NOT_BE_BLANK = "First name must not be blank";
    public static final String FIRST_NAME_SIZE_MUST_BE_BETWEEN_MIN_AND_MAX = "First name size must be between {min} and {max}";
    public static final String LAST_NAME_SIZE_MUST_BE_BETWEEN_MIN_AND_MAX = "Last name size must be between {min} and {max}";
    public static final String LAST_NAME_MUST_NOT_BE_BLANK = "Last name must not be blank";
    public static final String CHOCOLATE_NOT_FOUND = "Chocolate not found";
    public static final String SHOPPING_CART_NOT_FOUND = "ShoppingCart not found";
    public static final String COOKIE_NOT_FOUND = "Cookie not found";
    public static final String SWEETS_NOT_FOUND = "Sweets not found";
    public static final String FAILED_TO_RETRIEVE_USER = "Failed to retrieve user: ";
    public static final String PASSWORD_MUST_NOT_BE_BLANK = "Password must not be blank";
    public static final String GIVEN_NAME = "given_name";
    public static final String FAMILY_NAME = "family_name";
    public static final String USE_COPY_METHOD_IN_CHOCOLATE_SERVICE_IMPL_INSTEAD = "Use 'copy' method in ChocolateServiceImpl instead";
    public static final String USE_COPY_METHOD_IN_COOKIE_SERVICE_IMPL_INSTEAD = "Use 'copy' method in CookieServiceImpl instead";
    public static final String USE_COPY_METHOD_IN_STORE_SERVICE_IMPL_INSTEAD = "Use 'copy' method in StoreServiceImpl instead";
    public static final String USE_COPY_METHOD_IN_SUPPLIER_SERVICE_IMPL_INSTEAD = "Use 'copy' method in SupplierServiceImpl instead";
    public static final String USE_COPY_METHOD_IN_SWEETS_SERVICE_IMPL_INSTEAD = "Use 'copy' method in SweetsServiceImpl instead";
}
