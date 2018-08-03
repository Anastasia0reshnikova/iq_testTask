package models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * Created by a.oreshnikova on 02.08.2018.
 */

@Getter
@Setter
@ToString
@Accessors(fluent = true)
public class User {

    private String email;
    private String password;
    private String userName;
}
