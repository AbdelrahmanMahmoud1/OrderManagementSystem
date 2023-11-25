import { CartItem } from 'src/app/interfaces/cartItem';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { OrderLineItemData, OrderLineItemDto, OrderRequest } from '../interfaces/product';

@Injectable({
  providedIn: 'root'
})
export class OrderService {

  constructor(private _HttpClient:HttpClient) { }

  placeOrder(orderLineItemsData: OrderLineItemData[]):Observable<any>{
    const orderLineItemsDtoList: OrderLineItemDto[] = orderLineItemsData.map(data => ({
      id: data.product.id,
      name: data.product.name,
      price: data.product.price,
      quantity: data.product.quantity,
      // map other properties if needed
    }));
    const orderRequest: OrderRequest = {
      orderLineItemsDtoList: orderLineItemsDtoList,
    };
    return this._HttpClient.post("http://localhost:8081/api/order",orderRequest);
  }
}
