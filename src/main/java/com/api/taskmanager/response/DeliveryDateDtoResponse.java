package com.api.taskmanager.response;

import com.api.taskmanager.model.DeliveryDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryDateDtoResponse {
    private UUID id;
    private LocalDate date;
    private String time;
    private Boolean active;
    private Boolean accomplished;

    public static DeliveryDateDtoResponse fromEntity(DeliveryDate deliveryDate){
        return new DeliveryDateDtoResponse(deliveryDate.getId(), deliveryDate.getDate(), deliveryDate.getTime(),
                deliveryDate.getActive(), deliveryDate.getAccomplished());
    }
}
