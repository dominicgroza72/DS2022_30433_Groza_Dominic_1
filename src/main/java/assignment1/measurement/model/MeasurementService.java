package assignment1.measurement.model;

import assignment1.device.DeviceService;
import assignment1.device.model.dto.DeviceDto;
import assignment1.measurement.MeasurementMapper;
import assignment1.measurement.MeasurementRepository;
import assignment1.measurement.model.dto.MeasurementDto;
import assignment1.user.UserService;
import assignment1.user.dto.UserListDto;
import assignment1.user.model.User;
import assignment1.websocket.NotificationController;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MeasurementService {

    private final MeasurementRepository measurementRepository;

    private final MeasurementMapper measurementMapper;

    private final DeviceService deviceService;

    private final UserService userService;

    private final NotificationController notificationController;

    private Measurement findById(Long id) {
        return measurementRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Measurement not found" + id));

    }

    public List<MeasurementDto> findAll() {
        return measurementRepository.findAll().stream()
                .map(measurementMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<MeasurementDto> findByDeviceId(Long id) {
        return measurementRepository.findAllForDeviceId(id).stream()
                .map(measurementMapper::toDto)
                .collect(Collectors.toList());
    }

    public MeasurementDto create(MeasurementDto measurement) {
        return measurementMapper.toDto(measurementRepository.save(measurementMapper.fromDto(measurement)));
    }


    public void delete(Long id) {
        measurementRepository.deleteById(id);
    }


    public MeasurementDto get(Long id) {
        return measurementMapper.toDto(findById(id));
    }

    public void checkMeasurementLimitForDevice(MeasurementDto measurementDto) throws Exception {
        DeviceDto device = deviceService.get(measurementDto.getDeviceId());
        User user = userService.findById(device.getUserId());
        Long maxConsumption = device.getMax_consumption();

        Date formattedDate = new Date(measurementDto.getDate().getTime());
        List<Measurement> allMeasurementsForDayAndDevice = measurementRepository.getMeasurementsForDeviceAndDay(measurementDto.getDeviceId(), formattedDate);


        float totalMeasurements = 0;
        for (Measurement measurement : allMeasurementsForDayAndDevice) {
            if (isMeasurementFromToday(measurement.getDate())) {
                totalMeasurements += measurement.getReading_value();
                if (totalMeasurements > maxConsumption) {
                    System.out.println("Limit reached for device "+device.getLocation());
                    notificationController.notification("Limit reached for device " + device.getTitle(), device.getUserId());
                    break;
                }
            }
        }
    }

    private Boolean isMeasurementFromToday(Timestamp measurementTimestamp) {
        Date formattedDate = new Date(measurementTimestamp.getTime());
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Europe/Bucharest"));
        cal.setTime(formattedDate);
        int hour = cal.get(Calendar.HOUR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        Date now = new Date(System.currentTimeMillis());
        Calendar cal1 = Calendar.getInstance(TimeZone.getTimeZone("Europe/Bucharest"));
        cal1.setTime(now);
        int hourNow = cal1.get(Calendar.HOUR);
        int monthNow = cal1.get(Calendar.MONTH);
        int dayNow = cal1.get(Calendar.DAY_OF_MONTH);

        return monthNow == month && dayNow == day && hourNow == hour;
    }

}
