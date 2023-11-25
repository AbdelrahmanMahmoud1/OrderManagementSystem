import { Component } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Category } from 'src/app/interfaces/product';
import { CategoryService } from 'src/app/services/category.service';

@Component({
  selector: 'app-category-form',
  templateUrl: './category-form.component.html',
  styleUrls: ['./category-form.component.scss']
})
export class CategoryFormComponent {
  categoryForm: FormGroup;
  productId: string;
  
  constructor(private fb: FormBuilder, private _CategoryService: CategoryService, private route: ActivatedRoute, private router: Router) {}

  ngOnInit() {
    this.productId = this.route.snapshot.paramMap.get('id');
  


    this.categoryForm = this.fb.group({
      name: new FormControl(null,[Validators.required]),
    });

   
  }

 

  submitProduct() {
      // Handle creating new product logic here
      this._CategoryService.addCategory(this.categoryForm.value).subscribe({
        next:(response)=>{
          console.log(response.message)
          this.router.navigate(['/home']);
        }  
      });
    }
  }

