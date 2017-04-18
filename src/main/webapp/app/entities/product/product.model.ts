import { Price } from '../price';
import { Stock } from '../stock';
import { Media } from '../media';
import { Catalog } from '../catalog';
import { Category } from '../category';
import {Currency} from "../currency/currency.model";
export class Product {
    constructor(
        public id?: number,
        public code?: string,
        public name?: string,
        public description?: string,
        public price?: Price,
        public stocks?: Stock,
        public media?: Media,
        public catalog?: Catalog,
        public categories?: Category,
        public priceValue?: number,
        public currency?: Currency,
        public currencySymbol?: string,
    ) {
    }
}
