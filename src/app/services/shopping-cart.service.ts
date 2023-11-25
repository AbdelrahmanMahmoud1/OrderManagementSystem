import { CartItem } from './../interfaces/cartItem';
import { Injectable } from '@angular/core';
import { Product } from '../interfaces/product';
import { MatSnackBar } from '@angular/material/snack-bar';

@Injectable({
  providedIn: 'root'
})
export class ShoppingCartService {

  constructor( private snackBar: MatSnackBar,) { }

  
  cartItems: CartItem[] = [];


  addToCart(product: Product) {
    if(product.quantity===0){
      this.snackBar.open('This product is currently unavailable', 'Close', {
        duration: 3000,
      });
    }else{
      const existingItem = this.cartItems.find((item) => item.product.id === product.id);
  
      if (existingItem) {
        // Increment the quantity if the item already exists
        existingItem.product.quantity += 1;
      } else {
        // Add a new item to the cart if it doesn't exist
        product.quantity=1;
        this.cartItems.push({ product});
      }
    }
    localStorage.setItem('cart',JSON.stringify(this.cartItems));
  }

  removeFromCart(product: Product) {
    const index = this.cartItems.findIndex((item) => item.product.id === product.id);
    if (index !== -1) {
      this.cartItems.splice(index, 1);
    }
    localStorage.removeItem('cart')
    localStorage.setItem('cart',JSON.stringify(this.cartItems))
  }

  getCartItems() {
    console.log(this.cartItems);
  //  this.cartItems=JSON.parse(localStorage.getItem('cart'));
    return this.cartItems;
  
  }

  clearCart() {
    this.cartItems = [];
    this.getCartItems();
  }
}
