import { Component, OnInit, OnDestroy } from '@angular/core';
import { Response } from '@angular/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager, ParseLinks, PaginationUtil, JhiLanguageService, AlertService, DataUtils } from 'ng-jhipster';

import { Media } from './media.model';
import { MediaService } from './media.service';
import { ITEMS_PER_PAGE, Principal } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

import {DomSanitizer} from '@angular/platform-browser';

@Component({
    selector: 'jhi-media',
    templateUrl: './media.component.html',
    styleUrls:['./media.component.css']
})
export class MediaComponent implements OnInit, OnDestroy {
media: Media[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private mediaService: MediaService,
        private alertService: AlertService,
        private dataUtils: DataUtils,
        private eventManager: EventManager,
        private principal: Principal,
        public _DomSanitizer: DomSanitizer
    ) {
        this.jhiLanguageService.setLocations(['media']);
    }

    loadAll() {
        this.mediaService.query().subscribe(
            (res: Response) => {
                console.log("media response:")
                console.log(res.json())
                this.media = res.json();
            },
            (res: Response) => this.onError(res.json())
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInMedia();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId (index: number, item: Media) {
        return item.id;
    }



    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    registerChangeInMedia() {
        this.eventSubscriber = this.eventManager.subscribe('mediaListModification', (response) => this.loadAll());
    }


    private onError (error) {
        this.alertService.error(error.message, null, null);
    }
}
