export class ProductCreateModel {
    name: string;
    title: string;
    price: number;
    description: string;

    constructor(
        name: string,
        title: string,
        price: number,
        description: string
    ) {
        this.name = name;
        this.title = title;
        this.price = price;
        this.description = description;
    }
}