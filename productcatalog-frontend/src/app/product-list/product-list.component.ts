import { Component, OnInit } from '@angular/core';
import { ProductService } from '../services/product.service';
import { ProductModel } from '../models/product.mode';

@Component({
  selector: 'app-product-list',
  templateUrl: './product-list.component.html',
  styleUrls: ['./product-list.component.scss']
})
export class ProductListComponent implements OnInit {

  products: Array<ProductModel> = [];

  constructor(
    private service: ProductService
  ) {}

  ngOnInit(): void {
    this.service.list().subscribe(res => {
      this.products = res.products;
      console.log(this.products)
    })

  }

}
