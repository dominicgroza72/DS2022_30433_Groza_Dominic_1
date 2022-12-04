package assignment1.measurement;

import assignment1.device.model.dto.DeviceDto;
import assignment1.measurement.model.Measurement;
import assignment1.measurement.model.MeasurementService;
import assignment1.measurement.model.dto.MeasurementDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static assignment1.UrlMapping.ENTITY;
import static assignment1.UrlMapping.MEASUREMENTS;

@RestController
@RequestMapping(MEASUREMENTS)
@RequiredArgsConstructor
public class MeasurementController {

    private final MeasurementService measurementService;

    @CrossOrigin
    @GetMapping
    public List<MeasurementDto> allMeasurements() {
        return measurementService.findAll();
    }

    @CrossOrigin
    @GetMapping(ENTITY)
    public List<MeasurementDto> measurementForDevice(@PathVariable Long id) {
        return measurementService.findByDeviceId(id);
    }


}
