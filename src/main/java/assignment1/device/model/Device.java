package assignment1.device.model;

import assignment1.measurement.model.Measurement;
import assignment1.user.model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Set;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Device {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(length = 512,nullable = false)
    private String location;

    @Column(length = 512,nullable = false)
    private String description;

    @Column(length = 512,nullable = false)
    private String title;

    @Column()
    private Long max_consumption;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id",nullable = true)
    @OnDelete(action= OnDeleteAction.CASCADE)
    private User user;

    @OneToMany(mappedBy = "device",fetch = FetchType.EAGER)
    private Set<Measurement> measurements;


}
