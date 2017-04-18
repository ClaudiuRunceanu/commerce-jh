import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CommerceSharedModule } from '../../shared';

import {
    StockService,
    StockPopupService,
    StockComponent,
    StockDetailComponent,
    StockDialogComponent,
    StockPopupComponent,
    StockDeletePopupComponent,
    StockDeleteDialogComponent,
    stockRoute,
    stockPopupRoute,
} from './';

let ENTITY_STATES = [
    ...stockRoute,
    ...stockPopupRoute,
];

@NgModule({
    imports: [
        CommerceSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        StockComponent,
        StockDetailComponent,
        StockDialogComponent,
        StockDeleteDialogComponent,
        StockPopupComponent,
        StockDeletePopupComponent,
    ],
    entryComponents: [
        StockComponent,
        StockDialogComponent,
        StockPopupComponent,
        StockDeleteDialogComponent,
        StockDeletePopupComponent,
    ],
    providers: [
        StockService,
        StockPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CommerceStockModule {}
