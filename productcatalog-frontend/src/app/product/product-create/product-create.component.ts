import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { ProductCreateModel } from 'src/app/models/product-create.mode';
import { ProductService } from 'src/app/services/product.service';

@Component({
  selector: 'app-product-create',
  templateUrl: './product-create.component.html',
  styleUrls: ['./product-create.component.scss']
})
export class ProductCreateComponent implements OnInit {

  formCreate: FormGroup = new FormGroup({});

  @Output("creatingProduct") createProduct: EventEmitter<boolean> = new EventEmitter();

  constructor(
    private formBuilder: FormBuilder,
    private service: ProductService
  ) {}

  ngOnInit(): void {
    this.formCreate = this.formBuilder.group({
      name: [''],
      title: [''],
      price: [''],
      description: ['']
    })
  }

  public submit(form: FormGroup): void {
    let product = new ProductCreateModel(
      form.get('name')?.value,
      form.get('title')?.value,
      form.get('price')?.value as number,
      form.get('name')?.value
    )
    this.service.create(product).subscribe(res => {
      console.log(res);
      this.createProduct.emit(true);
    })
  }

}
