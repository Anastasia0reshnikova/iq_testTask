package models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * Created by a.oreshnikova on 03.08.2018.
 */

@Getter
@Setter
@EqualsAndHashCode
@ToString
@Accessors(fluent = true)
public class Error {
    private Integer code;
    private String title;
}
