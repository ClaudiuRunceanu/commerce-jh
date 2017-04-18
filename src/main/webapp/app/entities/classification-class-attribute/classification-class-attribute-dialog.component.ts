import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { ClassificationClassAttribute } from './classification-class-attribute.model';
import { ClassificationClassAttributePopupService } from './classification-class-attribute-popup.service';
import { ClassificationClassAttributeService } from './classification-class-attribute.service';
import { Category, CategoryService } from '../category';

@Component({
    selector: 'jhi-classification-class-attribute-dialog',
    templateUrl: './classification-class-attribute-dialog.component.html'
})
export class ClassificationClassAttributeDialogComponent implements OnInit {

    classificationClassAttribute: ClassificationClassAttribute;
    authorities: any[];
    isSaving: boolean;

    categories: Category[];
    constructor(
        public activeModal: NgbActiveModal,
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private classificationClassAttributeService: ClassificationClassAttributeService,
        private categoryService: CategoryService,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['classificationClassAttribute', 'classificationAttributeType']);
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.categoryService.query().subscribe(
            (res: Response) => { this.categories = res.json(); }, (res: Response) => this.onError(res.json()));
    }
    clear () {
        this.activeModal.dismiss('cancel');
    }

    save () {
        this.isSaving = true;
        if (this.classificationClassAttribute.id !== undefined) {
            this.classificationClassAttributeService.update(this.classificationClassAttribute)
                .subscribe((res: ClassificationClassAttribute) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.classificationClassAttributeService.create(this.classificationClassAttribute)
                .subscribe((res: ClassificationClassAttribute) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess (result: ClassificationClassAttribute) {
        this.eventManager.broadcast({ name: 'classificationClassAttributeListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError (error) {
        try {
            error.json();
        } catch (exception) {
            error.message = error.text();
        }
        this.isSaving = false;
        this.onError(error);
    }

    private onError (error) {
        this.alertService.error(error.message, null, null);
    }

    trackCategoryById(index: number, item: Category) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-classification-class-attribute-popup',
    template: ''
})
export class ClassificationClassAttributePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private classificationClassAttributePopupService: ClassificationClassAttributePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if ( params['id'] ) {
                this.modalRef = this.classificationClassAttributePopupService
                    .open(ClassificationClassAttributeDialogComponent, params['id']);
            } else {
                this.modalRef = this.classificationClassAttributePopupService
                    .open(ClassificationClassAttributeDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
