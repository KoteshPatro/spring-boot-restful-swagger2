package com.test.restfulwebservices.user;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class UserResource {
	
	@Autowired
	private UserDao userService;
	
	@GetMapping(path="/users")
	public List<User> retrievedAllUsers(){
		return userService.findAll();
	}
	@GetMapping(path="/users/{id}")
	public Resource<User> retrieveUser(@PathVariable int id){
		
		User user=userService.findOne(id);
		
		if(user==null)
			throw new UserNotFoundException("id-"+id);
		
		Resource<User>  resource=new Resource<User>(user);
		
		ControllerLinkBuilder  controllerLinkBuilder=	ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(this.getClass()).retrievedAllUsers());
		resource.add(controllerLinkBuilder.withRel("all-users"));
		
		return resource;
	}
	
	
	@PostMapping(path="/users")
	public ResponseEntity<Object> createUser(@Valid @RequestBody User user){
		User savedUser=userService.save(user);
		
		URI location=ServletUriComponentsBuilder.fromCurrentRequest().path("{id}").buildAndExpand(savedUser.getId()).toUri();
		return ResponseEntity.created(location).build();
		
		
	}
	@DeleteMapping(path="/users/{id}")
	public void deleteUser(@PathVariable int id){
		
		User user=userService.deleteUser(id);
		
		if(user==null)
			throw new UserNotFoundException("id-"+id);
	}
	

}
