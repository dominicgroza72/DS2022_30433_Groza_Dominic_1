package assignment1.measurement.model.dto;

import assignment1.device.model.Device;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MeasurementDto {
    private Long id;
    private String location;
    private String description;
    private String title;
    private Long max_consumption;
    private Device device;
}
