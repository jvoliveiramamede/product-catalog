export class ProductModel {

    id: string;
    name: string;
    title: string;
    price: number;

    constructor(
        id: string,
        name: string,
        title: string,
        price: number
    ) {
        this.id = id;
        this.name = name;
        this.title = title;
        this.price = price;
    }

}