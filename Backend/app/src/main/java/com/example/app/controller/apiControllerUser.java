package com.example.app.controller;

import com.example.app.exception.UserNotFoundException;
import com.example.app.model.user;
import com.example.app.repo.userRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * API class for managing users
 */

@RestController
@RequestMapping("/api/user")
public class apiControllerUser {

    //Reference to userRepo interface
    @Autowired
    userRepo userRepo;

    //*****************//

    /******************/
    /**** GET USER ****/
    /******************/

    /**
     * Get all users in DB
     *
     * @return all of the users registered on the app
     */
    @GetMapping("/users")
    public List<user> getAllUsers() {
        return userRepo.findAll();
    }

    //Get user by userId
    @GetMapping("/getUserByID/{id}")
    public user getUserByID(@PathVariable(value = "id") Long id) throws UserNotFoundException {
        return userRepo.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    /**
     * Get user by userName
     *
     * @param username
     * @return User values
     */
    @GetMapping("/getUserByUserName/{username}")
    public user getUserByUserName(@PathVariable(value = "username") String username) {
        return userRepo.findByUserName(username);
    }

    //Get user by email
    @GetMapping("/getUserByEmail/{email}")
    public user getUserByEmail(@PathVariable(value = "email") String email) {
        return userRepo.findByEmail(email);
    }

    //Check if user exists with userId
    @GetMapping("/userExistById/{id}")
    public user userExist(@PathVariable(value = "id") Long id) throws UserNotFoundException {
        return userRepo.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    /**
     * Check if user exists by userName
     *
     * @param username
     * @return True or false if user exists
     * @throws UserNotFoundException
     */
    @GetMapping("/userExistByUserName/{username}")
    public user userExist(@PathVariable(value = "username") String username) throws UserNotFoundException {
        if (userRepo.findByUserName(username).equals(userRepo)) {
            throw new UserNotFoundException(username);
        } else {
            return userRepo.findByUserName(username);
        }
    }

    /******************/
    /** END GET USER **/
    /******************/

    //*****************//

    /*******************/
    /**** POST USER ****/
    /*******************/

    /**
     * Create user
     *
     * @param user
     * @return User is saved to database
     */
    @PostMapping("/createUser")
    public user createUser(@Valid @RequestBody user user) {
        return userRepo.save(user);
    }

    /*******************/
    /** END POST USER **/
    /*******************/

    //*****************//

    /******************/
    /**** PUT USER ****/
    /******************/

    /**
     * Update user with userId
     *
     * @param id
     * @param userDetails
     * @return Updates user values
     * @throws UserNotFoundException
     */
    @PutMapping("/updateUserById/{id}")
    public user updateUserName(@PathVariable(value = "id") Long id,
                               @Valid @RequestBody user userDetails) throws UserNotFoundException {

        user user = userRepo.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        user.setUserName(userDetails.getUserName());
        user.setFirstName(userDetails.getFirstName());
        user.setLastName(userDetails.getLastName());

        user updatedUser = userRepo.save(user);

        return updatedUser;
    }

    /**
     * Update user with userName
     *
     * @param username
     * @param userDetails
     * @return Updates user values in database
     */
    @PutMapping("/updateUserByUserName/{username}")
    public user updateUserByUserName(@PathVariable(value = "username") String username,
                                     @Valid @RequestBody user userDetails) {

        user user = userRepo.findByUserName(username);

        user.setUserName(userDetails.getUserName());
        user.setFirstName(userDetails.getFirstName());
        user.setLastName(userDetails.getLastName());

        user updatedUser = userRepo.save(user);

        return updatedUser;
    }

    /*******************/
    /** END PUT USER **/
    /*******************/

    //*****************//

    /*****************/
    /** DELETE USER **/
    /*****************/

    /**
     * Delete user with userName
     *
     * @param username
     * @return Deleted user in user base
     */
    @DeleteMapping("/deleteUserWithUserName/{username}")
    public ResponseEntity<?> deleteUserWithUserName(@PathVariable(value = "username") String username) {
        user user = userRepo.findByUserName(username);

        userRepo.delete(user);

        return ResponseEntity.ok("Deleted");
    }

    /**
     * Delete user with userId
     *
     * @param id
     * @return Deleted user in database
     * @throws UserNotFoundException
     */
    @DeleteMapping("/deleteUserWithUserId/{id}")
    public ResponseEntity<?> deleteUserWithUserId(@PathVariable(value = "id") Long id) throws UserNotFoundException {
        user user = userRepo.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        userRepo.delete(user);

        return ResponseEntity.ok("Deleted");
    }

    /*********************/
    /** END DELETE USER **/
    /*********************/

    //*****************//
}
