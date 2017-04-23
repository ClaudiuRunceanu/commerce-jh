import { Product } from '../product';
export class Media {
    constructor(
        public id?: number,
        public code?: string,
        public title?: string,
        public image?: any,
        public imageContentType?: string,
        public product?: Product,
        public productId?: number,
    ) {
    }
}
