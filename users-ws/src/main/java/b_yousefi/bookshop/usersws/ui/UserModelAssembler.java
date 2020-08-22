package b_yousefi.bookshop.usersws.ui;

import b_yousefi.bookshop.usersws.data.User;
import b_yousefi.bookshop.usersws.data.UserRepository;
import b_yousefi.bookshop.usersws.ui.controllers.UserController;
import b_yousefi.bookshop.usersws.ui.model.UserModel;
import lombok.SneakyThrows;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URL;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * Created by: b.yousefi
 * Date: 7/9/2020
 */
@Component
public class UserModelAssembler extends RepresentationModelAssemblerSupport<User, UserModel> {
    private RepositoryRestConfiguration config;

    public UserModelAssembler(RepositoryRestConfiguration config) {
        super(UserRepository.class, UserModel.class);
        this.config = config;
    }

    @Override
    @NonNull
    public UserModel toModel(@NonNull User entity) {
        UserModel userModel = instantiateModel(entity);
        userModel.setId(entity.getId());
        userModel.setUsername(entity.getUsername());
        userModel.setEmail(entity.getEmail());
        userModel.setFirstName(entity.getFirstName());
        userModel.setLastName(entity.getLastName());
        userModel.setPassword(entity.getPassword());
        userModel.setPhoneNumber(entity.getPhoneNumber());
        userModel.setAdmin(entity.isAdmin());
        userModel.add(fixLinkSelf(
                methodOn(UserController.class)
                        .getUserById(entity.getId()))
                .withSelfRel());

        return userModel;
    }

    protected Link fixLinkSelf(Object invocationValue) {
        return fixLinkTo(invocationValue).withSelfRel();
    }

    @SneakyThrows
    protected Link fixLinkTo(Object invocationValue) {
        UriComponentsBuilder uriComponentsBuilder = linkTo(invocationValue).toUriComponentsBuilder();
        URL url = new URL(uriComponentsBuilder.toUriString());
        uriComponentsBuilder.replacePath(config.getBasePath() + url.getPath());
        return new Link(uriComponentsBuilder.toUriString());
    }
}
