import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CommerceSharedModule } from '../../shared';

import {
    CatalogService,
    CatalogPopupService,
    CatalogComponent,
    CatalogDetailComponent,
    CatalogDialogComponent,
    CatalogPopupComponent,
    CatalogDeletePopupComponent,
    CatalogDeleteDialogComponent,
    catalogRoute,
    catalogPopupRoute,
} from './';

let ENTITY_STATES = [
    ...catalogRoute,
    ...catalogPopupRoute,
];

@NgModule({
    imports: [
        CommerceSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        CatalogComponent,
        CatalogDetailComponent,
        CatalogDialogComponent,
        CatalogDeleteDialogComponent,
        CatalogPopupComponent,
        CatalogDeletePopupComponent,
    ],
    entryComponents: [
        CatalogComponent,
        CatalogDialogComponent,
        CatalogPopupComponent,
        CatalogDeleteDialogComponent,
        CatalogDeletePopupComponent,
    ],
    providers: [
        CatalogService,
        CatalogPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CommerceCatalogModule {}
