package models.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class GenericFeignException extends RuntimeException{
    private Integer status;
    private Map error;
}
