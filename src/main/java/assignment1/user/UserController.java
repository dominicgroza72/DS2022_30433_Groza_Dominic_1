package assignment1.user;

import assignment1.UrlMapping;
import assignment1.user.dto.UserListDto;
import assignment1.websocket.Message;
import com.fasterxml.jackson.annotation.JsonRawValue;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(UrlMapping.USERS)
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @CrossOrigin
    @GetMapping
    public List<UserListDto> allUsers() {
        return userService.allUsersForList();
    }

    @CrossOrigin
    @PostMapping
    public ResponseEntity<?> create(@RequestBody UserListDto user) {
        return userService.create(user);
    }

    @CrossOrigin
    @DeleteMapping(UrlMapping.ENTITY)
    public void delete(@PathVariable Long id) {
        userService.delete(id);
    }

    @CrossOrigin
    @PutMapping(UrlMapping.ENTITY)
    public UserListDto edit(@PathVariable Long id, @RequestBody UserListDto user) {

        return userService.edit(id, user);
    }

    @CrossOrigin
    @PostMapping(UrlMapping.TOUSERNAME)
    @JsonRawValue
    public void sendMessage(@PathVariable String toUsername, @RequestBody Message message) throws Exception {
        userService.sendMessage(toUsername, message);
    }

}