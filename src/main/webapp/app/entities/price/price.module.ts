import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CommerceSharedModule } from '../../shared';

import {
    PriceService,
    PricePopupService,
    PriceComponent,
    PriceDetailComponent,
    PriceDialogComponent,
    PricePopupComponent,
    PriceDeletePopupComponent,
    PriceDeleteDialogComponent,
    priceRoute,
    pricePopupRoute,
} from './';

let ENTITY_STATES = [
    ...priceRoute,
    ...pricePopupRoute,
];

@NgModule({
    imports: [
        CommerceSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        PriceComponent,
        PriceDetailComponent,
        PriceDialogComponent,
        PriceDeleteDialogComponent,
        PricePopupComponent,
        PriceDeletePopupComponent,
    ],
    entryComponents: [
        PriceComponent,
        PriceDialogComponent,
        PricePopupComponent,
        PriceDeleteDialogComponent,
        PriceDeletePopupComponent,
    ],
    providers: [
        PriceService,
        PricePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CommercePriceModule {}
