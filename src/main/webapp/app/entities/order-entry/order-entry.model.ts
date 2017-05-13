import { Product } from '../product';
import { Cart } from '../cart';
import { CustomerOrder } from '../customer-order';
export class OrderEntry {
    constructor(
        public id?: number,
        public quantity?: number,
        public value?: number,
        public product?: Product,
        public cart?: Cart,
        public customerOrder?: CustomerOrder,
    ) {
    }
}
