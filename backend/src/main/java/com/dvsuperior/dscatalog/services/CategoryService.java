package com.dvsuperior.dscatalog.services;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dvsuperior.dscatalog.dto.CategoryDTO;
import com.dvsuperior.dscatalog.entities.Category;
import com.dvsuperior.dscatalog.repositories.CategoryRepository;
import com.dvsuperior.dscatalog.services.exceptions.DataBaseException;
import com.dvsuperior.dscatalog.services.exceptions.ResourceNotFoundException;

@Service
public class CategoryService {
	
	@Autowired
	private CategoryRepository repository;
	
	@Transactional (readOnly = true)
	public Page<CategoryDTO> findAllPaged(PageRequest pageRequest){
		Page<Category> list = repository.findAll(pageRequest);
		
		//.stream = recurso que permite trabalhar com expressões de alta ordem
		return list.map(x -> new CategoryDTO(x));
		
		}

	@Transactional (readOnly = true)
	public CategoryDTO findById(Long id) {
		//optional = abordagem evitar trabalhar com valor nulo 
		Optional<Category> obj = repository.findById(id);
		Category entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found."));
		return new CategoryDTO(entity);
	}

	@Transactional
	public CategoryDTO insert(CategoryDTO dto) {
		Category entity = new Category();
		entity.setName(dto.getName());
		entity = repository.save(entity);
	 	return new CategoryDTO(entity);
	}
	//getone não toca o banco de dados, para não fazer vários acessos ao banco em uma mesma execução
	@Transactional
	public CategoryDTO update(Long id, CategoryDTO dto) {
		try {
			Category entity = repository.getOne(id);
			entity.setName(dto.getName());
			entity = repository.save(entity);
			return new CategoryDTO(entity);
		}
		catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException ("Id not found " + id);
			}
		}

	public void delete(Long id) {
		try {
			repository.deleteById(id);
		}
		
		catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("Id not found " + id);						
		}
		
		catch (DataIntegrityViolationException e) {
			throw new DataBaseException("Integrity Violation.");
		}
				
		}
	}


