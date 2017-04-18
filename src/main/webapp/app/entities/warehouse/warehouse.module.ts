import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CommerceSharedModule } from '../../shared';

import {
    WarehouseService,
    WarehousePopupService,
    WarehouseComponent,
    WarehouseDetailComponent,
    WarehouseDialogComponent,
    WarehousePopupComponent,
    WarehouseDeletePopupComponent,
    WarehouseDeleteDialogComponent,
    warehouseRoute,
    warehousePopupRoute,
} from './';

let ENTITY_STATES = [
    ...warehouseRoute,
    ...warehousePopupRoute,
];

@NgModule({
    imports: [
        CommerceSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        WarehouseComponent,
        WarehouseDetailComponent,
        WarehouseDialogComponent,
        WarehouseDeleteDialogComponent,
        WarehousePopupComponent,
        WarehouseDeletePopupComponent,
    ],
    entryComponents: [
        WarehouseComponent,
        WarehouseDialogComponent,
        WarehousePopupComponent,
        WarehouseDeleteDialogComponent,
        WarehouseDeletePopupComponent,
    ],
    providers: [
        WarehouseService,
        WarehousePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CommerceWarehouseModule {}
