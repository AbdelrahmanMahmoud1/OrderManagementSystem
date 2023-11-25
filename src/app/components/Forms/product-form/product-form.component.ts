import { Category } from './../../../interfaces/product';
import { Component } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ProductsService } from 'src/app/services/products.service';

@Component({
  selector: 'product-form',
  templateUrl: './product-form.component.html',
  styleUrls: ['./product-form.component.scss']
})
export class ProductFormComponent {
  productForm: FormGroup;
  productId: string;
  isEditing: boolean;
  

  constructor(private fb: FormBuilder, private productService: ProductsService, private route: ActivatedRoute, private router: Router) {}

  ngOnInit() {
    this.productId = this.route.snapshot.paramMap.get('id');
    this.isEditing = !!this.productId;


    this.productForm = this.fb.group({
      name: new FormControl(null,[Validators.required]),
      // imageUrl: new FormControl(null),
      price: new FormControl(null,[Validators.required]),
      description:new FormControl(null,[Validators.required]),
      quantity:new FormControl(null,[Validators.required]),
      category:this.fb.group({
        id: new FormControl(null,Validators.required),
        name: new FormControl(null,Validators.required)
      })
    });

    if (this.isEditing) {
      this.initializeFormForEdit(this.productId);
    }
  }

  initializeFormForEdit(productId: string) {
    // Fetch product details by ID and set the form values
    this.productService.getProductById(productId).subscribe((product) => {
      if (product && product.length > 0) {
        const productData = product[0];
        this.productForm.setValue({
          name: productData.name,
          // imageUrl: productData.imageUrl,
          price: productData.price,
          description: productData.description,
          category: productData.category,
          quantity: productData.quantity
        });
      }
    });
  }

  submitProduct() {
   

    if (this.isEditing) {
      // Handle editing logic here
      this.productService.editProduct(this.productId, this.productForm.value).subscribe((response) => {
        this.router.navigate(['/home']); // Navigate to the product list page after editing
        console.log(response);
      });
    } else {
      // Handle creating new product logic here
      this.productService.addProduct(this.productForm.value).subscribe({
        next:(response)=>{
          console.log(response.message)
          this.router.navigate(['/home']);
        }  
      });
    }
  }
}
