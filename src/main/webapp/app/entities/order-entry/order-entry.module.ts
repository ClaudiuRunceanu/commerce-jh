import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CommerceSharedModule } from '../../shared';

import {
    OrderEntryService,
    OrderEntryPopupService,
    OrderEntryComponent,
    OrderEntryDetailComponent,
    OrderEntryDialogComponent,
    OrderEntryPopupComponent,
    OrderEntryDeletePopupComponent,
    OrderEntryDeleteDialogComponent,
    orderEntryRoute,
    orderEntryPopupRoute,
} from './';

let ENTITY_STATES = [
    ...orderEntryRoute,
    ...orderEntryPopupRoute,
];

@NgModule({
    imports: [
        CommerceSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        OrderEntryComponent,
        OrderEntryDetailComponent,
        OrderEntryDialogComponent,
        OrderEntryDeleteDialogComponent,
        OrderEntryPopupComponent,
        OrderEntryDeletePopupComponent,
    ],
    entryComponents: [
        OrderEntryComponent,
        OrderEntryDialogComponent,
        OrderEntryPopupComponent,
        OrderEntryDeleteDialogComponent,
        OrderEntryDeletePopupComponent,
    ],
    providers: [
        OrderEntryService,
        OrderEntryPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CommerceOrderEntryModule {}
