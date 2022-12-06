package assignment1;


import assignment1.device.DeviceRepository;
import assignment1.device.model.Device;
import assignment1.measurement.MeasurementRepository;
import assignment1.measurement.model.Measurement;
import assignment1.reader.Reader;
import assignment1.security.AuthService;
import assignment1.security.dto.SignupRequest;
import assignment1.user.RoleRepository;
import assignment1.user.UserRepository;
import assignment1.user.model.ERole;
import assignment1.user.model.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Random;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class Bootstrapper implements ApplicationListener<ApplicationReadyEvent> {

    private final RoleRepository roleRepository;

    private final UserRepository userRepository;

    private final AuthService authService;

    private final DeviceRepository deviceRepository;

    private final MeasurementRepository measurementRepository;

    @Value("${app.bootstrap}")
    private Boolean bootstrap;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {

        if (bootstrap) {
            try {
                deviceRepository.deleteAll();
                userRepository.deleteAll();
                roleRepository.deleteAll();
                for (ERole value : ERole.values()) {
                    roleRepository.save(
                            Role.builder()
                                    .name(value)
                                    .build()
                    );
                }
                authService.register(SignupRequest.builder()
                        .email("dominic@gmail.com")
                        .username("dominic")
                        .password("@Dominic1")
                        .roles(Set.of("ADMIN"))
                        .build());
                authService.register(SignupRequest.builder()
                        .email("dominic@gmail.de")
                        .username("dominic1")
                        .password("@Dominic2")
                        .roles(Set.of("CUSTOMER"))
                        .build());
                for (int i = 0; i < 5; i++) {
                    Random rand = new Random();
                    Device device = Device.builder()
                            .location(String.format("Location #%d", i + 1))
                            .title(String.format("Device #%d", i + 1))
                            .max_consumption((long) (i + 1) * rand.nextInt(1000))
                            .description(String.format("Description random  #%d", i + 1))
                            .build();
                    deviceRepository.save(device);
                }

//                for (int i = 0; i < 5; i++) {
//                    Random rand = new Random();
//                    Measurement measurement = Measurement.builder()
//                            .date(Timestamp.from(Instant.now()))
//                            .reading_value(rand.nextFloat(1000))
//                            .build();
//                    measurementRepository.save(measurement);
//                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}