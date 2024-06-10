import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { ProductModel } from '../models/product.mode';

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  constructor(
    private http: HttpClient
  ) { }

  public list(): Observable<{
    products: Array<ProductModel>,
    total: number,
    links: Array<any>
  }> {
    return this.http.get<any>(`
      ${environment.url}:${environment.port}/${environment.path}/product/list  
    `);
  }
}
