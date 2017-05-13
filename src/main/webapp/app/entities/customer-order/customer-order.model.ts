
const enum OrderStatus {
    'OPEN',
    'SUCCESS',
    'ACCEPTED',
    'CANCELED',
    'REJECTED'

};
import { OrderEntry } from '../order-entry';
import { User } from '../../shared';
export class CustomerOrder {
    constructor(
        public id?: number,
        public code?: string,
        public date?: any,
        public totalCost?: number,
        public status?: OrderStatus,
        public taxCost?: number,
        public paymentCost?: number,
        public deliveryCost?: number,
        public discountValue?: number,
        public discountPercentage?: number,
        public entries?: OrderEntry,
        public user?: User,
    ) {
    }
}
