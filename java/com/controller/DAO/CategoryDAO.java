package com.controller.DAO;

import com.controller.Model.CateGory;


public class CategoryDAO {
	public static boolean  newCategory(CateGory Item) {
		try {
			
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}
}
