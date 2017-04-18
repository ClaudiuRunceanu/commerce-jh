import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CommerceSharedModule } from '../../shared';

import {
    CurrencyService,
    CurrencyPopupService,
    CurrencyComponent,
    CurrencyDetailComponent,
    CurrencyDialogComponent,
    CurrencyPopupComponent,
    CurrencyDeletePopupComponent,
    CurrencyDeleteDialogComponent,
    currencyRoute,
    currencyPopupRoute,
} from './';

let ENTITY_STATES = [
    ...currencyRoute,
    ...currencyPopupRoute,
];

@NgModule({
    imports: [
        CommerceSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        CurrencyComponent,
        CurrencyDetailComponent,
        CurrencyDialogComponent,
        CurrencyDeleteDialogComponent,
        CurrencyPopupComponent,
        CurrencyDeletePopupComponent,
    ],
    entryComponents: [
        CurrencyComponent,
        CurrencyDialogComponent,
        CurrencyPopupComponent,
        CurrencyDeleteDialogComponent,
        CurrencyDeletePopupComponent,
    ],
    providers: [
        CurrencyService,
        CurrencyPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CommerceCurrencyModule {}
