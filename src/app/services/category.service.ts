import { Observable } from 'rxjs';
import { Category } from './../interfaces/product';
import { HttpClient } from '@angular/common/http';
import { Injectable, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class CategoryService  {

  constructor( private _HttpClient:HttpClient,private _Router:Router) { }
  


  addCategory(category:Category):Observable<any>{
    return this._HttpClient.post("http://localhost:8080/categories", category)
  }

  getAllCategories():Observable<any>{
    return this._HttpClient.get("http://localhost:8080/categories")
  }
  deleteCategory(id:any):Observable<any>{

    return this._HttpClient.delete(`http://localhost:8080/categories/${id}`)
  }
}
