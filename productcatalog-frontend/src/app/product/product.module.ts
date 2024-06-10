import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ProductListComponent } from './product-list/product-list.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { NgbActiveModal, NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { ProductCreateComponent } from './product-create/product-create.component';
import { ProductComponent } from './product.component';
import { CoreModule } from '../core/core.module';
import { ProductUpdateComponent } from './product-update/product-update.component'; // Verifique se CoreModule está importado

@NgModule({
  declarations: [
    ProductListComponent,
    ProductCreateComponent,
    ProductComponent,
    ProductUpdateComponent,
  ],
  exports: [ProductListComponent, ProductComponent, ProductCreateComponent],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    HttpClientModule,
    NgbModule,
    CoreModule,
    FormsModule
  ],
  providers: [NgbActiveModal]
})
export class ProductModule {}
