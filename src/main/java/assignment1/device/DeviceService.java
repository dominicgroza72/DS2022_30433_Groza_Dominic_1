package assignment1.device;

import assignment1.device.model.Device;
import assignment1.device.model.dto.DeviceDto;
import assignment1.user.UserRepository;
import assignment1.user.UserService;
import assignment1.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DeviceService {

    private final DeviceRepository deviceRepository;
    private final DeviceMapper deviceMapper;

    private final UserService userService;

    private final UserRepository userRepository;

    private Device findById(Long id) {
        return deviceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Book not found" + id));
    }

    public List<DeviceDto> findAll() {
        return deviceRepository.findAll().stream()
                .map(deviceMapper::toDto)
                .collect(Collectors.toList());
    }

    public DeviceDto create(DeviceDto device, Long userId) {
        device.setUserId(userId);
        return deviceMapper.toDto(deviceRepository.save(deviceMapper.fromDto(device)));
    }

    public DeviceDto edit(Long id, DeviceDto device) {
        Device deviceToUpdate = findById(id);
        deviceToUpdate.setTitle(device.getTitle());
        User user=userRepository.findById(device.getUserId()).get();
        deviceToUpdate.setUser(user);
        deviceToUpdate.setDescription(device.getDescription());
        deviceToUpdate.setMax_consumption(device.getMax_consumption());
        deviceToUpdate.setLocation(device.getLocation());;
        return deviceMapper.toDto(deviceRepository.save(deviceToUpdate));
    }

    public void delete(Long id) {
        deviceRepository.deleteById(id);
    }


    public DeviceDto get(Long id) {
        return deviceMapper.toDto(findById(id));
    }
}
