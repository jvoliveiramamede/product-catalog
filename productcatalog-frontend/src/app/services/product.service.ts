import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, Subject } from 'rxjs';
import { environment } from 'src/environments/environment';
import { ProductModel } from '../models/product.mode';
import { ProductCreateModel } from '../models/product-create.mode';
import { ProductUpdateModel } from '../models/product-update.model';

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  private updateSubject = new Subject<void>();
  updateCompleted$ = this.updateSubject.asObservable();

  constructor(
    private http: HttpClient
  ) { }

  public triggerUpdate(): void {
    this.updateSubject.next();
  }

  public list(page: number, pageSize: number): Observable<{
    products: Array<ProductModel>,
    total: number,
    links: Array<any>
  }> {
    const params = new HttpParams()
      .set('page', page.toString())
      .set('pageSize', pageSize.toString());

    return this.http.get<{
      products: Array<ProductModel>,
      total: number,
      links: Array<any>
    }>(`${environment.url}:${environment.port}/${environment.path}/product/list`, {
      params: params
    });
  }

  public create(product: ProductCreateModel) {
    return this.http.post<{
      products: Array<ProductModel>,
      total: number,
      links: Array<any>
    }>(`${environment.url}:${environment.port}/${environment.path}/product/create`, product);
  }

  public deleteById(id: string): Observable<boolean> {
    return this.http.delete<boolean>(`${environment.url}:${environment.port}/${environment.path}/product/delete/byId/${id}`);
  }

  public updateById(id: string, product: ProductUpdateModel): Observable<{
    product: ProductModel,
    links: Array<any>
  }> {
    return this.http.put<{
      product: ProductModel,
      links: Array<any>
    }>(`${environment.url}:${environment.port}/${environment.path}/product/update/byId/${id}`, product);
  }

  public updatePartialById(id: string, product: Partial<ProductUpdateModel>): Observable<any> {
    return this.http.patch<{
      product: ProductModel,
      links: Array<any>
    }>(`${environment.url}:${environment.port}/${environment.path}/product/updatePartial/byId/${id}`, product);
  }
}
