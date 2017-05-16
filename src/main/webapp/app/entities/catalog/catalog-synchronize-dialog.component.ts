import {Component, OnInit, OnDestroy} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {Response} from '@angular/http';

import {NgbActiveModal, NgbModalRef} from '@ng-bootstrap/ng-bootstrap';
import {EventManager, AlertService, JhiLanguageService} from 'ng-jhipster';

import {Catalog} from './catalog.model';
import {CatalogPopupService} from './catalog-popup.service';
import {CatalogService} from './catalog.service';

@Component({
    selector: 'jhi-catalog-synchronize-dialog',
    templateUrl: './catalog-synchronize-dialog.component.html'
})
export class CatalogSynchronizeDialogComponent implements OnInit {

    catalog: Catalog;
    sourceCatalog: Catalog;
    catalogs: Catalog[];
    authorities: any[];
    isSaving: boolean;

    constructor(public activeModal: NgbActiveModal,
                private jhiLanguageService: JhiLanguageService,
                private alertService: AlertService,
                private catalogService: CatalogService,
                private eventManager: EventManager) {
        this.jhiLanguageService.setLocations(['catalog', 'catalogVersion']);
    }

    loadAll() {
        this.catalogService.query().subscribe(
            (res: Response) => {
                this.catalogs = res.json();
            },
            (res: Response) => this.onError(res.json())
        );
    }

    ngOnInit() {
        this.loadAll();
        this.sourceCatalog = new Catalog();
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        this.catalogService.synchronize(this.catalog.id, this.sourceCatalog).subscribe((res: Catalog) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));

        // if (this.catalog.id !== undefined) {
        //     this.catalogService.update(this.catalog)
        //         .subscribe((res: Catalog) =>
        //             this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        // } else {
        //     this.catalogService.create(this.catalog)
        //         .subscribe((res: Catalog) =>
        //             this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        // }
    }

    trackCatalogById (index: number, item: Catalog) {
        return item.id;
    }

    private onSaveSuccess(result: Catalog) {
        this.eventManager.broadcast({name: 'catalogListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError(error) {
        try {
            error.json();
        } catch (exception) {
            error.message = error.text();
        }
        this.isSaving = false;
        this.onError(error);
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}

@Component({
    selector: 'jhi-catalog-popup',
    template: ''
})
export class CatalogSynchronizePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(private route: ActivatedRoute,
                private catalogPopupService: CatalogPopupService) {
    }

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if (params['id']) {
                this.modalRef = this.catalogPopupService
                    .open(CatalogSynchronizeDialogComponent, params['id']);
            } else {
                this.modalRef = this.catalogPopupService
                    .open(CatalogSynchronizeDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
