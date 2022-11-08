package assignment1.device;

import assignment1.device.model.dto.DeviceDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import static assignment1.UrlMapping.*;

@RestController
@RequestMapping(DEVICES)
@RequiredArgsConstructor
public class DeviceController {

    private final DeviceService deviceService;

    @CrossOrigin
    @GetMapping
    public List<DeviceDto> allBooks() {
        return deviceService.findAll();
    }


    @CrossOrigin
    @PostMapping(ENTITY)
    public DeviceDto create(@PathVariable Long id,@RequestBody DeviceDto deviceDto){
        return deviceService.create(deviceDto,id);
    }


    @CrossOrigin
    @PutMapping(ENTITY)
    public DeviceDto edit(@PathVariable Long id, @RequestBody DeviceDto device){
        return deviceService.edit(id,device);
    }

    @CrossOrigin
    @DeleteMapping(ENTITY)
    public void delete(@PathVariable Long id){
        deviceService.delete(id);
    }

    @CrossOrigin
    @GetMapping(ENTITY)
    public DeviceDto getDevice(@PathVariable Long id){
        return deviceService.get(id);
    }


}
