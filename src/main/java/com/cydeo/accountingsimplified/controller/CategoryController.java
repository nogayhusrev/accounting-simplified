package com.cydeo.accountingsimplified.controller;

import com.cydeo.accountingsimplified.dto.CategoryDto;
import com.cydeo.accountingsimplified.service.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/list")
    public String navigateToCategoryList(Model model) throws Exception {
        model.addAttribute("categories", categoryService.getAllCategories());
        return "/category/category-list";
    }

    @GetMapping("/create")
    public String navigateToCategoryCreate(Model model) {
        model.addAttribute("newCategory", new CategoryDto());
        return "/category/category-create";
    }

    @PostMapping("/create")
    public String createNewCategory(CategoryDto categoryDto) throws Exception {
        categoryService.create(categoryDto);
        return "redirect:/categories/list";
    }

    @PostMapping(value = "/actions/{categoryId}", params = {"action=update"})
    public String navigateToCategoryUpdate(@PathVariable("categoryId") Long categoryId){
        return "redirect:/categories/update/" + categoryId;
    }

    @GetMapping("/update/{categoryId}")
    public String navigateToCategoryUpate(@PathVariable("categoryId") Long categoryId, Model model) {
        model.addAttribute("category", categoryService.findCategoryById(categoryId));
        return "/category/category-update";
    }

    @PostMapping("/update/{categoryId}")
    public String updateCategory(@PathVariable("categoryId") Long categoryId, CategoryDto categoryDto) {
        categoryService.update(categoryId, categoryDto);
        return "redirect:/categories/list";
    }

    @PostMapping(value = "/actions/{categoryId}", params = {"action=delete"})
    public String activateCategory(@PathVariable("categoryId") Long categoryId){
        categoryService.delete(categoryId);
        return "redirect:/categories/list";
    }

}