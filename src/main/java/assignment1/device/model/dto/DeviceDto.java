package assignment1.device.model.dto;

import assignment1.measurement.model.Measurement;
import assignment1.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DeviceDto {
    private Long id;
    private String location;
    private String description;
    private String title;
    private Long max_consumption;
    private Long userId;
    private Set<Measurement> measurements;
}
