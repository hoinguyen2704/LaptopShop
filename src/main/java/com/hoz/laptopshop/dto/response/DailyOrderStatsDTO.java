package com.hoz.laptopshop.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DailyOrderStatsDTO {
    private String date;
    private Long completedOrders;
    private Long failedOrders;
}
