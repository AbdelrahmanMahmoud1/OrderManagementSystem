import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { CartItem } from 'src/app/interfaces/cartItem';
import { Product } from 'src/app/interfaces/product';
import { ShoppingCartService } from 'src/app/services/shopping-cart.service';

@Component({
  selector: 'app-shopping-cart',
  templateUrl: './shopping-cart.component.html',
  styleUrls: ['./shopping-cart.component.scss']
})
export class ShoppingCartComponent implements OnInit {
  cartItems: CartItem[] = [];

  constructor(private shoppingCartService: ShoppingCartService,
    private router: Router) {
    this.cartItems = shoppingCartService.getCartItems();
  }
  ngOnInit(): void {
    this.shoppingCartService.getCartItems();
  }

  removeFromCart(product: Product) {
    this.shoppingCartService.removeFromCart(product);
    this.cartItems = this.shoppingCartService.getCartItems();
  }

  increaseQuantity(item: CartItem) {
   item.product.quantity+=1;
  }

  decreaseQuantity(item: CartItem) {
      if (item.product.quantity > 1) {
          item.product.quantity -= 1;
          console.log(item)
      }
  }


  calculateTotal(): number {
    return this.cartItems.reduce((total, item) => total + item.product.price*item.product.quantity, 0);
  }

  clearCart(){
    this.shoppingCartService.clearCart();
    return this.cartItems = this.shoppingCartService.getCartItems();
  }
  goToCheckout(total:number){
    this.router.navigate(['checkout']);
  }
}
