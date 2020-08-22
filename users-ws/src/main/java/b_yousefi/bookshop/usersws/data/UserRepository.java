package b_yousefi.bookshop.usersws.data;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.Optional;

/**
 * Created by: b.yousefi
 * Date: 8/9/2020
 */
@RepositoryRestResource(collectionResourceRel = "users", path = "users")
public interface UserRepository extends CrudRepository<User, String> {
    @Override
    Iterable<User> findAll();

    Optional<User> findByUsername(@Param("username") String username);

    @PreAuthorize("hasRole('ADMIN') || (isAuthenticated() && (#s.username == principal.username)) || #s.id == null")
    @Override
    <S extends User> S save(S s);

    @PreAuthorize("isAuthenticated()")
    @PostAuthorize("hasRole('ADMIN') " +
            "|| (returnObject.isPresent() && (returnObject.get().username == principal.username))")
    @Override
    Optional<User> findById(String id);
}