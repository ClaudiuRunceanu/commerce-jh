import { Currency } from '../currency';
export class Price {
    constructor(
        public id?: number,
        public value?: number,
        public currency?: Currency,
    ) {
    }
}
