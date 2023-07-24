package souzxvini.com.ToDoAPI.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import souzxvini.com.ToDoAPI.dto.CategoryRequest;
import souzxvini.com.ToDoAPI.dto.CategoryResponse;
import souzxvini.com.ToDoAPI.dto.ProgressResponse;
import souzxvini.com.ToDoAPI.service.CategoryService;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping(value = "/category")
@CrossOrigin(origins = "http://localhost:4200")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

   @PostMapping(value = "/register", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity addCategory(@RequestBody CategoryRequest categoryRequest, Principal principal) throws Exception {
       categoryService.addCategory(categoryRequest, principal);

       return new ResponseEntity<>(HttpStatus.CREATED);
   }
    @GetMapping(value = "/{id}")
    public CategoryResponse getCategory(@PathVariable Integer id, Principal principal) throws Exception {
        return categoryService.getCategory(id, principal);
    }

    @GetMapping(value = "/all")
    public List<CategoryResponse> getCategories(Principal principal) throws Exception {
        return categoryService.getCategories(principal);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity deleteCategory(@PathVariable Integer id, Principal principal) throws Exception {
        categoryService.deleteCategory(id, principal);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping(value = "/progress")
    public ProgressResponse getProgress(Principal principal) throws Exception {
        return categoryService.getProgress(principal);
    }

}
