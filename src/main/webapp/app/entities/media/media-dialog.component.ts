import {Component, OnInit, OnDestroy} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {Response} from '@angular/http';

import {NgbActiveModal, NgbModalRef} from '@ng-bootstrap/ng-bootstrap';
import {EventManager, AlertService, JhiLanguageService, DataUtils} from 'ng-jhipster';

import {Media} from './media.model';
import {MediaPopupService} from './media-popup.service';
import {MediaService} from './media.service';
import {Product, ProductService} from '../product';

@Component({
    selector: 'jhi-media-dialog',
    templateUrl: './media-dialog.component.html'
})
export class MediaDialogComponent implements OnInit {

    media: Media;
    authorities: any[];
    isSaving: boolean;

    products: Product[];

    constructor(public activeModal: NgbActiveModal,
                private jhiLanguageService: JhiLanguageService,
                private dataUtils: DataUtils,
                private alertService: AlertService,
                private mediaService: MediaService,
                private productService: ProductService,
                private eventManager: EventManager) {
        this.jhiLanguageService.setLocations(['media']);
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.productService.query().subscribe(
            (res: Response) => {
                this.products = res.json();
            }, (res: Response) => this.onError(res.json()));
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    setFileData($event, media, field, isImage) {
        if ($event.target.files && $event.target.files[0]) {
            let $file = $event.target.files[0];
            if (isImage && !/^image\//.test($file.type)) {
                return;
            }
            this.dataUtils.toBase64($file, (base64Data) => {
                media[field] = base64Data;
                media[`${field}ContentType`] = $file.type;
            });
        }
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.media.id !== undefined) {
            this.mediaService.update(this.media)
                .subscribe((res: Media) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.mediaService.create(this.media)
                .subscribe((res: Media) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess(result: Media) {
        this.eventManager.broadcast({name: 'mediaListModification', content: 'OK'});
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

    trackProductById(index: number, item: Product) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-media-popup',
    template: ''
})
export class MediaPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(private route: ActivatedRoute,
                private mediaPopupService: MediaPopupService) {
    }

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if (params['productId']) {
                this.modalRef = this.mediaPopupService.createMediaForProduct(MediaDialogComponent, params['productId']);
            }

            if (params['id']) {
                this.modalRef = this.mediaPopupService
                    .open(MediaDialogComponent, params['id']);
            } else {
                this.modalRef = this.mediaPopupService
                    .open(MediaDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
