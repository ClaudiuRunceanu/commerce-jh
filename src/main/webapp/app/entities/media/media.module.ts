import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CommerceSharedModule } from '../../shared';

import {
    MediaService,
    MediaPopupService,
    MediaComponent,
    MediaDetailComponent,
    MediaDialogComponent,
    MediaPopupComponent,
    MediaDeletePopupComponent,
    MediaDeleteDialogComponent,
    mediaRoute,
    mediaPopupRoute,
} from './';

let ENTITY_STATES = [
    ...mediaRoute,
    ...mediaPopupRoute,
];

@NgModule({
    imports: [
        CommerceSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        MediaComponent,
        MediaDetailComponent,
        MediaDialogComponent,
        MediaDeleteDialogComponent,
        MediaPopupComponent,
        MediaDeletePopupComponent,
    ],
    entryComponents: [
        MediaComponent,
        MediaDialogComponent,
        MediaPopupComponent,
        MediaDeleteDialogComponent,
        MediaDeletePopupComponent,
    ],
    providers: [
        MediaService,
        MediaPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CommerceMediaModule {}
