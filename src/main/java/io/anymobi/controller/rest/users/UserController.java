package io.anymobi.controller.rest.users;

import io.anymobi.common.ErrorResource;
import io.anymobi.common.annotation.CurrentUser;
import io.anymobi.domain.entity.sec.ActiveUserStore;
import io.anymobi.domain.entity.sec.User;
import io.anymobi.services.jpa.users.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "/api")
@Slf4j
public class UserController {

    @Autowired
    ActiveUserStore activeUserStore;

    @Autowired
    IUserService userService;

    @GetMapping(path = "/users/{id}", produces = {"application/vnd.siren+json"})
    public ResponseEntity<Resource<User>> getOneById(@PathVariable Long id) {
        Optional<User> UserOptional = userService.findById(id);
        if (UserOptional.isPresent()) {
            Resource<User> UserResource = new Resource<>(UserOptional.get());
            ControllerLinkBuilder allBooks = linkTo(methodOn(this.getClass()).getAll());
            ControllerLinkBuilder self = linkTo(methodOn(this.getClass()).getOneById(id));
            ControllerLinkBuilder createBook = linkTo(methodOn(this.getClass()).create(null));
            ControllerLinkBuilder updateBook = linkTo(methodOn(this.getClass()).update(id, null));
            ControllerLinkBuilder deleteBook = linkTo(methodOn(this.getClass()).delete(id));
            UserResource.add(self.withSelfRel().withType("GET"));
            UserResource.add(allBooks.withRel("all-book").withType("GET").withDeprecation("false"));
            UserResource.add(createBook.withRel("create").withType("POST").withDeprecation("false"));
            UserResource.add(updateBook.withRel("update").withType("UPDATE").withDeprecation("false"));
            UserResource.add(deleteBook.withRel("delete").withType("DELETE").withDeprecation("false"));
            return new ResponseEntity<>(UserResource, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(path = "/users", produces = {"application/vnd.siren+json"})
    public ResponseEntity<Resources<Resource<User>>> getAll() {
        List<User> Users = userService.findAll();
        List<Resource<User>> UserResources = new ArrayList<>();
        for (User user : Users) {
            Resource<User> UserResource = new Resource<>(user);
            ControllerLinkBuilder allUsers = linkTo(methodOn(this.getClass()).getAll());
            ControllerLinkBuilder self = linkTo(methodOn(this.getClass()).getOneById(user.getId()));
            UserResource.add(self.withSelfRel());
            UserResource.add(allUsers.withRel("all-Users"));
            UserResources.add(UserResource);
        }
        return new ResponseEntity<>(new Resources<>(UserResources,
                linkTo(methodOn(UserController.class).getAll()).withSelfRel()), HttpStatus.OK);
    }

    @PostMapping(path = "/users", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<User> create(@RequestBody User user) {

        log.info("hop hey");

        if (userService.isExist(user)) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        user.setId(null);
        return new ResponseEntity<>(userService.save(user), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity getUser(@PathVariable Long id, @CurrentUser User currentUser) {
        Optional<User> byId = userService.findById(id);
        if (!byId.isPresent()) {
            return notFoundResponse();
        }

        User User = byId.get();
        UserResource UserResource = new UserResource(User);
        if (currentUser != null) {
            UserResource.add(linkToUpdate(User));
        }
        UserResource.add(linkToProfile("resources-Users-get"));
        return ResponseEntity.ok().body(UserResource);
    }

    @PutMapping(path = "/users/{id}")
    public ResponseEntity<User> update(@PathVariable Long id, @RequestBody User User) {

        Optional<User> currentUser = userService.findById(id);
        if (currentUser.isPresent()) {
            currentUser.get().setEmail(User.getEmail());
            return new ResponseEntity<>(userService.save(currentUser.get()), HttpStatus.OK);
        }

        return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping(path = "/users/{id}")
    public ResponseEntity.HeadersBuilder<?> delete(@PathVariable Long id) {
        userService.deleteById(id);
        return ResponseEntity.noContent();
    }


    private ResponseEntity<Object> notFoundResponse() {
        return ResponseEntity.notFound().build();
    }

    private ResponseEntity<ErrorResource> badRequestResponse(BindingResult errors) {
        return ResponseEntity.badRequest().body(new ErrorResource(errors));
    }

    private Link linkToProfile(String anchor) {
        String linkValue = "</docs/index.html>; rel=\"profile\";";
        if (anchor != null) {
            linkValue = "</docs/index.html#" + anchor + ">; rel=\"profile\";";
        }
        return Link.valueOf(linkValue);
    }

    private Link linkToUpdate(User user) {
        return linkTo(methodOn(UserController.class).update(user.getId(), null)).withRel("update");
    }
}
