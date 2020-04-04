package me.wilsonhu.csce247.finalproject.databases;

import java.io.File;

public abstract class DataConstants {

	protected static final String USER_FILE_NAME = System.getProperty("user.dir") + File.separator + "users.json";
	protected static final String ADMIN_FILE_NAME = System.getProperty("user.dir")  + File.separator + "admins.json";
	protected static final String VENUE_FILE_NAME = System.getProperty("user.dir")  + File.separator + "venues.json";
}