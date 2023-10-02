package com.controller.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.controller.Model.CateGory;
import com.controller.Repositories.CategoryRepository;

@Service
public class CateService {
	

	@Autowired
	private CategoryRepository  rep;
	  /***
	   * 
	   * List Search
	   * @param id
	   * @return
	   */
	  public List<CateGory> filterCate(String name){
		  
		 
		  
		  return rep.getCateByFilter(name);
		  
	  }
}
