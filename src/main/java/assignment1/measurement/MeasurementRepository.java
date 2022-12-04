package assignment1.measurement;

import assignment1.device.model.Device;
import assignment1.measurement.model.Measurement;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface MeasurementRepository  extends JpaRepository<Measurement, Long>, JpaSpecificationExecutor<Measurement> {
    @Query("SELECT ms from Measurement ms WHERE ms.device.id =:deviceId")
    List<Measurement> findAllForDeviceId(@Param("deviceId")Long deviceId);

    @Query("select ms from Measurement ms where ms.device.id = :deviceId AND date(ms.date) = :date")
    List<Measurement> getMeasurementsForDeviceAndDay(@Param("deviceId") Long deviceId, @Param("date") Date date);
}
