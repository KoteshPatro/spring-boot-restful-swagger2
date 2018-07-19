package com.test.restfulwebservices.user;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class UserDao {
	
	private static List<User> users=new ArrayList<>();
	
	static{
		
		users.add(new User(1,"Kotesh",new Date()));
		users.add(new User(2,"Mohan",new Date()));
		users.add(new User(3,"Mallik",new Date()));
		
	}
	
	
	private static int usersCount=3;
	public List<User> findAll(){
		return users;
	}
	
	
	public User save(User user){
		if(user.getId()==null){
			user.setId(++usersCount);
		}
		users.add(user);
		return user;
	}
	public User findOne(int id){
		
		for (User user : users) {
			if(user.getId()==id)
				return user;
				
			
		}
		return null;
		
	}
	
public User deleteUser(int id){
		
	Iterator<User> userItr=users.iterator();	
	while(userItr.hasNext()){
		User usr=userItr.next();
		if(usr.getId()==id){
			userItr.remove();
			return usr;
		}
	}
	
		return null;
		
	}
	
	
	
	

}
