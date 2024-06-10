import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { NavbarComponent } from './navbar/navbar.component';
import { NgbModal, NgbModule } from '@ng-bootstrap/ng-bootstrap';

@NgModule({
  declarations: [
    NavbarComponent
  ],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    FormsModule,
    HttpClientModule,
    NgbModule,
  ],
  exports: [
    NavbarComponent
  ]
})
export class CoreModule { }
