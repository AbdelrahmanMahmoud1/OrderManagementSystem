export interface Product {
    name: string;
    description: string;
    category:Category;
    price: number;
    quantity: number;
    image: string; 
    id: string;
}
export interface Category{
    id:number;
    name:string;
}

export class OrderRequest {
    orderLineItemsDtoList: OrderLineItemDto[];
  }
  
  export class OrderLineItemDto {
    name: string;
    price: number;
    quantity: number;
  }
  export class OrderLineItemData {
    product: {
      id: number;
      name: string;
      price: number;
      category: {
        id: number;
        name: string;
      };
      description: string | null;
      quantity: number;
      image: string;
    };
  }