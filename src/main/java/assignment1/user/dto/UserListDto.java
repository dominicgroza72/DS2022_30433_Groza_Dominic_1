package assignment1.user.dto;
import assignment1.device.model.Device;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@AllArgsConstructor
public class UserListDto extends UserMinimalDto {
    private String email;
    private Set<String> roles;
    private Set<Device> devices;
    private String password;
}