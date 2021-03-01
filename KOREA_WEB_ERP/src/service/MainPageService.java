package service;

import java.sql.Timestamp;

import exception.AddException;
import exception.FindException;
import exception.ModifyException;
import vo.Commute;

public interface MainPageService {
	
	void addCommute(Commute c) throws AddException;
	
	String findName(String id) throws FindException;
	
	Timestamp findStartTime(String id) throws FindException;
	
	Timestamp findEndTime(String id) throws FindException;
	
	void modifyCommute(Commute c) throws ModifyException;
}
