package assignment1.measurement;

import assignment1.device.model.Device;
import assignment1.device.model.dto.DeviceDto;
import assignment1.measurement.model.Measurement;
import assignment1.measurement.model.dto.MeasurementDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface MeasurementMapper {

    @Mapping(target = "deviceId", source = "device.id")
    MeasurementDto toDto(Measurement measurement);

    @Mapping(target = "device.id", source = "deviceId")
    Measurement fromDto(MeasurementDto measurementDto);
}


