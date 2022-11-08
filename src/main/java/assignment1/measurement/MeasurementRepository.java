package assignment1.measurement;

import assignment1.device.model.Device;
import assignment1.measurement.model.Measurement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface MeasurementRepository  extends JpaRepository<Measurement, Long>, JpaSpecificationExecutor<Measurement> {
}
