package assignment1.device;

import assignment1.device.model.Device;
import assignment1.device.model.dto.DeviceDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DeviceMapper {

    @Mapping(target="userId", source="user.id")
    DeviceDto toDto (Device device);

    @Mapping(target="user.id", source="userId")
    Device fromDto (DeviceDto deviceDto);
}
