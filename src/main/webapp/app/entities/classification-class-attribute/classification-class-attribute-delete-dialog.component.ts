import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, JhiLanguageService } from 'ng-jhipster';

import { ClassificationClassAttribute } from './classification-class-attribute.model';
import { ClassificationClassAttributePopupService } from './classification-class-attribute-popup.service';
import { ClassificationClassAttributeService } from './classification-class-attribute.service';

@Component({
    selector: 'jhi-classification-class-attribute-delete-dialog',
    templateUrl: './classification-class-attribute-delete-dialog.component.html'
})
export class ClassificationClassAttributeDeleteDialogComponent {

    classificationClassAttribute: ClassificationClassAttribute;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private classificationClassAttributeService: ClassificationClassAttributeService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['classificationClassAttribute', 'classificationAttributeType']);
    }

    clear () {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete (id: number) {
        this.classificationClassAttributeService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'classificationClassAttributeListModification',
                content: 'Deleted an classificationClassAttribute'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-classification-class-attribute-delete-popup',
    template: ''
})
export class ClassificationClassAttributeDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private classificationClassAttributePopupService: ClassificationClassAttributePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.modalRef = this.classificationClassAttributePopupService
                .open(ClassificationClassAttributeDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
