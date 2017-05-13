import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, JhiLanguageService } from 'ng-jhipster';

import { CustomFilter } from './custom-filter.model';
import { CustomFilterPopupService } from './custom-filter-popup.service';
import { CustomFilterService } from './custom-filter.service';

@Component({
    selector: 'jhi-custom-filter-delete-dialog',
    templateUrl: './custom-filter-delete-dialog.component.html'
})
export class CustomFilterDeleteDialogComponent {

    customFilter: CustomFilter;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private customFilterService: CustomFilterService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['customFilter']);
    }

    clear () {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete (id: number) {
        this.customFilterService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'customFilterListModification',
                content: 'Deleted an customFilter'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-custom-filter-delete-popup',
    template: ''
})
export class CustomFilterDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private customFilterPopupService: CustomFilterPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.modalRef = this.customFilterPopupService
                .open(CustomFilterDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
