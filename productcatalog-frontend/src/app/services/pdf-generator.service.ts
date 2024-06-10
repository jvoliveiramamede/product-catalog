// src/app/services/pdf-generator.service.ts

import { Injectable } from '@angular/core';
import * as pdfMake from 'pdfmake/build/pdfmake';
import * as pdfFonts from 'pdfmake/build/vfs_fonts';
import { ProductModel } from '../models/product.mode';


@Injectable({
  providedIn: 'root'
})
export class PdfGeneratorService {

  constructor() {
    (pdfMake as any).vfs = pdfFonts.pdfMake.vfs;
  }

  generateProductPdf(product: ProductModel): void {
    const documentDefinition = this.getDocumentDefinition(product);
    pdfMake.createPdf(documentDefinition).download(`product_details_${product.name}.pdf`);
  }

  private getDocumentDefinition(product: ProductModel): any {
    return {
      content: [
        { text: 'Product Details', style: 'header' },
        this.getProductDetailsTable(product)
      ],
      styles: {
        header: {
          fontSize: 22,
          bold: true,
          margin: [0, 0, 0, 10]
        },
        tableHeader: {
          bold: true,
          fontSize: 13,
          color: 'black'
        }
      }
    };
  }

  private getProductDetailsTable(product: ProductModel): any {
    return {
      table: {
        widths: ['auto', '*'],
        body: [
          [{ text: 'Field', style: 'tableHeader' }, { text: 'Details', style: 'tableHeader' }],
          ['Name', product.name],
          ['Title', product.title],
          ['Price', { text: product.price, alignment: 'right' }],
          ['Description', product.description || 'No description available.']
        ]
      }
    };
  }
}
