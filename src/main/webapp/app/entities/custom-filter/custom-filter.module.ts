import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CommerceSharedModule } from '../../shared';

import {
    CustomFilterService,
    CustomFilterPopupService,
    CustomFilterComponent,
    CustomFilterDetailComponent,
    CustomFilterDialogComponent,
    CustomFilterPopupComponent,
    CustomFilterDeletePopupComponent,
    CustomFilterDeleteDialogComponent,
    customFilterRoute,
    customFilterPopupRoute,
} from './';

let ENTITY_STATES = [
    ...customFilterRoute,
    ...customFilterPopupRoute,
];

@NgModule({
    imports: [
        CommerceSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        CustomFilterComponent,
        CustomFilterDetailComponent,
        CustomFilterDialogComponent,
        CustomFilterDeleteDialogComponent,
        CustomFilterPopupComponent,
        CustomFilterDeletePopupComponent,
    ],
    entryComponents: [
        CustomFilterComponent,
        CustomFilterDialogComponent,
        CustomFilterPopupComponent,
        CustomFilterDeleteDialogComponent,
        CustomFilterDeletePopupComponent,
    ],
    providers: [
        CustomFilterService,
        CustomFilterPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CommerceCustomFilterModule {}
