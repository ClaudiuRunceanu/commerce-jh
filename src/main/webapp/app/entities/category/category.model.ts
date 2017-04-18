import { ClassificationClassAttribute } from '../classification-class-attribute';
import { Product } from '../product';
export class Category {
    constructor(
        public id?: number,
        public name?: string,
        public description?: string,
        public classificationAttribute?: ClassificationClassAttribute,
        public parent?: Category,
        public products?: Product,
    ) {
    }
}
