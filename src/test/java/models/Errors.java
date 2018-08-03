package models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * Created by a.oreshnikova on 03.08.2018.
 */

@Getter
@Setter
@EqualsAndHashCode
@ToString
@Accessors(fluent = true)
public class Errors {
    private List<Error> errors;
}
