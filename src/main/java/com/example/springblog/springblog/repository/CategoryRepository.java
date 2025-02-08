package com.example.springblog.springblog.repository;

import com.example.springblog.springblog.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
