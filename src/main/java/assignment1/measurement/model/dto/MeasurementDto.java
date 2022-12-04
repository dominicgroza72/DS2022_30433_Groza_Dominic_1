package assignment1.measurement.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MeasurementDto {
    private Long id;
    private Timestamp date;
    private Float reading_value;
    private Long deviceId;
}
