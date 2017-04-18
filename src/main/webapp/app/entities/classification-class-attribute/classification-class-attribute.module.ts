import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CommerceSharedModule } from '../../shared';

import {
    ClassificationClassAttributeService,
    ClassificationClassAttributePopupService,
    ClassificationClassAttributeComponent,
    ClassificationClassAttributeDetailComponent,
    ClassificationClassAttributeDialogComponent,
    ClassificationClassAttributePopupComponent,
    ClassificationClassAttributeDeletePopupComponent,
    ClassificationClassAttributeDeleteDialogComponent,
    classificationClassAttributeRoute,
    classificationClassAttributePopupRoute,
} from './';

let ENTITY_STATES = [
    ...classificationClassAttributeRoute,
    ...classificationClassAttributePopupRoute,
];

@NgModule({
    imports: [
        CommerceSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ClassificationClassAttributeComponent,
        ClassificationClassAttributeDetailComponent,
        ClassificationClassAttributeDialogComponent,
        ClassificationClassAttributeDeleteDialogComponent,
        ClassificationClassAttributePopupComponent,
        ClassificationClassAttributeDeletePopupComponent,
    ],
    entryComponents: [
        ClassificationClassAttributeComponent,
        ClassificationClassAttributeDialogComponent,
        ClassificationClassAttributePopupComponent,
        ClassificationClassAttributeDeleteDialogComponent,
        ClassificationClassAttributeDeletePopupComponent,
    ],
    providers: [
        ClassificationClassAttributeService,
        ClassificationClassAttributePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CommerceClassificationClassAttributeModule {}
