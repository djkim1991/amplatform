package me.whiteship.natual.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class EventDto {

    @Data
    @Builder @AllArgsConstructor @NoArgsConstructor
    public static class Create {

        @NotEmpty
        String name;

        String description;

        @NotNull
        LocalDateTime beginEnrollmentDateTime;

        @NotNull
        LocalDateTime closeEnrollmentDateTime;

        @NotNull
        LocalDateTime beginEventDateTime;

        @NotNull
        LocalDateTime endEventDateTime;

        @NotEmpty
        String location;

        @Min(0)
        Integer basePrice;

        @Min(0)
        Integer maxPrice;

        @Min(0)
        Integer limitOfEnrollment;
    }
}
