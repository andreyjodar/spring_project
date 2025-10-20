// package com.github.andreyjodar.backend.features.category.service;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.context.MessageSource;
// import org.springframework.context.i18n.LocaleContextHolder;
// import org.springframework.data.domain.Page;
// import org.springframework.data.domain.Pageable;
// import org.springframework.data.jpa.domain.Specification;
// import org.springframework.stereotype.Service;

// import com.github.andreyjodar.backend.features.auction.repository.AuctionRepository;
// import com.github.andreyjodar.backend.features.category.mapper.CategoryMapper;
// import com.github.andreyjodar.backend.features.category.model.Category;
// import com.github.andreyjodar.backend.features.category.model.CategoryFilterRequest;
// import com.github.andreyjodar.backend.features.category.model.CategoryRequest;
// import com.github.andreyjodar.backend.features.category.repository.CategoryRepository;
// import com.github.andreyjodar.backend.features.user.model.User;
// import com.github.andreyjodar.backend.shared.errors.ForbiddenException;
// import com.github.andreyjodar.backend.shared.errors.NotFoundException;

// @Service
// public class CategoryService {
    
//     @Autowired
//     CategoryRepository categoryRepository;
//     @Autowired
//     MessageSource messageSource;
//     @Autowired
//     private CategoryMapper categoryMapper;
//     @Autowired
//     private AuctionRepository auctionRepository;

//     public Category createCategory(User authUser, CategoryRequest categoryRequest) {
//         validateOperation(authUser);
//         Category category = categoryMapper.fromDto(categoryRequest);
//         return categoryRepository.save(category);
//     }

//     public Category updateCategory(Long id, User authUser, CategoryRequest categoryRequest) {     
//         validateOperation(authUser);   
//         Category category = findById(id);
//         return categoryRepository.save(category);
//     }

//     public void deleteCategory(Long id, User authUser) {
//         validateOperation(authUser);
//         Category category = findById(id);
//         validateDelete(category);
//         categoryRepository.delete(category);
//     }

//     public Page<Category> findFiltered(CategoryFilterRequest filter, Pageable pageable) {
//         Specification<Category> spec = CategorySpecifications.buildFilter(filter);
//         return categoryRepository.findAll(spec, pageable);
//     } 

//     public Category findById(Long id) {
//         return categoryRepository.findById(id)
//             .orElseThrow(() -> new NotFoundException(messageSource.getMessage("exception.categories.notfound",
//                 new Object[] { id }, LocaleContextHolder.getLocale())));
//     }

//     private void validateOperation(User authUser) {
//         if(!authUser.isAdmin()) {
//             throw new ForbiddenException(messageSource.getMessage("exception.categories.forbidden",
//                 new Object[] { authUser.getName() }, LocaleContextHolder.getLocale()));
//         }
//     }

//     private void validateDelete(Category category) {
//         if(auctionRepository.existsByCategoryAndDeletedFalse(category)) {
//             throw new ForbiddenException(messageSource.getMessage("exception.categories.notdelete",
//                 new Object[] { category.getName() }, LocaleContextHolder.getLocale())); 
//         }
//     }
// }
