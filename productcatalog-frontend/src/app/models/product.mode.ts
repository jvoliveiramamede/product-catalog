export class ProductModel {

    id: string;
    name: string;
    title: string;
    price: number;
    description: string

    constructor(
        id: string,
        name: string,
        title: string,
        price: number,
        description: string
    ) {
        this.id = id;
        this.name = name;
        this.title = title;
        this.price = price;
        this.description = description;
    }

}