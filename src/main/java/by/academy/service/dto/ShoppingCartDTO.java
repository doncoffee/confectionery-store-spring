package by.academy.service.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ShoppingCartDTO {
    Long id;
    String sessionId;
}
