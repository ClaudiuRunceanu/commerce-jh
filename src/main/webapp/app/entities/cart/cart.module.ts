import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CommerceSharedModule } from '../../shared';
import { CommerceAdminModule } from '../../admin/admin.module';

import {
    CartService,
    CartPopupService,
    CartComponent,
    CartDetailComponent,
    CartDialogComponent,
    CartPopupComponent,
    CartDeletePopupComponent,
    CartDeleteDialogComponent,
    cartRoute,
    cartPopupRoute,
} from './';

let ENTITY_STATES = [
    ...cartRoute,
    ...cartPopupRoute,
];

@NgModule({
    imports: [
        CommerceSharedModule,
        CommerceAdminModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        CartComponent,
        CartDetailComponent,
        CartDialogComponent,
        CartDeleteDialogComponent,
        CartPopupComponent,
        CartDeletePopupComponent,
    ],
    entryComponents: [
        CartComponent,
        CartDialogComponent,
        CartPopupComponent,
        CartDeleteDialogComponent,
        CartDeletePopupComponent,
    ],
    providers: [
        CartService,
        CartPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CommerceCartModule {}
