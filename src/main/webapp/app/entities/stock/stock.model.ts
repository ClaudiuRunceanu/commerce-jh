import { Warehouse } from '../warehouse';
import { Product } from '../product';
export class Stock {
    constructor(
        public id?: number,
        public available?: number,
        public preOrder?: number,
        public reserved?: number,
        public expireDate?: any,
        public creationDate?: any,
        public warehouse?: Warehouse,
        public product?: Product,
        public productId?: number,
    ) {
    }
}
