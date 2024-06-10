// src/app/product/product-update/product-update.component.ts

import { Component, Input, OnInit, Output, EventEmitter } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { ProductModel } from 'src/app/models/product.mode';
import { ProductService } from 'src/app/services/product.service';

@Component({
  selector: 'app-product-update',
  templateUrl: './product-update.component.html',
  styleUrls: ['./product-update.component.scss']
})
export class ProductUpdateComponent implements OnInit {

  @Input() product!: ProductModel;
  @Output() productUpdated = new EventEmitter<void>();
  productForm!: FormGroup;

  constructor(
    private modal: NgbActiveModal,
    private fb: FormBuilder,
    private service: ProductService
  ) {}

  ngOnInit(): void {
    this.productForm = this.fb.group({
      name: [this.product.name, Validators.required],
      title: [this.product.title, Validators.required],
      price: [this.product.price, Validators.required],
      description: [this.product.description],
    });
  }

  saveChanges() {
    if (this.productForm.valid) {
      const updatedFields: any = this.getUpdatedFields();
      if (Object.keys(updatedFields).length > 0) {
        if (this.areAllFieldsUpdated()) {
          this.service.updateById(this.product.id, updatedFields).subscribe(res => {
            console.log(res);
            this.productUpdated.emit();
            this.modal.close('save');
          });
        } else {
          this.service.updatePartialById(this.product.id, updatedFields).subscribe(res => {
            console.log(res);
            this.productUpdated.emit();
            this.modal.close('save');
          });
        }
      } else {
        this.modal.dismiss('no changes');
      }
    }
  }

  cancel() {
    this.modal.dismiss('cancel');
  }

  private getUpdatedFields(): Partial<ProductModel> {
    const updatedFields: Partial<ProductModel> = {};
    for (const key in this.productForm.controls) {
      if (this.productForm.controls.hasOwnProperty(key)) {
        const control = this.productForm.get(key);
        if (control && control.dirty) {
          updatedFields[key as keyof ProductModel] = control.value;
        }
      }
    }
    return updatedFields;
  }

  private areAllFieldsUpdated(): boolean {
    return Object.keys(this.productForm.controls).every(key => {
      const control = this.productForm.get(key);
      return control && control.dirty;
    });
  }
}
