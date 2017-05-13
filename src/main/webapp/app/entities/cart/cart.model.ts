import { User } from '../../shared';
import { OrderEntry } from '../order-entry';
export class Cart {
    constructor(
        public id?: number,
        public code?: string,
        public user?: User,
        public entries?: OrderEntry,
    ) {
    }
}
