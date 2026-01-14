package com.hoz.laptopshop.dto.request;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class CartRequestDTO {
    private long quantity;
    private long productId;
}
