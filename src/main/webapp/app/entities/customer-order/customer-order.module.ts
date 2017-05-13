import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CommerceSharedModule } from '../../shared';
import { CommerceAdminModule } from '../../admin/admin.module';

import {
    CustomerOrderService,
    CustomerOrderPopupService,
    CustomerOrderComponent,
    CustomerOrderDetailComponent,
    CustomerOrderDialogComponent,
    CustomerOrderPopupComponent,
    CustomerOrderDeletePopupComponent,
    CustomerOrderDeleteDialogComponent,
    customerOrderRoute,
    customerOrderPopupRoute,
} from './';

let ENTITY_STATES = [
    ...customerOrderRoute,
    ...customerOrderPopupRoute,
];

@NgModule({
    imports: [
        CommerceSharedModule,
        CommerceAdminModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        CustomerOrderComponent,
        CustomerOrderDetailComponent,
        CustomerOrderDialogComponent,
        CustomerOrderDeleteDialogComponent,
        CustomerOrderPopupComponent,
        CustomerOrderDeletePopupComponent,
    ],
    entryComponents: [
        CustomerOrderComponent,
        CustomerOrderDialogComponent,
        CustomerOrderPopupComponent,
        CustomerOrderDeleteDialogComponent,
        CustomerOrderDeletePopupComponent,
    ],
    providers: [
        CustomerOrderService,
        CustomerOrderPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CommerceCustomerOrderModule {}
