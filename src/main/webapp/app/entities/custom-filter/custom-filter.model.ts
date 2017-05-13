export class CustomFilter {
    constructor(
        public id?: number,
        public name?: string,
        public value?: string,
        public displayValue?: string,
        public active?: boolean,
    ) {
        this.active = false;
    }
}
