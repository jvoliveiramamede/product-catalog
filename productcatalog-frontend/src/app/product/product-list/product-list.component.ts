import { Component, OnInit } from '@angular/core';
import { ProductService } from '../../services/product.service';
import { ProductModel } from '../../models/product.mode';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { ProductUpdateComponent } from '../product-update/product-update.component';
import { PdfGeneratorService } from 'src/app/services/pdf-generator.service';

@Component({
  selector: 'app-product-list',
  templateUrl: './product-list.component.html',
  styleUrls: ['./product-list.component.scss'],
})
export class ProductListComponent implements OnInit {
  products: Array<ProductModel> = [];
  page: number = 1;
  pageSize: number = 10;
  collectionSize: number = 0;
  modalRef: NgbModalRef | null = null;
  selectedProductId: string | null = null;

  constructor(
    private service: ProductService,
    private modalService: NgbModal,
    private pdfService: PdfGeneratorService
  ) {}

  ngOnInit(): void {
    this.loadProducts();

    this.service.updateCompleted$.subscribe(() => {
      this.loadProducts();
    })
  }

  loadProducts() {
    this.service.list(this.page - 1, this.pageSize).subscribe((res) => {
      this.products = res.products;
      this.collectionSize = res.total;
    });
  }

  onPageChange() {
    this.loadProducts();
  }

  openConfirmationModal(content: any, productId: string) {
    this.selectedProductId = productId;
    this.modalRef = this.modalService.open(content, { centered: true });
  }

  confirmDelete() {
    if (this.selectedProductId) {
      this.service.deleteById(this.selectedProductId).subscribe(
        () => {
          console.log('Product deleted successfully');
          this.modalRef?.close();
          this.loadProducts(); // Reload products after deletion
        },
        (error) => {
          console.error('Failed to delete product', error);
        }
      );
    }
  }

  openUpdateModal(product: ProductModel) {
    const modalRef = this.modalService.open(ProductUpdateComponent);
    modalRef.componentInstance.product = product;
    modalRef.componentInstance.productUpdated.subscribe(() => {
      this.loadProducts();
    });
  }

  getCreatingProduct(event: boolean) {
    if(event) {
      this.loadProducts();
    }
  }

  getUpdatingProduct(event: boolean) {
    if(event) {
      this.loadProducts();
    }
  }

  generatePdf(product: ProductModel): void {
    this.pdfService.generateProductPdf(product);
  }


}
