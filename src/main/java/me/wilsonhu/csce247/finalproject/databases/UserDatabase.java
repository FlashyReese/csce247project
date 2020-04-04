package me.wilsonhu.csce247.finalproject.databases;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import me.wilsonhu.csce247.finalproject.objects.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;

public class UserDatabase extends SavableDatabase implements Database<User> {

	private HashSet<RegisteredUser> users = new HashSet<>();
	private HashSet<Admin> admins = new HashSet<>();

	public void addUser(String username, String password, Status status) {
		getUsers().add(new RegisteredUser(username, password, status));
		saveUsers();
	}

	public void addAdmin(String username, String password) {
		getAdmins().add(new Admin(username, password));
		saveAdmins();
	}

	public void add(User user) {
		if(user instanceof RegisteredUser){
			getUsers().add((RegisteredUser) user);
			saveUsers();
		}else if(user instanceof Admin){
			getAdmins().add((Admin) user);
			saveAdmins();
		}
	}

	public boolean remove(User user) {
		if(user instanceof RegisteredUser){
			getUsers().remove((RegisteredUser) user);
			saveUsers();
		}else if(user instanceof Admin){
			getAdmins().remove((Admin) user);
			saveAdmins();
		}
		return false;
	}

	public User get(String username) {
		for(RegisteredUser user : getUsers()){
			if (user.getUsername().equals(username))
				return user;
		}
		for (Admin user: getAdmins()){
			if (user.getUsername().equals(username))
				return user;
		}
		return null;
	}

	public boolean hasUser(String username) {
		for(RegisteredUser user : getUsers()){
			if (user.getUsername().equals(username))
				return true;
		}
		for (Admin user: getAdmins()){
			if (user.getUsername().equals(username))
				return true;
		}
		return false;
	}

	public void loadUsers(){
		if (!new File(USER_FILE_NAME).exists()) {
			try {
				Files.createDirectories(Paths.get(USER_FILE_NAME).getParent());
				Files.createFile(Paths.get(USER_FILE_NAME));
			} catch (IOException e) {
				e.printStackTrace();
			}
			saveUsers();
		}
		setUsers(read(USER_FILE_NAME, new TypeToken<HashSet<RegisteredUser>>(){}.getType()));
	}

	public void loadAdmins(){
		if (!new File(ADMIN_FILE_NAME).exists()) {
			try {
				Files.createDirectories(Paths.get(ADMIN_FILE_NAME).getParent());
				Files.createFile(Paths.get(ADMIN_FILE_NAME));
			} catch (IOException e) {
				e.printStackTrace();
			}
			saveAdmins();
		}
		setAdmin(read(ADMIN_FILE_NAME, new TypeToken<HashSet<Admin>>(){}.getType()));
	}

	public void saveUsers(){
		write(getUsers(), USER_FILE_NAME);
	}

	public void saveAdmins(){
		write(getAdmins(), ADMIN_FILE_NAME);
	}

	public HashSet<RegisteredUser> getUsers() {
		return users;
	}

	public HashSet<Admin> getAdmins() {
		return admins;
	}

	public void setUsers(HashSet<RegisteredUser> users) {
		this.users = users;
	}

	public void setAdmin(HashSet<Admin> admins) {
		this.admins = admins;
	}
}
