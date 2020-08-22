package b_yousefi.bookshop.usersws.ui.model;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by: b.yousefi
 * Date: 5/11/2020
 */
@Data
public class JwtResponse implements Serializable {
    private static final long serialVersionUID = -8091879091924046844L;
    private final String jwttoken;
    private final String id;
}
