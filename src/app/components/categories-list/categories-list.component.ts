import { CategoryService } from './../../services/category.service';
import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { Category } from 'src/app/interfaces/product';
import { ProductsService } from 'src/app/services/products.service';

@Component({
  selector: 'app-categories-list',
  templateUrl: './categories-list.component.html',
  styleUrls: ['./categories-list.component.scss']
})
export class CategoriesListComponent {
  categories: Category[] = []; 
  displayedColumns: string[] = ['Id', 'Name','actions'];

  constructor(private _CategoryService:CategoryService, private _router: Router) {}

  ngOnInit(): void {
    // Fetch the list of products from your service when the component is initialized
    this.loadProducts();
  }
  
  loadProducts() {
    this._CategoryService.getAllCategories().subscribe((data) => {
      this.categories = data;
      console.log(data);
    });
  }

  deleteProduct(id: any) {
    this._CategoryService.deleteCategory(id).subscribe(() => {
      // If the deletion is successful, remove the product from the local list.
      this.categories = this.categories.filter((product) => product.id !== id);
      this.loadProducts();
    }, (error) => {
      // Handle the error, e.g., display an error message.
      console.error('Error deleting product:', error);
    });
  }
}

