package br.com.jonas.customer.manager.customer.domain.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Filter {
    private String search;

    private LocalDateTime startDate;
    private LocalDateTime endDate;

    public static final LocalDateTime start = LocalDateTime.of(1, 1, 1, 0, 0);
    public static final LocalDateTime end = LocalDateTime.of(9999, 1, 1, 0, 0);
}
