package b_yousefi.bookshop.usersws.ui.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

/**
 * Created by: b.yousefi
 * Date: 7/9/2020
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@JsonRootName(value = "order")
@Relation(collectionRelation = "orders")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserModel extends RepresentationModel<UserModel> {
    public boolean isAdmin;
    private String id;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String role = "ROLE_USER";
    private String phoneNumber;
    private String email;
}
