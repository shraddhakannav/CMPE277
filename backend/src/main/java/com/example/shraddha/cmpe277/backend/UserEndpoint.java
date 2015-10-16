package com.example.shraddha.cmpe277.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;

import java.util.List;
import java.util.logging.Logger;

import javax.inject.Named;

/**
 * An endpoint class we are exposing
 */
@Api(
        name = "userApi",
        version = "v1",
        resource = "user",
        namespace = @ApiNamespace(
                ownerDomain = "backend.cmpe277.shraddha.example.com",
                ownerName = "backend.cmpe277.shraddha.example.com",
                packagePath = ""
        )
)
public class UserEndpoint {

    private static final Logger logger = Logger.getLogger(UserEndpoint.class.getName());

    /**
     * This method gets the <code>User</code> object associated with the specified <code>id</code>.
     *
     * @param id The id of the object to be returned.
     * @return The <code>User</code> associated with <code>id</code>.
     */
    @ApiMethod(name = "getUser")
    public User getUser(@Named("id") Long id) {
        // TODO: Implement this function
        logger.info("Calling getUser method");
        return null;
    }

    /**
     * This inserts a new <code>User</code> object.
     *
     * @param user The object to be added.
     * @return The object to be added.
     */
    @ApiMethod(name = "insertUser", path = "user/insert")
    public User insertUser(User user) {
        logger.info("Calling insertUser method");
        DatabaseConnection.insertUser(user);
        return user;
    }

    /**
     * This deletes a existing <code>User</code> object.
     *
     * @param user The object to be added.
     * @return Nothing
     */
    @ApiMethod(name = "deleteUser", path = "user/delete")
    public void deleteUser(User user) {
        logger.info("Calling insertUser method");
        DatabaseConnection.deleteUser(user);
    }

    /**
     * This List a users
     *
     * @return The list of Users
     */
    @ApiMethod(name = "list", path = "user/list")
    public List<User> list() {
        logger.info("Calling listUser method");
        return DatabaseConnection.listAllUsers();
    }
}